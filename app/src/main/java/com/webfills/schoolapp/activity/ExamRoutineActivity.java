package com.webfills.schoolapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.adapters.ExamRoutineAdapter;
import com.webfills.schoolapp.adapters.ExamRoutineData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

public class ExamRoutineActivity extends AppCompatActivity {
    private TextView datesheetTitle;
    private RecyclerView recyclerView;
    private ArrayList<ExamRoutineData> examList = new ArrayList<>();
    private ExamRoutineAdapter examRoutineAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_routine);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Exam Routine");
        initViews();
        initViewsWithData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchExamRoutine();
    }

    private void fetchExamRoutine() {
        FireBaseRepo.getInstance.fetchExamRoutine(new ServerResponse<ArrayList<ExamRoutineData>>() {
            @Override
            public void onSuccess(ArrayList<ExamRoutineData> body) {
                examList.clear();
                examList.addAll(body);
                examRoutineAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(ExamRoutineActivity.this, error.toString());
            }
        });
    }

    private void initViews() {
        datesheetTitle = findViewById(R.id.tv_datesheet_title);
        recyclerView = findViewById(R.id.rv_recycler_view);
    }

    private void initViewsWithData() {
        setList();
    }

    private void setList() {
        examRoutineAdapter = new ExamRoutineAdapter(ExamRoutineActivity.this, examList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(examRoutineAdapter);
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
//                Log.d("", "home pressed");
//                if (SessionData.I().isDeleteMode) {
//                    SessionData.I().isDeleteMode = false;
//                    eventsAdapter.notifyDataSetChanged();
//                } else {
                    finish();
//                }
                return true;
            case R.id.add:
                //add the function to perform here
                utils.goTo(ExamRoutineActivity.this, CreateNoticeActivity.class, Constants.EXAM_ROUTINE);
                return (true);
            case R.id.delete:
                //add the function to perform here
//                SessionData.I().isDeleteMode = true;
//                eventsAdapter.notifyDataSetChanged();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

}
