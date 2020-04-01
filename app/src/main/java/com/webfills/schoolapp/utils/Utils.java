package com.webfills.schoolapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.brouding.simpledialog.SimpleDialog;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static Utils utils = new Utils();

    private Utils() {
    }

    public void goTo(Context context, Class<?> className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    public void goTo(Context context, Class<?> className, String mode) {
        Intent intent = new Intent(context, className);
        intent.putExtra("mode", mode);
        context.startActivity(intent);
    }

    public void showMsg(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public String todayDate(String datePattern) {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        return df.format(c);
    }

    public void showNoticeDetails(Context context) {
        new SimpleDialog.Builder(context)
                .setTitle(Constants.noticeTitle)
                .setContent(Constants.noticeDate + " : " + Constants.noticeSubtitle)
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
    public void chooseImage(Activity activity, ArrayList<Image> images){
        ImagePicker.with(activity)                         //  Initialize ImagePicker with activity or fragment context
                .setToolbarColor("#212121")         //  Toolbar color
                .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                .setProgressBarColor("#4CAF50")     //  ProgressBar color
                .setBackgroundColor("#212121")      //  Background color
                .setCameraOnly(false)               //  Camera mode
                .setMultipleMode(true)              //  Select multiple images or single image
                .setFolderMode(true)                //  Folder mode
                .setShowCamera(true)                //  Show camera button
                .setFolderTitle("Albums")           //  Folder title (works with FolderMode = true)
                .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
                .setDoneTitle("Done")               //  Done button title
                .setLimitMessage("You have reached selection limit")    // Selection limit message
                .setMaxSize(10)                     //  Max images can be selected
                .setSavePath("ImagePicker")         //  Image capture folder name
                .setSelectedImages(images)          //  Selected images
                .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                .setRequestCode(100)                //  Set request code, default Config.RC_PICK_IMAGES
                .setKeepScreenOn(true)              //  Keep screen on when selecting images
                .start();
    }
}
