package com.asdf1st.mydemo.memoryControl;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;

import com.asdf1st.mydemo.Base.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.View.Activity.BaseActivity;
import com.asdf1st.mydemo.R;

import butterknife.BindView;

public class MemoryLeakActivity extends BaseActivity {
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
