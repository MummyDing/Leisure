package com.mummyding.app.leisure.model.reading;

import java.io.Serializable;

/**
 * Created by mummyding on 15-11-15.
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
