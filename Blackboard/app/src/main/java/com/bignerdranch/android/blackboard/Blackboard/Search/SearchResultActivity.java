package com.bignerdranch.android.blackboard.Blackboard.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bignerdranch.android.blackboard.Blackboard.BoardActivity;
import com.bignerdranch.android.blackboard.Mine.Post.PostFragment;
import com.bignerdranch.android.blackboard.R;

public class SearchResultActivity extends AppCompatActivity {

    private Button searchResultButton;
    private Button searchResultBackButton;
    private EditText searchResultEdittext;
    private SearchResultFragment fg;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        searchResultButton = findViewById(R.id.search_result);
        searchResultBackButton = findViewById(R.id.search_result_back);
        searchResultEdittext = findViewById(R.id.search_result_edittext);

        Intent intent = getIntent();
        String searchResult = intent.getStringExtra("search");
        searchResultEdittext.setText(searchResult);

        searchResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = searchResultEdittext.toString();

                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                if (fg == null) {
                    fg = new SearchResultFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("content", content);
                    //fragment保存参数，传入一个Bundle对象
                    fg.setArguments(bundle);
                    transaction.add(R.id.fragment_search, fg);
                } else {
                    fg = new SearchResultFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("content", content);
                    //fragment保存参数，传入一个Bundle对象
                    fg.setArguments(bundle);
                    transaction.replace(R.id.fragment_search,fg);
                }
                transaction.commit();

            }
        });

        searchResultBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(SearchResultActivity.this, BoardActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }

    public void ClickBack(View view) {
        finish();
    }
}