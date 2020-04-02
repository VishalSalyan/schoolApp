package com.webfills.schoolapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webfills.schoolapp.R;

public class ImageActivity extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private String imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = findViewById(R.id.iv_image);
        progressBar = findViewById(R.id.progress_bar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imageUrl = extras.getString("mode");
        }
        progressBar.setVisibility(View.VISIBLE);

        Picasso.get().load(imageUrl).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
