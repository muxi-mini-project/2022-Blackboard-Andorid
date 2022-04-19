package com.bignerdranch.android.blackboard.Blackboard.LatestMessage;

import static com.bignerdranch.android.blackboard.R.drawable.star_hollow;
import static com.bignerdranch.android.blackboard.R.drawable.star_solid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        UpDate upDate = new UpDate();
        ArrayList<MessageItem> data = upDate.UpDate();

        //RecyclerView需要adapter来处理数据     新建一个能将List传进去的Adapter
        boardAdapter = new BoardAdapter(this, data);
        mRecyclerView.setAdapter(boardAdapter);

        boardAdapter.setOnStarClickListener(new BoardAdapter.OnButtonClickListener() {
            @Override
            public void OnButtonClick(View view,int position) {
//                Toast.makeText(getActivity(), "Star", Toast.LENGTH_SHORT).show();

//                view.setBackground(getResources().getDrawable(star_solid));
                if (data.get(position).ismStar() == 0){
                    data.get(position).setmStar(1);
                } else {
                    data.get(position).setmStar(0);
                }

                if (data.get(position).ismStar() == 0){
                    view.setBackground(getResources().getDrawable(star_hollow));
                }else{
                    view.setBackground(getResources().getDrawable(star_solid));
                }

            }
        });

        //返回View
        return view;
    }

    //创建数据
    private class UpDate
    {
        private ArrayList<MessageItem> items;

        //构造时候为items加入100个数据 并返回一个ArrayLIst
        public ArrayList UpDate()
        {
            items = new ArrayList<>();
            for (int i=0 ; i<100 ; i++)
            {
                MessageItem messageItem = new MessageItem();
                messageItem.setmName("name" + i);
                messageItem.setmPhoto(R.drawable.qq);
                messageItem.setmText(R.string.CCNU);
                messageItem.setmStar(i%2);
                items.add(messageItem);
            }

            return items;
        }

    }


}
