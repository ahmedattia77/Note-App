package com.example.nonote;

public class Note {
    private String Title;
    private String des;
    private String date;
    private String time;

    public Note() {
    }

    public Note(String title, String des, String date, String time) {
        Title = title;
        this.des = des;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
