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
import com.webfills.schoolapp.adapters.AssignmentListAdapter;
import com.webfills.schoolapp.data.AssignmentData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

public class AssignmentActivity extends AppCompatActivity {

    private RecyclerView rvAssignment;
    private ArrayList<AssignmentData> assignmentArrayList = new ArrayList<>();
    private AssignmentListAdapter assignmentListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Assignments");
        rvAssignment = findViewById(R.id.rv_assignment);
        fetchAssignments();
        setList();
    }

    private void setList() {
        assignmentListAdapter = new AssignmentListAdapter(assignmentArrayList, AssignmentActivity.this);
        RecyclerView.LayoutManager nLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAssignment.setLayoutManager(nLayoutManager);
        rvAssignment.setAdapter(assignmentListAdapter);
    }

    private void fetchAssignments() {
        FireBaseRepo.getInstance.fetchAssignments(new ServerResponse<ArrayList<AssignmentData>>() {

            @Override
            public void onSuccess(ArrayList<AssignmentData> body) {
                assignmentArrayList.clear();
                assignmentArrayList.addAll(body);
                assignmentListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(AssignmentActivity.this, error.toString());
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
                utils.goTo(AssignmentActivity.this, CreateNoticeActivity.class, Constants.ASSIGNMENT);

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
