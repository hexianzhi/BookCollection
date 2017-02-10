package com.example.gedune.bookcollection.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.adpater.CollectionTilingAdapter;
import com.example.gedune.bookcollection.orm.OrmHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gedune on 2017/2/8.
 */

public class CollectionTile extends Fragment{


    @BindView(R.id.toolbar_imgBtn)
    ImageButton mSwitchBtn;

    @BindView(R.id.fragment_list_swReView)
    RecyclerView mRecyclerView;



    private CollectionTilingAdapter tileAdpater;
    private View rootView;
    private Activity mActivity;
    private List<BookDetail> books;
    private OrmHelper helper;
    private GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.collection_statistic,null);
        ButterKnife.bind(this,rootView);
        mActivity = getActivity();
        return  rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        mSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fl,new CollectionList()).commit();
            }
        });

        helper = OrmHelper.getInstance(mActivity);
        books = helper.queryBooks();

        if (books != null){
            tileAdpater = new CollectionTilingAdapter(mActivity,books);
        }else {
            tileAdpater = new CollectionTilingAdapter(mActivity);
        }

        gridLayoutManager = new GridLayoutManager(mActivity,3,GridLayoutManager.VERTICAL,false);

        mRecyclerView.setAdapter(tileAdpater);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }
}
