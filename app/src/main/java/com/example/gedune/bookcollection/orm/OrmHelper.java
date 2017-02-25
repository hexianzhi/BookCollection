package com.example.gedune.bookcollection.orm;

import android.content.Context;

import com.example.gedune.bookcollection.AppProfile;
import com.example.gedune.bookcollection.Bean.BookDetail;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengwen on 16/8/22.
 */
public class OrmHelper {
    private static OrmHelper instance;

    private DaoMaster daoMaster = null;
    private DaoSession daoSession = null;

    public static OrmHelper getInstance(Context context) {
        if(instance==null){
            instance = new OrmHelper(context);
        }
        return instance;
    }

    public OrmHelper(Context context){
        MySqlOpenHelper openHelper = new MySqlOpenHelper(context,"ORMDB");
        daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public void insertBook( BookDetail book){
        BookDetailDao bookDao =  daoSession.getBookDetailDao();
        bookDao.insert(book);
    }

    public void insertBooks(List<BookDetail> lists){
        BookDetailDao bookDao =  daoSession.getBookDetailDao();
        bookDao.insertInTx(lists);
    }

    public void deleteBook(BookDetail bookDetail){
        BookDetailDao bookDao =  daoSession.getBookDetailDao();
        bookDao.delete(bookDetail);
    }

    public List<BookDetail> queryBooks(){
        BookDetailDao bookDao =  daoSession.getBookDetailDao();
        Query<BookDetail> query =  bookDao.queryBuilder().orderAsc(BookDetailDao.Properties.Isbn13).build();

        return query.list();

    }
    public List<BookDetail> queryBook(String isbn13){
        BookDetailDao bookDao =  daoSession.getBookDetailDao();
        List<BookDetail> bookDetails = bookDao.queryBuilder().where(BookDetailDao.Properties.Isbn13.eq(isbn13)).list();
        return bookDetails;
    }

    public static List<Map.Entry<String, Integer>> getBookTag(){
        List<String> tags = new ArrayList<String>();

        List<BookDetail> bookDetails = OrmHelper.getInstance(AppProfile.getContext()).queryBooks();
        for (int i = 0 ; i < bookDetails.size(); i++ ){
            BookDetail bookDetail = bookDetails.get(i);
            if (!bookDetail.getTag().isEmpty()){
                String tag = bookDetail.getTag();
                tags.add(tag);
            }
        }

        //统计每个 tag 数量
        HashMap<String,Integer> tagMap = new HashMap<String,Integer>();

        for (String tag : tags){
            int tagCount = 0;
             if (tagMap.get(tag) == null){
                 tagCount ++;
                 tagMap.put(tag,tagCount);
             }else {
                 tagCount = tagMap.get(tag);
                 tagCount ++;
                 tagMap.put(tag,tagCount);
             }
        }
        
        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<Map.Entry<String, Integer>>(tagMap.entrySet());

        //排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });

        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
        }
        return infoIds;
    }

    public static int getBookCount(){
        List<BookDetail> bookDetails = OrmHelper.getInstance(AppProfile.getContext()).queryBooks();
        return bookDetails.size();
    }

    public static Integer getBookPriceCout(){
        float priceCount  = 0;

        List<BookDetail> bookDetails = OrmHelper.getInstance(AppProfile.getContext()).queryBooks();
        for (int i = 0 ; i < bookDetails.size(); i++ ){
            BookDetail bookDetail = bookDetails.get(i);
            String s= bookDetail.getPrice();
            String[] part = new String[0];

            if (s.contains("元")){
                 part = s.split("元");
                priceCount += Float.valueOf(part[0]);
            } else if (s.contains("CNY")) {
                 part = s.split("CNY");
                priceCount += Float.valueOf(part[1]);
            }else {
                priceCount += Float.valueOf(s);
            }
        }
        return (int )priceCount;
    }

    public static Map.Entry<String, Integer> getFavoriteAuthor(){
        List<String> authors = new ArrayList<String>();

        List<BookDetail> bookDetails = OrmHelper.getInstance(AppProfile.getContext()).queryBooks();
        if (!bookDetails.isEmpty()) {
            for (int bookIndext = 0; bookIndext < bookDetails.size(); bookIndext++) {
                BookDetail bookDetail = bookDetails.get(bookIndext);
                String author = bookDetail.getAuthors();
                String[] strings = author.split(",");
                for (int indext = 0; indext < strings.length; indext++) {
                    authors.add(strings[indext]);
                }
            }

            //统计每个 tag 数量
            HashMap<String,Integer> authorMap = new HashMap<String,Integer>();

            for (String author : authors){
                int authorCount = 0;
                if (authorMap.get(author) == null){
                    authorCount ++;
                    authorMap.put(author,authorCount);
                }else {
                    authorCount = authorMap.get(author);
                    authorCount ++;
                    authorMap.put(author,authorCount);
                }
            }

            List<Map.Entry<String, Integer>> infoIds =
                    new ArrayList<Map.Entry<String, Integer>>(authorMap.entrySet());
            //排序
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (o2.getValue() - o1.getValue());
                }
            });

            return infoIds.get(0);
        } else {
            return null;
        }
    }

    public static Map.Entry<String, Integer> getFavoritePublisher(){
        List<String> publishers = new ArrayList<String>();

        List<BookDetail> bookDetails = OrmHelper.getInstance(AppProfile.getContext()).queryBooks();
        if (!bookDetails.isEmpty()){
            for (int bookIndext = 0 ; bookIndext < bookDetails.size(); bookIndext++ ){
                BookDetail bookDetail = bookDetails.get(bookIndext);
                String publisher = bookDetail.getPublisher();
                String s = new String();
                publishers.add(publisher);
            }

            //统计每个 tag 数量
            HashMap<String,Integer> publisherMap = new HashMap<String,Integer>();

            for (String publisher : publishers){
                int authorCount = 0;
                if (publisherMap.get(publisher) == null){
                    authorCount ++;
                    publisherMap.put(publisher,authorCount);
                }else {
                    authorCount = publisherMap.get(publisher);
                    authorCount ++;
                    publisherMap.put(publisher,authorCount);
                }
            }

            List<Map.Entry<String, Integer>> infoIds =
                    new ArrayList<Map.Entry<String, Integer>>(publisherMap.entrySet());

            //排序
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (o2.getValue() - o1.getValue());
                }
            });

            return infoIds.get(0);
        } else {
            return null;
        }
    }
}
