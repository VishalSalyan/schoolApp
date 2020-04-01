package com.webfills.schoolapp.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.webfills.schoolapp.R;
import com.webfills.schoolapp.data.User;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;
import com.webfills.schoolapp.utils.SessionData;

import static com.webfills.schoolapp.utils.Utils.utils;

/**
 * Created by shreyansh on 26-10-2017.
 */

public class LoginActivity extends AppCompatActivity {
    private Button bLogin;
    private TextView createUser;
    private EditText etPassword, etUser;
    private RadioButton rbAdmin, rbTeacher, rbStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initViewsWithData();
    }

    private void initViews() {
        bLogin = findViewById(R.id.bLogin);
        createUser = findViewById(R.id.create_user);
        etUser = findViewById(R.id.et_user);
        etPassword = findViewById(R.id.et_password);
        rbAdmin = findViewById(R.id.rb_admin);
        rbTeacher = findViewById(R.id.rb_teacher);
        rbStudent = findViewById(R.id.rb_student);
    }

    private void initViewsWithData() {
        checkUserAdmin();

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.goTo(LoginActivity.this, CreateUserActivity.class);
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isValidate()) {
                    return;
                }

                String user = etUser.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                if (rbAdmin.isChecked()) {
                    FireBaseRepo.getInstance.login(user, pass, Constants.ADMIN, new ServerResponse<String>() {
                        @Override
                        public void onSuccess(String body) {
//                            if (body.equalsIgnoreCase(Constants.ADMIN)) {
                            SessionData.I().isEditable = true;
//                            } else if (body.equalsIgnoreCase(Constants.TEACHER)) {
//                                SessionData.I().isEditable = true;
//                            } else if (body.equalsIgnoreCase(Constants.STUDENT)) {
//                                SessionData.I().isEditable = false;
//                            }
                            utils.goTo(LoginActivity.this, MainActivity.class);

                        }

                        @Override
                        public void onFailure(Throwable error) {
                            utils.showMsg(LoginActivity.this, error.toString());
                        }
                    });
                } else if (rbTeacher.isChecked()) {
                    FireBaseRepo.getInstance.login(user, pass, Constants.TEACHER, new ServerResponse<String>() {
                        @Override
                        public void onSuccess(String body) {
//                            if (body.equalsIgnoreCase(Constants.ADMIN)) {
//                                SessionData.I().isEditable = true;
//                            } else if (body.equalsIgnoreCase(Constants.TEACHER)) {
                            SessionData.I().isEditable = true;
//                            } else if (body.equalsIgnoreCase(Constants.STUDENT)) {
//                                SessionData.I().isEditable = false;
//                            }
                            utils.goTo(LoginActivity.this, MainActivity.class);

                        }

                        @Override
                        public void onFailure(Throwable error) {
                            utils.showMsg(LoginActivity.this, error.toString());
                        }
                    });
                } else if (rbStudent.isChecked()) {
                    FireBaseRepo.getInstance.login(user, pass, Constants.STUDENT, new ServerResponse<String>() {
                        @Override
                        public void onSuccess(String body) {
//                            if (body.equalsIgnoreCase(Constants.ADMIN)) {
//                                SessionData.I().isEditable = true;
//                            } else if (body.equalsIgnoreCase(Constants.TEACHER)) {
//                                SessionData.I().isEditable = true;
//                            } else if (body.equalsIgnoreCase(Constants.STUDENT)) {
                            SessionData.I().isEditable = false;
//                            }
                            utils.goTo(LoginActivity.this, MainActivity.class);

                        }

                        @Override
                        public void onFailure(Throwable error) {
                            utils.showMsg(LoginActivity.this, error.toString());
                        }
                    });
                }


            }
        });
    }

    private void checkUserAdmin() {
        FireBaseRepo.getInstance.checkAdmin(new ServerResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean body) {
                if (!body) {
                    createUserAdmin();
                }
            }

            @Override
            public void onFailure(Throwable error) {

            }
        });
    }

    private void createUserAdmin() {
        User user = new User();
        user.setUserName("Vishal");
        user.setPassword("2193");
        user.setUserType("Admin");
        SessionData.I().isEditable = true;
        FireBaseRepo.getInstance.createAdmin(user);
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (etUser.getText().toString().isEmpty()) {
            isValid = false;
            etUser.setError("Please fill the field");
        }
        if (etPassword.getText().toString().isEmpty()) {
            isValid = false;
            etPassword.setError("Please fill the field");
        }
        if (rbAdmin.isChecked() || rbTeacher.isChecked() || rbStudent.isChecked()) {

        } else {
            isValid = false;
            utils.showMsg(LoginActivity.this, "Select Type of login");
        }
        return isValid;
    }
}
