package com.bignerdranch.android.blackboard.Blackboard.New;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.blackboard.Bean.Message.MessageItem;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Utils.MyResponse;
import com.bignerdranch.android.blackboard.Utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    private static String EXTRA_OgnName = "OgnName";
    private static String EXTRA_GroupName = "GroupName";

    public Intent newIntent(Context context,String OgnName, String GroupName)
    {
        Intent intent = new Intent(context,PostActivity.class);
        intent.putExtra(EXTRA_OgnName,OgnName);
        intent.putExtra(EXTRA_GroupName,GroupName);
        return intent;
    }

    /*发布通知*/
    private CircleImageView photo;
    private EditText editText;
    private TextView topic;
    private ImageButton addPhoto;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //初始化界面
        initView();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (editText.getText().toString().trim().equals(""))
                    Toast.makeText(PostActivity.this, "要不写点什么", Toast.LENGTH_SHORT).show();
                else NetPostMessage(new MessageItem(
                        getIntent().getStringExtra(EXTRA_OgnName),
                        getIntent().getStringExtra(EXTRA_GroupName),
                        editText.getText().toString()));
            }
        });

    }

    private void NetPostMessage(MessageItem messageItem)
    {
        SharedPreferences p = getSharedPreferences(Utils.SP, Context.MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN,null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API post = retrofit.create(API.class);
        Call<MyResponse<MessageItem>> call = post.announcement(messageItem,Authorization);

        call.enqueue(new Callback<MyResponse<MessageItem>>() {
            @Override
            public void onResponse(Call<MyResponse<MessageItem>> call, Response<MyResponse<MessageItem>> response)
            {
                if (response.isSuccessful())
                    Toast.makeText(PostActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(PostActivity.this, ":(", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<MyResponse<MessageItem>> call, Throwable t) {

            }
        });
    }

    //初始化控件
    private void initView() {
        photo = findViewById(R.id.photo);
        editText = findViewById(R.id.edit);
        topic = findViewById(R.id.topic);
        addPhoto = findViewById(R.id.addPhoto);
        send = findViewById(R.id.send);
    }
    //返回
    @Override
    public void onBackPressed() {
        finish();
    }
    public void clickBack(View view) {
        finish();
    }
}