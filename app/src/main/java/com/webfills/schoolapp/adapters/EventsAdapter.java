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
import com.webfills.schoolapp.data.EventsData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;


/**
 * Created by shreyansh on 08-05-2017.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private ArrayList<EventsData> eventsList;
    private Context context;

    public EventsAdapter(Context context, ArrayList<EventsData> eventsList) {
        this.eventsList = eventsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if (SessionData.I().isDeleteMode) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }
        final EventsData eventsData = eventsList.get(position);
        holder.ctvDate.setText(eventsData.getDate());
        holder.title.setText(eventsData.getTitle());
        holder.subtitle.setText(eventsData.getSubtitle());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseRepo.getInstance.deleteEvents(eventsData.getTitle(), new ServerResponse<String>() {
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
                Constants.noticeDate = eventsData.getDate();
                Constants.noticeTitle = eventsData.getTitle();
                Constants.noticeSubtitle = eventsData.getSubtitle();
                utils.showNoticeDetails(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, subtitle;
        private CircularTextview ctvDate;
        private LinearLayout mainContent;
        private ImageView ivDelete;

        MyViewHolder(View view) {
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
