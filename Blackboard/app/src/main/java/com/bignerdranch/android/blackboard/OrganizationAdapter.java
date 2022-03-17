package com.bignerdranch.android.blackboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;


public class OrganizationAdapter extends BaseExpandableListAdapter {
    private String[] parent;
    private String[][] child;
    private Context context;

    public OrganizationAdapter(String[] parent, String[][] child, Context context)
    {
        this.parent = parent;
        this.child = child;
        this.context = context;
    }

    @Override
    public View getGroupView(int ParentPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        if(view == null)
        {
//            view = LayoutInflater.from(context).inflate()
        }
        return view;
    }

    @Override
    public View getChildView(int ParentPosition, int ChildPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        return null;
    }


    //获得父项的长度
    @Override
    public int getGroupCount() {
        return parent.length;
    }
    //获得子项的长度
    @Override
    public int getChildrenCount(int ChildPosition) {
        return child[ChildPosition].length;
    }

    //获得父项
    @Override
    public Object getGroup(int ParentPosition) {
        return parent[ParentPosition];
    }
    //获得子项
    @Override
    public Object getChild(int ParentPosition, int ChildPosition) {
        return child[ParentPosition][ChildPosition];
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
}
