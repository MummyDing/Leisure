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

package com.mummyding.app.leisure.model.reading;

import java.io.Serializable;

/**
 * Created by mummyding on 15-11-15.<br>
 * Reading Model . <br>
 * @author MummyDing
 * @version Leisure 1.0
 */
public class ReadingBean implements Serializable{

    private BookBean books[];
    public BookBean[] getBooks() {
        return books;
    }

    public void setBooks(BookBean[] books) {
        this.books = books;
    }
}
