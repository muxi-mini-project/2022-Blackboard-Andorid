package com.bignerdranch.android.blackboard.Blackboard.Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bignerdranch.android.blackboard.Blackboard.BoardActivity;
import com.bignerdranch.android.blackboard.R;

public class SearchResultActivity extends AppCompatActivity {

    private Button searchResultButton;
    private Button searchResultBackButton;
    private EditText searchResultEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_search,new SearchResultFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();

        searchResultButton = findViewById(R.id.search_result);
        searchResultBackButton = findViewById(R.id.search_result_back);
        searchResultEdittext = findViewById(R.id.search_result_edittext);

        Intent intent = getIntent();
        String searchResult = intent.getStringExtra("search");
        searchResultEdittext.setText(searchResult);

        searchResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        searchResultBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(SearchResultActivity.this, BoardActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }

    public void ClickBack(View view) {
        finish();
    }
}