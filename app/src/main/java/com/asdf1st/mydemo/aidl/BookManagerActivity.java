package com.asdf1st.mydemo.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.asdf1st.mydemo.R;

import java.util.List;

public class BookManagerActivity extends Activity {
    private static final String TAG="BookManagerActivity";
    public TextView tv_text;
    public StringBuilder stringBuilder=new StringBuilder();
    private static final int MESSAGE_NEW_BOOK_ARRIVED=1;
    private static final int MESSAGE_QUERY_BOOK_LIST=2;
    private IBookManager mRemoteBookManager;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    appendTestAndUpdateUI("get new book:"+msg.obj);
                    Log.e(TAG,"receive new book:\n"+msg.obj);
                    break;
                case MESSAGE_QUERY_BOOK_LIST:
                    if (msg.obj==null || !(msg.obj instanceof List)){
                        Log.e(TAG, "wtf: error on msg.obj");
                        appendTestAndUpdateUI("wtf: error on msg.obj");
                    }else {
                        List<Book> bookList= (List<Book>) msg.obj;
                        appendTestAndUpdateUI("query book list:\n"+bookList.toString());
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };
    private ServiceConnection mConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
             mRemoteBookManager=IBookManager.Stub.asInterface(iBinder);
            try {
                List<Book> list=mRemoteBookManager.getBookList();
                Log.e(TAG, "query book list type:"+list.getClass().getCanonicalName());
                Log.e(TAG,"query book list:"+list.toString());
                mRemoteBookManager.registerListener(mOnNewBookArrivedListener);
                mHandler.obtainMessage(MESSAGE_QUERY_BOOK_LIST,list).sendToTarget();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteBookManager=null;
            Log.e(TAG,"binder died");
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener=new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED,newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        tv_text=findViewById(R.id.tv_text);
        Intent intent=new Intent(this,BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager!=null && mRemoteBookManager.asBinder().isBinderAlive()){
            Log.e(TAG,"unregister listener:"+mOnNewBookArrivedListener);
            try {
                mRemoteBookManager.unRegisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }

    private void appendTestAndUpdateUI(String str){
        stringBuilder.append(str);
        stringBuilder.append("\n\n");
        tv_text.setText(stringBuilder.toString());
    }
}
