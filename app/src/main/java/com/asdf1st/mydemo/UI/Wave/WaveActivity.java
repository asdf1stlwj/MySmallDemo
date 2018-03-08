package com.asdf1st.mydemo.UI.Wave;

import android.app.Activity;
import android.os.Bundle;

import com.asdf1st.mydemo.R;

public class WaveActivity extends Activity {
    MyWaveView waveView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        waveView=findViewById(R.id.waveView);
        //waveView.startMove();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //waveView.stopMove();
    }
}
