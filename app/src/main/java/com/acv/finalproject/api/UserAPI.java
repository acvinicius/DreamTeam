package com.acv.finalproject.api;

import com.acv.finalproject.model.User;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vinicius.castro on 08/03/2017.
 */

public interface UserAPI {
    @GET("/v2/58b9b1740f0000b614f09d2f")
    Call<User> getUser();
}
