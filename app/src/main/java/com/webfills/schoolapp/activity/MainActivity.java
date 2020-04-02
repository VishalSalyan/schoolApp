package com.webfills.schoolapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.LinearLayout;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.fragments.DashboardFragment;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import static com.webfills.schoolapp.utils.Utils.utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //    private DashboardFragment dashboardFragment;
    private FragmentManager fragmentManager;
    private LinearLayout llAttendance, llNotice, llStudent, llAssignment, llMedia, llSchool, llEvents, llHolidays, llSyllabus, llHomework, llResult, llExamRoutine;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseVariables();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initialiseVariables() {
//        dashboardFragment =new DashboardFragment();
        fragmentManager = getSupportFragmentManager();
        llAttendance = findViewById(R.id.ll_attendance);
        llNotice = findViewById(R.id.ll_notice);
        llStudent = findViewById(R.id.ll_student);
        llAssignment = findViewById(R.id.ll_assignment);
        llMedia = findViewById(R.id.ll_media);
        llSchool = findViewById(R.id.llSchool);
        llEvents = findViewById(R.id.ll_events);
        llHolidays = findViewById(R.id.ll_holidays);
        llSyllabus = findViewById(R.id.ll_syllabus);
        llHomework = findViewById(R.id.ll_homework);
        llResult = findViewById(R.id.ll_result);
        llExamRoutine = findViewById(R.id.ll_exam_routine);
        llSyllabus.setOnClickListener(this);
        llHomework.setOnClickListener(this);
        llResult.setOnClickListener(this);
        llExamRoutine.setOnClickListener(this);
        llSchool.setOnClickListener(this);
        llEvents.setOnClickListener(this);
        llHolidays.setOnClickListener(this);
        llMedia.setOnClickListener(this);
        llAssignment.setOnClickListener(this);
        llStudent.setOnClickListener(this);
        llNotice.setOnClickListener(this);
        llAttendance.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemAdd = menu.findItem(R.id.add);
        MenuItem itemDelete = menu.findItem(R.id.delete);
        MenuItem itemLogOut = menu.findItem(R.id.menu_log_out);

        itemAdd.setVisible(false);
        itemDelete.setVisible(false);
        itemLogOut.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_log_out) {
            SessionData.I().saveLogin(false);
            utils.goTo(MainActivity.this, LoginActivity.class);
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_school) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(llAttendance)) {
            Intent i = new Intent(MainActivity.this, AttendanceActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llNotice)) {
            Intent i = new Intent(MainActivity.this, NoticeActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llStudent)) {
            Intent i = new Intent(MainActivity.this, StudentListActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llAssignment)) {
            Intent i = new Intent(MainActivity.this, AssignmentActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llMedia)) {
            Intent i = new Intent(MainActivity.this, MediaActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llSchool)) {
            Intent i = new Intent(MainActivity.this, SchoolActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llEvents)) {
            Intent i = new Intent(MainActivity.this, EventsActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llHolidays)) {
            Intent i = new Intent(MainActivity.this, HolidaysActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llSyllabus)) {
            Intent i = new Intent(MainActivity.this, SyllabusActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llHomework)) {
            Intent i = new Intent(MainActivity.this, HomeworkActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llResult)) {
            Intent i = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        } else if (view.equals(llExamRoutine)) {
            Intent i = new Intent(MainActivity.this, ExamRoutineActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
