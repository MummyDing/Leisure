package com.mummyding.app.leisure.model.science;


import java.io.Serializable;

/**
 * Created by mummyding on 15-11-17.
 */
public class ArticleBean implements Serializable{

    private Author author;
    private String date_published;
    private int replies_count;
    private Image_info image_info;
    private String url;
    private String title;
    private String summary;
    /*
            self define
         */
    private String Info;
    private int is_collected = 0;

    class Author implements Serializable{
        String nickname;
        public String getNickname() {
            return nickname;
        }
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public class Image_info implements Serializable{
        String url;
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
    public Image_info getImage_info() {
        return image_info;
    }

    public void setImage_info(Image_info image_info) {
        this.image_info = image_info;
    }


    public String getDate_published() {
        StringBuffer tmpStr = new StringBuffer(date_published);
        if(date_published.length() >20){
            tmpStr.setCharAt(10,' ');
            date_published = tmpStr.substring(0,19);
        }
        return date_published;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }

    public int getReplies_count() {
        return replies_count;
    }

    public void setReplies_count(int replies_count) {
        this.replies_count = replies_count;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInfo() {
        if(Info == null) return toString();
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }
    @Override
    public String toString() {
        return getAuthor().getNickname()+"  "+getDate_published();
    }
}
