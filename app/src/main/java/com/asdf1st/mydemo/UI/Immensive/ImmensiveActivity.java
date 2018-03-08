package com.asdf1st.mydemo.UI.Immensive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.asdf1st.mydemo.R;

public class ImmensiveActivity extends Activity {
    Button btn_one;
    Button btn_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immensive);
        btn_one=findViewById(R.id.btn_one);
        btn_two=findViewById(R.id.btn_two);
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ImmensiveActivity.this,ChenJinActivity1.class);
                startActivity(intent);
            }
        });
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ImmensiveActivity.this,ChenJinActivity2.class);
                startActivity(intent);
            }
        });
    }
}
