package com.webfills.schoolapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.adapters.HolidaysAdapter;
import com.webfills.schoolapp.data.HolidaysData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

/**
 * Created by PA01 on 10/29/2017.
 */

public class HolidaysActivity extends AppCompatActivity {

    private ArrayList<HolidaysData> holidaysList = new ArrayList<>();
    private RecyclerView rvHolidays;
    private HolidaysAdapter holidaysAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Holidays");

        rvHolidays = findViewById(R.id.rv_recycler_view);
        fetchHolidays();
        setList(SessionData.I().isDeleteMode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemAdd = menu.findItem(R.id.add);
        MenuItem itemDelete = menu.findItem(R.id.delete);
        if (SessionData.I().isEditable) {
            itemAdd.setVisible(true);
            itemDelete.setVisible(true);
        } else {
            itemAdd.setVisible(false);
            itemDelete.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("", "home pressed");
                if (SessionData.I().isDeleteMode) {
                    SessionData.I().isDeleteMode = false;
                    setList(false);
                } else {
                    finish();
                }
                return true;
            case R.id.add:
                //add the function to perform here
                utils.goTo(HolidaysActivity.this, CreateNoticeActivity.class,Constants.HOLIDAYS);
                return (true);
            case R.id.delete:
                //add the function to perform here
                SessionData.I().isDeleteMode = true;
                setList(true);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void fetchHolidays() {

        FireBaseRepo.getInstance.fetchHolidays(new ServerResponse<ArrayList<HolidaysData>>() {
            @Override
            public void onSuccess(ArrayList<HolidaysData> body) {
                holidaysList.clear();
                holidaysList.addAll(body);
                holidaysAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(HolidaysActivity.this, error.toString());
            }
        });

    }

    private void setList(boolean isDeleteMode) {
        holidaysAdapter = new HolidaysAdapter(HolidaysActivity.this, holidaysList, isDeleteMode);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvHolidays.setLayoutManager(mLayoutManager);
        rvHolidays.setItemAnimator(new DefaultItemAnimator());
        rvHolidays.setAdapter(holidaysAdapter);
//        rvHolidays.addOnItemTouchListener(new NoticeActivity.RecyclerTouchListener(getApplicationContext(),
//                rvHolidays, new NoticeActivity.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Constants.noticeDate = holidaysList.get(position).getDate();
//                Constants.noticeTitle = holidaysList.get(position).getStudentName();
//                Constants.noticeSubtitle = holidaysList.get(position).getSubtitle();
//                utils.showNoticeDetails();
////                Intent i = new Intent(getActivity(), NoticeDetailActivity.class);
////                startActivity(i);
//                Log.d("", "position clicked : " + position + " data : " + holidaysList.get(position).getDate());
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
    }



}
