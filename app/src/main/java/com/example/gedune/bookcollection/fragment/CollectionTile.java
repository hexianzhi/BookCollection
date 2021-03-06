package com.example.gedune.bookcollection.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.activity.BookDetailActivity;
import com.example.gedune.bookcollection.adpater.CollectionTilingAdapter;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.example.gedune.bookcollection.widget.SimpleDialog;

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

    @BindView(R.id.noDataHintTv)
    TextView noDataTv;

    private CollectionTilingAdapter tileAdpater;
    private View rootView;
    private Activity mActivity;
    private List<BookDetail> books;
    private OrmHelper helper;
    private GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_collection_tile,null);
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
            noDataTv.setVisibility(View.GONE);
            tileAdpater = new CollectionTilingAdapter(mActivity,books);
            tileAdpater.setItemClickListener(new CollectionTilingAdapter.mOnItemClickListener() {
                @Override
                public void onLongClick(RecyclerView.ViewHolder holder, final int position) {

                    SimpleDialog.Builder builder = new SimpleDialog.Builder(mActivity);
                    builder.setCancelButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setconfirmButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tileAdpater.removeItem(position);
                            tileAdpater.notifyItemRemoved(position);
                            dialog.dismiss();

                        }
                    });
                    builder.create().show();
                }

                @Override
                public void onClick(RecyclerView.ViewHolder holder, int position) {
                    BookDetail bookDetail = tileAdpater.getItem(position);
                    Intent i = new Intent(mActivity, BookDetailActivity.class);
                    i.putExtra(BookDetailActivity.sBOOK, bookDetail);
                    startActivity(i);
                }
            });

            gridLayoutManager = new GridLayoutManager(mActivity,3,GridLayoutManager.VERTICAL,false);

            mRecyclerView.setAdapter(tileAdpater);
            mRecyclerView.setLayoutManager(gridLayoutManager);

        }else {
            tileAdpater = new CollectionTilingAdapter(mActivity);
        }


    }


}
