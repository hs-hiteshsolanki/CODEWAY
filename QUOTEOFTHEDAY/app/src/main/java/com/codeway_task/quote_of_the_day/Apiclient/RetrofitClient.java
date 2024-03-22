package com.codeway_task.quote_of_the_day.Apiclient;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {

//    private static final String BASE_URL ="https://zenquotes.io/";
    private static final String BASE_URL ="https://type.fit/";

    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;

    private RetrofitClient(){
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

    }
    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient=new RetrofitClient();
        }
        return retrofitClient;
    }
    public ApiInterface getApi(){

        return retrofit.create(ApiInterface.class);
    }
}
