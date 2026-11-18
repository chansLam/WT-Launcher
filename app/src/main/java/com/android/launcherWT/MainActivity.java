package com.android.launcherWT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.android.launcherWT.home.OriginalHomeView;

public class MainActivity extends Activity {
    private static final String START_SPEECH = "com.tencent.wecarspeech.engine_init_broadcast";
    private static final long SPEECH_BOOT_WINDOW_MS = 40000L;

    private boolean firstResume = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OriginalHomeView home = (OriginalHomeView) findViewById(R.id.home);
        if (home != null) {
            home.reload();
        }
        if (SystemClock.elapsedRealtime() < SPEECH_BOOT_WINDOW_MS && firstResume) {
            sendBroadcast(new Intent(START_SPEECH));
            firstResume = false;
        }
    }

    @Override
    public void onBackPressed() {
    }
}
