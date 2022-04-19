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

    /*发布通知*/
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
        initView();

    }

    //初始化控件
    private void initView() {
        backButton = findViewById(R.id.back_button);
        back = findViewById(R.id.back);
        photo = findViewById(R.id.photo);
        editText = findViewById(R.id.edit);
        topic = findViewById(R.id.topic);
        addPhoto = findViewById(R.id.addPhoto);
        send = findViewById(R.id.send);
    }
    //返回
    public void clickBack(View view)
    {
        finish();
    }
}