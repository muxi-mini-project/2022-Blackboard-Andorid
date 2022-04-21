package com.bignerdranch.android.blackboard.Utils;

import android.content.Context;
import android.widget.LinearLayout;

public class collapsibleTextView extends LinearLayout {

    private static boolean fold;

    public collapsibleTextView(Context context)
    {
        super(context);
        String fold = "显示更多";
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }
}
