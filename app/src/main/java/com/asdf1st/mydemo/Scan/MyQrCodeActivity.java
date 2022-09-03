package com.asdf1st.mydemo.Scan;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.KeyEvent;

import com.asdf1st.mydemo.Base.MVP.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.MVP.View.Activity.BaseMVPActivity;
import com.asdf1st.mydemo.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import butterknife.BindView;

public class MyQrCodeActivity extends BaseMVPActivity {
    @BindView(R.id.dbv_custom)
    public DecoratedBarcodeView barcodeScannerView;
    private CaptureManager capture;
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_qr_code;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_middle.setText("扫描二维码");
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), null);
        capture.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }
}
