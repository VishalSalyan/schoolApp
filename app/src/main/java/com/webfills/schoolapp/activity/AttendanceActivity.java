package com.webfills.schoolapp.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.roomorama.caldroid.CaldroidFragment;
import com.webfills.schoolapp.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by shreyansh on 06-05-2017.
 */
public class AttendanceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public static FragmentManager fragmentManagerAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        fragmentManagerAttendance = getSupportFragmentManager();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Attendance");
        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.llContainerAttendance, caldroidFragment);
        t.commit();

        ColorDrawable red = new ColorDrawable(Color.RED);
        Date date = new Date();
        date.setDate(5);
        caldroidFragment.setBackgroundDrawableForDate(red, date);

        ColorDrawable yellow = new ColorDrawable(Color.YELLOW);
        Date date2 = new Date();
        date.setDate(4);
        caldroidFragment.setBackgroundDrawableForDate(yellow, date2);

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
