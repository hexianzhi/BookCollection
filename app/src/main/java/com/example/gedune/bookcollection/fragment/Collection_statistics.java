package com.example.gedune.bookcollection.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gedune.bookcollection.R;

/**
 * Created by gedune on 2017/2/1.
 */

public class Collection_statistics extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collection_statistic,null);
        return  view;
    }
}
