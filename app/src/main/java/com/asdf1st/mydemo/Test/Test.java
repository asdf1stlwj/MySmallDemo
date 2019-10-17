package com.asdf1st.mydemo.Test;

import android.util.Log;

import com.asdf1st.mydemo.Utils.ChangNumUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Test {
    public static void main(String args[]) {
        byte[] testBodyInfoByte = {71, 86, 83, 71, 86, 83, -91, -91, -91, -91, 0, 73, 0, 2, 70, 123, 34, 98, 111, 100, 121, 84, 121, 112, 101, 34, 58, 48, 44, 34, 100, 97, 116, 97, 34, 58, 48, 44, 34, 100, 97, 116, 97, 49, 34, 58, 51, 55, 44, 34, 100, 97, 116, 97, 84, 121, 112, 101, 34, 58, 49, 48, 48, 44, 34, 116, 105, 109, 101, 34, 58, 49, 53, 54, 51, 56, 55, 53, 49, 48, 49, 49, 48, 49, 125};
        System.out.println("byte[]=" + ChangNumUtils.byte2HexStr(testBodyInfoByte, " "));
        byte[] data = Arrays.copyOfRange(testBodyInfoByte, 10, testBodyInfoByte.length);
        if (data.length < 3)
            return;
        byte[] eventCodeByte = new byte[2];
        System.arraycopy(data, 2, eventCodeByte, 0, 2);
        int eventCode=eventCodeByte[0]*256+eventCodeByte[1];
        switch (eventCode) {
            case 2://体征数据
                int jsonLength=data[4];
                byte[] jsonBytes;
                if (jsonLength==-1){
                    jsonLength=data[5]*256+data[6];
                    jsonBytes=new byte[jsonLength];
                    System.arraycopy(data,7,jsonBytes,0,jsonLength);
                }else {
                    jsonBytes=new byte[jsonLength];
                    System.arraycopy(data,5,jsonBytes,0,jsonLength);
                }
                try{
                    String json=new String(jsonBytes, StandardCharsets.UTF_8);
                    System.out.println("messageDeal: 体征json= "+json );
                }catch (Exception e){
                    System.out.println("messageDeal: "+e.getMessage());
                }
                break;
        }
    }
}
