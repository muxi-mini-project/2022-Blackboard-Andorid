package com.bignerdranch.android.blackboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MineActivity extends AppCompatActivity {

    private Button myPageButton;
    private Button myMessageButton;
    private Button myPostButton;
    private Button myFavoritesButton;

   //private static final String EXTRA_CLICK = "com.bignerdranch.android.blackboard.click";

    //public static Intent newIntent(Context packageContext,){

    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        myPageButton = findViewById(R.id.my_page);
        myPostButton = findViewById(R.id.my_post);
        myFavoritesButton = findViewById(R.id.my_favorites);
        myMessageButton = findViewById(R.id.my_message);

        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, PageActivity.class);
                startActivity(intent);
            }
        });

        myMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, BoardActivity.class);
                startActivity(intent);
            }
        });

        myPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, PageActivity.class);
                startActivity(intent);
            }
        });

        myFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, PageActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //设置现在选择的页面
        bottomNavigationView.setSelectedItemId(R.id.mine);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch(menuitem.getItemId()) {
                    case R.id.board:
                        startActivity(new Intent(getApplicationContext(),BoardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.mine:
                        return true;

                }
                return false;
            }
        });
    }
}