package com.bignerdranch.android.blackboard.Mine.Post;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.postViewHolder> {
    private Context context;
    private ArrayList<Post> postList;

    //创建构造函数
    public PostAdapter(Context context, ArrayList<Post> postList) {

        //将传递过来的数据，赋值给本地变量
        this.context = context; //上下文
        this.postList = postList; //实体类数据ArrayList
    }

    //创建viewHolder，相当于listview中getView中的创建view和viewHolder

    @Override
    public postViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局,用之前的每个item的布局
        View itemView = View.inflate(context, R.layout.item_post, null);
        return new postViewHolder(itemView);
    }

    //绑定数据，数据与view绑定
    @Override
    public void onBindViewHolder(postViewHolder holder, int position) {
        //根据点击位置绑定数据
        Post post = postList.get(position);

        holder.mItemPostUpdatedAt.setText(post.getUpdatedAt());//获取实体类中的updatedAt字段并设置
        holder.mItemPostContents.setText(post.getContents());//获取实体类中的contents字段并设置
        holder.mItemPostOrganizationName.setText(post.getOrganization_name());
    }

    //得到总条数
    @Override
    public int getItemCount() {
        return postList.size();
    }

    //自定义viewHolder
    class postViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemPostUpdatedAt;
        private TextView mItemPostContents;
        private TextView mItemPostOrganizationName;

        public postViewHolder(final View itemView) {
            super(itemView);
            mItemPostUpdatedAt = (TextView) itemView.findViewById(R.id.post_time);
            mItemPostContents = (TextView) itemView.findViewById(R.id.post_contents);
            mItemPostOrganizationName = (TextView) itemView.findViewById(R.id.post_organization_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, postList.get(getLayoutPosition()));

                    }
                }
            });
        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param post 点击的item的数据
         */
        public void OnItemClick(View view, Post post);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
