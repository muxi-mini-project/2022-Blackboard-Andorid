package com.bignerdranch.android.blackboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.favoritesViewHolder>{
    private Context context;
    private ArrayList<Favorites> favoritesList;

    //创建构造函数
    public FavoritesAdapter(Context context, ArrayList<Favorites> favoritesList) {

        //将传递过来的数据，赋值给本地变量
        this.context = context; //上下文
        this.favoritesList = favoritesList; //实体类数据ArrayList
    }

    //创建viewHolder，相当于listview中getView中的创建view和viewHolder

    @Override
    public FavoritesAdapter.favoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局,用之前的每个item的布局
        View itemView = View.inflate(context, R.layout.item_post, null);
        return new FavoritesAdapter.favoritesViewHolder(itemView);
    }

    //绑定数据，数据与view绑定
    @Override
    public void onBindViewHolder(FavoritesAdapter.favoritesViewHolder holder, int position) {
        //根据点击位置绑定数据
        Favorites favorites = favoritesList.get(position);

        holder.mItemFavoritesUpdatedAt.setText(favorites.getUpdatedAt());//获取实体类中的updatedAt字段并设置
        holder.mItemFavoritesContents.setText(favorites.getContents());//获取实体类中的contents字段并设置
        holder.mItemFavoritesOrganizationName.setText(favorites.getOrganization_name());
    }

    //得到总条数
    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    //自定义viewHolder
    class favoritesViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemFavoritesUpdatedAt;
        private TextView mItemFavoritesContents;
        private TextView mItemFavoritesOrganizationName;

        public favoritesViewHolder(final View itemView) {
            super(itemView);
            mItemFavoritesUpdatedAt = (TextView) itemView.findViewById(R.id.post_time);
            mItemFavoritesContents = (TextView) itemView.findViewById(R.id.post_contents);
            mItemFavoritesOrganizationName = (TextView) itemView.findViewById(R.id.post_organization_name);
        }
    }


}
