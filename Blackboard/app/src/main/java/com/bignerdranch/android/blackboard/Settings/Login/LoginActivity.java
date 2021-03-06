package com.bignerdranch.android.blackboard.Settings.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Blackboard.BoardActivity;
import com.bignerdranch.android.blackboard.Mine.Information;
import com.bignerdranch.android.blackboard.R;

import de.hdodenhof.circleimageview.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    private EditText mNumber;
    private EditText mPassword;
    private Retrofit mRetrofit;
    private API mLogin;
    private String token;
    private API information;
    private Retrofit mRetrofit1;

    private ImageButton eye;
    private Boolean isHide = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences myPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        Boolean remember = myPreferences.getBoolean("remember", false);
        if(remember){
            Intent intent=new Intent(LoginActivity.this,BoardActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }

        mNumber = (EditText) findViewById(R.id.number);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.login_button);
        eye = findViewById(R.id.eye);

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHide==true)
                {
                    isHide = false;
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    mPassword.setTransformationMethod(method1);
                }else
                {
                    isHide = true;
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    mPassword.setTransformationMethod(method);
                }
                int index = mPassword.getText().toString().length();
                mPassword.setSelection(index);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(
                        mNumber.getText().toString(),
                        mPassword.getText().toString()
                );

                sendNetworkRequest(user);
            }
        });
    }

    private void sendNetworkRequest(User user) {
        //??????OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//????????????????????????body???????????????

        //????????????????????????????????????OkHttp??????????????????????????????debug
        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //??????retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        mLogin = mRetrofit.create(API.class);
        Call<LoginResponse> call = mLogin.post(user);
        //????????????????????????
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() == true) {
                    token = "Bearer:" + response.body().getData();

                    SharedPreferences p = getSharedPreferences("myPreferences", MODE_PRIVATE);
                    //??????SharedPreferences???Editor??????
                    SharedPreferences.Editor editor = p.edit();
                    editor.putString("token",token);
                    editor.putBoolean("remember",true);
                    editor.commit();//?????????????????????????????????

                    Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();

                    getNickname();
                    finish();

//                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                    startActivity(intent);



                } else {
                    Toast.makeText(LoginActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNickname(){

        //??????OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//????????????????????????body???????????????

        //????????????????????????????????????OkHttp??????????????????????????????debug
        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //??????retrofit
        mRetrofit1 = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        SharedPreferences p = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Authorization = p.getString("token","null");

        information = mRetrofit1.create(API.class);
        Call<Information> call = information.get(Authorization);

        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                if (response.isSuccessful()) {

                    String nickname = response.body().getData().getNickname();

                    Intent intent;
                    if(nickname.equals(" ")){
                        intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    }else{
                        intent = new Intent(LoginActivity.this, BoardActivity.class);
                    }
                    startActivity(intent);

                    finish();

                } else {
                    Log.d("LoginActivity","error");
                }
            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {

            }
        });

    }


}
