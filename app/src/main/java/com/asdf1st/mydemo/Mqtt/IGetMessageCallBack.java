package com.asdf1st.mydemo.Mqtt;

public interface IGetMessageCallBack {
        public void getMessageSuccess(Object message);
        public void getMessageFail(String message);
    }