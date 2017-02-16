package com.example.gedune.bookcollection.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gedune.bookcollection.R;
import com.example.gedune.bookcollection.orm.OrmHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gedune on 2017/2/1.
 */

public class CollectionStatistics extends Fragment {

    @BindView(R.id.static_circle_rl)
    RelativeLayout circleRl;

    @BindView(R.id.static_tag_rl)
    RelativeLayout tagRl;

    @BindView(R.id.circle_iv)
    ImageView circleIv;

    @BindView(R.id.tag_iv)
    ImageView tagIv;

    @BindView(R.id.book_count)
    TextView bookCountTv;

    @BindView(R.id.staticBookPriceCount)
    TextView bookPriceCountTv;

    @BindView(R.id.favorite_author)
    TextView favoriteAuthorTv;

    @BindView(R.id.favarite_publisher)
    TextView favoritePublisherTv;

    @BindView(R.id.favorite_publisherCount)
    TextView favoritePublisherCountTv;

    @BindView(R.id.favorite_bookCount)
    TextView favoriteAuthorsBookCountTv;

    private Bitmap circleBitmap,tagBitmap;
    private Canvas circleCanvas,tagCanvas;

    private int circleRlWidth;
    private int circleRlHeight;

    private int tagRlWidth;
    private int tagRlHeight;

    private int MaxTagConut = 6;

    private List<String> tagList;
    private List<Map.Entry<String, Integer>> tags;

    private int bookCount;
    private int bookPriceCount;
    private Map.Entry<String, Integer> favoriteAuthorEntry;
    private Map.Entry<String, Integer> faveritePublisherEntry;

    private int[] colors = {0xFF009D82,0xFF21CAAD,0xFFAFE00D,0xFFFBF22E,0xFFFFC351,0xFFFF9C51};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collection_statistic,null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        tagList = new ArrayList<String>();

        bookCount  = OrmHelper.getBookCount();
        bookCountTv.setText(String.valueOf(bookCount));

        bookPriceCount  = OrmHelper.getBookPriceCout();
        bookPriceCountTv.setText("¥ " + String.valueOf(bookPriceCount));

        favoriteAuthorEntry  = OrmHelper.getFavoriteAuthor();
        if (favoriteAuthorEntry !=null){
            favoriteAuthorsBookCountTv.setText(String.valueOf(favoriteAuthorEntry.getValue()));
            favoriteAuthorTv.setText(favoriteAuthorEntry.getKey());
        }

        faveritePublisherEntry  = OrmHelper.getFavoritePublisher();
        if (faveritePublisherEntry != null){
            favoritePublisherTv.setText(faveritePublisherEntry.getKey());
            favoritePublisherCountTv.setText(String.valueOf(faveritePublisherEntry.getValue()));
        }

        tags  = OrmHelper.getBookTag();

