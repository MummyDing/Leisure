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
                Utils.DLog("title "+tmpVal.toString().trim());
                tmpBean.setTitle(tmpVal.toString());
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("link")){
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("author")){
                tmpBean.setAuthor(tmpVal.toString());
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("pubDate")){
                tmpBean.setPubDate(tmpVal.toString().trim());
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("description")){
                tmpBean.setDescription(tmpVal.toString().trim());
                Utils.DLog("description " + tmpVal.toString());
                tmpVal = new StringBuffer();
            }else if(qName.equalsIgnoreCase("guid")){
                Utils.DLog("guid "+tmpVal.toString().trim());
                tmpVal = new StringBuffer();
            }

        }

    }
}
