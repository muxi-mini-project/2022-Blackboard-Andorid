package com.bignerdranch.android.blackboard.Mine.Post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.Mine.Post.Post;
import com.bignerdranch.android.blackboard.Mine.Post.PostAdapter;
import com.bignerdranch.android.blackboard.R;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    private View view; //定义view用来设置fragment的layout
    public RecyclerView mPostRecyclerView; //定义RecyclerView
    //定义以post实体类为对象的数据集合
    private ArrayList<Post> postList = new ArrayList<Post>();
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

        return view;
    }

    private void initData() {
        for (int i=0;i<10;i++){
            Post post=new Post();
            post.setUpdatedAt("发布时间"+i);
            post.setContents("发布内容"+i);
            post.setOrganization_name("发布团队"+i);
            postList.add(post);
        }
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
        mPostAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Post data) {
                //此处进行监听事件的业务处理
                Toast.makeText(getActivity(), data.toString(), Toast.LENGTH_SHORT).show();
            }

        });
    }

}
