package com.example.gedune.bookcollection.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by gedune on 2017/2/1.
 */

public class CollectionListAdapter extends SwipeMenuAdapter<CollectionViewHolder> {
    private  Context mContext;
    private List<BookDetail> books;

    public CollectionListAdapter(Context context ) {
        this.mContext = context;
    }
    public CollectionListAdapter(Context context,List<BookDetail> bookDetails) {
        this.mContext = context;
        books = bookDetails;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_list, parent, false);
        return view;
    }

    @Override
    public CollectionViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new CollectionViewHolder(realContentView);
    }


    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        holder.fillData(books.get(position));
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

}
