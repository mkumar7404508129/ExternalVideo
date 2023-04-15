package com.example.externalvideo.Camera;

import android.graphics.Bitmap;

public interface CameraFrames {
    void onCameraFrame(Bitmap bitmap, long timestamp);
}
