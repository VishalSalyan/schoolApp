package com.webfills.schoolapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.adapters.StudentAdapter;
import com.webfills.schoolapp.data.StudentData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

public class StudentListActivity extends AppCompatActivity {

    private RecyclerView rvStudentList;
    private ArrayList<StudentData> studentsList;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Students");
        rvStudentList = findViewById(R.id.lv_student_list);
        studentsList = new ArrayList<>();
        setList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchStudents();
    }

    private void setList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StudentListActivity.this, RecyclerView.VERTICAL, false);
        rvStudentList.setLayoutManager(linearLayoutManager);
        studentAdapter = new StudentAdapter(StudentListActivity.this, studentsList);
        rvStudentList.setAdapter(studentAdapter);

    }

    private void fetchStudents() {
        FireBaseRepo.getInstance.fetchStudentsList(new ServerResponse<ArrayList<StudentData>>() {
            @Override
            public void onSuccess(ArrayList<StudentData> body) {
                studentsList.clear();
                studentsList.addAll(body);
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(StudentListActivity.this, error.toString());
            }
        });
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
//                if (SessionData.I().isDeleteMode) {
//                    SessionData.I().isDeleteMode = false;
//                    noticeListAdapter.notifyDataSetChanged();
//                } else {
                finish();
//                }
                return true;
            case R.id.add:
                //add the function to perform here
                utils.goTo(StudentListActivity.this, CreateNoticeActivity.class, Constants.STUDENT);

                return (true);
            case R.id.delete:
                //add the function to perform here
//                SessionData.I().isDeleteMode = true;
//                noticeListAdapter.notifyDataSetChanged();
//                setList();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

}
