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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asdf1st.mydemo.R;

import java.util.List;

public class BookManagerActivity extends Activity {
    private static final String TAG="BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED=1;
    private IBookManager mRemoteBookManager;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.e(TAG,"receive new book:"+msg.obj);
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
}
