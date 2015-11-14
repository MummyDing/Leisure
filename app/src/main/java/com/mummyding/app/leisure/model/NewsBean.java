package com.mummyding.app.leisure.model;

import com.mummyding.app.leisure.support.Utils;

/**
 * Created by mummyding on 15-11-14.
 */
public class NewsBean {
    private String title;
    private String link;
    private String description;
    private String pubTime;

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = formatTime(pubTime);
    }
    public void setPubTimeWithFormat(String pubTime) {
        this.pubTime = formatTime(pubTime);
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setDescriptionWithFormat(String description) {
        this.description = formatDescription(description);
    }
    private String formatTime(String pubTime){

        Utils.DLog(pubTime);
        String date = Utils.RegexFind("-.{4} ", pubTime)+"年"+
                formatMonth(Utils.RegexFind("-.{3}-", pubTime))+"月"+
                Utils.RegexFind(",.{1,2}-", pubTime)+"日"+
                Utils.RegexFind(" .{2}:", pubTime)+"点"+
                Utils.RegexFind(":.{2}:", pubTime)+"分"+
                Utils.RegexFind(":.{2} ", pubTime)+"秒";
        Utils.DLog(date);
        return date;
    }
    private String formatDescription(String description){
      return  this.description = Utils.RegexReplace("<[^>]+>",description,"");
    }
    private  final String MONTH [] =
            {"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};

    private int formatMonth(String month){
        for(int i = 1 ; i < MONTH.length;i++)
            if(month.equalsIgnoreCase(MONTH[i]))
            return i;
        return -1;
    }
}

