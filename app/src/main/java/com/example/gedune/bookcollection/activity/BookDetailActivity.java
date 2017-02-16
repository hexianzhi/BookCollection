package com.example.gedune.bookcollection.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gedune.bookcollection.AppProfile;
import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.orm.OrmHelper;
import com.example.gedune.bookcollection.utils.ResourceHelper;
import com.example.gedune.bookcollection.utils.image.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String sBOOK = "BOOK";
    public static final String sIsbn = "ISBN13";

    @BindView(R.id.bookname)
    TextView booName;

    @BindView(R.id.author)
    TextView author;

    @BindView(R.id.translator)
    TextView translator;

    @BindView(R.id.classification)
    TextView classification;

    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.publisher)
    TextView publisher;

    @BindView(R.id.pubdate)
    TextView pubdate;

    @BindView(R.id.pages)
    TextView pages;

    @BindView(R.id.isbn)
    TextView isbn;

    @BindView(R.id.booklImg)
    ImageView booklImg;

    @BindView(R.id.detailAtyCollectiBtn)
    RelativeLayout relativeLayout;

    @BindView(R.id.detail_return)
    ImageButton detailReturn;

    @BindView(R.id.book_introduction)
    TextView bookIntroduction;

    @BindView(R.id.isCollectedIv)
    ImageView isCollectedIv;

    @BindView(R.id.isCollectedTv)
    TextView isCollectedTv;

    private BookDetail bookDetail;
    private Boolean isBookCollected = false;
    private OrmHelper helper;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);

        helper = OrmHelper.getInstance(this);

        Intent intent = getIntent();

        bookDetail = (BookDetail) intent.getSerializableExtra(sBOOK);
        List<BookDetail> bookDetails = helper.queryBook(bookDetail.getIsbn13());

        //该书没有收藏
        if (bookDetails.isEmpty()){
            isBookCollected = false;
        }else {
            isBookCollected = true;
            isCollectedIv.setBackground(AppProfile.getContext().getDrawable(R.drawable.start_collected));
            isCollectedTv.setText("已收藏");
            isCollectedTv.setTextColor(ResourceHelper.getColor(R.color.basic_green));
            relativeLayout.setBackground(AppProfile.getContext().getDrawable(R.drawable.shape_collected));
        }

        initData();


    }

    private void initData() {
        booName.setText(String.format(AppProfile.getContext().getResources().getString(R.string.bookname), bookDetail.getTitle()));
        author.setText(String.format(AppProfile.getContext().getResources().getString(R.string.author), bookDetail.getAuthors()));
        translator.setText(String.format(AppProfile.getContext().getResources().getString(R.string.translator), bookDetail.getTranslators()));
        classification.setText(String.format(AppProfile.getContext().getResources().getString(R.string.classification), bookDetail.getTag()));
        price.setText(String.format(AppProfile.getContext().getResources().getString(R.string.price), bookDetail.getPrice()));
        publisher.setText(String.format(AppProfile.getContext().getResources().getString(R.string.publisher), bookDetail.getPublisher()));
        pubdate.setText(String.format(AppProfile.getContext().getResources().getString(R.string.pubdate), bookDetail.getPubdate()));
        pages.setText(String.format(AppProfile.getContext().getResources().getString(R.string.pages), bookDetail.getPages()));
        isbn.setText(String.format(AppProfile.getContext().getResources().getString(R.string.bookname), bookDetail.getIsbn13()));

        bookIntroduction.setText(bookDetail.getSummary());

        ImageUtils.setUrl(booklImg, bookDetail.getImage());

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.detail_return:
                //TODO
                startActivity(new Intent(BookDetailActivity.this,MainActivity.class));
                finish();

                break;

            case R.id.detailAtyCollectiBtn:
                //TODO

                if (isBookCollected){
                    //取消收藏
                    helper.deleteBook(bookDetail);
                    isCollectedIv.setBackground( AppProfile.getContext().getDrawable(R.drawable.star_uncollected));
                    isCollectedTv.setText("收藏");
                    isCollectedTv.setTextColor(ResourceHelper.getColor(R.color.basec_white));
                    relativeLayout.setBackground(AppProfile.getContext().getDrawable(R.drawable.shape_uncollected));
                    isBookCollected = false;

                }else {
                    helper.insertBook(bookDetail);
                    isCollectedIv.setBackground(AppProfile.getContext().getDrawable(R.drawable.start_collected));
                    isCollectedTv.setText("已收藏");
                    isCollectedTv.setTextColor(ResourceHelper.getColor(R.color.basic_green));
                    relativeLayout.setBackground(AppProfile.getContext().getDrawable(R.drawable.shape_collected));
                    isBookCollected = true;

                }

                break;


        }

    }


}
