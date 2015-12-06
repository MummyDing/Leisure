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

package com.mummyding.app.leisure.support.sax;

import com.mummyding.app.leisure.model.daily.DailyBean;
import com.mummyding.app.leisure.model.news.NewsBean;
import com.mummyding.app.leisure.support.Utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mummyding on 15-11-21.
 */
public class SAXDailyHandler extends DefaultHandler {
    private List<DailyBean> items = new LinkedList<>();
    private DailyBean tmpBean = new DailyBean();
    private StringBuffer tmpVal = new StringBuffer();
    private boolean isStart = false;
    public List<DailyBean> getItems(){
        return  this.items;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equalsIgnoreCase("item")){
            tmpBean = new DailyBean();
            items.add(tmpBean);
            isStart = true;
            tmpVal = new StringBuffer();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tmpVal.append(ch,start,length);
        super.characters(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(isStart){
            if(qName.equalsIgnoreCase("title")){
                tmpBean.setTitle(tmpVal.toString().trim());
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("link")){
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("author")){
                tmpBean.setAuthor(tmpVal.toString().trim());
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("pubDate")){
                tmpBean.setPubDateWithFormat(tmpVal.toString().trim());
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("description")){
                tmpBean.setDescription((Utils.getImageHtml()+tmpVal.toString()).trim());
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("guid")){
                tmpVal = new StringBuffer();
            }

        }

    }
}
