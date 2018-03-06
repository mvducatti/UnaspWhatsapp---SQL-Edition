package com.example.marcos.unaspwhatsapp_sqledition.Model;

/**
 * Created by Marcos on 02/03/2018.
 */

public class Noticia {

    private int user_id;
    private String newsTitle;
    private String newsMessage;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsMessage() {
        return newsMessage;
    }

    public void setNewsMessage(String newsMessage) {
        this.newsMessage = newsMessage;
    }
}
