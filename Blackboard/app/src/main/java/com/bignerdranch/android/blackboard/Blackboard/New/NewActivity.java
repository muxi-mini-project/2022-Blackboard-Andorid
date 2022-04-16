package com.bignerdranch.android.blackboard.Blackboard.New;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.blackboard.API;
import com.bignerdranch.android.blackboard.Bean.Organization;
import com.bignerdranch.android.blackboard.Blackboard.BoardActivity;
import com.bignerdranch.android.blackboard.Bean.OrganizationActivity;
import com.bignerdranch.android.blackboard.MyResponse;
import com.bignerdranch.android.blackboard.R;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewActivity extends AppCompatActivity {

    /*新建组织*/

    private Context context;
    private CircleImageView Portrait;
    private TextView newPortrait;
    private EditText newNameEdittext;
    private EditText newIntroductionEdittext;
    private Button newBackButton;
    private Button newOrganizationButton;
    private int creatable=0;

//    private API createORGN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Portrait = findViewById(R.id.img_portrait);
        newPortrait = findViewById(R.id.new_portrait);

        newNameEdittext = findViewById(R.id.new_name);
        newIntroductionEdittext = findViewById(R.id.new_introduction);

        newBackButton = findViewById(R.id.new_back);
        newOrganizationButton = findViewById(R.id.new_organization);


        newOrganizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newNameEdittext.getText().toString().trim().equals(""))
                    Toast.makeText(NewActivity.this, "组织要有名字啊喂", Toast.LENGTH_SHORT).show();
                else {
                    creatable++;
                    if (newIntroductionEdittext.getText().toString().trim().equals(""))
                        Toast.makeText(NewActivity.this, "还要有酷酷de简介呢", Toast.LENGTH_SHORT).show();
                    else creatable++;
                }

                if (creatable == 2) {
                    Organization organization = new Organization(
                            newNameEdittext.getText().toString(),
                            newIntroductionEdittext.getText().toString()
                    );
                    sendNetWorkRequest(organization);
                }
            }
        });

//        newBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(NewActivity.this, BoardActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    public void ChangeAvatar2(View view)
    {
        switch(view.getId())
        {
            case R.id.new_portrait:
            case R.id.img_portrait:
                Toast.makeText(this, "跳转到上传头像", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void sendNetWorkRequest(Organization organization)
    {
        SharedPreferences p = getSharedPreferences("myPreferences",MODE_PRIVATE);
        String Authorization = p.getString("token","null");

        Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl("http://119.3.2.168:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        API createORGN = retrofit.create(API.class);
        Call<MyResponse<Organization>> call = createORGN.createOgn(organization,Authorization);

        call.enqueue(new Callback<MyResponse<Organization>>() {
            @Override
            public void onResponse(Call<MyResponse<Organization>> call, Response<MyResponse<Organization>> response) {
                Toast.makeText(NewActivity.this, "创建成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(NewActivity.this,OrganizationActivity.class);
                intent.putExtra("name",response.body().getData().getOrganization_name());
                intent.putExtra("id",response.body().getData().getID());
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<MyResponse<Organization>> call, Throwable t) {
                Toast.makeText(NewActivity.this, "sth wrong :( ", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void back(View view)
    {
        finish();
    }
}