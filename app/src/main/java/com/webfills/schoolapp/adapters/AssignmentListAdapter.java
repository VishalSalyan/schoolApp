package com.webfills.schoolapp.adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brouding.simpledialog.SimpleDialog;
import com.webfills.schoolapp.data.AssignmentData;
import com.webfills.schoolapp.R;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.utils.Constants;

import java.util.List;


/**
 * Created by shreyansh on 08-05-2017.
 */
public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.MyViewHolder> {

    private List<AssignmentData> assignmentsList;
    private Context context;

    public AssignmentListAdapter(List<AssignmentData> assignmentsList, Context context) {
        this.assignmentsList = assignmentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_list_row, parent, false);
        Log.d("", "onCreateViewHolder");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AssignmentData assignment = assignmentsList.get(position);
        Log.d("", "position : " + position + " date : " + assignment.getUpdatedDate());

        holder.title.setText(assignment.getTitle());
        holder.subtitle.setText(assignment.getUpdatedDate());
        holder.rlAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAssignmentDetails(assignment.getTitle(), assignment.getDescription(), assignment.getUpdatedDate());
            }
        });
        holder.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireBaseRepo.getInstance.downloadFile(context, assignment.getTitle() + Constants.PDF,
                        context.getResources().getString(R.string.app_name) + "/" + Constants.ASSIGNMENT);
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
        return assignmentsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, subtitle;
        private ImageView ivDownload;
        private RelativeLayout rlAssignment;
//        LinearLayout llFee;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_assignment_title_list_row);
            subtitle = view.findViewById(R.id.tv_assignment_date_list_row);
            ivDownload = view.findViewById(R.id.iv_download_assignment_list_row);
            rlAssignment = view.findViewById(R.id.rl_assignment_list_row);

        }
    }
}