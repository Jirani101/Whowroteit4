package com.example.jelani.whowroteit4;

/**
 * Created by jelani on 12/4/17.
 */

public class BookModel {

    private String author;
    private int year;

    public BookModel(String author, int year) {
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
