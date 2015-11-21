package com.mummyding.app.leisure.support.sax;

import com.mummyding.app.leisure.model.daily.DailyBean;
import com.mummyding.app.leisure.model.news.NewsBean;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by mummyding on 15-11-21.
 */
public class SAXDailyParse {
    public static List<DailyBean> items;
    public static List<DailyBean> parse(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        SAXDailyHandler saxHandler = new SAXDailyHandler();
        xmlReader.setContentHandler(saxHandler);
        xmlReader.parse(new InputSource(is));
        items = saxHandler.getItems();
        return items;
    }
}
