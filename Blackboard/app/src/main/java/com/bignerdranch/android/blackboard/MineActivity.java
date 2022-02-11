package com.bignerdranch.android.blackboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

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