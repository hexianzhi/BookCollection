package com.example.gedune.bookcollection.orm;

import android.content.Context;

import com.example.gedune.bookcollection.Bean.BookDetail;

import org.greenrobot.greendao.query.Query;

import java.util.List;

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

    public List<BookDetail> queryBooks(){

        BookDetailDao bookDao =  daoSession.getBookDetailDao();
        Query<BookDetail> query =  bookDao.queryBuilder().orderAsc(BookDetailDao.Properties.Isbn13).build();


        return query.list();

    }

}