        circleRl.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        prepareCanvas();
                        initBitmap();
                        circleRl.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

    }

    private void initBitmap() {
        int tagCount = 0;

        List<Float> percent = new ArrayList<Float>();
        List<Integer> values = new ArrayList<Integer>();
        List<String> finalTag = new ArrayList<String>();

        Iterator<Map.Entry<String, Integer>> entryIterator = tags.iterator();

        Map.Entry<String, Integer> tempEntry;

        for(int index = 0; entryIterator.hasNext() && index < MaxTagConut; index++) {
            tempEntry = entryIterator.next();

            finalTag.add(tempEntry.getKey());

            int value1 = 0;
            value1 = tempEntry.getValue();
            tagCount += value1;
            values.add(value1);
        }

        for (int index = 0; index < values.size() && index < MaxTagConut; index++){
            percent.add((float) values.get(index) / tagCount);
        }

        float rectLeft = (float) (circleRlWidth * 0.1);
        float rectRight = circleRlWidth;
        float rectWidth =  (rectRight - rectLeft);
        float rectTop = (float) (( circleRlHeight * 0.5) - (rectWidth * 0.5));
        float rectBottom = rectTop + rectWidth;

        int tagRectleft = (int) (tagRlWidth * 0.20);
        int tagRectTop = (int) (tagRlHeight * 0.15);
        int tagRectRight = (int) (tagRlWidth * 0.28);
        int tagRectBottom = (int) (tagRlHeight * 0.20);


        //画藏书基因分析圆弧和右边tag
        if (tags != null){
            RectF rect = new RectF(rectLeft, rectTop, rectRight , rectBottom);
            float angleCount = 0;
            for (int indext = 0; indext < percent.size(); indext++){
                //画藏书基因分析圆弧
                float angle = percent.get(indext) * 360;
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Integer.valueOf(colors[indext]));
                circleCanvas.drawArc(rect,angleCount, angle, true, paint);
                angleCount += angle;

                //画右边的Tag
                int tagOffset = ( tagRectBottom - tagRectTop )* 2 * indext;
                int tagHeight = tagRectBottom - tagRectTop;
                Paint tagPaint = new Paint();
                tagPaint.setColor(colors[indext]);

                RectF tagRectF = new RectF(tagRectleft,tagRectTop + tagOffset ,tagRectRight,tagRectBottom + tagOffset);
                float radiuX = 10f;
                float radiuY = 10f;
                tagCanvas.drawRoundRect(tagRectF, radiuX ,radiuY,tagPaint);


                int tagTextOffset = (int) ((tagRectRight - tagRectleft) * 0.8);
                int tagTextVerticalOffset = (int) (tagHeight * 0.15);

                Paint tagTextPaint = new Paint();
                tagTextPaint.setColor(Color.BLACK);
                tagTextPaint.setTextSize(45f);
                tagTextPaint.setAntiAlias(true);
                tagCanvas.drawText(finalTag.get(indext),tagRectRight + tagTextOffset , tagRectBottom - tagTextVerticalOffset + tagOffset,tagTextPaint);


                int percentTextX = (int) (tagRlWidth * 0.95);
                int percentTextY = tagRectBottom - tagTextVerticalOffset;
                Paint percentPaint = new Paint();
                percentPaint.setColor(Color.BLACK);
                percentPaint.setTextSize(50f);
                percentPaint.setAntiAlias(true);
                percentPaint.setTextAlign(Paint.Align.RIGHT);

                NumberFormat nt = NumberFormat.getPercentInstance();
                nt.setMinimumFractionDigits(0);
                String s = nt.format(percent.get(indext));
                tagCanvas.drawText(s,percentTextX  , percentTextY + tagOffset ,percentPaint);
            }
        }

        //画藏书基因分析文字
        Paint textPaint = new Paint();
        textPaint.setColor(0xFF555555);
        textPaint.setTextSize(50);
        textPaint.setAntiAlias(true);
        circleCanvas.drawText("藏书基因分析", rectLeft + rectWidth * 0.2f,rectTop - rectTop * 0.15f,textPaint);

        //画中心白圆
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.WHITE);
        circlePaint.setAntiAlias(true);
        float circleCenterWidth = (rectLeft + rectRight) / 2;
        float circleCenterHeight = (rectTop + rectBottom) / 2;
        circleCanvas.drawCircle(circleCenterWidth,circleCenterHeight, (float) (rectWidth * 0.325),circlePaint);

        saveBitmap();
    }

    private void prepareCanvas() {

        circleRlHeight = circleRl.getHeight();
        circleRlWidth = circleRl.getWidth();
        circleBitmap = Bitmap.createBitmap(circleRlWidth, circleRlHeight, Bitmap.Config.ARGB_8888);
        circleCanvas = new Canvas(circleBitmap);

        tagRlWidth = tagRl.getWidth();
        tagRlHeight = tagRl.getHeight();
        tagBitmap = Bitmap.createBitmap(circleRlWidth, circleRlHeight, Bitmap.Config.ARGB_8888);
        tagCanvas = new Canvas(tagBitmap);

    }


    /**
     * 保存图片，同时显示到imageview和保存到图片文件
     */
    private void saveBitmap() {
        circleIv.setImageBitmap(circleBitmap);
        tagIv.setImageBitmap(tagBitmap);

    }

}
