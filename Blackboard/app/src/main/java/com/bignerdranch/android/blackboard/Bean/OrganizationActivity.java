package com.bignerdranch.android.blackboard.Bean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.blackboard.API;
import com.bignerdranch.android.blackboard.Blackboard.New.AddActivity;
import com.bignerdranch.android.blackboard.MyResponse;
import com.bignerdranch.android.blackboard.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrganizationActivity extends AppCompatActivity {

    /*组织详细信息界面*/


    private ImageView photo;
    private TextView name;
    private TextView introduction;
    private RecyclerView TopicRLV;
    private Button addTopic;
    private Button backBTN;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        //初始化界面
        initView();

        //网络请求获取详细信息
        Organization organization = new Organization(
                getIntent().getIntExtra("id",0),
                getIntent().getStringExtra("name")
        );
        sendNetRequest(organization);


    }

    private void initView() {
        backBTN = findViewById(R.id.back_button2);
        back = findViewById(R.id.back2);
        photo = findViewById(R.id.photo2);
        name = findViewById(R.id.name2);
        introduction = findViewById(R.id.tag2);
        addTopic = findViewById(R.id.addTopic);
        TopicRLV = findViewById(R.id.TopicRLV);
//        TopicRLV.setLayoutManager(new LinearLayoutManager(this));
    }

    private void sendNetRequest(Organization organization) {
        SharedPreferences p = getSharedPreferences("myPreference", MODE_PRIVATE);
        String Authorization = p.getString("token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API get = retrofit.create(API.class);
        Call<MyResponse<Organization>> call = get.details(organization, Authorization);

        call.enqueue(new Callback<MyResponse<Organization>>() {
            @Override
            public void onResponse(Call<MyResponse<Organization>> call, Response<MyResponse<Organization>> response) {
                Toast.makeText(OrganizationActivity.this, "good", Toast.LENGTH_SHORT).show();
                name.setText(response.body().getData().getOrganization_name());
                introduction.setText(response.body().getData().getIntro());
            }

            @Override
            public void onFailure(Call<MyResponse<Organization>> call, Throwable t) {

            }
        });
    }

    //返回按钮
    public void back(View view)
    {
        finish();
    }
    //添加分组
    public void addTopic(View view) {
        Intent intent = new Intent(OrganizationActivity.this, AddActivity.class);
        startActivity(intent);
    }
}