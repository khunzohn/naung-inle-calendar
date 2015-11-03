package com.hilllander.naunginlecalendar.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hilllander.naunginlecalendar.R;

public class WelcomeActivity extends AppCompatActivity {
    private static final long delay = 1000; // 1 sec

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(main);
                WelcomeActivity.this.finish();
            }
        }, delay);
    }

    @Override
    public void onBackPressed() {

    }
}
