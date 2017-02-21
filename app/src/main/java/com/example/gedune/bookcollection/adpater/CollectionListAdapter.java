package com.example.gedune.bookcollection.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.example.gedune.bookcollection.utils.ResourceHelper;
import com.example.gedune.bookcollection.utils.image.ImageUtils;
import com.example.gedune.bookcollection.widget.SlidingButtonView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by gedune on 2017/2/1.
 */

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.ListAdpaterViewHolder> implements SlidingButtonView.IonSlidingButtonListener{


    private  Context mContext;
    private List<BookDetail> books;
    private OrmHelper helper;
    private SlidingButtonView mMenu = null;

    public CollectionListAdapter(Context context ) {
        this.mContext = context;
        helper = OrmHelper.getInstance(context);
    }

    public CollectionListAdapter(Context context,List<BookDetail> bookDetails) {
        this.mContext = context;
        books = bookDetails;
        helper = OrmHelper.getInstance(context);
    }

    @Override
    public ListAdpaterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_collection_list_itemt, parent, false);
        return new ListAdpaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdpaterViewHolder holder, int position) {
        holder.fillData(books.get(position));
    }

    public class ListAdpaterViewHolder extends RecyclerView.ViewHolder   {

        @BindView(R.id.book_img)
        ImageView mBookImg;

        @BindView(R.id.book_name)
        TextView mBookName;

        @BindView(R.id.book_des)
        TextView mBookdes;

        @BindView(R.id.list_adapter_rl)
        RelativeLayout mRlContent;

        @BindView(R.id.book_delete)
        TextView mBookDelete;

        public ListAdpaterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            ((SlidingButtonView) itemView).setSlidingButtonListener(CollectionListAdapter.this);

        }

        public void fillData(BookDetail bookDetail){
            mBookName.setText(bookDetail.getTitle());
            mBookdes.setText(bookDetail.getAuthors());
            ImageUtils.setUrl(mBookImg,bookDetail.getImage());

            mRlContent.getLayoutParams().width = ResourceHelper.getDisplayWidth();
            mRlContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断是否有删除菜单打开
                    if (menuIsOpen()) {
                        closeMenu();//关闭菜单
                    } else {
                        int n = getAdapterPosition();
                        mIDeleteBtnClickListener.onItemClick(v, n);
                    }
                }
            });
            mBookDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = getAdapterPosition();
                    mIDeleteBtnClickListener.onDeleteBtnCilck(v, n);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setItems(List<BookDetail> items) {
        this.books.clear();
        this.books.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<BookDetail> items) {
        int insertPosition = this.books.size();
        this.books.addAll(items);
        notifyItemRangeInserted(insertPosition, items.size());
    }

    public void removeItem(int position){
        helper.deleteBook(books.get(position));
        books.remove(position);
        notifyItemRemoved(position);
    }

    public BookDetail getItem(int position) {
        return books.get(position);
    }

    public List<BookDetail> getItems() {
        return books;
    }


    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }


    /**
     * 滑动或者点击了Item监听
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                closeMenu();
            }
        }
    }


    /**     * 关闭菜单     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;
    }

    /**     * 判断是否有菜单打开     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        return false;
    }
    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    public void setmIDeleteBtnClickListener(IonSlidingViewClickListener listener){
        mIDeleteBtnClickListener  = listener;
    }

    public interface IonSlidingViewClickListener {
        void onItemClick(View view,int position);
        void onDeleteBtnCilck(View view,int position);
    }
}
