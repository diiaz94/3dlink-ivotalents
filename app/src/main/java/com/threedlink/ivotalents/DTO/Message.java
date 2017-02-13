package com.threedlink.ivotalents.DTO;

/**
 * Created by diiaz94 on 23-01-2017.
 */
public class Message {
    private int imAuthor;

    private String resume;

    public Message(String msg, String date,String type) {
        this.msg = msg;
        this.date = date;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String msg;
    private String date;
    private String type;

    public Message(String name, String resume, int imAuthor) {
        this.name = name;
        this.resume = resume;
        this.imAuthor = imAuthor;
    }

    public int getImAuthor() {
        return imAuthor;
    }
    public void setImAuthor(int imAuthor) {
        this.imAuthor = imAuthor;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
