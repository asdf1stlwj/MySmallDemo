package com.asdf1st.mydemo.Utils;

import java.util.Map;

/**
 * Created by User on 2017/11/17.
 */

public class Data {

    int id;
    String name;
    Map<String,Object> otherData;

    public Data(String name){
        this(-1,name);
    }

    public Data(int id, String name) {
        this(id,name,null);
    }

    public Data(int id, String name, Map<String, Object> otherData) {
        this.id = id;
        this.name = name;
        this.otherData = otherData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getOtherData() {
        return otherData;
    }

    public void setOtherData(Map<String, Object> otherData) {
        this.otherData = otherData;
    }
}
