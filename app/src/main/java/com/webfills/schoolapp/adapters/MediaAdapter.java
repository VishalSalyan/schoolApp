package com.webfills.schoolapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.webfills.schoolapp.R;
import com.webfills.schoolapp.activity.ImageActivity;
import com.webfills.schoolapp.data.MediaData;

import java.util.ArrayList;

import static com.webfills.schoolapp.utils.Utils.utils;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {

    private ArrayList<MediaData> mediaList;
    private Context context;

    public MediaAdapter(ArrayList<MediaData> mediaList, Context context) {
        this.mediaList = mediaList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_media, parent, false);
        Log.d("", "onCreateViewHolder");
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MediaData mediaData = mediaList.get(position);
        Log.d("", "position : " + position + " date : " + mediaData.getUpdatedDate());

        Picasso.get().load(mediaData.getFileUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.goTo(context, ImageActivity.class, mediaData.getFileUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_media);
        }
    }
}