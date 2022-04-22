package com.bignerdranch.android.blackboard.Blackboard.LatestMessage;

import static android.content.Context.MODE_PRIVATE;
import static com.bignerdranch.android.blackboard.R.drawable.star;
import static com.bignerdranch.android.blackboard.R.drawable.unstar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bignerdranch.android.blackboard.Bean.Message.MessageItem;
import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationActivity;
import com.bignerdranch.android.blackboard.Bean.Organization.Topic.Topics;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Utils.MyResponse;
import com.bignerdranch.android.blackboard.Utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardFragmentRLV extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout freshLayout;
    private View view;

    private RecyclerView mRecyclerView;
    private BoardAdapter boardAdapter;
    private List<MessageItem> data = new LinkedList<MessageItem>();
    private List<MessageItem> like = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //需要返回一个View        所以调用inflate
        if (view == null)
            view = inflater.inflate(R.layout.fragment_board, container, false);
        else
            return view;

        //获取数据
        NetGetAnnounce(100,0);
        NetGetLike();

        /*下拉刷新*/
        freshLayout = view.findViewById(R.id.freshMess);
        freshLayout.setOnRefreshListener(this);


        /*构造一个RecyclerView*/
        //绑定布局
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rlv_board);
        //linearlayout
        LinearLayoutManager LM = new LinearLayoutManager(getActivity());
        LM.setStackFromEnd(true);//由底部开始展示
        LM.setReverseLayout(true);//
        mRecyclerView.setLayoutManager(LM);
        //adapter
        boardAdapter = new BoardAdapter(this, data);
        mRecyclerView.setAdapter(boardAdapter);
        //监听
        boardAdapter.setItemOnClickListener(new BoardAdapter.ItemOnClickListener() {
            @Override
            public void OnStarClick(View view, int position) {
            }

            @Override
            public void OnAvatarClick(String name, int id) {
                Intent intent = OrganizationActivity.newIntent(getActivity(),name,id);
                startActivity(intent);
            }

            @Override
            public void OnItemClick() {

            }
        });

        //返回View
        return view;
    }

    /*网络请求*/
    private void NetGetLike() {
        SharedPreferences p = getActivity().getSharedPreferences(Utils.SP, MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN, null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API get = retrofit.create(API.class);
        Call<MyResponse<List<MessageItem>>> call = get.collected(1000,0,Authorization);

        call.enqueue(new Callback<MyResponse<List<MessageItem>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<MessageItem>>> call, Response<MyResponse<List<MessageItem>>> response)
            {
                if (response.isSuccessful())
                {
                    like.addAll(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<MessageItem>>> call, Throwable t) {
                Toast.makeText(getActivity(), "网络出错了", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void NetGetAnnounce(int limit ,int page) {
        //获取token
        SharedPreferences p = getActivity().getSharedPreferences(Utils.SP, MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN, null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API get = retrofit.create(API.class);
        Call<MyResponse<List<MessageItem>>> call = get.announcement(limit,page,Authorization);

        call.enqueue(new Callback<MyResponse<List<MessageItem>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<MessageItem>>> call, Response<MyResponse<List<MessageItem>>> response)
            {
                if (response.isSuccessful()) {
                    data.addAll(response.body().getData());
                    freshLayout.setRefreshing(false);
                    mRecyclerView.scrollToPosition(boardAdapter.getItemCount()-1);
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<MessageItem>>> call, Throwable t) {

            }
        });
    }
    private void NetCollect()  {}

    @Override
    public void onRefresh()
    {
        int size = data.size();
        NetGetAnnounce(size,1);
    }
}