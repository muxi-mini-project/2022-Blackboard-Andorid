package com.bignerdranch.android.blackboard.Blackboard.OrganizationList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bignerdranch.android.blackboard.Bean.Organization.Organization;
import com.bignerdranch.android.blackboard.R;

import java.util.List;


public class OrganizationAdapter extends BaseExpandableListAdapter {
    private String[] parent = new String[] {"我创建的","我关注的"};
    private List<List<Organization>> myList;
    private Context context;

    public OrganizationAdapter( List<List<Organization>> myList, Context context)
    {
        this.myList = myList;
        this.context = context;
    }

    //创建布局
    @Override
    public View getGroupView(int ParentPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.ogn_group_item,null);
        }
        TextView textView = view.findViewById(R.id.group_TV);
        textView.setText(parent[ParentPosition]);
        return view;
    }
    @Override
    public View getChildView(int ParentPosition, int ChildPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.ogn_list_item,null);
        TextView textView = view.findViewById(R.id.Child_TV);

        String name = myList.get(ParentPosition).get(ChildPosition).getOrganization_name();
        textView.setText(name);
        int id = myList.get(ParentPosition).get(ChildPosition).getID();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onChildClickListener != null)
                {
                    onChildClickListener.OnChildClick(name,id);
                }
            }
        });

        return view;
    }

    //获得父项的长度
    @Override
    public int getGroupCount() {
        return parent.length;
    }
    //获得子项的长度
    @Override
    public int getChildrenCount(int ChildPosition)
    {
        return myList.get(ChildPosition).size();
    }

    //获得父项
    @Override
    public Object getGroup(int ParentPosition) {
        return parent[ParentPosition];
    }
    //获得子项
    @Override
    public Object getChild(int ParentPosition, int ChildPosition) {
        return myList.get(ParentPosition).get(ChildPosition);
    }

    //获得父项的ID
    @Override
    public long getGroupId(int ParentID) {
        return ParentID;
    }
    //获得子项的ID
    @Override
    public long getChildId(int ParentID, int ChildID) {
        return ChildID;
    }

    //是否有确定的ID
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //子项可否被选中
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


    public interface OnChildClickListener{
        public void OnChildClick(String name,int id);
    }
    private OnChildClickListener onChildClickListener;
    public void setOnChildClickListener(OnChildClickListener onChildClickListener)
    {
        this.onChildClickListener = onChildClickListener;
    }
}
