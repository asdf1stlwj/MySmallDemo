package com.asdf1st.mydemo.memoryControl;

import android.widget.ImageView;

import com.asdf1st.mydemo.Base.MVP.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.MVP.View.Activity.BaseMVPActivity;
import com.asdf1st.mydemo.R;

import butterknife.BindView;

public class MemoryLeakActivity extends BaseMVPActivity {
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @Override
    public int getLayoutId() {
        return R.layout.activity_memory_leak;
    }





    @Override
    public IPresenter createPresenter() {
        return null;
    }
}
