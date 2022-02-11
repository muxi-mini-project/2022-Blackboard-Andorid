package com.bignerdranch.android.blackboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button messageButton;
    private Button organizationButton;
    private BoardFragment f1;
    private OrganizationFragment f2;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //设置初始页面
        bottomNavigationView.setSelectedItemId(R.id.board);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch(menuitem.getItemId()) {
                    case R.id.board:
                        return true;

                    case R.id.mine:
                        startActivity(new Intent(getApplicationContext(),MineActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        manager = getSupportFragmentManager();
        init();
        messageButton.performClick();
    }

    private void init() {
        messageButton = findViewById(R.id.board_message);
        organizationButton = findViewById(R.id.board_organization);
        messageButton.setOnClickListener(this);
        organizationButton.setOnClickListener(this);
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            transaction.hide(f1);
        }
        if (f2 != null) {
            transaction.hide(f2);
        }
    }
    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = manager.beginTransaction();
        hideAllFragment(transaction);

        switch (view.getId()) {
            case R.id.board_message:
                if (f1 == null) {
                    f1 = new BoardFragment();
                    transaction.add(R.id.fragment_container, f1);
                } else {
                    transaction.show(f1);
                }
                break;
            case R.id.board_organization:
                if (f2 == null) {
                    f2 = new OrganizationFragment();
                    transaction.add(R.id.fragment_container, f2);
                } else {
                    transaction.show(f2);
                }
                break;
        }
        transaction.commit();
    }
}