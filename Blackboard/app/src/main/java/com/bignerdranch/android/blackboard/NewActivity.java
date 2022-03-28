package com.bignerdranch.android.blackboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewActivity extends AppCompatActivity {

    private Button newBackButton;
    private Button newOrganizationButton;
    private EditText newNameEdittext;
    private EditText newIntroductionEdittext;
    private TextView newPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        newBackButton = findViewById(R.id.new_back);
        newOrganizationButton = findViewById(R.id.new_organization);
        newNameEdittext = findViewById(R.id.new_name);
        newIntroductionEdittext = findViewById(R.id.new_introduction);
        newPortrait = findViewById(R.id.new_portrait);

        newPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        newBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewActivity.this,BoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        newOrganizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewActivity.this,OrganizationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}