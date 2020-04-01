package com.webfills.schoolapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.customViews.CircularTextview;
import com.webfills.schoolapp.data.HolidaysData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;


/**
 * Created by shreyansh on 08-05-2017.
 */
public class HolidaysAdapter extends RecyclerView.Adapter<HolidaysAdapter.MyViewHolder> {

    private boolean isDeleteMode;
    private ArrayList<HolidaysData> holidaysList;
    private Context context;

    public HolidaysAdapter(Context context, ArrayList<HolidaysData> holidaysList, boolean isDeleteMode) {
        this.holidaysList = holidaysList;
        this.isDeleteMode = isDeleteMode;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (SessionData.I().isDeleteMode) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }

        final HolidaysData holidaysData = holidaysList.get(position);
        holder.ctvDate.setText(holidaysData.getDate());
        holder.title.setText(holidaysData.getTitle());
        holder.subtitle.setText(holidaysData.getSubtitle());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseRepo.getInstance.deleteHolidays(holidaysData.getTitle(), new ServerResponse<String>() {
                    @Override
                    public void onSuccess(String body) {
                        utils.showMsg(context, body);
                        SessionData.I().isDeleteMode = false;
                        holder.ivDelete.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        utils.showMsg(context, error.toString());

                    }
                });
            }
        });
        holder.mainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.noticeDate = holidaysList.get(position).getDate();
                Constants.noticeTitle = holidaysList.get(position).getTitle();
                Constants.noticeSubtitle = holidaysList.get(position).getSubtitle();
                utils.showNoticeDetails(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return holidaysList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public CircularTextview ctvDate;
        LinearLayout mainContent;
        ImageView ivDelete;

        public MyViewHolder(View view) {
            super(view);
            ctvDate = view.findViewById(R.id.ctvNoticeDate);
            ctvDate.setSolidColor("#f44336");
            title = view.findViewById(R.id.tvNoticeTitle);
            subtitle = view.findViewById(R.id.tvNoticeSubtitle);
            mainContent = view.findViewById(R.id.ll_main_content);
            ivDelete = view.findViewById(R.id.iv_delete);
        }
    }
}
