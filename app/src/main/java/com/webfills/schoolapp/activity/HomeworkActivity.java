package com.webfills.schoolapp.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.adapters.HomeworkAdapter;
import com.webfills.schoolapp.adapters.NoticeListAdapter;
import com.webfills.schoolapp.data.HomeworkData;
import com.webfills.schoolapp.data.NoticeData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

/**
 * Created by PA01 on 10/29/2017.
 */

public class HomeworkActivity extends AppCompatActivity {

    private ArrayList<HomeworkData> homeworkList = new ArrayList<>();
    private RecyclerView rvHomework;
    private HomeworkAdapter homeworkAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Homework");
        rvHomework = findViewById(R.id.rv_recycler_view);
        fetchNotices();
        setList();

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
                    homeworkAdapter.notifyDataSetChanged();
                } else {
                    finish();
                }
                return true;
            case R.id.add:
                //add the function to perform here
                utils.goTo(HomeworkActivity.this, CreateNoticeActivity.class, Constants.HOMEWORK);
                return (true);
            case R.id.delete:
                //add the function to perform here
                SessionData.I().isDeleteMode = true;
                homeworkAdapter.notifyDataSetChanged();
//                setList();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void setList() {
        homeworkAdapter = new HomeworkAdapter(HomeworkActivity.this, homeworkList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvHomework.setLayoutManager(mLayoutManager);
        rvHomework.setItemAnimator(new DefaultItemAnimator());
        rvHomework.setAdapter(homeworkAdapter);
    }

    private void fetchNotices() {

        FireBaseRepo.getInstance.fetchHomework(new ServerResponse<ArrayList<HomeworkData>>() {
            @Override
            public void onSuccess(ArrayList<HomeworkData> body) {
                homeworkList.clear();
                homeworkList.addAll(body);
                homeworkAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(HomeworkActivity.this, error.toString());
            }
        });

    }

}
