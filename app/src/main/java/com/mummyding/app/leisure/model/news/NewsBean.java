/*
 *  Copyright (C) 2015 MummyDing
 *
 *  This file is part of Leisure( <https://github.com/MummyDing/Leisure> )
 *
 *  Leisure is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *                             ｀
 *  Leisure is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Leisure.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mummyding.app.leisure.model.news;

import com.mummyding.app.leisure.support.CONSTANT;
import com.mummyding.app.leisure.support.Utils;

/**
 * Created by mummyding on 15-11-14.
 * @author MummyDing
 * @version Leisure 1.0
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

