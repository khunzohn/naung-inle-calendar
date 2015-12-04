package com.hilllander.naunginlecalendar.view.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.LicenseObject;
import com.hilllander.naunginlecalendar.util.Util;
import com.hilllander.naunginlecalendar.util.adapter.LicenseRecyclerAdapter;

import java.util.ArrayList;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View toolbarShow = findViewById(R.id.license_toolbar_shadow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        Util.hideToolBarShadowForLollipop(this, toolbar, toolbarShow);
        Util.setSystemUiVisibilityForLollipop(this);
        Util.setStatusBarPaddingForLollipop(this, mainLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.license_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new LicenseRecyclerAdapter(this, createLicenses()));
    }

    private ArrayList<LicenseObject> createLicenses() {
        ArrayList<LicenseObject> items = new ArrayList<>();
        String[] licenses = getResources().getStringArray(R.array.license_text);
        Resources r = getResources();
        String[] links = {r.getString(R.string.calendar_calculation), r.getString(R.string.mmText),
                r.getString(R.string.circular_reveal), r.getString(R.string.listview_animations),
                r.getString(R.string.material_datepicker), r.getString(R.string.google_iconset)};
        for (int i = 0; i < links.length; i++) {
            items.add(new LicenseObject(links[i], licenses[i]));
        }
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_license, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
