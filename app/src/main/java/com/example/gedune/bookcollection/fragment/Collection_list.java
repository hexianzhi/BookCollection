package com.example.gedune.bookcollection.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.gedune.bookcollection.AppProfile;
import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.adpater.CollectionListAdapter;
import com.example.gedune.bookcollection.adpater.CollectionTilingAdapter;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.ResCompat;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMovementListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gedune on 2017/2/1.
 */

public class Collection_list extends Fragment {

    @BindView(R.id.toolbar_imgBtn)
    ImageButton mSwitchBtn;

    @BindView(R.id.main_recyclerView)
    SwipeMenuRecyclerView mRecyclerView;


    private RecyclerView gridReView;
    private SwipeMenuRecyclerView listReView;
    private CollectionListAdapter listAdapter;
    private CollectionTilingAdapter tilingAdapter;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private Activity mActivity;
    private List<BookDetail> books;
    private OrmHelper helper;
    private ListViewDecoration listViewDecoration;
    private Boolean isList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection_list, null);
        ButterKnife.bind(this,view);
        mActivity = getActivity();
        return view;

    }


    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                changeLayout();
            }
        });

        helper = OrmHelper.getInstance(mActivity);
        books = helper.queryBooks();
        Log.e("fuck",books.toString());

        if (books != null){
            listAdapter = new CollectionListAdapter(mActivity,books);
            tilingAdapter = new CollectionTilingAdapter(mActivity,books);
        }else {

            listAdapter = new CollectionListAdapter(mActivity);
            tilingAdapter = new CollectionTilingAdapter(mActivity);
        }



        gridLayoutManager = new GridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
        linearLayoutManager = new LinearLayoutManager(mActivity);

        listReView = new SwipeMenuRecyclerView(mActivity);
        gridReView = new RecyclerView(mActivity);

        listViewDecoration = new ListViewDecoration();
        listReView.setLayoutManager(linearLayoutManager);// 布局管理器。
        isList = true;
        listReView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        listReView.addItemDecoration( listViewDecoration);// 添加分割线。
        listReView.setSwipeMenuCreator(swipeMenuCreator);
        listReView.setSwipeMenuItemClickListener(listItemMenu);
        listReView.setAdapter(listAdapter);

        gridReView.setLayoutManager(gridLayoutManager);
        gridReView.setAdapter(tilingAdapter);

        mRecyclerView = listReView;
    }

    private void changeLayout() {

        if (isList){
             mRecyclerView = (SwipeMenuRecyclerView) gridReView;
            mSwitchBtn.setBackground(AppProfile.getContext().getResources().getDrawable(R.drawable.listicon));
            isList = false;

        }else {
            mRecyclerView= listReView;
            mSwitchBtn.setBackground(AppProfile.getContext().getResources().getDrawable(R.drawable.tile_shape));
            isList = true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * 当Item被移动之前。
     */
    public static OnItemMovementListener onItemMovementListener1 = new OnItemMovementListener() {
        @Override
        public int onDragFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder) {
            return OnItemMovementListener.RIGHT;// 返回无效的方向。
        }
        @Override
        public int onSwipeFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder) {

            return OnItemMovementListener.RIGHT;// 返回无效的方向。
        }
    };



    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        int width = AppProfile.getContext().getResources().getDimensionPixelSize(R.dimen.my_item_width);

        // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {

            SwipeMenuItem deleteItem = new SwipeMenuItem(mActivity)
                    .setBackgroundDrawable(R.color.red_dark)
                    .setText("删除") // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。.
        }
    };


    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener listItemMenu = new OnSwipeMenuItemClickListener() {
        /**Item的菜单被点击的时候调用
         * 。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                listAdapter.notifyItemRemoved(adapterPosition);
            }
        }
    };


    public class ListViewDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDrawable;

        public ListViewDecoration() {
            mDrawable = ResCompat.getDrawable(AppProfile.getContext(),R.drawable.divider_recycler);
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
}
