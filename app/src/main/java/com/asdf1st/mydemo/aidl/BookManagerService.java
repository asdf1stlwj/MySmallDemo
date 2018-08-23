package com.asdf1st.mydemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG="BMS";
    private AtomicBoolean mIsServiceDestoryed=new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList=new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList=new CopyOnWriteArrayList<>();
    private Binder mBinder=new IBookManager.Stub(){
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void addBook(Book book) throws RemoteException {
           mBookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (!mListenerList.contains(listener)){
                mListenerList.add(listener);
            }else {
                Log.e(TAG,"already exist!");
            }
            Log.e(TAG,"register listenerList:"+mListenerList.size());
        }

        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (mListenerList.contains(listener)){
                mListenerList.remove(listener);
                Log.e(TAG,"unregister listener success");
            }else {
                Log.e(TAG,"not found,can not unregister!");
            }
            Log.e(TAG,"register listenerList:"+mListenerList.size());
        }
    };

    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1,"Android"));
        mBookList.add(new Book(2,"Ios"));
        Log.e(TAG, "onCreate: Andoid lwj");
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException{
        mBookList.add(book);
        Log.e(TAG,"onNewBookArrived,notify listener:"+mListenerList.size());
        for (IOnNewBookArrivedListener listener:mListenerList){
            Log.e(TAG,"onNewBookArrived,notify listener:"+listener);
            listener.onNewBookArrived(book);
        }
    }

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId=mBookList.size()+1;
                Book newBook=new Book(bookId,"new book#"+bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
