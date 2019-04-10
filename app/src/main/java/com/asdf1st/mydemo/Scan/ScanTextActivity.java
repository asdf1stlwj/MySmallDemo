package com.asdf1st.mydemo.Scan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asdf1st.mydemo.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanTextActivity extends Activity {
    Button btn_scan_barcode,btn_scan_myqrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_text);

        btn_scan_barcode=findViewById(R.id.btn_scan_barcode);
        btn_scan_myqrcode=findViewById(R.id.btn_myqrcode);
        btn_scan_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(ScanTextActivity.this);
                integrator.setPrompt("请扫描"); //底部的提示文字，设为""可以置空
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
                integrator.setCaptureActivity(ScanBarCodeActivity.class);
                integrator.initiateScan();
            }
        });
        btn_scan_myqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ScanTextActivity.this);
                integrator.setPrompt(""); //底部的提示文字，设为""可以置空
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(false); //扫描成功的「哔哔」声，默认开启
                integrator.setCaptureActivity(MyQrCodeActivity.class);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
