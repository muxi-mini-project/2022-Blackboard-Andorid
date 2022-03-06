package com.bignerdranch.android.blackboard;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @POST("login")
    Call<LoginResponse> post(@Body User user);

    @GET("announcement")
    Call<Post> get(@Body Post post);
}
