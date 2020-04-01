package com.webfills.schoolapp.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webfills.schoolapp.R;


/**
 * Created by shreyansh on 03-05-2017.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=(View)inflater.inflate(R.layout.fragment_dashboard,container,false);
        return rootView;
    }

    @Override
    public void onClick(View v) {
    }
}
