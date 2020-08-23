package com.asdf1st.mydemo.Mqtt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.asdf1st.mydemo.Base.Presenter.IPresenter;
import com.asdf1st.mydemo.R;

public class MyMqttActivity extends BaseMqttActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_mqtt;
    }

    @Override
    protected void initClass() {
        super.initClass();
        topic_subscribe="server.F0FE6BCD41FC";
        topic_publish="device.F0FE6BCD41FC";
        clientId="asdf"+System.currentTimeMillis();
        type=BaseMqttActivity.TYPE_DETAIL_FRESHAIR;
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        startMqtt();
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onGetOnlineState(boolean isOnline) {

    }

    @Override
    protected void getMessageSuccess(Object object) {
        String message= (String) object;
    }

    @Override
    protected void getMessageFail(String msg) {

    }
}
