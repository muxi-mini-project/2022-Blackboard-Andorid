package com.bignerdranch.android.blackboard.Bean.Message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationActivity;
import com.bignerdranch.android.blackboard.R;

public class MessageActivity extends AppCompatActivity {

    private static String EXTRA_NAME = "name";
    private static String EXTRA_DATE = "date";
    private static String EXTRA_TOPIC= "topic";
    private static String EXTRA_ID  = "ID";
    private static String EXTRA_CONTENT = "content";

    public static Intent newIntent(Context context,String name,String mDate,String topic,int id,String mContent)
    {
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra(EXTRA_NAME,name);
        intent.putExtra(EXTRA_DATE,mDate);
        intent.putExtra(EXTRA_TOPIC,topic);
        intent.putExtra(EXTRA_ID,id);
        intent.putExtra(EXTRA_CONTENT,mContent);
        return intent;
    }

    private TextView mName;
    private TextView mDate;
    private TextView mTopic;
    private TextView mContent;
    private Button mStar;
    private boolean isStar= false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        initView();

        mName.setText(getIntent().getStringExtra(EXTRA_NAME));
        mDate.setText(getIntent().getStringExtra(EXTRA_DATE));
        mTopic.setText(getIntent().getStringExtra(EXTRA_TOPIC));
        mContent.setText(getIntent().getStringExtra(EXTRA_CONTENT));

        mStar.setVisibility(View.INVISIBLE);
        mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getBackground() == getDrawable(R.drawable.unstar))
                    view.setBackground(getDrawable(R.drawable.star));
                else
                    view.setBackground(getDrawable(R.drawable.unstar));
            }
        });



    }

    private void initView()
    {
        mName = findViewById(R.id.nameAMD);
        mDate = findViewById(R.id.dateAMD);
        mTopic = findViewById(R.id.topicAMD);
        mContent = findViewById(R.id.contentAMD);
        mStar = findViewById(R.id.starAMD);
    }

    public void clickBack(View view) {finish(); }

    public void JumpOrganization(View view) {
        int id = getIntent().getIntExtra("ID",-1);
        Intent intent = OrganizationActivity.newIntent(MessageActivity.this,
                getIntent().getStringExtra(EXTRA_NAME),id);
        startActivity(intent);

    }


}