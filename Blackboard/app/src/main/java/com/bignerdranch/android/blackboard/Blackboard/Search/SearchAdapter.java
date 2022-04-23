package com.bignerdranch.android.blackboard.Blackboard.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.blackboard.Mine.Post.Posts;
import com.bignerdranch.android.blackboard.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Search.DataDTO.AnnouncementsDTO> mSearchRelativeMessageList = new ArrayList<>();
    private List<Search.DataDTO.OrganizationsDTO> mSearchRelativeOrganizationList = new ArrayList<>();


    public static final int ITEM_TYPE_1 = 1;
    public static final int ITEM_TYPE_2 = 2;


    //创建构造函数
    public SearchAdapter(Context context, List<Search.DataDTO.AnnouncementsDTO> mSearchRelativeMessageList, List<Search.DataDTO.OrganizationsDTO> mSearchRelativeOrganizationList) {

        //将传递过来的数据，赋值给本地变量
        this.context = context; //上下文
        this.mSearchRelativeMessageList = mSearchRelativeMessageList;
        this.mSearchRelativeOrganizationList = mSearchRelativeOrganizationList;
    }


    @Override
    public int getItemViewType(int position) {
        if (position < mSearchRelativeMessageList.size()) {
            return ITEM_TYPE_1;

        } else {
            return ITEM_TYPE_2;
        }

    }


    //创建viewHolder，相当于listview中getView中的创建view和viewHolder

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_1) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_relative_message,parent,false);
            return new SearchRelativeMessageHolder(itemView);

        } else {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_relative_organization,parent,false);
            return new SearchRelativeOrganizationHolder(itemView);
        }
    }


    //绑定数据，数据与view绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchRelativeMessageHolder) {
            SearchRelativeMessageHolder searchRelativeMessageViewHolder = (SearchRelativeMessageHolder) holder;
            Search.DataDTO.AnnouncementsDTO message = mSearchRelativeMessageList.get(position);
            searchRelativeMessageViewHolder.mItemRelativeMessageOrganization.setText(message.getOrganization_name());
            searchRelativeMessageViewHolder.mItemRelativeMessageContents.setText(message.getContents());
            searchRelativeMessageViewHolder.mItemRelativeMessageTime.setText(message.getCreatedAt());

        }else {
            position = position-mSearchRelativeMessageList.size();
            SearchRelativeOrganizationHolder searchRelativeOrganizationHolder = (SearchRelativeOrganizationHolder) holder;
            Search.DataDTO.OrganizationsDTO organization = mSearchRelativeOrganizationList.get(position);
            searchRelativeOrganizationHolder.mItemRelativeOrganizationName.setText(organization.getOrganization_name());
            searchRelativeOrganizationHolder.mItemRelativeOrganizationIntroduction.setText(organization.getIntro());
            searchRelativeOrganizationHolder.mItemRelativeOrganizationSubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    if(onItemClickListener != null) {
                        onItemClickListener.OnItemClick2(view, position, searchRelativeOrganizationHolder.mItemRelativeOrganizationSubscribe);
                    }
                }
            });
        }
    }

    //得到总条数
    @Override
    public int getItemCount() {
        return mSearchRelativeMessageList.size()+mSearchRelativeOrganizationList.size();
    }


    public void refresh(List<Search.DataDTO.AnnouncementsDTO> list1,List<Search.DataDTO.OrganizationsDTO> list2) {
//        this.data.addAll(list);
        notifyDataSetChanged();
    }


    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {

        public void OnItemClick2(View view, int position, Button button);
        public void OnItemClick1(View view, int position);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }





    public class SearchRelativeMessageHolder extends RecyclerView.ViewHolder{
        //自定义viewHolder
        TextView mItemRelativeMessageOrganization;
        TextView mItemRelativeMessageContents;
        TextView mItemRelativeMessageTime;

        public SearchRelativeMessageHolder(View itemView) {
            super(itemView);
            mItemRelativeMessageOrganization = (TextView) itemView.findViewById(R.id.relative_message_organization_name);
            mItemRelativeMessageContents = (TextView) itemView.findViewById(R.id.relative_message_contents);
            mItemRelativeMessageTime = (TextView) itemView.findViewById(R.id.relative_message_time);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    //此处回传点击监听事件
//                    if (onItemClickListener != null) {
//                        onItemClickListener.OnItemClick(v, mRelativeMessageList.get(getLayoutPosition()));
//
//                    }
//                }
//            });
        }
    }

    public class SearchRelativeOrganizationHolder extends RecyclerView.ViewHolder {

        private TextView mItemRelativeOrganizationName;
        private TextView mItemRelativeOrganizationIntroduction;
        private Button mItemRelativeOrganizationSubscribe;
//        private CircleImageView mItemRelativeOrganizationAvatar;

        public SearchRelativeOrganizationHolder (View itemView){
            super(itemView);
            mItemRelativeOrganizationName = itemView.findViewById(R.id.relative_organization_name);
            mItemRelativeOrganizationIntroduction = itemView.findViewById(R.id.relative_organization_introduction);
            mItemRelativeOrganizationSubscribe = itemView.findViewById(R.id.subscribe);
        }
    }

}
