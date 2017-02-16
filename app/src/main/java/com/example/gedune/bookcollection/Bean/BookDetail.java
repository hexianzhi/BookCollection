package com.example.gedune.bookcollection.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;

/**
 * Created by gedune on 2017/2/5.
 */

@SuppressWarnings("serial")
@Entity
public class BookDetail implements Serializable {

    @Id
    private String isbn13;

    @Generated(hash = 467010836)
    public BookDetail() {
    }

    @Keep
    public BookDetail(String title, String subtitle, String pubdate, String image,
            String pages, String publisher, @NotNull String isbn13, String summary, String price,
            String tag, String authors, String translators) {
        this.title = title;
        this.subtitle = subtitle;
        this.pubdate = pubdate;
        this.image = image;
        this.pages = pages;
        this.publisher = publisher;
        this.isbn13 = isbn13;
        this.summary = summary;
        this.price = price;
        this.tag = tag;
        this.authors = authors;
        this.translators = translators;
    }

    private String title;
    private String subtitle;
    private String pubdate;
    private String image;
    private String pages;
    private String publisher;

    private String summary;
    private String price;
    private String tag;
    private String authors;
    private String translators;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTranslators() {
        return translators;
    }

    public void setTranslators(String translators) {
        this.translators = translators;
    }

    @Keep
    @Override
    public String toString() {
        return "BookDetail{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", image='" + image + '\'' +
                ", pages='" + pages + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", summary='" + summary + '\'' +
                ", price='" + price + '\'' +
                ", tag='" + tag + '\'' +
                ", authors='" + authors + '\'' +
                ", translators='" + translators + '\'' +
                '}';
    }





}
