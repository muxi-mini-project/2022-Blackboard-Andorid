package com.bignerdranch.android.blackboard1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button pagePostButton;
    private Button pageFavoritesButton;
    private Button pageInformationButton;
    private FragmentManager manager2;
    private PostFragment f3;
    private FavoritesFragment f4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);

        manager2 = getSupportFragmentManager();
        init();

        pageInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageActivity.this,InformationActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        boolean selector = intent.getBooleanExtra("selector",true);
        if(selector == true){
            pagePostButton.performClick();
        } else{
            pageFavoritesButton.performClick();
        }
    }

    private void init() {
        pagePostButton = findViewById(R.id.page_post);
        pageFavoritesButton = findViewById(R.id.page_favorites);
        pageInformationButton = findViewById(R.id.page_information);
        pagePostButton.setOnClickListener(this);
        pageFavoritesButton.setOnClickListener(this);
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (f3 != null) {
            transaction.hide(f3);
        }
        if (f4 != null) {
            transaction.hide(f4);
        }
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = manager2.beginTransaction();
        hideAllFragment(transaction);

        switch (view.getId()) {
            case R.id.page_post:
                if (f3 == null) {
                    f3 = new PostFragment();
                    transaction.add(R.id.page_fragment_container, f3);
                } else {
                    transaction.show(f3);
                }
                break;
            case R.id.page_favorites:
                if (f4 == null) {
                    f4 = new FavoritesFragment();
                    transaction.add(R.id.page_fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                break;
        }
        transaction.commit();
    }
}