package com.example.tubesabp;

import com.example.tubesabp.data.model.Detail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCall {
    @GET("Data/{olahraga}/{id}.json")
    Call<Detail> getData(@Path("olahraga") String olahraga, @Path("id") String id);
}
