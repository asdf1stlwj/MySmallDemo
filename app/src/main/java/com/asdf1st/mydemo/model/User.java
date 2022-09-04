package com.asdf1st.mydemo.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
public class User extends BaseObservable {
    @Bindable
    public String name;
    public String password;
    private String detail;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
