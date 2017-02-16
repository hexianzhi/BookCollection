package com.example.gedune.bookcollection.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by gedune on 2017/2/1.
 */

public class CollectionTilingAdapter extends SwipeMenuAdapter<CollectionViewHolder>  {
    private  Context mContext;
    private List<BookDetail> books;
    private OrmHelper helper;

    public CollectionTilingAdapter(Context context ) {
        this.mContext = context;
        helper = OrmHelper.getInstance(context);
    }
    public CollectionTilingAdapter(Context context, List<BookDetail> bookDetails) {
        this.mContext = context;
        books = bookDetails;
        helper = OrmHelper.getInstance(context);
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_collection_tile_item, parent, false);
        return view;
    }

    @Override
    public CollectionViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new CollectionViewHolder(realContentView);
    }


    @Override
    public void onBindViewHolder(final CollectionViewHolder holder, final int position) {
        holder.fillData(books.get(position));

        if (itemClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickListener.onLongClick(holder,position);
                    return false;
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

    public BookDetail getItem(int position) {
        return books.get(position);
    }

    public List<BookDetail> getItems() {
        return books;
    }

    public void removeItem(int positon){
        helper.deleteBook(books.get(positon));
        books.remove(positon);
        this.notifyItemRemoved(positon);
    }

    public interface mOnItemClickListener{
        void onLongClick(RecyclerView.ViewHolder holder, int position);
    }

    private mOnItemClickListener itemClickListener;

    public void setItemClickListener(mOnItemClickListener listener){
        this.itemClickListener = listener;
    }


}
