package com.bignerdranch.android.blackboard.Blackboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bignerdranch.android.blackboard.Blackboard.LatestMessage.BoardFragmentRLV;
import com.bignerdranch.android.blackboard.Blackboard.New.NewActivity;
import com.bignerdranch.android.blackboard.Blackboard.OrganizationList.OrganizationFragment;
import com.bignerdranch.android.blackboard.Blackboard.Search.SearchActivity;
import com.bignerdranch.android.blackboard.Blackboard.Search.SearchResultActivity;
import com.bignerdranch.android.blackboard.Mine.MineActivity;
import com.bignerdranch.android.blackboard.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button messageButton;
    private Button organizationButton;
    private Button searchButton;
    private Button addButton;

    private BoardFragmentRLV f1;
    private OrganizationFragment f2;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    //用于切换 通知和组织 的按钮
    int flag[] = new int[]{1, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        /*          设置初始页面          */

        //初始化 BottomNavigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.board);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch (menuitem.getItemId()) {
                    case R.id.board:
                        return true;

                    case R.id.mine:
                        startActivity(new Intent(getApplicationContext(), MineActivity.class));
                        overridePendingTransition(0, 0 );
//                        overridePendingTransition(0, android.R.anim.fade_out );
                        finish();
                        return true;

                    case R.id.study:
                        showNormalDialog();

                }
                return false;
            }
        });

        //搜索按钮
        searchButton = findViewById(R.id.board_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardActivity.this, SearchResultActivity.class);
                startActivity(intent);
            }
        });
        //新建按钮
        addButton = findViewById(R.id.board_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });
        //设置FragmentManager
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //通知按钮
        messageButton = findViewById(R.id.board_message);
        messageButton.setOnClickListener(this);
        //组织按钮
        organizationButton = findViewById(R.id.board_organization);
        organizationButton.setOnClickListener(this);
        //默认点击 通知
        messageButton.performClick();
        if (f1 == null) {
            f1 = new BoardFragmentRLV();
            transaction.add(R.id.fragment_container, f1);
        }
    }

    @Override
    public void onClick(View view) {
        transaction = manager.beginTransaction();

        switch (view.getId()) {
            case R.id.board_message: {
                //如果已经被点击 那么不做反应
                if(flag[0]== 1)
                    break;
                //切换Fragment
                if (f1 == null) {
                    f1 = new BoardFragmentRLV();
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

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(BoardActivity.this);
        normalDialog.setTitle("学习圈");
        normalDialog.setMessage("别急别急，在做了，在做了~");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        normalDialog.setNegativeButton("被迫确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = normalDialog.create();

        // 显示
        dialog.show();

        //自定义的东西
        //放在show()之后，不然有些属性是没有效果的，比如height和width
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
    }

}