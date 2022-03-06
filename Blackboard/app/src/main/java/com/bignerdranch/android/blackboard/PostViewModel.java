package com.bignerdranch.android.blackboard;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Post>> ListData;
    List<Post> postList = new ArrayList<>();
    Context postContext;

    public PostViewModel() {
        ListData = new MutableLiveData<>();
        initNewList();
    }

    public LiveData<ArrayList<Post>> getList() {
        return ListData;
    }
    private void initNewList(){//上文通过API获取数据源，在doSuccess中完成了数据解析，并调用了setListValue()将数据装入ViewModel的ListData中
    }
    public void setContext(Context context) {
        postContext = context;
    }
    public void setListValue() {
        ((PageActivity) postContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListData.setValue((ArrayList<Post>) postList);
            }
        });
    }


}
