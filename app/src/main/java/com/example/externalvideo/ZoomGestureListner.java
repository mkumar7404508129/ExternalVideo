package com.example.externalvideo;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
public class ZoomGestureListner extends GestureDetector.SimpleOnGestureListener implements ScaleGestureDetector.OnScaleGestureListener,View.OnTouchListener {
     float mLastTouchX;
     float mLastTouchY;
     String TAG= "Zoom Move Gesture";
     boolean zooming=false;
        private ScaleGestureDetector scaleGestureDetector;
        private View cameraViw;
        private float scaleFactor = 1.0f;

        public ZoomGestureListner(Context context, View view) {
            this.cameraViw = view;
            scaleGestureDetector = new ScaleGestureDetector(context,this );
        }
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(1f, Math.min(scaleFactor, 20.0f));
            cameraViw.setPivotX(0f);
            cameraViw.setPivotY(0f);
            cameraViw.setScaleX(scaleFactor);
            cameraViw.setScaleY(scaleFactor);
            cameraViw.invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            zooming=true;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            // do nothing
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        zooming = false;
                    }
                    catch (Exception e){

                    }
                }
            }).start();
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
           // cameraViw.scrollBy((int) distanceX, (int) distanceY);
            return true;
        }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        if(!zooming)
            switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Store the initial touch position
                mLastTouchX = motionEvent.getX();
                mLastTouchY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // Calculate the distance moved
                    float dx = motionEvent.getX() - mLastTouchX;
                    float dy = motionEvent.getY() - mLastTouchY;

                    // Update the position of the item
                    float x = cameraViw.getX() + dx;
                    float y = cameraViw.getY() + dy;
                    Log.i(TAG,x+" "+" "+y);
                    cameraViw.setX(x);
                    cameraViw.setY(y);

                    // Store the new touch position
                    mLastTouchX = motionEvent.getX();
                    mLastTouchY = motionEvent.getY();
                    // Invalidate the view to trigger a redraw
                    cameraViw.invalidate();

                break;
        }
        return true;
    }
}


