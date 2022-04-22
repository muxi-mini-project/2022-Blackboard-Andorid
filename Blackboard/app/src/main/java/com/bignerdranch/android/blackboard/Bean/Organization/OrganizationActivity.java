package com.bignerdranch.android.blackboard.Bean.Organization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Bean.Organization.Topic.TopicAdapter;
import com.bignerdranch.android.blackboard.Bean.Organization.Topic.Topics;
import com.bignerdranch.android.blackboard.Blackboard.New.PostActivity;
import com.bignerdranch.android.blackboard.Utils.MyResponse;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Utils.Utils;

import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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


    /*组织详细信息界面*/
    private List<Topics> topicsData;
    private TopicAdapter adapter;

    private CircleImageView photo;
    private TextView ognName;
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

        //获取基本资料
        String name = getIntent().getStringExtra("name");
        int id = getIntent().getIntExtra("id",-1);

        //初始化界面
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

        //获取详细信息
        NetGetInformation(name);    //获取组织信息
        NetGetTopic(id);            //获取组织列表
    }


    /*调用接口*/
    //查看话题
    private void NetGetTopic(int id)
    {
        //获取token
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
//                Toast.makeText(OrganizationActivity.this, "获取话题成功", Toast.LENGTH_SHORT).show();
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
    //设置界面基本信息
    private void NetGetInformation(String name)
    {
        //获取token
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
                }else
                {
                    Toast.makeText(OrganizationActivity.this, "error:"+response.code()+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse<Organization>> call, Throwable t) {
                Toast.makeText(OrganizationActivity.this, "啊偶 出错了呢 ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //请求创建一个话题   并更新话题数据
    private void NetCreateTopic(Topics topics)
    {
        //获取token
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
                    Toast.makeText(OrganizationActivity.this, "创建话题成功", Toast.LENGTH_SHORT).show();
                    //获取新的话题
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
                Toast.makeText(OrganizationActivity.this, "创建话题失败", Toast.LENGTH_SHORT).show();

            }
        });
    }


    //初始化控件
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
    //更新RLV
    public void refreshRLV(List<Topics> list) {
        topicsData.clear();
        topicsData.addAll(list);
        adapter.notifyDataSetChanged();
    }
    //添加按钮的响应
    public void addTopic(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrganizationActivity.this);
        final Dialog dialog= builder.create();
        View v = LayoutInflater.from(OrganizationActivity.this).inflate(R.layout.dialog_create,null);
        TextView cancel = v.findViewById(R.id.passive);
        TextView sure = v.findViewById(R.id.positive);
        EditText editText = v.findViewById(R.id.edittext);

        dialog.show();
        dialog.getWindow().setContentView(v);
        //使EdiText可以唤起软键盘
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
    //返回按钮
    public void ClickBack(View view) {
        finish();
    }



}

