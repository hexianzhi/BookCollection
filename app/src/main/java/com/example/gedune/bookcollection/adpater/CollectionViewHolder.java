package com.example.gedune.bookcollection.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.utils.image.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gedune on 2017/2/1.
 */

public class CollectionViewHolder extends RecyclerView.ViewHolder   {

    @BindView(R.id.book_img)
    ImageView mBookImg;

    @BindView(R.id.book_name)
    TextView mBookName;

    @BindView(R.id.book_des)
    TextView mBookdes;

    public CollectionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void fillData(BookDetail bookDetail){

        mBookName.setText(bookDetail.getTitle());
        mBookdes.setText(bookDetail.getAuthors());
        ImageUtils.setUrl(mBookImg,bookDetail.getImage());

    }


}
