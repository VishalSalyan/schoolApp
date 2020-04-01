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
import com.webfills.schoolapp.adapters.ResultAdapter;
import com.webfills.schoolapp.data.ResultData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

/**
 * Created by PA01 on 10/29/2017.
 */

public class ResultActivity extends AppCompatActivity {

    private RecyclerView rvResult;
    private ArrayList<ResultData> resultList = new ArrayList<>();
    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Result");
        rvResult = findViewById(R.id.rv_recycler_view);
        fetchAssignments();
        setList();
    }

    private void setList() {
        resultAdapter = new ResultAdapter(resultList, ResultActivity.this);
        RecyclerView.LayoutManager nLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvResult.setLayoutManager(nLayoutManager);
        rvResult.setAdapter(resultAdapter);
    }

    private void fetchAssignments() {
        FireBaseRepo.getInstance.fetchResults(new ServerResponse<ArrayList<ResultData>>() {

            @Override
            public void onSuccess(ArrayList<ResultData> body) {
                resultList.clear();
                resultList.addAll(body);
                resultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(ResultActivity.this, error.toString());
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
                utils.goTo(ResultActivity.this, CreateNoticeActivity.class, Constants.RESULT);

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
