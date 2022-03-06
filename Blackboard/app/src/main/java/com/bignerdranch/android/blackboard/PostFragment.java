package com.bignerdranch.android.blackboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    PostViewModel postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_post, container, false);
        final RecyclerView postRecyclerView = root.findViewById(R.id.post_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        postRecyclerView.setLayoutManager(layoutManager);

        //ViewModel里用于刷新UI的观察者，getList()获取到数据后则在这个方法中显示在UI上
        postViewModel.getList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Post> s) {
                //将数据传入适配器，将适配器绑定到RecyclerView
                PostAdapter adapter = new PostAdapter(s);
                postRecyclerView.setAdapter(adapter);
                //负责页面刷新效果的代码
                postRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }



}
