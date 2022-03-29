package com.bignerdranch.android.blackboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import de.hdodenhof.circleimageview.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private Button mRegisterButton;
    private Retrofit mRetrofit;
    private API mRegister;
    private EditText mNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mRegisterButton = (Button) findViewById(R.id.register_button);
        mNickname = (EditText) findViewById(R.id.nickname);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeName changeName = new ChangeName(
                        mNickname.getText().toString()
                );

                sendNetworkRequest(changeName);


            }
        });
    }

    private void sendNetworkRequest(ChangeName changeName) {
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

        mRegister = mRetrofit.create(API.class);
        Call<ChangeName> call = mRegister.put(changeName,Authorization);
        //回调产生结果
        call.enqueue(new Callback<ChangeName>() {
            @Override
            public void onResponse(Call<ChangeName> call, Response<ChangeName> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(RegisterActivity.this, "欢迎您", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, BoardActivity.class);
                    startActivity(intent);

                } else {
                    Log.d("RegisterActivity", "error");
                    Toast.makeText(RegisterActivity.this, "出错了", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangeName> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "出错了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}