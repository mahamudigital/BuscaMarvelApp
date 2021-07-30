package com.mahamudigitallab.buscandoheroismarvelapi.Api;

import com.mahamudigitallab.buscandoheroismarvelapi.Model.ReturnData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HeroServices {


    @GET("v1/public/characters")
    Call<ReturnData> getHeros(@Query("ts") String ts,
                              @Query("apikey") String apiKey,
                              @Query("hash") String hash);
}
