package com.bignerdranch.android.blackboard.Mine.Post;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.API;
import com.bignerdranch.android.blackboard.MyResponse;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostFragment extends Fragment {

    private View view; //定义view用来设置fragment的layout
    public RecyclerView mPostRecyclerView; //定义RecyclerView
    //定义以post实体类为对象的数据集合
    private List<Posts.DataDTO> postList = new ArrayList<>();
    //自定义recyclerview的适配器
    private PostAdapter mPostAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post, container, false);

        //初始化recyclerview
        initRecyclerView();

        //填入数据
        initData();


        mPostAdapter.refresh(postList);


        return view;
    }


    private void initData() {
        SharedPreferences p = getActivity().getSharedPreferences(Utils.SP, MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN, null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API get = retrofit.create(API.class);
        Call<Posts> call = get.myPost(5,0,Authorization);

        call.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {

                Posts posts = response.body();
                postList.addAll(posts.getData());
                mPostAdapter.refresh(postList);
            }

            @Override
            public void onFailure(Call <Posts> call, Throwable t)
            {

            }
        });
    }



    private void initRecyclerView() {
        //获取RecyclerView
        mPostRecyclerView = (RecyclerView) view.findViewById(R.id.post_recyclerview);

        //创建adapter
        mPostAdapter = new PostAdapter(getActivity(),postList);
        //给RecyclerView设置adapter
        mPostRecyclerView.setAdapter(mPostAdapter);

        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mPostRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
//        mPostAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, Post data) {
//                //此处进行监听事件的业务处理
//                Toast.makeText(getActivity(), data.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//        });
    }

}
