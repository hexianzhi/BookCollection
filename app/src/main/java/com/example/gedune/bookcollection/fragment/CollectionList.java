package com.example.gedune.bookcollection.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.activity.BookDetailActivity;
import com.example.gedune.bookcollection.adpater.CollectionListAdapter;
import com.example.gedune.bookcollection.orm.OrmHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gedune on 2017/2/1.
 */

public class CollectionList extends Fragment implements CollectionListAdapter.IonSlidingViewClickListener{

    @BindView(R.id.toolbar_imgBtn)
    ImageButton mSwitchBtn;

    @BindView(R.id.fragment_list_swReView)
    RecyclerView mRecyclerView;

    private CollectionListAdapter listAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Activity mActivity;
    private List<BookDetail> books;
    private OrmHelper helper;
    private ListViewDecoration listViewDecoration;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_collection_list, null);
        ButterKnife.bind(this,rootView);
        mActivity = getActivity();
        return rootView;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fl,new CollectionTile()).commit();
            }
        });

        helper = OrmHelper.getInstance(mActivity);
        books = helper.queryBooks();

        if (books != null){
            listAdapter = new CollectionListAdapter(mActivity,books);
        }else {
            listAdapter = new CollectionListAdapter(mActivity);
        }

        listAdapter.setmIDeleteBtnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(mActivity);
        listViewDecoration = new ListViewDecoration();

        mRecyclerView.setAdapter(listAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(listViewDecoration);// 添加分割线。

    }



    public class ListViewDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDrawable;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ListViewDecoration() {
            mDrawable = mActivity.getResources().getDrawable(R.drawable.divider_recycler);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                // 以下计算主要用来确定绘制的位置
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDrawable.getIntrinsicHeight();
                mDrawable.setBounds(left, top, right, bottom);
                mDrawable.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        BookDetail bookDetail = listAdapter.getItem(position);
        Intent i = new Intent(mActivity, BookDetailActivity.class);
        i.putExtra(BookDetailActivity.sBOOK, bookDetail);
        startActivity(i);

    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        Toast.makeText(mActivity, "删除" + position, Toast.LENGTH_SHORT).show();
        listAdapter.removeItem(position);
    }

}
