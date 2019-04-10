package com.asdf1st.mydemo.Algorithm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asdf1st.mydemo.MyApplication;
import com.asdf1st.mydemo.R;

public class AlgorithmActivity extends AppCompatActivity {
    private String TAG="Algorithm";
    public static Context context;
    Button btn_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        ((MyApplication)getApplication()).mRefWatcher.watch(this);
        btn_answer= (Button) findViewById(R.id.btn_answer);
        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int []test={23,2,34,8,34,28,98,89,13,8,33,56,75,67};
                AlgorithmUtils.quick_sort(test,0,test.length-1);
                String rs=AlgorithmUtils.ArrayTransformString(test);
                Log.e(TAG, rs+"");
                Toast.makeText(AlgorithmActivity.this,rs+"",Toast.LENGTH_LONG);
//                for(int i=0;i<test.length;i++)
//                {
//                    Log.e(TAG, test+"");
//                    Toast.makeText(AlgorithmActivity.this,test+"",Toast.LENGTH_LONG);
//                }

            }
        });
//        context=this;
    }
}
