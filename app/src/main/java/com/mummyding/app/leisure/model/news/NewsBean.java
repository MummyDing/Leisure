package com.mummyding.app.leisure.model.news;

import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.Utils;

/**
 * Created by mummyding on 15-11-14.
 */
public class NewsBean {
    private String title;
    private String link;
    private String description;
    private String pubTime;

    /*
        self define
     */
    private int is_collected = 0;

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }
    public void setPubTimeWithFormat(String pubTime) {
        this.pubTime = formatTime(pubTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = formatClearHtmlLabel(title);
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

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setDescriptionWithFormat(String description) {
        this.description = formatClearHtmlLabel(description);
    }
    private String formatTime(String pubTime){
        String date = Utils.RegexFind("-.{4} ", pubTime)+"年"+
                formatMonth(Utils.RegexFind("-.{3}-", pubTime))+"月"+
                Utils.RegexFind(",.{1,2}-", pubTime)+"日"+
                Utils.RegexFind(" .{2}:", pubTime)+"点"+
                Utils.RegexFind(":.{2}:", pubTime)+"分"+
                Utils.RegexFind(":.{2} ", pubTime)+"秒";
        return date;
    }
    private String formatClearHtmlLabel(String string){
      return  this.description = Utils.RegexReplace("<[^>\n]*>",string,"");
    }

    private int formatMonth(String month){
        for(int i = 1 ; i < CONSTANT.MONTH.length;i++)
            if(month.equalsIgnoreCase(CONSTANT.MONTH[i]))
            return i;
        return -1;
    }
}

