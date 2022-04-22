package com.bignerdranch.android.blackboard.Mine.Favourite;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.Mine.Favourite.Favorites;
import com.bignerdranch.android.blackboard.Mine.Favourite.FavoritesAdapter;
import com.bignerdranch.android.blackboard.Mine.Post.Posts;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesFragment extends Fragment {

    private View view; //定义view用来设置fragment的layout
    public RecyclerView mFavoritesRecyclerView; //定义RecyclerView
    //定义以post实体类为对象的数据集合
    private List<Favorites.DataDTO> favoritesList = new ArrayList<>();
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

        mFavoritesAdapter.refresh(favoritesList);

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
        Call<Favorites> call = get.myFavorites(100,0,Authorization);

        call.enqueue(new Callback<Favorites>() {
            @Override
            public void onResponse(Call<Favorites> call, Response<Favorites> response) {

                Favorites favorites = response.body();
                favoritesList.addAll(favorites.getData());
                mFavoritesAdapter.refresh(favoritesList);
            }

            @Override
            public void onFailure(Call <Favorites> call, Throwable t)
            {

            }
        });
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
