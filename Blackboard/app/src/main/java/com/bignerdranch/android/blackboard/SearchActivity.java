package com.bignerdranch.android.blackboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
                Intent intent = new Intent(SearchActivity.this,BoardActivity.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchEdittext.getText().toString();
                Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                intent.putExtra("search",search);
                startActivity(intent);
            }
        });
    }

}