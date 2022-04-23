package com.bignerdranch.android.blackboard.Mine.Favourite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.Mine.Post.Posts;
import com.bignerdranch.android.blackboard.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.favoritesViewHolder>{
    private Context context;
    private List<Favorites.DataDTO> data = new ArrayList<>();

    //创建构造函数
    public FavoritesAdapter(Context context, List<Favorites.DataDTO> data) {

        //将传递过来的数据，赋值给本地变量
        this.context = context; //上下文
        this.data = data; //实体类数据ArrayList
    }

    //创建viewHolder，相当于listview中getView中的创建view和viewHolder
    @NonNull
    @Override
    public favoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建自定义布局,用之前的每个item的布局
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_favorites,parent,false);
        return new favoritesViewHolder(itemView);
    }

    //绑定数据，数据与view绑定
    @Override
    public void onBindViewHolder(FavoritesAdapter.favoritesViewHolder holder, int position) {
        //根据点击位置绑定数据
        Favorites.DataDTO favorites = data.get(position);

//        holder.mName.setText(favorites.get);
        holder.mItemFavoritesContents.setText(favorites.getAnnouncement());//获取实体类中的contents字段并设置
    }

    //得到总条数
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refresh(List<Favorites.DataDTO> list) {
//        this.data.addAll(list);
        notifyDataSetChanged();
    }

    //自定义viewHolder
    class favoritesViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemFavoritesContents;
        private TextView mName;

        public favoritesViewHolder(final View itemView) {
            super(itemView);
            mItemFavoritesContents = (TextView) itemView.findViewById(R.id.favorites_contents);
            mName = itemView.findViewById(R.id.favourite_name);
        }
    }


}
