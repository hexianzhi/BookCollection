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

        if (detail != null) {
            if (bookBean.getAuthor() != null){
                detail.setAuthors(bookBean.getAuthor().toString());
            }else {
                detail.setAuthors("");
            }

            if (bookBean.getImage() != null){
                detail.setImage(bookBean.getImage());
            }else {
                detail.setImage("");
            }

            if (bookBean.getIsbn13() != null){
                detail.setIsbn13(bookBean.getIsbn13());
            }else {
                detail.setIsbn13("");
            }

            if (bookBean.getPages() != null){
                detail.setPages(bookBean.getPages());
            }else {
                detail.setPages("");
            }

            if (bookBean.getPrice() != null){
                detail.setPrice(bookBean.getPrice());
            }else {
                detail.setPrice("");
            }

            if (bookBean.getPubdate() != null){
                detail.setPubdate(bookBean.getPubdate());
            }else {
                detail.setPubdate("");
            }

            if (bookBean.getSubtitle() != null){
                detail.setSubtitle(bookBean.getSubtitle());
            }else {
                detail.setSubtitle("");
            }

            if (bookBean.getTags() != null){
                detail.setTitle(bookBean.getTitle());
            }else {
                detail.setTitle("");
            }
            if (bookBean.getPublisher() != null){
                detail.setPublisher(bookBean.getPublisher());
            }else {
                detail.setPublisher("");
            }
            if (bookBean.getSummary() != null){
                detail.setSummary(bookBean.getSummary());
            }else {
                detail.setSummary("");
            }
            if (bookBean.getTranslator() != null){
                detail.setTranslators(bookBean.getTranslator().toString());
            }else {
                detail.setTranslators("");
            }


            //取第一个，第一个人数最多
            TagsBean tags;
            if ( bookBean.getTags().isEmpty()){
                detail.setTag("");
            }else {
                tags = bookBean.getTags().get(0);
                detail.setTag(tags.getTitle());
            }

        }

        return detail;
    }
}
