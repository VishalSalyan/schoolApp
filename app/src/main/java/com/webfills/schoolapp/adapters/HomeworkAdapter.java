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
import com.webfills.schoolapp.data.HomeworkData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;


/**
 * Created by shreyansh on 08-05-2017.
 */
public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.MyViewHolder> {

    private ArrayList<HomeworkData> homeworkList;
    private Context context;

    public HomeworkAdapter(Context context, ArrayList<HomeworkData> noticeList) {
        this.homeworkList = noticeList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_homework, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if (SessionData.I().isDeleteMode) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }
        final HomeworkData homeworkData = homeworkList.get(position);
        holder.ctvDate.setText(homeworkData.getDate());
        holder.subject.setText(homeworkData.getSubjectName());
        holder.description.setText(homeworkData.getDescription());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseRepo.getInstance.deleteHomework(homeworkData.getSubjectName(), new ServerResponse<String>() {
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
                Constants.noticeDate = homeworkData.getDate();
                Constants.noticeTitle = homeworkData.getSubjectName();
                Constants.noticeSubtitle = homeworkData.getDescription();
                utils.showNoticeDetails(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView subject, description;
        private CircularTextview ctvDate;
        private LinearLayout mainContent;
        private ImageView ivDelete;

        MyViewHolder(View view) {
            super(view);
            ctvDate = view.findViewById(R.id.ctv_homework_date);
            ctvDate.setSolidColor("#f44336");
            subject = view.findViewById(R.id.tv_subject);
            description = view.findViewById(R.id.tv_subject_description);
            mainContent = view.findViewById(R.id.ll_main_content);
            ivDelete = view.findViewById(R.id.iv_delete);
        }
    }
}
