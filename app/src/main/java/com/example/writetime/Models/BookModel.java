package com.example.writetime.Models;

public class BookModel {
    String image;
    String bookname;
    String writer;
    String bookdesc;
    String url;

    public BookModel(String image, String bookname, String writer ,String bookdesc,String url) {
        this.image = image;
        this.bookname = bookname;
        this.writer = writer;
        this.bookdesc = bookdesc;
        this.url = url;

    }

    public BookModel() { }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getBookname() {
        return bookname;
    }
    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getBookdesc() {
        return bookdesc;
    }
    public void setBookdesc(String bookdesc) {
        this.bookdesc = bookdesc;
    }

    public String getUrl() { return url;}
    public void setUrl(String url) { this.url = url; }
}
