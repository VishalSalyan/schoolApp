package com.webfills.schoolapp.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.webfills.schoolapp.R;

/**
 * Created by PA01 on 10/29/2017.
 */

public class SchoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("School");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            Log.d("", "home pressed");
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
