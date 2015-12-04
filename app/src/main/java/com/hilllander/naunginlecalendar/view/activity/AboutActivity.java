package com.hilllander.naunginlecalendar.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.FacebookOpener;
import com.hilllander.naunginlecalendar.util.Util;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View toolbarShaw = findViewById(R.id.about_toolbar_shadow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        Util.hideToolBarShadowForLollipop(this, toolbar, toolbarShaw);
        Util.setSystemUiVisibilityForLollipop(this);
        Util.setStatusBarPaddingForLollipop(this, mainLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_about_us);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebook = FacebookOpener.getOpenFacebookIntent(AboutActivity.this);
                startActivity(facebook);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
