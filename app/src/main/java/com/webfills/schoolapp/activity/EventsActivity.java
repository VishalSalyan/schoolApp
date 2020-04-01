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
import com.webfills.schoolapp.adapters.EventsAdapter;
import com.webfills.schoolapp.data.EventsData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

/**
 * Created by PA01 on 10/29/2017.
 */

public class EventsActivity extends AppCompatActivity {

    private ArrayList<EventsData> eventList = new ArrayList<>();
    private RecyclerView rvEvents;
    private EventsAdapter eventsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Events");
        rvEvents = findViewById(R.id.rv_recycler_view);
        setList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchNotices();
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
                    eventsAdapter.notifyDataSetChanged();
                } else {
                    finish();
                }
                return true;
            case R.id.add:
                //add the function to perform here
                utils.goTo(EventsActivity.this, CreateNoticeActivity.class, Constants.EVENTS);
                return (true);
            case R.id.delete:
                //add the function to perform here
                SessionData.I().isDeleteMode = true;
                eventsAdapter.notifyDataSetChanged();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void fetchNotices() {

        FireBaseRepo.getInstance.fetchEvents(new ServerResponse<ArrayList<EventsData>>() {
            @Override
            public void onSuccess(ArrayList<EventsData> body) {
                eventList.clear();
                eventList.addAll(body);
                eventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(EventsActivity.this, error.toString());
            }
        });

    }

    private void setList() {
        eventsAdapter = new EventsAdapter(EventsActivity.this, eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvEvents.setLayoutManager(mLayoutManager);
        rvEvents.setItemAnimator(new DefaultItemAnimator());
        rvEvents.setAdapter(eventsAdapter);

    }
}
