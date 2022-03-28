package com.bignerdranch.android.blackboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private Button settingsInformationButton;
    private Button settingsBackButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsInformationButton = findViewById(R.id.settings_information);
        settingsBackButton = findViewById(R.id.settings_back);
        exitButton = findViewById(R.id.exit);

        settingsInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,InformationActivity.class);
                intent.putExtra("from",1);
                startActivity(intent);
                finish();
            }
        });

        settingsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this,MineActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //写退出程序
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        //将DengLuActivity至于栈顶
                startActivity(intent);
                DestroyActivityUtil destroyActivityUtil = new DestroyActivityUtil();
                destroyActivityUtil.exit();        // DestroyActivityUtil类是专门用来退出程序的类，利用对象调用exit方法，即可完全退出程序
            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(SettingsActivity.this,MineActivity.class);
        startActivity(intent);
    }
}