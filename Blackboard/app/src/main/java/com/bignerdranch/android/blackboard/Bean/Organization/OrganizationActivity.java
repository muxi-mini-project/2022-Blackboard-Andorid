package com.bignerdranch.android.blackboard.Bean.Organization;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.blackboard.Blackboard.New.NewActivity;
import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Bean.Organization.Topic.TopicAdapter;
import com.bignerdranch.android.blackboard.Bean.Organization.Topic.Topics;
import com.bignerdranch.android.blackboard.Blackboard.New.PostActivity;
import com.bignerdranch.android.blackboard.Utils.MyResponse;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Utils.Utils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.BuildConfig;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class OrganizationActivity extends AppCompatActivity
{
    private static String EXTRA_NAME = "name";
    private static String EXTRA_ID = "id";

    public static Intent newIntent(Context context,String name,int id)
    {
        Intent intent = new Intent(context,OrganizationActivity.class);
        intent.putExtra(EXTRA_NAME,name);
        intent.putExtra(EXTRA_ID,id);
        return intent;
    }


    /*????????????????????????*/
    private List<Topics> topicsData;
    private TopicAdapter adapter;

    private CircleImageView photo;
    private TextView ognName;
    private TextView introduction;
    private RecyclerView TopicRLV;
    private Button addTopic;
    private Button backBTN;
    private TextView back;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        //??????????????????
        name = getIntent().getStringExtra("name");
        int id = getIntent().getIntExtra("id",-1);

        //???????????????
        initView();

        topicsData = new LinkedList<>();
        adapter = new TopicAdapter(OrganizationActivity.this,topicsData);
        TopicRLV.setAdapter(adapter);

        adapter.setItemClick(new TopicAdapter.OnItemClick() {
            @Override
            public void addClick(String GroupName) {
                Intent intent = PostActivity.newIntent(OrganizationActivity.this,name,GroupName);
                startActivity(intent);
            }
        });

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * ??????????????????
                 */
//                ??????????????????
                NetGetInformation(name);
                NetGetTopic(id);
            }
        }.start();

    }


    /*????????????*/
    //????????????
    private void NetGetTopic(int id)
    {
        //??????token
        SharedPreferences p = getSharedPreferences(Utils.SP, MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN, null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API get = retrofit.create(API.class);
        Call<MyResponse<List<Topics>>> call = get.topicDetail(String.valueOf(id),"100","0",Authorization);

        call.enqueue(new Callback<MyResponse<List<Topics>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<Topics>>> call, Response<MyResponse<List<Topics>>> response) {
//                Toast.makeText(OrganizationActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                List<Topics> list = response.body().getData();
                topicsData.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MyResponse<List<Topics>>> call, Throwable t)
            {
                Toast.makeText(OrganizationActivity.this, "sth wrong :( ", Toast.LENGTH_SHORT).show();
            }
        });



    }
    //????????????????????????
    private void NetGetInformation(String name)
    {
        //??????token
        SharedPreferences p = getSharedPreferences(Utils.SP, MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN, null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API get = retrofit.create(API.class);
        Call<MyResponse<Organization>> call = get.details(name,Authorization);

        call.enqueue(new Callback<MyResponse<Organization>>() {
            @Override
            public void onResponse(Call<MyResponse<Organization>> call, Response<MyResponse<Organization>> response)
            {
                if (response.isSuccessful())
                {
                    ognName.setText(response.body().getData().getOrganization_name());
                    introduction.setText(response.body().getData().getIntro());
                    String url = response.body().getData().getAvatar();

                    Glide.with(OrganizationActivity.this.getApplicationContext())
                            .load(url)
                            .into(photo);

                }else
                {
                    Toast.makeText(OrganizationActivity.this, "error:"+response.code()+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse<Organization>> call, Throwable t) {
                Toast.makeText(OrganizationActivity.this, "?????? ???????????? ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //????????????????????????   ?????????????????????
    private void NetCreateTopic(Topics topics)
    {

        //??????token
        SharedPreferences p = getSharedPreferences(Utils.SP, MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN, null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API post = retrofit.create(API.class);
        Call<MyResponse<Topics>> call = post.createTopic(topics,Authorization);

        call.enqueue(new Callback<MyResponse<Topics>>() {
            @Override
            public void onResponse(Call<MyResponse<Topics>> call, Response<MyResponse<Topics>> response)
            {
                if(response.isSuccessful())
                {
                    Toast.makeText(OrganizationActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    //??????????????????
                    topicsData.add(topics);
                    adapter.notifyDataSetChanged();
                }else
                {
                    Toast.makeText(OrganizationActivity.this, response.code()+"\n"+response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MyResponse<Topics>> call, Throwable t)
            {
                Toast.makeText(OrganizationActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();

            }
        });
    }


    //???????????????
    private void initView() {
        backBTN = findViewById(R.id.back_button2);
        back = findViewById(R.id.back2);
        photo = findViewById(R.id.photo2);
        ognName = findViewById(R.id.name2);
        introduction = findViewById(R.id.tag2);
        addTopic = findViewById(R.id.addTopic);
        TopicRLV = findViewById(R.id.TopicRLV);
        TopicRLV.setLayoutManager(new LinearLayoutManager(OrganizationActivity.this));
    }
    //??????RLV
    public void refreshRLV(List<Topics> list) {
        topicsData.clear();
        topicsData.addAll(list);
        adapter.notifyDataSetChanged();
    }
    //?????????????????????
    public void addTopic(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrganizationActivity.this);
        final Dialog dialog= builder.create();
        View v = LayoutInflater.from(OrganizationActivity.this).inflate(R.layout.dialog_create,null);
        TextView cancel = v.findViewById(R.id.passive);
        TextView sure = v.findViewById(R.id.positive);
        EditText editText = v.findViewById(R.id.edittext);

        dialog.show();
        dialog.getWindow().setContentView(v);
        //???EdiText?????????????????????
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        sure.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Topics newTopic = new Topics(
                        getIntent().getIntExtra("id",-1),
                        getIntent().getStringExtra("name"),
                        editText.getText().toString());
                NetCreateTopic(newTopic);
                dialog.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
    //????????????
    public void ClickBack(View view) {
        finish();
    }




}

