package com.bignerdranch.android.blackboard1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface API {
    @POST("login")
    Call<LoginResponse> post(@Body User user);

    @GET("announcement")
    Call<Post> get(@Body Post post);

    @PUT("user/changename")
    Call<ChangeName> put(@Body ChangeName changeName, @Header("Authorization") String Authorization);

    @GET("user/info")
    Call<Information> get(@Header("Authorization") String Authorization);
}
