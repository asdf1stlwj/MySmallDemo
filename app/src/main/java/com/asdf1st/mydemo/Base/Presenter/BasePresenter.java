package com.asdf1st.mydemo.Base.Presenter;



import com.asdf1st.mydemo.Base.Model.INetMdoule;
import com.asdf1st.mydemo.Base.View.IView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T extends IView, F extends INetMdoule> implements IPresenter<T> {
    protected WeakReference<T> mReference;
    protected F iMdoule;

    public BasePresenter() {
        iMdoule = getiMdoule();

    }

    @Override
    public void attachView(T view) {
        mReference = new WeakReference<T>(view);
    }

    @Override
    public void dettachView() {
        if (mReference != null) {
            mReference.clear();
            mReference = null;
        }
    }

    @Override
    public T getView() {
        if (mReference != null) {
            return mReference.get();
        }
        return null;
    }

    @Override
    public boolean isAttachedView() {
        return mReference != null && mReference.get() != null;
    }

    protected abstract F getiMdoule();



    public void onDestroy(){

    }
}
