package com.bignerdranch.android.blackboard;

import com.bignerdranch.android.blackboard.Bean.Organization;
import com.bignerdranch.android.blackboard.Mine.Information;
import com.bignerdranch.android.blackboard.Mine.Post.Post;
import com.bignerdranch.android.blackboard.Settings.Change.ChangeName;
import com.bignerdranch.android.blackboard.Settings.Change.UploadAvatar;
import com.bignerdranch.android.blackboard.Settings.Login.LoginResponse;
import com.bignerdranch.android.blackboard.Settings.Login.User;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface API {


    /*创建组织*/
    @POST("organization/create")
    Call<MyResponse<Organization>> createOgn(@Body Organization organization, @Header("Authorization")String Authorization);

    /*查看指定组织*/
    //get不能有body怎么办？？？？
    @GET("organization/details")
    Call<MyResponse<Organization>> details(@Body Organization organization, @Header("Authorization")String Authorization);

    @POST("login")
    Call<LoginResponse> post(@Body User user);

    @GET("announcement")
    Call<Post> get(@Body Post post);

    @PUT("user/changename")
    Call<ChangeName> put(@Body ChangeName changeName, @Header("Authorization") String Authorization);

    @GET("user/info")
    Call<Information> get(@Header("Authorization") String Authorization);

//    @POST("organization/create")
//    Call<> post(@Body ,@Header("Authorization") String Authorization);

    @Multipart
    @POST("user/update")
    Call<UploadAvatar> post(@Part("file=") RequestBody body , @Header("Authorization") String Authorization );

}
