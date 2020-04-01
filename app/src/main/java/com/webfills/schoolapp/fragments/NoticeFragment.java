package com.webfills.schoolapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brouding.simpledialog.SimpleDialog;
import com.webfills.schoolapp.adapters.NoticeListAdapter;
import com.webfills.schoolapp.data.NoticeData;
import com.webfills.schoolapp.activity.NoticeActivity;
import com.webfills.schoolapp.R;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;


/**
 * Created by shreyansh on 10-05-2017.
 */
public class NoticeFragment extends Fragment {

    private View rootView;
    private ArrayList<NoticeData> noticeList = new ArrayList<>();
    private RecyclerView rvNotice;
    private NoticeListAdapter noticeListAdapter;
    private int month;

    public void addMonth(int month) {
        this.month = month;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notice, container, false);
        rvNotice = rootView.findViewById(R.id.rvNotice);
        fetchNotices();
        setList();
        return rootView;
    }

    private void fetchNotices() {
//        NoticeData noticeData = new NoticeData();
//        noticeData.setDate("29 Jan");
//        noticeData.setStudentName("Alumni Luncheon");
//        noticeData.setSubtitle("School is cordially organising a luncheon for the alumni batch held at the school auditorium, in recognition of the alumni batch");
//        noticeList.add(noticeData);
//        noticeData = new NoticeData();
//        noticeData.setDate("30 Jan");
//        noticeData.setStudentName("Sports Meet");
//        noticeData.setSubtitle("School is cordially organising a luncheon for the alumni batch held at the school auditorium, in recognition of the alumni batch");
//        noticeList.add(noticeData);

        FireBaseRepo.getInstance.fetchNotice(new ServerResponse<ArrayList<NoticeData>>() {
            @Override
            public void onSuccess(ArrayList<NoticeData> body) {
                noticeList.addAll(body);
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(getContext(), error.toString());
            }
        });

    }

    private void setList() {
//        noticeListAdapter = new NoticeListAdapter(noticeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvNotice.setLayoutManager(mLayoutManager);
        rvNotice.setItemAnimator(new DefaultItemAnimator());
        rvNotice.setAdapter(noticeListAdapter);
        rvNotice.addOnItemTouchListener(new NoticeActivity.RecyclerTouchListener(getActivity().getApplicationContext(),
                rvNotice, new NoticeActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Constants.noticeDate = noticeList.get(position).getDate();
                Constants.noticeTitle = noticeList.get(position).getTitle();
                Constants.noticeSubtitle = noticeList.get(position).getSubtitle();
                showNoticeDetails();
//                Intent i = new Intent(getActivity(), NoticeDetailActivity.class);
//                startActivity(i);
                Log.d("", "position clicked : " + position + " data : " + noticeList.get(position).getDate());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void showNoticeDetails() {
        new SimpleDialog.Builder(getActivity())
                .setTitle(Constants.noticeTitle)
                .setContent(Constants.noticeDate + " : " + Constants.noticeSubtitle)
                .setBtnConfirmText("Ok")
                .setCancelable(true)
                .onConfirm(new SimpleDialog.BtnCallback() {
                    @Override
                    public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}

