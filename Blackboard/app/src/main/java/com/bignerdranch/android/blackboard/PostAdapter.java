package com.bignerdranch.android.blackboard;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemPostContents;
        private TextView mItemPostOrganization;
        private TextView mItemPostTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemPostContents = (TextView) itemView.findViewById(R.id.post_content);
            mItemPostOrganization = (TextView) itemView.findViewById(R.id.post_organization_name);
            mItemPostTime = (TextView) itemView.findViewById(R.id.post_time);
        }
    }

    public PostAdapter(List<Post> list) {
        postList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);

        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = postList.get(position);
        holder.mItemPostContents.setText(post.getContents());
        holder.mItemPostTime.setText(post.getUpdatedAt());
        holder.mItemPostOrganization.setText(post.getOrganization_name());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private API mPost;
    private Retrofit mRetrofit;

    private void sendNetworkRequest(Post post) {
        //创建OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//此处有四个级别，body为显示所有

        //判断是开发者模式，则调用OkHttp日志记录拦截器，方便debug
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //构建retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        mPost = mRetrofit.create(API.class);
        Call<Post> call = mPost.get(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                String object = response.body().getObject();

                String postJson = " [{}]";
                Gson gson = new Gson();
                Type postListType = new TypeToken<ArrayList<Post>>(){}.getType();

                List<Post> postList = gson.fromJson(postJson, postListType);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }

        });



    }
}
