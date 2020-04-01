package com.webfills.schoolapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.nguyenhoanglam.imagepicker.model.Config;
import com.squareup.picasso.Picasso;
import com.webfills.schoolapp.R;
import com.webfills.schoolapp.adapters.ExamRoutineData;
import com.webfills.schoolapp.data.AssignmentData;
import com.webfills.schoolapp.data.EventsData;
import com.webfills.schoolapp.data.HolidaysData;
import com.webfills.schoolapp.data.HomeworkData;
import com.webfills.schoolapp.data.NoticeData;
import com.webfills.schoolapp.data.ResultData;
import com.webfills.schoolapp.data.StudentData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.webfills.schoolapp.utils.Utils.utils;

public class CreateNoticeActivity extends AppCompatActivity {
    private Button btnSubmit;
    private EditText etTitle, etDescription;
    private ProgressBar progressBar;
    private LinearLayout llDetailsLayout;

    private LinearLayout llStudentLayout;
    private Button btn_create;
    private EditText etClass, etRollNo, etStudentName, etAddress, etPhone, etPassword;
    private ImageView iv_profile_image;
    private String studentProfileImage;

    private LinearLayout llExamLayout;
    private EditText etExamDate, etFirstSubject, etSecondSubject;
    private Button btnExamSubmit;
    private final Calendar myCalendar = Calendar.getInstance();

