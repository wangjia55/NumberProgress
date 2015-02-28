package com.jacob.progress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;


public class MainActivity extends FragmentActivity {
    HorizontalNumProgressBar horizontalNumProgressBar;
    CircleNumberProgress circleNumberProgress;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (horizontalNumProgressBar.getProgress() >= 100) {
                removeMessages(10);
                return;
            }

            sendEmptyMessageDelayed(10, 80);
            horizontalNumProgressBar.setProgress(horizontalNumProgressBar.getProgress() + 1);
            circleNumberProgress.setProgress(circleNumberProgress.getProgress() + 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        horizontalNumProgressBar = (HorizontalNumProgressBar) findViewById(R.id.progress_horizontal);
        circleNumberProgress = (CircleNumberProgress) findViewById(R.id.progress_circle);

        mHandler.sendEmptyMessage(10);

    }


}
