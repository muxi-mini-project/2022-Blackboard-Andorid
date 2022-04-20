package com.bignerdranch.android.blackboard;

import com.bignerdranch.android.blackboard.Bean.Organization.Organization;
import com.bignerdranch.android.blackboard.Bean.Topic.Topics;
import com.bignerdranch.android.blackboard.Mine.Information;
import com.bignerdranch.android.blackboard.Mine.Post.Posts;
import com.bignerdranch.android.blackboard.Settings.Change.ChangeName;
import com.bignerdranch.android.blackboard.Settings.Change.UploadAvatar;
import com.bignerdranch.android.blackboard.Settings.Login.LoginResponse;
import com.bignerdranch.android.blackboard.Settings.Login.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {


    /*创建组织*/
    @POST("organization/create")
    Call<MyResponse<Organization>> createOgn(@Body Organization organization, @Header("Authorization")String Authorization);

    /*查看指定组织*/
    @GET("organization/details/{name}")
    Call<MyResponse<Organization>> details(@Path("name")String name, @Header("Authorization")String Authorization);

    /*创建话题*/
    @POST("announcement/group")
    Call<MyResponse<Topics>> createTopic(@Body Topics topics,@Header("Authorization")String Authorization);

    /*查看话题*/
    @GET("announcement/group")
    Call<MyResponse<List<Topics>>> topicDetail(
            @Query("ID") String id,
            @Query("limit") String limit,
            @Query("page") String page, @Header("Authorization")String Authorization);

    /*查看创建的组织*/
    @GET("organization/personal/created")
    Call<MyResponse<List<Organization>>> myCreate(
            @Query("limit") int limit,
            @Query("page") int page, @Header("Authorization")String Authorization);

    /*查看关注的组织*/
    @GET("organization/personal/following")
    Call<MyResponse<List<Organization>>> myFollow(
            @Query("limit") int limit,
            @Query("page") int page,@Header("Authorization")String Authorization);


    @POST("login")
    Call<LoginResponse> post(@Body User user);

    @GET("announcement")
    Call<Posts> get(@Body Posts post);

    @PUT("user/changename")
    Call<ChangeName> put(@Body ChangeName changeName, @Header("Authorization") String Authorization);

    @GET("user/info")
    Call<Information> get(@Header("Authorization") String Authorization);

//    @POST("organization/create")
//    Call<> post(@Body ,@Header("Authorization") String Authorization);

    @Multipart
    @POST("user/update")
    Call<UploadAvatar> post(@Part("file=") RequestBody body , @Header("Authorization") String Authorization );

    @GET("user/published")
    Call<Posts> myPost(
            @Query("limit") int limit,
            @Query("page") int page, @Header("Authorization")String Authorization);

}
