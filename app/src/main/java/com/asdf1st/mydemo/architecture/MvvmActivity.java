package com.asdf1st.mydemo.architecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.databinding.ActivityMvvmBinding;
import com.asdf1st.mydemo.model.User;

public class MvvmActivity extends AppCompatActivity {
    User user;
    ActivityMvvmBinding dataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_mvvm);
        user=new User("lwj","asdf1599");
        dataBinding.setUser(user);

    }

    public class UserHandler{
        public void changeUserName(View view){
            Log.i("UserHandler", "changeUserName: ");
        }
    }


}