package com.bignerdranch.android.blackboard.Mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Blackboard.BoardActivity;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Settings.Change.ImageUtil;
import com.bignerdranch.android.blackboard.Settings.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MineActivity extends AppCompatActivity {

    private Button myPageButton;
    private Button myMessageButton;
    private Button myPostButton;
    private Button myFavoritesButton;
    private Retrofit mRetrofit;
    private API information;
    private TextView mMineNickname;
    private Button settingsButton;
    private ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        myPageButton = findViewById(R.id.my_page);
        myPostButton = findViewById(R.id.my_post);
        myFavoritesButton = findViewById(R.id.my_favorites);
        myMessageButton = findViewById(R.id.my_message);
        mMineNickname = (TextView)findViewById(R.id.mine_nickname);
        settingsButton = findViewById(R.id.settings);
        ivAvatar = findViewById(R.id.mine_portrait);

        SharedPreferences spfRecord = getSharedPreferences("spfRecord", MODE_PRIVATE);
        String image64 = spfRecord.getString("image_64", "");
        ivAvatar.setImageBitmap(ImageUtil.base64ToImage(image64));

        sendNetworkRequest();

        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, PageActivity.class);
                intent.putExtra("selector",true);
                startActivity(intent);
            }
        });

        /*myMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, .class);
                startActivity(intent);
            }
        });**/

        myPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, PageActivity.class);
                intent.putExtra("selector",true);
                startActivity(intent);
            }
        });

        myFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, PageActivity.class);
                intent.putExtra("selector",false);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //设置现在选择的页面
        bottomNavigationView.setSelectedItemId(R.id.mine);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                switch(menuitem.getItemId()) {
                    case R.id.board:
                        startActivity(new Intent(getApplicationContext(), BoardActivity.class));
                        overridePendingTransition(0, 0 );
//                        overridePendingTransition(0,android.R.anim.fade_out);
                        finish();
                        return true;

                    case R.id.mine:
                        return true;

                    case R.id.study:
                        showNormalDialog();

                }
                return false;
            }
        });


    }

    public void sendNetworkRequest() {
        //创建OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//此处有四个级别，body为显示所有

        //判断是开发者模式，则调用OkHttp日志记录拦截器，方便debug
        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //构建retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        SharedPreferences p = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Authorization = p.getString("token","null");

        information = mRetrofit.create(API.class);
        Call<Information> call = information.get(Authorization);

        call.enqueue(new Callback<Information>() {
            @Override
            public void onResponse(Call<Information> call, Response<Information> response) {
                if (response.isSuccessful()) {

                    String nickname = response.body().getData().getNickname();
                    //使用setText的方法对textview动态赋值
                    mMineNickname.setText(nickname);
                    Log.d("MineActivity",nickname + "..........................");

                } else {
                    Log.d("MineActivity","error");
                    Toast.makeText(MineActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                Toast.makeText(MineActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MineActivity.this);
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