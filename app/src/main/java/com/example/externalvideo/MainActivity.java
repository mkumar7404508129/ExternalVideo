package com.example.externalvideo;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.externalvideo.Camera.ManageExternalCamera;
import com.example.externalvideo.Camera.R100PermissionListener;
import com.example.externalvideo.Camera.R100PermissionManager;

public class MainActivity extends AppCompatActivity {

    ZoomGestureListner zoomGestureListner;
    View  view;
    R100PermissionManager permissionManager;
    boolean hasCameraPermission=false;
    String TAG = "VideoDemo";
    ManageExternalCamera externalCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.UVCCamera);
        zoomGestureListner = new ZoomGestureListner(this,view);
        externalCamera = new ManageExternalCamera(this,this);
        permissionManager = new R100PermissionManager(this,listener);
    }

    R100PermissionListener listener= new R100PermissionListener() {
        @Override
        public void permissionGranted(Boolean bool, int code) {
            externalCamera.openCamera();
            hasCameraPermission=true;
            view.setOnTouchListener((View.OnTouchListener) zoomGestureListner);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if(!hasCameraPermission){
            permissionManager.requestCameraPermission();
        }
    }
}