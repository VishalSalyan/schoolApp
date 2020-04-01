package com.webfills.schoolapp.repositories;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.webfills.schoolapp.R;
import com.webfills.schoolapp.adapters.ExamRoutineData;
import com.webfills.schoolapp.data.AssignmentData;
import com.webfills.schoolapp.data.EventsData;
import com.webfills.schoolapp.data.HolidaysData;
import com.webfills.schoolapp.data.HomeworkData;
import com.webfills.schoolapp.data.MediaData;
import com.webfills.schoolapp.data.NoticeData;
import com.webfills.schoolapp.data.ResultData;
import com.webfills.schoolapp.data.StudentData;
import com.webfills.schoolapp.data.TeacherData;
import com.webfills.schoolapp.data.User;
import com.webfills.schoolapp.utils.Constants;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.webfills.schoolapp.utils.Utils.utils;

public class FireBaseRepo {
    public static FireBaseRepo getInstance = new FireBaseRepo();

    private FireBaseRepo() {
    }

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference("user");
    private DatabaseReference teacherRef = database.getReference("teachers");
    private DatabaseReference studentRef = database.getReference("students");
    private DatabaseReference noticeRef = database.getReference("notice");
    private DatabaseReference holidayRef = database.getReference("holidays");
    private DatabaseReference eventRef = database.getReference("events");
    private DatabaseReference assignmentRef = database.getReference("assignment");
    private DatabaseReference resultRef = database.getReference("result");
    private DatabaseReference mediaRef = database.getReference("media");
    private DatabaseReference examRoutineRef = database.getReference("exam_routine");
    private DatabaseReference homeworkRef = database.getReference("homework");

    //File Storage
    private static final String BASE_URL = "gs://schoolgoa-75974.appspot.com";
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorageReference = storage.getReference();
    ;


    //Create Users
    public void createAdmin(User user) {
        // Write a message to the database
        userRef.push().setValue(user);
    }

    public void createTeacher(TeacherData teacherData) {
        teacherRef.setValue(teacherData);
    }

    public void createStudent(StudentData studentData) {
        studentRef.push().setValue(studentData);
    }

