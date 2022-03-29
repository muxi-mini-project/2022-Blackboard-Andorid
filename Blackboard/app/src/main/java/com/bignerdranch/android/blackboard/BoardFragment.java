package com.bignerdranch.android.blackboard;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BoardFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //需要返回一个View        所以调用inflate
        View view = inflater.inflate(R.layout.fragment_board,container,false);

        //构造一个RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rlv_board);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //建立MessageItem的数据          所以调用UpDate并返回数据
        UpDate upDate = new UpDate();
        ArrayList<MessageItem> data = upDate.UpDate();

        //RecyclerView需要adapter来处理数据     新建一个能将List传进去的Adapter
        myAdapter myAdapter = new myAdapter(data);
        mRecyclerView.setAdapter(myAdapter);

        //返回View
        return view;
    }

    private class UpDate
    {
        private ArrayList<MessageItem> items;

        //构造时候为items加入100个数据 并返回一个ArrayLIst
        public ArrayList UpDate()
        {
            items = new ArrayList<>();
            for (int i=0 ; i<100 ; i++)
            {
                MessageItem messageItem1 = new MessageItem();
                messageItem1.setmName("name "+ i);
                messageItem1.setmPhoto(R.drawable.ic_add);
                messageItem1.setmText("text " + i);
                items.add(messageItem1);
            }

            return items;
        }

    }

    private class myViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView_name;
        private ImageView imageView_photo;
        private TextView textView_text;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_name = itemView.findViewById(R.id.TV_name);
            imageView_photo = itemView.findViewById(R.id.IV_photo);
            textView_text = itemView.findViewById(R.id.TV_text);
        }
    }

    private class myAdapter extends RecyclerView.Adapter<myViewHolder>
    {
        //adapter是RV的适配器 用于连数据与UI 所以需要一个数据列表List
        ArrayList<MessageItem> data ;

        //新建adapter需要构造器能将数据传入
        public myAdapter(ArrayList<MessageItem> InData)
        {
            this.data = InData;
        }

        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //需要返回一个 myViewHolder       所以要new一个myViewHolder
            //myViewHolder函数 需要 View        所以要new一个View
            //inflate函数能将xml文件加载成View   所以要调用一个inflate函数(View子类)
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_message,null,false);
            return new myViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position)
        {
            MessageItem messageItem = data.get(position);

            holder.textView_name.setText(messageItem.getmName());
            Drawable photo = getResources().getDrawable(messageItem.getmPhoto());
            holder.imageView_photo.setImageDrawable(photo);
            holder.textView_text.setText(messageItem.getmText());
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }
    }
}
