package com.mummyding.app.leisure.model.reading;

import java.io.Serializable;

/**
 * Created by mummyding on 15-11-15.
 */
public class ReadingBean implements Serializable{
    private int count;
    private int start;
    private int total;
    private BookBean books[];

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public BookBean[] getBooks() {
        return books;
    }

    public void setBooks(BookBean[] books) {
        this.books = books;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(BookBean book : books){
            sb.append(book.getTitle()+"\n");
        }
        return sb.toString();
    }
}
