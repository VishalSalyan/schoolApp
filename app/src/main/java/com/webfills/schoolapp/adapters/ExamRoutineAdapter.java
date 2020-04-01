package com.webfills.schoolapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webfills.schoolapp.R;

import java.util.ArrayList;

public class ExamRoutineAdapter extends RecyclerView.Adapter<ExamRoutineAdapter.MyViewHolder> {
    private ArrayList<ExamRoutineData> studentList;
    private Context context;

    public ExamRoutineAdapter(Context context, ArrayList<ExamRoutineData> studentList) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExamRoutineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_exam_routine, parent, false);
        Log.d("", "onCreateViewHolder");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExamRoutineAdapter.MyViewHolder holder, int position) {
        final ExamRoutineData examRoutineData = studentList.get(position);
        String br = "\n";
        holder.tvExamDate.setText(examRoutineData.getDate());
        if (!examRoutineData.getSecondExam().isEmpty()) {
            holder.tvSubject.setText(examRoutineData.getFirstExam() + br + examRoutineData.getSecondExam());
        } else {
            holder.tvSubject.setText(examRoutineData.getFirstExam());

        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private View viewCenterLine;
        private TextView tvExamDate, tvSubject;

        MyViewHolder(View view) {
            super(view);
            viewCenterLine = view.findViewById(R.id.view_center_line);
            tvExamDate = view.findViewById(R.id.tv_exam_date);
            tvSubject = view.findViewById(R.id.tv_subject);
        }
    }
}
