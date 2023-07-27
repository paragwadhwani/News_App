package com.parag.newapp.Network;

import com.parag.newapp.Model.NewListModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIS {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines")
    Observable<NewListModel> getNewsListData(@Query("country") String country, @Query("apiKey") String apiKey);

}
