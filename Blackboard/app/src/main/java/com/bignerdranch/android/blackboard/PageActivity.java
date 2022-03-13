package com.bignerdranch.android.blackboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        pagePostButton.performClick();
    }

    private void init() {
        pagePostButton = findViewById(R.id.page_post);
        pageFavoritesButton = findViewById(R.id.page_favorites);
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
            case R.id.board_message:
                if (f3 == null) {
                    f3 = new PostFragment();
                    transaction.add(R.id.fragment_container, f3);
                } else {
                    transaction.show(f3);
                }
                break;
            case R.id.board_organization:
                if (f4 == null) {
                    f4 = new FavoritesFragment();
                    transaction.add(R.id.fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                break;
        }
        transaction.commit();
    }
}