package com.bignerdranch.android.blackboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button messageButton;
    private Button organizationButton;
    private Button searchButton;
    private Button addButton;

    private BoardFragment f1;
    private OrganizationFragment f2;
    private FragmentManager manager1;

    //用于切换 通知和组织 的按钮
    int flag[] = new int[]{0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        /*          设置初始页面          */

        //初始化 BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.board);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
                    case R.id.board:
                        return true;

                    case R.id.mine:
                        startActivity(new Intent(getApplicationContext(), MineActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

        //初始化 搜索和添加 按钮
        searchButton = findViewById(R.id.board_search);
        addButton = findViewById(R.id.board_add);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });

        //设置FragmentManager
        manager1 = getSupportFragmentManager();
        //初始化 通知和组织 按钮
        messageButton = findViewById(R.id.board_message);
        organizationButton = findViewById(R.id.board_organization);
        messageButton.setOnClickListener(this);
        organizationButton.setOnClickListener(this);
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
    }
}