    //Login
    public void login(final String userName, final String password, final String userType, final ServerResponse<String> serverResponse) {
        // Read from the database
        if (userType.equalsIgnoreCase(Constants.ADMIN)) {
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean isNotFound = false;
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        assert user != null;
                        if (user.getUserName().equals(userName) && user.getPassword().equals(password) && user.getUserType().equals(userType)) {
                            serverResponse.onSuccess(user.getUserType());
                            break;
                        } else {
                            isNotFound = true;
                        }

                    }
                    if (isNotFound) {
                        serverResponse.onFailure(new Throwable("User not found"));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    serverResponse.onFailure(new Throwable(error.toString()));
                }
            });
        } else if (userType.equalsIgnoreCase(Constants.TEACHER)) {
            teacherRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean isNotFound = false;
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData user = snapshot.getValue(TeacherData.class);
                        assert user != null;
                        if (user.getTeacherName().equals(userName) && user.getPassword().equals(password) && user.getUserType().equals(userType)) {
                            serverResponse.onSuccess(user.getUserType());
                            break;
                        } else {
                            isNotFound = true;
                        }

                    }
                    if (isNotFound) {
                        serverResponse.onFailure(new Throwable("User not found"));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    serverResponse.onFailure(new Throwable(error.toString()));
                }
            });
        } else {
            studentRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean isNotFound = false;
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StudentData user = snapshot.getValue(StudentData.class);
                        assert user != null;
                        if (user.getStudentName().equals(userName) && user.getStudentPassword().equals(password)) {
                            serverResponse.onSuccess(Constants.STUDENT);
                            break;
                        } else {
                            isNotFound = true;
                        }

                    }
                    if (isNotFound) {
                        serverResponse.onFailure(new Throwable("User not found"));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    serverResponse.onFailure(new Throwable(error.toString()));
                }
            });
        }


    }

    public void checkAdmin(final ServerResponse<Boolean> serverResponse) {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isNotFound = false;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);

                        if (user.getUserType().equals(Constants.ADMIN)) {
                            serverResponse.onSuccess(true);
                            break;
                        } else {
                            serverResponse.onSuccess(false);
                            isNotFound = true;
                        }
                    }
                } else {
                    serverResponse.onSuccess(false);
                }

                if (isNotFound) {
                    serverResponse.onFailure(new Throwable("User not found"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                serverResponse.onFailure(new Throwable(error.toString()));
            }
        });
    }

    //Create, Fetch, Delete Notices
    public void createNotice(NoticeData noticeData, final ServerResponse<String> serverResponse) {
        noticeRef.push().setValue(noticeData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                serverResponse.onSuccess("Notice created Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                serverResponse.onFailure(new Throwable(e));
            }
        });
    }

    public void fetchNotice(final ServerResponse<ArrayList<NoticeData>> serverResponse) {
        noticeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<NoticeData> noticeList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoticeData noticeData = snapshot.getValue(NoticeData.class);
                    noticeList.add(noticeData);
                }
                serverResponse.onSuccess(noticeList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.toString()));
            }
        });
    }

    public void deleteNotice(final String title, final ServerResponse<String> serverResponse) {
        noticeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NoticeData noticeData = snapshot.getValue(NoticeData.class);
                    assert noticeData != null;
                    if (noticeData.getTitle().equalsIgnoreCase(title)) {
                        key = snapshot.getKey();
                        assert key != null;
                        noticeRef/*.child(key)*/.orderByChild("title").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
                                }
                                serverResponse.onSuccess("Deleted Successfully");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.toString()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.toString()));
            }
        });
    }

    //Create, Fetch, Delete Holidays
    public void createHolidays(HolidaysData holidaysData, final ServerResponse<String> serverResponse) {
        holidayRef.push().setValue(holidaysData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                serverResponse.onSuccess("Holidays created Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                serverResponse.onFailure(new Throwable(e));
            }
        });
    }

    public void fetchHolidays(final ServerResponse<ArrayList<HolidaysData>> serverResponse) {
        holidayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HolidaysData> holidaysList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HolidaysData holidaysData = snapshot.getValue(HolidaysData.class);
                    holidaysList.add(holidaysData);
                }
                serverResponse.onSuccess(holidaysList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.toString()));
            }
        });
    }

    public void deleteHolidays(final String title, final ServerResponse<String> serverResponse) {
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HolidaysData holidaysData = snapshot.getValue(HolidaysData.class);
                    assert holidaysData != null;
                    if (holidaysData.getTitle().equalsIgnoreCase(title)) {
                        key = snapshot.getKey();
                        assert key != null;
                        eventRef.orderByChild("title").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
                                }
                                serverResponse.onSuccess("Deleted Successfully");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.toString()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.toString()));
            }
        });
    }//Create, Fetch, Delete Holidays

    //Create, Fetch, Delete Events
    public void createEvents(EventsData eventsData, final ServerResponse<String> serverResponse) {
        eventRef.push().setValue(eventsData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                serverResponse.onSuccess("Event created Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                serverResponse.onFailure(new Throwable(e));
            }
        });
    }

    public void fetchEvents(final ServerResponse<ArrayList<EventsData>> serverResponse) {
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<EventsData> eventsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EventsData eventsData = snapshot.getValue(EventsData.class);
                    eventsList.add(eventsData);
                }
                serverResponse.onSuccess(eventsList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.toString()));
            }
        });
    }

    public void deleteEvents(final String title, final ServerResponse<String> serverResponse) {
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EventsData eventsData = snapshot.getValue(EventsData.class);
                    assert eventsData != null;
                    if (eventsData.getTitle().equalsIgnoreCase(title)) {
                        key = snapshot.getKey();
                        assert key != null;
                        eventRef.orderByChild("title").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
                                }
                                serverResponse.onSuccess("Deleted Successfully");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.toString()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.toString()));
            }
        });
    }

    public void uploadFile(String fileNameWithExtension, Uri data, final ServerResponse<String> serverResponse) {
        final StorageReference sRef = mStorageReference.child(fileNameWithExtension);
        sRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        serverResponse.onSuccess(uri.toString());
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        serverResponse.onFailure(new Throwable(exception.getMessage()));
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });
    }

    public void downloadFile(final Context context, String fileNameWithExtension, String storagePath) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(Constants.STORAGE_URL);
        StorageReference islandRef = storageRef.child(fileNameWithExtension);

        final File rootPath = new File(Environment.getExternalStorageDirectory(), storagePath);
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

