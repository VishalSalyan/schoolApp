package com.webfills.schoolapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    static DashboardFragment sdashboardFragment;
    FragmentManager fragmentManager;
    LinearLayout llAttendance,llNotice,llStudent,llAssignment,llMedia,llSchool,llEvents,llHolidays,llSyllabus,llHomework,llResult,llExamRoutine;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseVariables();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        selectFragment(0);
    }

    private void initialiseVariables() {
        sdashboardFragment=new DashboardFragment();
        fragmentManager=getSupportFragmentManager();
        llAttendance=(LinearLayout)findViewById(R.id.ll_attendance);
        llNotice=(LinearLayout)findViewById(R.id.ll_notice);
        llStudent=(LinearLayout)findViewById(R.id.ll_student);
        llAssignment=(LinearLayout)findViewById(R.id.ll_assignment);
        llMedia=(LinearLayout)findViewById(R.id.ll_media);
        llSchool=(LinearLayout)findViewById(R.id.llSchool);
        llEvents=(LinearLayout)findViewById(R.id.ll_events);
        llHolidays=(LinearLayout)findViewById(R.id.ll_holidays);
        llSyllabus=(LinearLayout)findViewById(R.id.ll_syllabus);
        llHomework=(LinearLayout)findViewById(R.id.ll_homework);
        llResult=(LinearLayout)findViewById(R.id.ll_result);
        llExamRoutine=(LinearLayout)findViewById(R.id.ll_exam_routine);
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

//    private void selectFragment(int i) {
//        if(i==0){
//            fragmentManager.beginTransaction()
//                    .replace(R.id.cl_container, sdashboardFragment,"TAG_FRAGMENT")
//                    .addToBackStack(null)
//                    .commit();
//        }else if(i==1){
////            fragmentManager.beginTransaction()
////                    .replace(R.id.rlContainer, fragmentDashboard,"TAG_FRAGMENT")
////                    .addToBackStack(null)
////                    .commit();
//        }
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.equals(llAttendance)){
            Intent i = new Intent(MainActivity.this, AttendanceActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llNotice)){
            Intent i = new Intent(MainActivity.this, NoticeActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llStudent)){
            Intent i = new Intent(MainActivity.this, StudentListActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llAssignment)){
            Intent i = new Intent(MainActivity.this, AssignmentActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llMedia)){
            Intent i = new Intent(MainActivity.this, MediaActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llSchool)){
            Intent i = new Intent(MainActivity.this, SchoolActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llEvents)){
            Intent i = new Intent(MainActivity.this, EventsActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llHolidays)){
            Intent i = new Intent(MainActivity.this, HolidaysActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llSyllabus)){
            Intent i = new Intent(MainActivity.this, SyllabusActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llHomework)){
            Intent i = new Intent(MainActivity.this, HomeworkActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llResult)){
            Intent i = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }else if(view.equals(llExamRoutine)){
            Intent i = new Intent(MainActivity.this, ExamRoutineActivity.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
