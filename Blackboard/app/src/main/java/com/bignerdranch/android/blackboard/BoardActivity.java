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
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button messageButton;
    private Button organizationButton;
    private Button searchButton;
    private Button addButton;

    private BoardFragment f1;
    private OrganizationFragment f2;
    private FragmentManager manager1;

    //用于切换 通知和组织 的按钮
    int flag[] = new int[]{0, 0};
<<<<<<< HEAD
=======
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button messageButton;
    private Button organizationButton;
    private BoardFragment f1;
    private OrganizationFragment f2;
    private FragmentManager manager1;
    private Button searchButton;
    private Button addButton;
>>>>>>> flsdqm
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
        /*          设置初始页面          */

        //初始化 BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.board);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
<<<<<<< HEAD
=======
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.board_search);
        addButton = findViewById(R.id.board_add);

        //设置初始页面
        bottomNavigationView.setSelectedItemId(R.id.board);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch(menuitem.getItemId()) {
>>>>>>> flsdqm
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
                    case R.id.board:
                        return true;

                    case R.id.mine:
<<<<<<< HEAD
<<<<<<< HEAD
                        startActivity(new Intent(getApplicationContext(), MineActivity.class));
                        overridePendingTransition(0, 0);
=======
                        startActivity(new Intent(getApplicationContext(),MineActivity.class));
                        overridePendingTransition(0,0);
>>>>>>> flsdqm
=======
                        startActivity(new Intent(getApplicationContext(), MineActivity.class));
                        overridePendingTransition(0, 0);
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
                        return true;

                }
                return false;
            }
        });

<<<<<<< HEAD
<<<<<<< HEAD
        //初始化 搜索和添加 按钮
        searchButton = findViewById(R.id.board_search);
        addButton = findViewById(R.id.board_add);
=======
        manager1 = getSupportFragmentManager();
        init();
        messageButton.performClick();

>>>>>>> flsdqm
=======
        //初始化 搜索和添加 按钮
        searchButton = findViewById(R.id.board_search);
        addButton = findViewById(R.id.board_add);
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> flsdqm
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83

        //设置FragmentManager
        manager1 = getSupportFragmentManager();
        //初始化 通知和组织 按钮
<<<<<<< HEAD
=======
    }

    private void init() {
>>>>>>> flsdqm
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
        messageButton = findViewById(R.id.board_message);
        organizationButton = findViewById(R.id.board_organization);
        messageButton.setOnClickListener(this);
        organizationButton.setOnClickListener(this);
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
        //默认点击 通知
        messageButton.performClick();

    }


    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = manager1.beginTransaction();

        switch (view.getId()) {
            case R.id.board_message: {
                //如果已经被点击 那么不做反应
                if(flag[0]== 1)
                    break;
                //切换Fragment
                if (f1 == null) {
                    f1 = new BoardFragment();
                }
                transaction.replace(R.id.fragment_container, f1);
                if (flag[0] == 0) {
                    flag[0] = 1;
                    flag[1] = 0;
                } else {
                    flag[0] = 0;
                    flag[1] = 1;
                }
            }
            break;
            case R.id.board_organization: {
                //如果已经被点击 那么不做反应
                if(flag[1] == 1)
                    break;
                //切换Fragment
                if (f2 == null) {
                    f2 = new OrganizationFragment();
                }
                transaction.replace(R.id.fragment_container, f2);
                if (flag[1] == 0)
                {
                    flag[1] = 1;
                    flag[0] = 0;
                } else {
                    flag[1] = 0;
                    flag[0] = 1;
                }
            }
            break;
        }

        transaction.commit();

        //切换 按钮的颜色background
        if (flag[0] == 0)
        {
            messageButton.setBackground(getDrawable(R.drawable.button_unclicked));
        } else {
            messageButton.setBackground(getDrawable(R.drawable.button_clicked));
        }

        if (flag[1] == 0)
        {
            organizationButton.setBackground(getDrawable(R.drawable.button_unclicked));
        } else {
            organizationButton.setBackground(getDrawable(R.drawable.button_clicked));
        }
<<<<<<< HEAD
=======
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
        FragmentTransaction transaction = manager1.beginTransaction();
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
>>>>>>> flsdqm
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
    }
}