package com.asdf1st.mydemo.UI.Drag;

import java.util.Map;

/**
 * Created by User on 2017/11/17.
 */

public class GridItem  {

    int imgId;
    String name;
    Map<String,Object> otherData;

    public GridItem(String name){
        this(-1,name);
    }

    public GridItem(int imgId, String name) {
        this(imgId,name,null);
    }

    public GridItem(int imgId, String name, Map<String, Object> otherData) {
        this.imgId = imgId;
        this.name = name;
        this.otherData = otherData;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
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
