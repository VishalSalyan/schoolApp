package com.webfills.schoolapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nguyenhoanglam.imagepicker.model.Config;
import com.webfills.schoolapp.R;
import com.webfills.schoolapp.adapters.MediaAdapter;
import com.webfills.schoolapp.data.MediaData;
import com.webfills.schoolapp.repositories.FireBaseRepo;
import com.webfills.schoolapp.repositories.ServerResponse;
import com.webfills.schoolapp.utils.SessionData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

/**
 * Created by PA01 on 10/29/2017.
 */

public class MediaActivity extends AppCompatActivity {

    private RecyclerView rvMedia;
    private ArrayList<MediaData> mediaList = new ArrayList<>();
    private MediaAdapter mediaAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Photo Gallery");
        rvMedia = findViewById(R.id.rv_recycler_view);
        fetchMedia();
        setList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem itemAdd = menu.findItem(R.id.add);
        MenuItem itemDelete = menu.findItem(R.id.delete);
        if (SessionData.I().isEditable) {
            itemAdd.setVisible(true);
            itemDelete.setVisible(true);
        } else {
            itemAdd.setVisible(false);
            itemDelete.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("", "home pressed");
//                if (SessionData.I().isDeleteMode) {
//                    SessionData.I().isDeleteMode = false;
//                    noticeListAdapter.notifyDataSetChanged();
//                } else {
                finish();
//                }
                return true;
            case R.id.add:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, Config.RC_PICK_IMAGES);
                return (true);
            case R.id.delete:
                //add the function to perform here
//                SessionData.I().isDeleteMode = true;
//                noticeListAdapter.notifyDataSetChanged();
//                setList();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);  // You MUST have this line to be here
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
//            images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            data.getData();
            final String fileName = "image" + System.currentTimeMillis();
            FireBaseRepo.getInstance.uploadFile(fileName, data.getData()/*Uri.parse(String.valueOf(images))*/, new ServerResponse<String>() {
                @Override
                public void onSuccess(String body) {
                    MediaData mediaData = new MediaData();
                    mediaData.setTitle(fileName);
                    mediaData.setDescription(fileName);
                    mediaData.setUpdatedDate(utils.todayDate("dd MMM"));
                    mediaData.setFileUrl(body);
                    FireBaseRepo.getInstance.createMedia(mediaData);
                }

                @Override
                public void onFailure(Throwable error) {

                }
            });
            // do your logic here...
        }

    }

    private void setList() {
        mediaAdapter = new MediaAdapter(mediaList, MediaActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvMedia.setLayoutManager(gridLayoutManager);
        rvMedia.setAdapter(mediaAdapter);
    }

    private void fetchMedia() {
        FireBaseRepo.getInstance.fetchMedia(new ServerResponse<ArrayList<MediaData>>() {

            @Override
            public void onSuccess(ArrayList<MediaData> body) {
                mediaList.clear();
                mediaList.addAll(body);
                mediaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {
                utils.showMsg(MediaActivity.this, error.toString());
            }
        });
    }
}
