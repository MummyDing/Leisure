package com.mummyding.app.leisure.support.sax;

import com.mummyding.app.leisure.model.NewsBean;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mummyding on 15-11-14.
 */
public class SAXNewsHandler extends DefaultHandler {
    private List<NewsBean> items = new LinkedList<>();
    private NewsBean tmpBean = new NewsBean();
    private StringBuffer tmpVal = new StringBuffer();
    private boolean isStart = false;
    private boolean isFirst = true;
    public List<NewsBean> getItems(){
        return  this.items;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(isStart == false) return;
        if(qName.equalsIgnoreCase("item")){
            tmpBean = new NewsBean();
            items.add(tmpBean);
            if(isFirst){
                tmpVal = new StringBuffer();
                isFirst = false;
            }
        }else if(qName.equalsIgnoreCase("description")){
            tmpBean.setPubTimeWithFormat(tmpVal.toString());
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
        if(isStart == false) {
            if (qName.equalsIgnoreCase("copyright")) {
                isStart = true;
            }
            tmpVal = new StringBuffer();
            return;
        }
        if (qName.equalsIgnoreCase("title")){
            tmpBean.setTitle(tmpVal.toString().trim());
            tmpVal = new StringBuffer();
        }else if(qName.equalsIgnoreCase("link")){
            tmpBean.setLink(tmpVal.toString().trim());
            tmpVal = new StringBuffer();
        }else if(qName.equalsIgnoreCase("description")){
            tmpBean.setDescriptionWithFormat(tmpVal.toString().trim());
            tmpVal = new StringBuffer();
        }else if(qName.equalsIgnoreCase("author")){
            tmpVal = new StringBuffer();
        }
    }
}
