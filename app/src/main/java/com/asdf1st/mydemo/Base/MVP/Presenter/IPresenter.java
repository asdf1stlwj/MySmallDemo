package com.asdf1st.mydemo.Base.MVP.Presenter;
import com.asdf1st.mydemo.Base.MVP.View.IView;
public interface IPresenter<T extends IView> {
    void attachView(T t);

    void dettachView();

    T getView();

    boolean isAttachedView();

}