//        final File localFile = new File(rootPath,"imageName.txt");
        final File localFile = new File(rootPath, fileNameWithExtension);

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                utils.showMsg(context, "Download Successful in " + rootPath.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                utils.showMsg(context, "Download Failed");
            }
        });
    }

    //Create, Fetch Assignments
    public void createAssignments(AssignmentData assignmentData) {
        assignmentRef.push().setValue(assignmentData);
    }

    public void fetchAssignments(final ServerResponse<ArrayList<AssignmentData>> serverResponse) {
        assignmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<AssignmentData> assignmentList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AssignmentData assignmentData = snapshot.getValue(AssignmentData.class);
                    assignmentList.add(assignmentData);
                }
                serverResponse.onSuccess(assignmentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    //Create, Fetch Results
    public void createResult(ResultData resultData) {
        resultRef.push().setValue(resultData);
    }

    public void fetchResults(final ServerResponse<ArrayList<ResultData>> serverResponse) {
        resultRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ResultData> resultList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ResultData resultData = snapshot.getValue(ResultData.class);
                    resultList.add(resultData);
                }
                serverResponse.onSuccess(resultList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    //Create, Fetch Media
    public void createMedia(MediaData mediaData) {
        mediaRef.push().setValue(mediaData);
    }

    public void fetchMedia(final ServerResponse<ArrayList<MediaData>> serverResponse) {
        mediaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MediaData> mediaData = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MediaData mediaList = snapshot.getValue(MediaData.class);
                    mediaData.add(mediaList);
                }
                serverResponse.onSuccess(mediaData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    //Create, Fetch Students
    public void getStudentDetails(final String studentId, final ServerResponse<StudentData> serverResponse) {
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StudentData studentData = snapshot.getValue(StudentData.class);
                    assert studentData != null;
                    if (studentData.getStudentId().equalsIgnoreCase(studentId)) {
                        serverResponse.onSuccess(studentData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchStudentsList(final ServerResponse<ArrayList<StudentData>> serverResponse) {
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<StudentData> studentList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StudentData studentData = snapshot.getValue(StudentData.class);
                    studentList.add(studentData);
                }
                serverResponse.onSuccess(studentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    //Create, Fetch Exam Routine
    public void createExamRoutine(ExamRoutineData examRoutineData, final ServerResponse<String> serverResponse) {
        examRoutineRef.push().setValue(examRoutineData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                serverResponse.onSuccess("Added Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                serverResponse.onFailure(new Throwable(e.getMessage()));
            }
        });
    }

    public void fetchExamRoutine(final ServerResponse<ArrayList<ExamRoutineData>> serverResponse) {
        examRoutineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ExamRoutineData> examRoutineList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ExamRoutineData examRoutineData = snapshot.getValue(ExamRoutineData.class);
                    examRoutineList.add(examRoutineData);
                }
                serverResponse.onSuccess(examRoutineList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    //Create, Fetch, Delete homework
    public void deleteHomework(final String subjectName, final ServerResponse<String> serverResponse) {
        homeworkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HomeworkData homeworkData = snapshot.getValue(HomeworkData.class);
                    assert homeworkData != null;
                    if (homeworkData.getSubjectName().equalsIgnoreCase(subjectName)) {
                        key = snapshot.getKey();
                        assert key != null;
                        noticeRef/*.child(key)*/.orderByChild("subjectName").equalTo(subjectName).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
                                }
                                serverResponse.onSuccess("Deleted Successfully");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.toString()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.toString()));
            }
        });
    }

    public void fetchHomework(final ServerResponse<ArrayList<HomeworkData>> serverResponse) {
        homeworkRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HomeworkData> homeworkList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HomeworkData homeworkData = snapshot.getValue(HomeworkData.class);
                    homeworkList.add(homeworkData);
                }
                serverResponse.onSuccess(homeworkList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.toString()));
            }
        });
    }

    public void createHomework(HomeworkData homeworkData, final ServerResponse<String> serverResponse) {
        homeworkRef.push().setValue(homeworkData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                serverResponse.onSuccess("Notice created Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                serverResponse.onFailure(new Throwable(e));
            }
        });
    }
}