package com.bignerdranch.android.blackboard.Blackboard.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.blackboard.Blackboard.BoardActivity;
import com.bignerdranch.android.blackboard.R;

public class SearchActivity extends AppCompatActivity {

    private Button searchBackButton;
    private EditText searchEdittext;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBackButton = findViewById(R.id.search_back);
        searchButton = findViewById(R.id.search);
        searchEdittext = findViewById(R.id.search_edittext);

        searchBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchEdittext.getText().toString();
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("search",search);
                startActivity(intent);
            }
        });
    }

    public void ClickBack(View view) {
        finish();
    }
}