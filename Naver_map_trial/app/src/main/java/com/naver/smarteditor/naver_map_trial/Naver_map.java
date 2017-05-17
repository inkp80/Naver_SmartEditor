package com.naver.smarteditor.naver_map_trial;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;

/**
 * Created by NAVER on 2017. 5. 13..
 */

public class Naver_map extends NMapActivity {
    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "gbnn1os3zDfvajMF7RLC";// 애플리케이션 클라이언트 아이디 값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 허가 됨", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_SHORT).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


        Log.d("Map", "after asking permission");
        mMapView = new NMapView(this);
        setContentView(mMapView);
        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();

        NGeoPoint mCurLocation = new NGeoPoint();


        NMapLocationManager locationManager = new NMapLocationManager(this);
        locationManager.enableMyLocation(true);
        mCurLocation = locationManager.getMyLocation();
    }


    @Override
    public void onRequestPermissionsResult(int reqCode, String permissions[], int[] grantResults) {

        switch (reqCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 허가 됨", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, " 권한 거부 됨", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
