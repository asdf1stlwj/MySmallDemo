package com.asdf1st.mydemo.memoryControl;

import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.asdf1st.mydemo.Base.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.View.Activity.BaseActivity;
import com.asdf1st.mydemo.R;

import butterknife.BindView;

public class MemoryShadeActivity extends BaseActivity {
    @BindView(R.id.btn_test)
    Button btn_test;
    public static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (int i = 0; i < 100; i++) {
                String[] stringArgs=new String[1000000];
            }
            handler.sendEmptyMessageDelayed(0,30);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_shade;
    }

    @Override
    protected void initView() {
        super.initView();
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        handler.sendEmptyMessage(0);
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }
}
