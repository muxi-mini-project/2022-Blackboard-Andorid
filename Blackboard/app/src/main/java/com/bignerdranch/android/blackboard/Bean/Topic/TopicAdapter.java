package com.bignerdranch.android.blackboard.Bean.Topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
=======
import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationActivity;
>>>>>>> e956993242be224503b811aa3c432db77c4dd248
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
<<<<<<< HEAD
            TopicAdd = itemView.findViewById(R.id.TopicAdd);

            TopicAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addClick.addClick();
                }
            });
=======
//            TopicAdd = itemView.findViewById(R.id.TopicAdd);
>>>>>>> e956993242be224503b811aa3c432db77c4dd248
        }
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
<<<<<<< HEAD
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic,parent,false);
=======
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic,null,false);
>>>>>>> e956993242be224503b811aa3c432db77c4dd248
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

<<<<<<< HEAD
    public interface AddClick
    {
        public void addClick();
    }
    private AddClick addClick;
    public void SetAddClick(AddClick addClick)
    {
        this.addClick = addClick;
    }
=======

>>>>>>> e956993242be224503b811aa3c432db77c4dd248
}