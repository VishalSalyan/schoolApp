package com.webfills.schoolapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webfills.schoolapp.R;
import com.webfills.schoolapp.data.StudentData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

public class StudentDetailsActivity extends AppCompatActivity {

    private TextView studentName, studentRollNumber, studentClass, studentPhoneNumber, studentAddress,studentPassword;
    private ImageView studentProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        initViews();
        initViewsWithData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemAdd = menu.findItem(R.id.add);
        MenuItem itemDelete = menu.findItem(R.id.delete);
        itemAdd.setVisible(false);
        itemDelete.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("", "home pressed");
            finish();
            return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    private void initViews() {
        studentName = findViewById(R.id.tv_name);
        studentRollNumber = findViewById(R.id.tv_roll_number);
        studentClass = findViewById(R.id.tv_class);
        studentPhoneNumber = findViewById(R.id.tv_phone_number);
        studentAddress = findViewById(R.id.tv_address);
        studentPassword = findViewById(R.id.tv_student_password);
        studentProfile = findViewById(R.id.iv_student_profile);
    }

    private void initViewsWithData() {
        if (SessionData.I().isEditable){
            studentPassword.setVisibility(View.VISIBLE);

        }else{
            studentPassword.setVisibility(View.GONE);
        }
        String id = SessionData.I().studentId;
        fetchStudentDetails(id);
    }

    private void fetchStudentDetails(String studentId) {
        FireBaseRepo.getInstance.getStudentDetails(studentId, new ServerResponse<StudentData>() {
            @Override
            public void onSuccess(StudentData body) {
                studentName.setText(body.getStudentName());
                studentRollNumber.setText(body.getRollNumber());
                studentClass.setText(body.getClassName());
                studentPhoneNumber.setText(body.getPhoneNumber());
                studentAddress.setText(body.getStudentAddress());
                studentPassword.setText(body.getStudentPassword());
                Picasso.get().load(body.getStudentImage()).into(studentProfile);
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

}
