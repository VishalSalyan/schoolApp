package com.webfills.schoolapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.activity.StudentDetailsActivity;
import com.webfills.schoolapp.data.StudentData;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    private ArrayList<StudentData> studentList;
    private Context context;

    public StudentAdapter(Context context, ArrayList<StudentData> studentList) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_student, parent, false);
        Log.d("", "onCreateViewHolder");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentAdapter.MyViewHolder holder, int position) {
        final StudentData studentData = studentList.get(position);

        holder.studentName.setText(studentData.getStudentName());
        holder.className.setText(studentData.getClassName());
        holder.rlStudentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionData.I().studentId = studentData.getStudentId();
                utils.goTo(context, StudentDetailsActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView studentName, className;
        private RelativeLayout rlStudentLayout;

        MyViewHolder(View view) {
            super(view);
            studentName = view.findViewById(R.id.tv_student_name);
            className = view.findViewById(R.id.tv_class);
            rlStudentLayout = view.findViewById(R.id.rl_student_layout);

        }
    }
}
