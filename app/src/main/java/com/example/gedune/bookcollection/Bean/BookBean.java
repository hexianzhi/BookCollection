package com.example.gedune.bookcollection.Bean;


import java.util.List;

/**
 * Created by gedune on 2017/2/1.
 */

public class BookBean {

    /**
     * rating : {"max":10,"numRaters":10007,"average":"8.4","min":0}
     * subtitle :
     * author : ["刘未鹏"]
     * pubdate : 2011-7
     * tags : [{"count":6446,"name":"时间管理","title":"时间管理"},{"count":5588,"name":"思维","title":"思维"},{"count":3647,"name":"心理学","title":"心理学"},{"count":2470,"name":"刘未鹏","title":"刘未鹏"},{"count":2418,"name":"方法论","title":"方法论"},{"count":1780,"name":"暗时间","title":"暗时间"},{"count":1672,"name":"个人成长","title":"个人成长"},{"count":1462,"name":"心智","title":"心智"}]
     * origin_title :
     * image : https://img3.doubanio.com/mpic/s6586365.jpg
     * binding : 平装
     * translator : []
     * catalog : 序言　为什么人人都该学点心理学
     第一篇　暗时间
     暗时间
     设计你自己的进度条
     如何有效地记忆与学习
     学习密度与专注力
     一直以来伴随我的一些学习习惯
     我在南大的七年
     第二篇　思维改变生活
     逃出你的肖申克
     亲身经历才能明白？
     仁者见仁智者见智？从视觉错觉到偏见
     遇见20万年前的自己
     理智与情感
     书写是为了更好地思考
     为什么你从现在开始就应该写博客
     我不想与我不能
     遇到问题为什么应该自己动手
     什么才是你的不可替代性和核心竞争力
     第三篇　跟波利亚学解题
     跟波利亚学解题
     锤子和钉子
     鱼是最后一个看到水的
     知其所以然
     为什么有必要知其所以然
     康托尔、哥德尔、图灵 — 永恒的金色对角线
     数学之美番外篇：快排为什么那样快
     数学之美番外篇：平凡而又神奇的贝叶斯方法
     索 引
     参考文献
     * pages : 251
     * images : {"small":"https://img3.doubanio.com/spic/s6586365.jpg","large":"https://img3.doubanio.com/lpic/s6586365.jpg","medium":"https://img3.doubanio.com/mpic/s6586365.jpg"}
     * alt : https://book.douban.com/subject/6709809/
     * id : 6709809
     * publisher : 电子工业出版社
     * isbn10 : 7121140063
     * isbn13 : 9787121140068
     * title : 暗时间
     * url : https://api.douban.com/v2/book/6709809
     * alt_title :
     * author_intro : 刘未鹏：
     南京大学计算机系硕士毕业
     现就职于微软亚洲研究院创新工程中心
     兴趣爱好：计算机科学，人工智能，认知科学
     博客名 Mind Hacks 的含义：
     - Mind Hacks 是一本书
     - Mind Hacks 是一系列思维工具
     - Mind Hacks 有一个漫长的前生—一个有着近6年历史的技术博客
     - 在CSDN上有超过120万的访问量
     * summary : 2003年，刘未鹏在杂志上发表了自己的第一篇文章，并开始写博客。最初的博客较短，也较琐碎，并夹杂着一些翻译的文章。后来渐渐开始有了一些自己的心得和看法。总体上在这8年里，作者平均每个月写1篇博客或更少，但从未停止。
     刘未鹏说——
     写博客这件事情给我最大的体会就是，一件事情如果你能够坚持做8年，那么不管效率和频率多低，最终总能取得一些很可观的收益。而另一个体会就是，一件事情只要你坚持得足够久，“坚持”就会慢慢变成“习惯”。原本需要费力去驱动的事情便成了家常便饭，云淡风轻。
     这本书便是从刘未鹏8年的博客文章中精选出来的，主要关于心智模式、学习方法和时间利用，《暗时间》的书名便来自于此。
     * price : 35.00元
     */

    private RatingBean rating;
    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;

    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private String price;
    private List<String> author;
    private List<TagsBean> tags;
    private List<?> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
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

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
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

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<?> getTranslator() {
        return translator;
    }

    public void setTranslator(List<?> translator) {
        this.translator = translator;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                ", subtitle='" + subtitle + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", image='" + image + '\'' +
                ", pages='" + pages + '\'' +
                ", images=" + images +
                ", publisher='" + publisher + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", alt_title='" + alt_title + '\'' +
                ", summary='" + summary + '\'' +
                ", price='" + price + '\'' +
                ", author=" + author +
                ", tags=" + tags +
                ", translator=" + translator +
                '}';
    }
}
