package com.codeway_task.quote_of_the_day.Apiclient;

import com.google.gson.annotations.SerializedName;

public class Quotes {
    @SerializedName("q")
    private String quoteText;

    @SerializedName("a")
    private String author;
    @SerializedName("h")
    private String h;

    public String getQuoteText() {
        return quoteText;
    }

    public String getAuthor() {
        return author;
    }
}
