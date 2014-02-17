package com.retor.p000001;

import java.util.ArrayList;

/**
 * Created by Антон on 10.02.14.
 */
class massToView {
    private String title;
    private String author;
    private String picture;
    private String date;
    private long tid;
    private int commentCount;
    private ArrayList<Comment> listComments;


    public massToView(String _title, long _tid, int _commentCount, String _author, String _date, String _picture, ArrayList<Comment> _listComments){
        setTitle(_title);
        setAuthor(_author);
        setDate(_date);
        setTid(_tid);
        setPicture(_picture);
        //setCommentCount(_commentCount);
        listComments = _listComments;
        commentCount = _listComments.size();
    }

    public void setTitle(String _title){
        title=_title;
    }
    public void setAuthor(String _author){
        author=_author;
    }
    public String getTitle(){
        return title.toString();
    }
    public String getAuthor(){
        return author.toString();
    }
    public void setDate(String _date){
        date = _date;
    }
    public String getDate(){
        return date.toString();
    }
    public void setTid(long _tid) {
        tid = _tid;
    }
    public long getTid() {

        return tid;
    }
    public void setPicture(String _picture) {
        picture = _picture;
    }
    public String getPicture() {
        return picture.toString();
    }
    public void setCommentCount(int _commentCount) {
        commentCount = _commentCount;
    }
    public int getCommentCount() {
        return commentCount;
    }
    public void setListComments(ArrayList<Comment> _listComments) {
        listComments = _listComments;
    }
    public ArrayList<Comment> getListComments() {
        return listComments;
    }

    public int getCount() {
        return listComments.size();
    }
}