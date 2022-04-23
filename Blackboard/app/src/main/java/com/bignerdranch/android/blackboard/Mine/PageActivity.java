package com.bignerdranch.android.blackboard.Mine;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Mine.Favourite.FavoritesFragment;
import com.bignerdranch.android.blackboard.Mine.Post.PostFragment;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Settings.Change.ImageUtil;
import com.bignerdranch.android.blackboard.Settings.Change.InformationActivity;
import com.bumptech.glide.Glide;

import java.io.InputStream;

import de.hdodenhof.circleimageview.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button pagePostButton;
    private Button pageFavoritesButton;
    private Button pageInformationButton;
    private TextView pageNicknameTextview;
    private FragmentManager manager2;
    private PostFragment f3;
    private FavoritesFragment f4;
    private Retrofit mRetrofit;
    private API information;
    private ImageView ivAvatar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        manager2 = getSupportFragmentManager();

        ivAvatar = findViewById(R.id.page_portrait);

        SharedPreferences p = getSharedPreferences("URL", MODE_PRIVATE);
        String url = p.getString("url",null);
        Glide.with(this)
                .load(url)
                .into(ivAvatar);

        init();

        sendNetworkRequest();

        pageInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageActivity.this, InformationActivity.class);
                intent.putExtra("from",0);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        boolean selector = intent.getBooleanExtra("selector",true);
        if(selector == true){
            pagePostButton.performClick();
        } else{
            pageFavoritesButton.performClick();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void init() {
        pagePostButton = findViewById(R.id.page_post);
        pageFavoritesButton = findViewById(R.id.page_favorites);
        pageInformationButton = findViewById(R.id.page_information);
        pageNicknameTextview = findViewById(R.id.page_nickname);
        pagePostButton.setOnClickListener(this);
        pageFavoritesButton.setOnClickListener(this);
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (f3 != null) {
            transaction.hide(f3);
        }
        if (f4 != null) {
            transaction.hide(f4);
        }
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = manager2.beginTransaction();
        hideAllFragment(transaction);

        switch (view.getId()) {
            case R.id.page_post:
                pagePostButton.setBackground(getDrawable(R.drawable.button_clicked));
                pageFavoritesButton.setBackground(getDrawable(R.drawable.button_unclicked));
                if (f3 == null) {
                    f3 = new PostFragment();
                    transaction.add(R.id.page_fragment_container, f3);
                } else {
                    transaction.show(f3);
                }
                break;
            case R.id.page_favorites:
                pageFavoritesButton.setBackground(getDrawable(R.drawable.button_clicked));
                pagePostButton.setBackground(getDrawable(R.drawable.button_unclicked));
                if (f4 == null) {
                    f4 = new FavoritesFragment();
                    transaction.add(R.id.page_fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                break;
        }
        transaction.commit();
    }


    public void sendNetworkRequest() {
        //创建OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//此处有四个级别，body为显示所有

        //判断是开发者模式，则调用OkHttp日志记录拦截器，方便debug
        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //构建retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        SharedPreferences p = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Authorization = p.getString("token","null");

        information = mRetrofit.create(API.class);
        Call<Information> call = information.get(Authorization);

        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                if (response.isSuccessful()) {

                    String nickname = response.body().getData().getNickname();
                    pageNicknameTextview.setText(nickname);
                    Toast.makeText(PageActivity.this, nickname, Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(PageActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                Toast.makeText(PageActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

    }
}