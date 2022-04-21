package com.bignerdranch.android.blackboard.Blackboard.LatestMessage;

import static com.bignerdranch.android.blackboard.R.drawable.star;
import static com.bignerdranch.android.blackboard.R.drawable.unstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationActivity;
import com.bignerdranch.android.blackboard.R;

import java.util.ArrayList;

public class BoardFragmentRLV extends Fragment {

    private RecyclerView mRecyclerView;
    private BoardAdapter boardAdapter;

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
        Data upDate = new Data();
        ArrayList<MessageItem> data = upDate.DataList();

        //RecyclerView需要adapter来处理数据     新建一个能将List传进去的Adapter
        boardAdapter = new BoardAdapter(this, data);
        mRecyclerView.setAdapter(boardAdapter);

        boardAdapter.setItemOnClickListener(new BoardAdapter.ItemOnClickListener() {
            @Override
            public void OnStarClick(View view, int position)
            {
//                Toast.makeText(getActivity(), "Star", Toast.LENGTH_SHORT).show();

//                view.setBackground(getResources().getDrawable(star_solid));
                if (data.get(position).ismStar() == 0){
                    data.get(position).setmStar(1);
                } else {
                    data.get(position).setmStar(0);
                }

                if (data.get(position).ismStar() == 0){
                    view.setBackground(getResources().getDrawable(unstar));
                }else{
                    view.setBackground(getResources().getDrawable(star));
                }

            }
            @Override
            public void OnAvatarClick(String name, int id)
            {
//                Intent intent = OrganizationActivity.newIntent(getActivity(),name,id);
                Intent intent = OrganizationActivity.newIntent(getActivity(),"MUXI",106);
                startActivity(intent);
                Toast.makeText(getActivity(), "还在加工 先来MUXI休息会吧", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void OnItemClick()
            {

            }
        });

        //返回View
        return view;
    }

    //创建数据
    private class Data
    {
        private ArrayList<MessageItem> items;

        //构造时候为items加入100个数据 并返回一个ArrayLIst
        public ArrayList DataList()
        {
            items = new ArrayList<>();
            for (int i=0 ; i<100 ; i++)
            {
                MessageItem messageItem = new MessageItem();
                messageItem.setmName("name" + i);
                messageItem.setmPhoto(R.drawable.qq);
                messageItem.setmText(R.string.CCNU);
                messageItem.setmStar(0);
                items.add(messageItem);
            }

            return items;
        }



    }


}
