package com.example.gedune.bookcollection.utils;

import com.example.gedune.bookcollection.Bean.BookBean;
import com.example.gedune.bookcollection.Bean.BookDetail;
import com.example.gedune.bookcollection.Bean.TagsBean;

/**
 * Created by gedune on 2017/2/5.
 */

public class BookDetailGenarator {

    public static BookDetail BookBeanToDetail(BookBean bookBean){
        BookDetail detail = new BookDetail();

        detail.setAuthors(bookBean.getAuthor().toString());
        detail.setImage(bookBean.getImage());
        detail.setIsbn13(bookBean.getIsbn13());
        detail.setPages(bookBean.getPages());
        detail.setPrice(bookBean.getPrice());
        detail.setPubdate(bookBean.getPubdate());
        detail.setSubtitle(bookBean.getSubtitle());
        detail.setTitle(bookBean.getTitle());
        detail.setPublisher(bookBean.getPublisher());
        detail.setSummary(bookBean.getSummary());
        detail.setTranslators(bookBean.getTranslator().toString());

        //取第一个，第一个人数最多
        TagsBean tags = bookBean.getTags().get(0);
        if (tags !=null){
            detail.setTag(tags.getTitle());
        }else {
            detail.setTag("");
        }
        return detail;
    }
}
