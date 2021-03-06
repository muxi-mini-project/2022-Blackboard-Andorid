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
import com.bumptech.glide.Glide;
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

        SharedPreferences p = getSharedPreferences("URL", MODE_PRIVATE);
        String url = p.getString("url",null);
        Glide.with(this.getApplicationContext())
                .load(url)
                .into(ivAvatar);

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

        //???????????????????????????
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
        //??????OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//????????????????????????body???????????????

        //????????????????????????????????????OkHttp??????????????????????????????debug
        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //??????retrofit
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
                    //??????setText????????????textview????????????
                    mMineNickname.setText(nickname);
                    Log.d("MineActivity",nickname + "..........................");

                } else {
                    Log.d("MineActivity","error");
                    Toast.makeText(MineActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Information> call, Throwable t) {
                Toast.makeText(MineActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void showNormalDialog(){
        /* @setIcon ?????????????????????
         * @setTitle ?????????????????????
         * @setMessage ???????????????????????????
         * setXXX????????????Dialog???????????????????????????????????????
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MineActivity.this);
        normalDialog.setTitle("?????????");
        normalDialog.setMessage("????????????????????????????????????~");
        normalDialog.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        normalDialog.setNegativeButton("????????????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = normalDialog.create();

        // ??????
        dialog.show();

        //??????????????????
        //??????show()??????????????????????????????????????????????????????height???width
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // ?????????????????????????????????

        p.alpha = 0.8f;//???????????????
        dialogWindow.setAttributes(p);
    }

}