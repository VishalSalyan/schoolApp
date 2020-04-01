package com.webfills.schoolapp.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.adapters.NoticeListAdapter;
import com.webfills.schoolapp.data.NoticeData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;


/**
 * Created by shreyansh on 06-05-2017.
 */
public class NoticeActivity extends AppCompatActivity {
    private ArrayList<NoticeData> noticeList = new ArrayList<>();
    private RecyclerView rvNotice;
    private NoticeListAdapter noticeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notice");
        rvNotice = findViewById(R.id.rv_recycler_view);
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
                    noticeListAdapter.notifyDataSetChanged();
                } else {
                    finish();
                }
                return true;
            case R.id.add:
                //add the function to perform here
                utils.goTo(NoticeActivity.this, CreateNoticeActivity.class, Constants.NOTICE);
                return (true);
            case R.id.delete:
                //add the function to perform here
                SessionData.I().isDeleteMode = true;
                noticeListAdapter.notifyDataSetChanged();
//                setList();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    private void setList() {
        noticeListAdapter = new NoticeListAdapter(NoticeActivity.this, noticeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvNotice.setLayoutManager(mLayoutManager);
        rvNotice.setItemAnimator(new DefaultItemAnimator());
        rvNotice.setAdapter(noticeListAdapter);
    }

    private void fetchNotices() {

        FireBaseRepo.getInstance.fetchNotice(new ServerResponse<ArrayList<NoticeData>>() {
            @Override
            public void onSuccess(ArrayList<NoticeData> body) {
                noticeList.clear();
                noticeList.addAll(body);
                noticeListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(NoticeActivity.this, error.toString());
            }
        });

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }
}
