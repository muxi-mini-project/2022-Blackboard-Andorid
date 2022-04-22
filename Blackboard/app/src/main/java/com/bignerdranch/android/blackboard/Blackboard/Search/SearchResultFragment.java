package com.bignerdranch.android.blackboard.Blackboard.Search;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.Bean.Organization.Organization;
import com.bignerdranch.android.blackboard.Blackboard.BoardActivity;
import com.bignerdranch.android.blackboard.Mine.Information;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Settings.Login.LoginActivity;
import com.bignerdranch.android.blackboard.Settings.Login.RegisterActivity;
import com.bignerdranch.android.blackboard.Utils.API;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultFragment extends Fragment {
    private View view; //定义view用来设置fragment的layout
    public RecyclerView mSearchRecyclerView; //定义RecyclerView
    //自定义recyclerview的适配器
    private SearchAdapter mSearchAdapter;
    private ArrayList<RelativeMessage> mSearchRelativeMessageList = new ArrayList<RelativeMessage>();
    private ArrayList<RelativeOrganization> mSearchRelativeOrganizationList = new ArrayList<RelativeOrganization>();

    private int x = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_result, container, false);

        //初始化recyclerview
        initRecyclerView();

        //填入数据
        initData();

        return view;
    }

    private void initData() {

        for (int i=0;i<2;i++){
        RelativeMessage relativeMessage = new RelativeMessage();
        relativeMessage.setTag("#标签"+i);
        relativeMessage.setContents("发布内容"+i);
        mSearchRelativeMessageList.add(relativeMessage);
        }


        for (int i=0;i<2;i++){
            RelativeOrganization relativeOrganization = new RelativeOrganization();
            relativeOrganization.setOrganizationName("组织名称"+i);
            relativeOrganization.setOrganizationIntroduction("组织介绍"+i);
            mSearchRelativeOrganizationList.add(relativeOrganization);
        }

    }

    private void initRecyclerView() {
        //获取RecyclerView
        mSearchRecyclerView = (RecyclerView) view.findViewById(R.id.search_recyclerview);

        //创建adapter
        mSearchAdapter = new SearchAdapter(getActivity(),mSearchRelativeMessageList,mSearchRelativeOrganizationList);
        //给RecyclerView设置adapter
        mSearchRecyclerView.setAdapter(mSearchAdapter);

        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mSearchRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mSearchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick2(View view, int position, Button subscribe) {
                    if (x == 0) {
                        subscribe.setText("已关注");
                        subscribe.setTextColor(Color.parseColor("#FF000000"));
                        subscribe.setBackgroundResource(R.drawable.button_subscribed);
//                        subscribe(organization);
                        x = 1;
                    } else {
                        subscribe.setText("关注");
                        subscribe.setTextColor(Color.parseColor("#66ccff"));
                        subscribe.setBackgroundResource(R.drawable.button_subscribe);
                        x = 0;
                    }
            }

            @Override
            public void OnItemClick1(View view, int position) {
                //此处进行监听事件的业务处理

            }

        });
    }

    private void subscribe(Organization organization){
        //创建OkHttp client
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//此处有四个级别，body为显示所有

        //判断是开发者模式，则调用OkHttp日志记录拦截器，方便debug
        if(BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(logging);
        }
        //构建retrofit
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();

        SharedPreferences p = getActivity().getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Authorization = p.getString("token",null);

        API subscribe = mRetrofit.create(API.class);
        Call<Subscribe> call = subscribe.subscribe(organization,Authorization);

        call.enqueue(new Callback<Subscribe>() {
            @Override
            public void onResponse(Call<Subscribe> call, Response<Subscribe> response) {
                if (response.isSuccessful()) {


                } else {
                    Log.d("SearchResultFragment","error");
                }
            }

            @Override
            public void onFailure(Call<Subscribe> call, Throwable t) {

            }
        });
    }


}
