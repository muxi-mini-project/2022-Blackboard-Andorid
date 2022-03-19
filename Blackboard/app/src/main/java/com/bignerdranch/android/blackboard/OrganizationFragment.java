package com.bignerdranch.android.blackboard;

<<<<<<< HEAD
<<<<<<< HEAD
import android.content.Context;
=======
>>>>>>> flsdqm
=======
import android.content.Context;
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
<<<<<<< HEAD
import android.widget.ExpandableListView;
=======
>>>>>>> flsdqm
=======
import android.widget.ExpandableListView;
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83

import androidx.fragment.app.Fragment;

public class OrganizationFragment extends Fragment {

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
    Context context;
    ExpandableListView ELV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //onCreateView需要返回View  调用inflate
        View view = inflater.inflate(R.layout.fragment_organization, container, false);

        //绑定ELV
        ELV = view.findViewById(R.id.ELV);
        //初始化数据
        Data();
        //使用Adapter

        return view;
    }

    private void Data() {
        String[] parent = new String[]{"我加入的", "我创建的"};
        String[][] child = new String[][]{{"学校组织1", "学校组织2", "学校组织3"}, {"学校组织1", "学校组织2", "学校组织3"}};
    }
<<<<<<< HEAD
=======
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organization, container, false);
        return view;
    }
>>>>>>> flsdqm
=======
>>>>>>> 11fe2cc0ed6d3ebc729ddc6fd645bc97dec89e83
}
