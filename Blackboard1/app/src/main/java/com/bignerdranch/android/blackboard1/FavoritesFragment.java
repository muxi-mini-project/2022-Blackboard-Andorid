package com.bignerdranch.android.blackboard1;

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

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private View view; //定义view用来设置fragment的layout
    public RecyclerView mFavoritesRecyclerView; //定义RecyclerView
    //定义以post实体类为对象的数据集合
    private ArrayList<Favorites> favoritesList = new ArrayList<Favorites>();
    //自定义recyclerview的适配器
    private FavoritesAdapter mFavoritesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        //初始化recyclerview
        initRecyclerView();

        //填入数据
        initData();

        return view;
    }

    private void initData() {
        for (int i=0;i<10;i++){
            Favorites favorites = new Favorites();
            favorites.setUpdatedAt("发布时间"+i);
            favorites.setContents("发布内容"+i);
            favorites.setOrganization_name("发布团队"+i);
            favoritesList.add(favorites);
        }
    }

    private void initRecyclerView() {
        //获取RecyclerView
        mFavoritesRecyclerView = (RecyclerView) view.findViewById(R.id.favorites_recyclerview);

        //创建adapter
        mFavoritesAdapter = new FavoritesAdapter(getActivity(),favoritesList);
        //给RecyclerView设置adapter
        mFavoritesRecyclerView.setAdapter(mFavoritesAdapter);

        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mFavoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mFavoritesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

    }

}
