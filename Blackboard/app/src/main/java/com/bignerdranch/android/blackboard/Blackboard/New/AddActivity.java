package com.bignerdranch.android.blackboard.Blackboard.New;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.blackboard.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddActivity extends AppCompatActivity {

    /*添加话题*/

    private Button backButton;
    private TextView back;
    private CircleImageView photo;
    private EditText editText;
    private TextView topic;
    private ImageButton addPhoto;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //初始化界面
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        photo = findViewById(R.id.photo);
        editText = findViewById(R.id.edit);
        topic = findViewById(R.id.topic);
        addPhoto = findViewById(R.id.addPhoto);
        send = findViewById(R.id.send);

    }
}