package com.softcodeinfotech.easypick;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {
    ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pBar = findViewById(R.id.pBar);


        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                pBar.setVisibility(View.GONE);

                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();

                // handler.postDelayed(this,1000);

            }
        };

        handler.postDelayed(r, 2000);
    }

}
