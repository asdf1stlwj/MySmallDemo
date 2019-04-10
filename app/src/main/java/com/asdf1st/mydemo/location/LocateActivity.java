package com.asdf1st.mydemo.location;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.LocationUtils;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LocateActivity extends AppCompatActivity {
    LocationUtils locationUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);
        locationUtils=LocationUtils.getInstance(this);
        LocationUtils.initLocation();
        Map<String,String> map=LocationUtils.getLocation();
       List<Address> addresses=getAddress(LocationUtils.location);
        Log.e("locateActivity", "getLocation" );
    }

    private List<Address> getAddress(Location location) {
        //用来接收位置的详细信息
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.getInstance(this).remove();
    }
}