    private String mode = null;
    final static int PICK_PDF_CODE = 2342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initViewsWithData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("", "home pressed");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);
        btnSubmit = findViewById(R.id.btn_submit);
        etTitle = findViewById(R.id.et_title);
        etDescription = findViewById(R.id.et_description);
        llDetailsLayout = findViewById(R.id.ll_details_layout);

        llStudentLayout = findViewById(R.id.ll_student_layout);
        iv_profile_image = findViewById(R.id.iv_profile_image);
        etStudentName = findViewById(R.id.et_student_name);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        etRollNo = findViewById(R.id.et_roll_no);
        etClass = findViewById(R.id.et_class);
        etPassword = findViewById(R.id.et_password);
        btn_create = findViewById(R.id.btn_create);

        llExamLayout = findViewById(R.id.ll_exam_layout);
        etExamDate = findViewById(R.id.et_exam_date);
        etFirstSubject = findViewById(R.id.et_first_subject);
        etSecondSubject = findViewById(R.id.et_second_subject);
        btnExamSubmit = findViewById(R.id.btn_exam_submit);
    }

    private void initViewsWithData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString("mode").equalsIgnoreCase(Constants.HOLIDAYS)) {
                mode = Constants.HOLIDAYS;
            }
            if (extras.getString("mode").equalsIgnoreCase(Constants.EVENTS)) {
                mode = Constants.EVENTS;
            }
            if (extras.getString("mode").equalsIgnoreCase(Constants.NOTICE)) {
                mode = Constants.NOTICE;
            }
            if (extras.getString("mode").equalsIgnoreCase(Constants.ASSIGNMENT)) {
                mode = Constants.ASSIGNMENT;
                etTitle.setHint("File name");
            }
            if (extras.getString("mode").equalsIgnoreCase(Constants.RESULT)) {
                mode = Constants.RESULT;
            }
            if (extras.getString("mode").equalsIgnoreCase(Constants.EXAM_ROUTINE)) {
                llDetailsLayout.setVisibility(View.GONE);
                llExamLayout.setVisibility(View.VISIBLE);
                mode = Constants.EXAM_ROUTINE;
            }
            if (extras.getString("mode").equalsIgnoreCase(Constants.STUDENT)) {
                llDetailsLayout.setVisibility(View.GONE);
                llStudentLayout.setVisibility(View.VISIBLE);
                mode = Constants.STUDENT;
            }
            if (extras.getString("mode").equalsIgnoreCase(Constants.HOMEWORK)) {
                etTitle.setHint("Subject");
                mode = Constants.HOMEWORK;
            }

            //The key argument here must match that used in the other activity
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }
                switch (mode) {
                    case Constants.HOLIDAYS:
                        createHolidays();
                        break;
                    case Constants.NOTICE:
                        createNotice();
                        break;
                    case Constants.HOMEWORK:
                        createHomework();
                        break;
                    case Constants.EVENTS:
                        createEvents();
                        break;
                    case Constants.ASSIGNMENT:
                        createAssignments();
                        break;
                    case Constants.RESULT:
                        createResult();
                        break;
                }
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createStudent();
            }
        });
        iv_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Config.RC_PICK_IMAGES);
            }
        });

        btnExamSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createExamRoutine();
            }
        });

        etExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateNoticeActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void createHomework() {
        HomeworkData homeworkData = new HomeworkData();
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        homeworkData.setDate(utils.todayDate("dd MMM")/*"29 Jan"*/);
        homeworkData.setSubjectName(title);
        homeworkData.setDescription(description);
        FireBaseRepo.getInstance.createHomework(homeworkData, new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                utils.showMsg(CreateNoticeActivity.this, body);
                finish();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(CreateNoticeActivity.this, error.toString());
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd MMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etExamDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void createExamRoutine() {
        String date = etExamDate.getText().toString().trim();
        String firstSubject = etFirstSubject.getText().toString().trim();
        String secondSubject = etSecondSubject.getText().toString().trim();
        ExamRoutineData examRoutineData = new ExamRoutineData();
        examRoutineData.setDate(date);
        examRoutineData.setFirstExam(firstSubject);
        examRoutineData.setSecondExam(secondSubject);
        FireBaseRepo.getInstance.createExamRoutine(examRoutineData, new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                utils.showMsg(CreateNoticeActivity.this, body);
                finish();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(CreateNoticeActivity.this, error.getMessage());
            }
        });
    }

    private void createEvents() {
        EventsData eventsData = new EventsData();
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        eventsData.setDate(utils.todayDate("dd MMM")/*"29 Jan"*/);
        eventsData.setTitle(title);
        eventsData.setSubtitle(description);
        FireBaseRepo.getInstance.createEvents(eventsData, new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                utils.showMsg(CreateNoticeActivity.this, body);
                finish();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(CreateNoticeActivity.this, error.toString());
            }
        });
    }

    private void createNotice() {
        NoticeData noticeData = new NoticeData();
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        noticeData.setDate(utils.todayDate("dd MMM")/*"29 Jan"*/);
        noticeData.setTitle(title);
        noticeData.setSubtitle(description);
        FireBaseRepo.getInstance.createNotice(noticeData, new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                utils.showMsg(CreateNoticeActivity.this, body);
                finish();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(CreateNoticeActivity.this, error.toString());
            }
        });
    }

    private void createHolidays() {
        HolidaysData holidaysData = new HolidaysData();
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        holidaysData.setDate(utils.todayDate("dd MMM")/*"29 Jan"*/);
        holidaysData.setTitle(title);
        holidaysData.setSubtitle(description);
        FireBaseRepo.getInstance.createHolidays(holidaysData, new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                utils.showMsg(CreateNoticeActivity.this, body);
                finish();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(CreateNoticeActivity.this, error.toString());
            }
        });
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (etTitle.getText().toString().isEmpty()) {
            isValid = false;
            etTitle.setError("Please fill this field");
        }
        if (etDescription.getText().toString().isEmpty()) {
            isValid = false;
            etDescription.setError("Please fill this field");
        }
        return isValid;
    }

    private void createAssignments() {
        getPDF();
    }

    private void createResult() {
        getPDF();
    }

    private void createStudent() {
        if (!isStudentValidate()) {
            return;
        }
        String studentName = etStudentName.getText().toString().trim();
        String className = etClass.getText().toString().trim();
        String rollNumber = etRollNo.getText().toString().trim();
        String phoneNumber = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String studentId = etClass.getText().toString().trim() + etRollNo.getText().toString().trim();
        String studentPassword = etPassword.getText().toString().trim();

        StudentData studentData = new StudentData();
        studentData.setStudentName(studentName);
        studentData.setRollNumber(rollNumber);
        studentData.setClassName(className);
        studentData.setStudentImage(studentProfileImage);
        studentData.setStudentAddress(address);
        studentData.setPhoneNumber(phoneNumber);
        studentData.setStudentId(studentId);
        studentData.setStudentPassword(studentPassword);

        FireBaseRepo.getInstance.createStudent(studentData);
        finish();
    }

    private boolean isStudentValidate() {
        boolean isValid = true;
        if (etStudentName.getText().toString().trim().isEmpty()) {
            etStudentName.setError("Please fill this field");
            isValid = false;
        }
        if (etClass.getText().toString().trim().isEmpty()) {
            etClass.setError("Please fill this field");
            isValid = false;
        }
        if (etRollNo.getText().toString().trim().isEmpty()) {
            etRollNo.setError("Please fill this field");
            isValid = false;
        }
        if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setError("Please fill this field");
            isValid = false;
        }
        if (etAddress.getText().toString().trim().isEmpty()) {
            etAddress.setError("Please fill this field");
            isValid = false;
        }
        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError("Please fill this field");
            isValid = false;
        }
        if (studentProfileImage.isEmpty()) {
            utils.showMsg(CreateNoticeActivity.this, "Please select image");
            isValid = false;
        }
        return isValid;
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                String fileName = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                uploadFile(fileName, description, data.getData());

            } else {
                utils.showMsg(this, "No file chosen");
            }
        } else if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                Picasso.get().load(data.getData()).into(iv_profile_image);
                uploadFile("IMG" + System.currentTimeMillis(), "IMG" + System.currentTimeMillis(), data.getData());
            } else {
                utils.showMsg(this, "No file chosen");
            }
        }
    }

    private void uploadFile(final String fileName, final String description, Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        btn_create.setEnabled(false);

        FireBaseRepo.getInstance.uploadFile(fileName, data, new ServerResponse<String>() {
            @Override
            public void onSuccess(String body) {
                if (mode.equals(Constants.ASSIGNMENT)) {
                    AssignmentData assignmentData = new AssignmentData();
                    assignmentData.setTitle(fileName);
                    assignmentData.setDescription(description);
                    assignmentData.setUpdatedDate(utils.todayDate("dd MMM"));
                    assignmentData.setFileUrl(body);
                    FireBaseRepo.getInstance.createAssignments(assignmentData);
                } else if (mode.equals(Constants.RESULT)) {
                    ResultData resultData = new ResultData();
                    resultData.setTitle(fileName);
                    resultData.setDescription(description);
                    resultData.setUpdatedDate(utils.todayDate("dd MMM"));
                    resultData.setFileUrl(body);
                    FireBaseRepo.getInstance.createResult(resultData);
                } else if (mode.equals(Constants.STUDENT)) {
                    studentProfileImage = body;
                    btn_create.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                progressBar.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(CreateNoticeActivity.this, error.toString());
                btn_create.setEnabled(true);
            }
        });

    }

}
