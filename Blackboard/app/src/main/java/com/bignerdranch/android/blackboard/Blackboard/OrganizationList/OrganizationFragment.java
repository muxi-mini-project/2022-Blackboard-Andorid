package com.bignerdranch.android.blackboard.Blackboard.OrganizationList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.blackboard.Utils.API;
import com.bignerdranch.android.blackboard.Bean.Organization.Organization;
import com.bignerdranch.android.blackboard.Bean.Organization.OrganizationActivity;
import com.bignerdranch.android.blackboard.Utils.MyResponse;
import com.bignerdranch.android.blackboard.R;
import com.bignerdranch.android.blackboard.Utils.Utils;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrganizationFragment extends Fragment {

    private ExpandableListView ELV;
    private List<List<Organization>> myList = new LinkedList<>();
    private List<Organization> myCreateList = new LinkedList<>();
    private List<Organization> myFollowList = new LinkedList<>();
    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //onCreateView需要返回View  调用inflate
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_organization, container, false);


        //获取数据
        NetGetOrgans();
        myList.add(myCreateList);
        myList.add(myFollowList);

        //绑定ELV
        ELV = rootView.findViewById(R.id.ELV);

        //使用Adapter
        OrganizationAdapter organizationAdapter = new OrganizationAdapter(myList, getActivity());
        ELV.setAdapter(organizationAdapter);

        //跳转组织界面
        organizationAdapter.setOnChildClickListener(new OrganizationAdapter.OnChildClickListener()
        {
            @Override

            public void OnChildClick(String name,int id) {
                Intent intent = OrganizationActivity.newIntent(getActivity(), name, id);
//                Toast.makeText(getActivity(), "还没写好呢  要不\n先暂时来木犀看看叭~", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void NetGetOrgans()
    {
        SharedPreferences p = getActivity().getSharedPreferences(Utils.SP, Context.MODE_PRIVATE);
        String Authorization = p.getString(Utils.TOKEN,null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.3.2.168:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API get = retrofit.create(API.class);

        //创建
        Call<MyResponse<List<Organization>>> call1 = get.myCreate(1000,0,Authorization);
        call1.enqueue(new Callback<MyResponse<List<Organization>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<Organization>>> call, Response<MyResponse<List<Organization>>> response)
            {
                if (response.isSuccessful()) {
                    List<Organization> createList = new LinkedList<>();
                    createList = response.body().getData();
                    myCreateList.addAll(createList);
                }else
                {
                    Toast.makeText(getActivity(), "接收不到", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MyResponse<List<Organization>>> call, Throwable t)
            {
                Toast.makeText(getActivity(), "出错了呢", Toast.LENGTH_SHORT).show();
            }
        });
        //关注
        Call<MyResponse<List<Organization>>> call2 = get.myFollow(1000,0,Authorization);
        call2.enqueue(new Callback<MyResponse<List<Organization>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<Organization>>> call, Response<MyResponse<List<Organization>>> response)
            {
                List<Organization> followList =new LinkedList<>();
                followList =  response.body().getData();
                myFollowList.addAll(followList);
            }
            @Override
            public void onFailure(Call<MyResponse<List<Organization>>> call, Throwable t) {}
        });
    }
}
