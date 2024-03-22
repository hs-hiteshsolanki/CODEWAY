package com.codeway_task.quote_of_the_day.Apiclient;

import com.google.gson.annotations.SerializedName;

public class Quote {
    @SerializedName("text")
    private String text;
    @SerializedName("author")
    private String author;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public Quote(String text, String author) {
        this.text = text;
        this.author = author;

    }

    public void setAuthor(String author) {
        this.author = author;
    }



}
