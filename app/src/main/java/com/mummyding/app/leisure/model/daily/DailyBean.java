/*
 *
 *  * Copyright (C) 2015 MummyDing
 *  *
 *  * This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *  *
 *  * Leisure is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Leisure is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mummyding.app.leisure.model.daily;

import com.mummyding.app.leisure.LeisureApplication;
import com.mummyding.app.leisure.R;
import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.Utils;

/**
 * Created by mummyding on 15-11-21.
 */
public class DailyBean {
    private String title;
    private String author;
    private String pubDate;
    private String description;
    private String image;

    /*
        self define
     */
    private String Info;
    private int is_collected = 0;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setPubDateWithFormat(String pubDate) {
        this.pubDate = formatTime(pubDate);
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return LeisureApplication.AppContext.getString(R.string.text_author)+getAuthor()+"\t\t"+getPubDate();
    }

    public String getImage() {
        if(image == null) {
            setImage();
        }
        return image;
    }

    public void setImage() {
        int originlength = description.length();
        String url = Utils.RegexFind("<img class=\"content-image\\\" src=\"[^>]*jpg\"|<img src=\"[^>]*jpg\"", description,5,0);
        int currentlength = url.length()+5;
        if(originlength != currentlength) {
            url = Utils.RegexFind("\"[^\"]*jpg\"", url);
        }
        image = originlength==currentlength ? CONSTANT.placeHolderUri:url;
    }
    public void setImage(String image){
        this.image = image;
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

    private String formatTime(String pubTime){
        String date = Utils.RegexFind(" \\d{4} ", pubTime)+"年"+
                formatMonth(Utils.RegexFind(" \\w{3} ", pubTime))+"月"+
                Utils.RegexFind(" \\d{1,2} ", pubTime)+"日"+
                Utils.RegexFind(" \\d{2}:", pubTime)+"点"+
                Utils.RegexFind(":\\d{2}:", pubTime)+"分"+
                Utils.RegexFind(":\\d{2} ", pubTime)+"秒";
        return date;
    }
    private int formatMonth(String month){
        for(int i = 1 ; i < CONSTANT.MONTH.length;i++)
            if(month.equalsIgnoreCase(CONSTANT.MONTH[i]))
                return i;
        return -1;
    }

}
