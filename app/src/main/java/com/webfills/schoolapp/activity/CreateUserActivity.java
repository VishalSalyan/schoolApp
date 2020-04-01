package com.webfills.schoolapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.data.TeacherData;
import com.webfills.schoolapp.data.User;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.utils.Constants;

import static com.webfills.schoolapp.utils.Utils.utils;

public class CreateUserActivity extends AppCompatActivity {

    private Button login;
    private EditText password, userName;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        initViews();
        initViewsWithData();
    }

    private void initViews() {
        login = findViewById(R.id.btn_login);
        password = findViewById(R.id.et_password);
        userName = findViewById(R.id.user_name);
    }

    private void initViewsWithData() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }

                boolean isTeacher = false;
                String name = userName.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String userType = Constants.TEACHER;

                TeacherData user = new TeacherData();
                user.setTeacherName(name);
                user.setPassword(pass);
                user.setUserType(userType);


                FireBaseRepo.getInstance.createTeacher(user);

                utils.showMsg(CreateUserActivity.this, "Created Successfully");
                finish();
            }
        });
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (userName.getText().toString().trim().isEmpty()) {
            isValid = false;
            userName.setError("Please fill the field");
        }
        if (password.getText().toString().trim().isEmpty()) {
            isValid = false;
            password.setError("Please fill the field");
        }

        return isValid;
    }
}
