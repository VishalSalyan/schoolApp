package com.webfills.schoolapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brouding.simpledialog.SimpleDialog;
import com.webfills.schoolapp.R;
import com.webfills.schoolapp.data.ResultData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.utils.Constants;

import java.util.ArrayList;


/**
 * Created by shreyansh on 08-05-2017.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private ArrayList<ResultData> resultList;
    Context context;

    public ResultAdapter(ArrayList<ResultData> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_list_row, parent, false);
        Log.d("", "onCreateViewHolder");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ResultData resultData = resultList.get(position);
        Log.d("", "position : " + position + " date : " + resultData.getUpdatedDate());

        holder.title.setText(resultData.getTitle());
        holder.subtitle.setText(resultData.getUpdatedDate());
        holder.rlAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAssignmentDetails(resultData.getTitle(), resultData.getDescription(), resultData.getUpdatedDate());
            }
        });
        holder.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FireBaseRepo.getInstance.downloadFile(resultData.getStudentName(),resultData.getStudentImage());
                FireBaseRepo.getInstance.downloadFile(context, resultData.getFileUrl() + Constants.PDF,
                        context.getResources().getString(R.string.app_name) + Constants.RESULT);
            }
        });
    }

    private void showAssignmentDetails(String title, String description, String date) {
        new SimpleDialog.Builder(context)
                .setTitle(title)
                .setContent(date + " : " + description)
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

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, subtitle;
        private ImageView ivDownload;
        private RelativeLayout rlAssignment;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_assignment_title_list_row);
            subtitle = view.findViewById(R.id.tv_assignment_date_list_row);
            ivDownload = view.findViewById(R.id.iv_download_assignment_list_row);
            rlAssignment = view.findViewById(R.id.rl_assignment_list_row);

        }
    }
}