package com.example.writetime.Models;

public class ModelComment {
    //variables
    String id, bookID, timestamp, comment, uid;

    //constructor, empty required by firebase
public ModelComment() {
    }


    //constructor, with all params
    public ModelComment(String id, String bookID, String timestamp, String comment, String uid) {
        this.id = id;
        this.bookID = bookID;
        this.timestamp = timestamp;
        this.comment = comment;
        this.uid = uid;
    }


//    Getter setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
