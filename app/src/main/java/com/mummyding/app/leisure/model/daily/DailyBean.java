package com.mummyding.app.leisure.model.daily;

import com.mummyding.app.leisure.support.Utils;

/**
 * Created by mummyding on 15-11-21.
 */
public class DailyBean {
    private String title;
    private String link;
    private String author;
    private String pubDate;
    private String Description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "作者: "+getAuthor()+"  title"+getTitle()+" description "+getDescription();
    }
}
