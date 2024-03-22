package com.codeway_task.quote_of_the_day.Apiclient;

import com.codeway_task.quote_of_the_day.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/api/random")
    Call<Quotes[]> getRandomQuote();
    @GET("api/quotes")
    Call<List<Quote>> getQuotes();


}
