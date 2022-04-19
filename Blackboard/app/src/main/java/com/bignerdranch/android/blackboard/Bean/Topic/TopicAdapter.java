package com.bignerdranch.android.blackboard.Bean.Topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationActivity;
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
//            TopicAdd = itemView.findViewById(R.id.TopicAdd);
        }
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic,null,false);
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


}