package com.bignerdranch.android.blackboard.Blackboard.LatestMessage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.Bean.Message.MessageItem;
import com.bignerdranch.android.blackboard.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    private Context context;
    //adapter是RV的适配器 用于连数据与UI 所以需要一个数据列表List
    List<MessageItem> data;
    List<MessageItem> like;

    //新建adapter需要构造器能将数据传入
    public BoardAdapter(Context context, List<MessageItem> InData, List<MessageItem> InLike) {
        this.context = context;
        this.data = InData;
        this.like = InLike;
    }

    //绑定布局
    public class BoardViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView_name;
        private CircleImageView imageView_photo;
        private TextView textView_text;
        private Button Star;

        public BoardViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView_name = itemView.findViewById(R.id.TV_name);
            imageView_photo = itemView.findViewById(R.id.IV_photo);
            textView_text = itemView.findViewById(R.id.TV_text);
            Star = itemView.findViewById(R.id.star);

        }
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //需要返回一个 myViewHolder       所以要new一个myViewHolder
        //myViewHolder函数 需要 View        所以要new一个View
        //inflate函数能将xml文件加载成View   所以要调用一个inflate函数(View子类)
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new BoardViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        MessageItem messageItem = data.get(position);

        holder.textView_name.setText(messageItem.getOrganization_name());
        holder.textView_text.setText(messageItem.getContents());

        if (data.get(position).isStar()) holder.Star.setBackground(context.getDrawable(R.drawable.star));
        else holder.Star.setBackground(context.getDrawable(R.drawable.unstar));

        holder.Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemOnClickListener.OnStarClick(view,position);
                if (data.get(position).isStar()) holder.Star.setBackground(context.getDrawable(R.drawable.star));
                else holder.Star.setBackground(context.getDrawable(R.drawable.unstar));
            }
        });
        holder.imageView_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            { itemOnClickListener.OnAvatarClick(messageItem.getOrganization_name(),messageItem.getID()); }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            { itemOnClickListener.OnItemClick();}
        });

    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    //接口
    public interface ItemOnClickListener {
        public void OnStarClick(View view, int position);
        public void OnAvatarClick(String name, int OgnId);
        public void OnItemClick();
    }
    private ItemOnClickListener itemOnClickListener;
    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }
}
