package com.bignerdranch.android.blackboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class OrganizationActivity extends AppCompatActivity {


    private Button backBTN;
    private TextView back;
    private ImageView photo;
    private TextView name;
    private TextView tag;
    private RecyclerView TopicRLV;
    private Button addTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        //初始化界面
        backBTN = findViewById(R.id.back_button2);
        back = findViewById(R.id.back2);
        photo = findViewById(R.id.photo2);
        name = findViewById(R.id.name2);
        tag = findViewById(R.id.tag2);
        TopicRLV = findViewById(R.id.TopicRLV);
        addTopic = findViewById(R.id.addTopic);

        photo.setImageDrawable(getDrawable(R.drawable.qq));
        name.setText("MUXI");
        tag.setText("天真活泼们");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrganizationActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

    }
}