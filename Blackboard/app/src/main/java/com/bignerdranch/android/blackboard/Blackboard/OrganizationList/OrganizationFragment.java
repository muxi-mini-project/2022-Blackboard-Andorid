package com.bignerdranch.android.blackboard.Blackboard.OrganizationList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationActivity;
import com.bignerdranch.android.blackboard.R;

public class OrganizationFragment extends Fragment {

    //    Context context;
    ExpandableListView ELV;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //onCreateView需要返回View  调用inflate
        View view = inflater.inflate(R.layout.fragment_organization, container, false);

        //绑定ELV
        ELV = view.findViewById(R.id.ELV);

        //初始化数据
        final String[] parent = new String[]{"我加入的", "我创建的"};
        final String[][] child = new String[][]{{"学校组织1", "学校组织2", "学校组织3"}, {"学校组织1", "学校组织2", "学校组织3","学校组织4"}};


        //使用Adapter
        OrganizationAdapter organizationAdapter = new OrganizationAdapter(parent,child,getActivity());
        ELV.setAdapter(organizationAdapter);

        organizationAdapter.setOnChildClickListener(new OrganizationAdapter.OnChildClickListener() {
            @Override
            public void OnChildClick(View view) {
                Intent intent = OrganizationActivity.newIntent(getActivity(),"MUXI1",1);
                Toast.makeText(getActivity(), "还没写好呢  要不\n先暂时来木犀看看叭~", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        return view;
    }


}
