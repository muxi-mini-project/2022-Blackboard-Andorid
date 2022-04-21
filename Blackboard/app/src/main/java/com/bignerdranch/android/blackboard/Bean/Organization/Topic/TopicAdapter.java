package com.bignerdranch.android.blackboard.Bean.Organization.Topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.R;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder>
{
    private Context context;
    private List<Topics> topicsList;

    //构造器
    public TopicAdapter(Context context,List<Topics> topicsList)
    {
        this.context = context;
        this.topicsList = topicsList;
    }

    //viewHolder
    public class TopicHolder extends RecyclerView.ViewHolder
    {
        private TextView TopicName;
        private Button TopicAdd;

        public TopicHolder(@NonNull View itemView) {
            super(itemView);
            TopicName = itemView.findViewById(R.id.topicName);
            TopicAdd = itemView.findViewById(R.id.TopicAdd);

            TopicAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addClick.addClick();
                }
            });
        }
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic,parent,false);
        return new TopicHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position)
    {
        holder.TopicName.setText("# "+topicsList.get(position).getGroupName());
    }
    @Override
    public int getItemCount() {
        return topicsList.size();
    }

    public interface AddClick
    {
        public void addClick();
    }
    private AddClick addClick;
    public void SetAddClick(AddClick addClick)
    {
        this.addClick = addClick;
    }
}