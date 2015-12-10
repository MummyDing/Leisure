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

package com.mummyding.app.leisure.support.sax;

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
 * Created by mummyding on 15-11-14.
 */
public class SAXNewsParse  {
    public static List<NewsBean> items;
    public static List<NewsBean> parse(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        SAXNewsHandler saxHandler = new SAXNewsHandler();
        xmlReader.setContentHandler(saxHandler);
        xmlReader.parse(new InputSource(is));
        items = saxHandler.getItems();
        return items;
    }
}
