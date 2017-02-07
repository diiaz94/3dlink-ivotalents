package com.threedlink.ivotalents.DTO;

/**
 * Created by diiaz94 on 23-01-2017.
 */
public class Message {
    private int imAuthor;

    private String resume;
    private String msg;
    private String date;

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
