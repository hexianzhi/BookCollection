package com.example.gedune.bookcollection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gedune.bookcollection.AppProfile;
import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.utils.image.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String sBOOK = "BOOK";

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

    private BookDetail detail;
    private final String BOOKNAME = AppProfile.getContext().getResources().getString(R.string.bookname);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        detail = (BookDetail) intent.getSerializableExtra(sBOOK);

        initData();

    }

    private void initData() {
        booName.setText(String.format(AppProfile.getContext().getResources().getString(R.string.bookname), detail.getTitle()));
        author.setText(String.format(AppProfile.getContext().getResources().getString(R.string.author), detail.getAuthors()));
        translator.setText(String.format(AppProfile.getContext().getResources().getString(R.string.translator), detail.getTranslators()));
        classification.setText(String.format(AppProfile.getContext().getResources().getString(R.string.classification), detail.getTag()));
        price.setText(String.format(AppProfile.getContext().getResources().getString(R.string.price), detail.getPrice()));
        publisher.setText(String.format(AppProfile.getContext().getResources().getString(R.string.publisher), detail.getPublisher()));
        pubdate.setText(String.format(AppProfile.getContext().getResources().getString(R.string.pubdate), detail.getPubdate()));
        pages.setText(String.format(AppProfile.getContext().getResources().getString(R.string.pages), detail.getPages()));
        isbn.setText(String.format(AppProfile.getContext().getResources().getString(R.string.bookname), detail.getIsbn13()));

        bookIntroduction.setText(detail.getSummary());

        ImageUtils.setUrl(booklImg,detail.getImage());

    }

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
                Toast.makeText(BookDetailActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                break;


        }

    }
}
