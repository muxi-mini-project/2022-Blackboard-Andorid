package com.bignerdranch.android.blackboard1;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class ChangeHeadImagePop extends PopupWindow implements View.OnClickListener {

    private Activity activity;
    private View popView;

    private View v_item1;
    private View v_item2;
    private View v_item3;


    private OnItemClickListener onItemClickListener;

    /**
     *
     * @author moonfolower枚举，用于区分选择了哪个选项
     */
    public enum MENUITEM {
        ITEM1, ITEM2, ITEM3
    }

    public ChangeHeadImagePop(final Activity activity) {
        super(activity);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.pop_change_image, null);// 加载菜单布局文件
        this.setContentView(popView);// 把布局文件添加到popupwindow中
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);// 设置菜单的宽度（需要和菜单于右边距的距离搭配，可以自己调到合适的位置）
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);// 获取焦点
        this.setTouchable(true); // 设置PopupWindow可触摸
        this.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                /*setBackgroundAlpha(activity,1f);*/
            }
        });

        /*setBackgroundAlpha(activity,0.7f);*/

        // 获取选项卡
        v_item1 = popView.findViewById(R.id.camera);
        v_item2 = popView.findViewById(R.id.photoAlbum);
        v_item3 = popView.findViewById(R.id.cancel);

        // 添加监听
        v_item1.setOnClickListener(this);
        v_item2.setOnClickListener(this);
        v_item3.setOnClickListener(this);
    }

    /**
     * 设置显示的位置
     */
    public void showLocation(View anchorView) {
        /*int windowPos[] = PopupWindowUtil.calculatePopWindowPos(anchorView, popView);
        int xOff = 20; // 可以自己调整偏移
        windowPos[0] -= xOff;*/
        this.showAtLocation(anchorView, Gravity.BOTTOM , 0, 0);
    }

    /**
     * 设置背景色
     */
    private void setBackgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    // 点击监听接口
    public interface OnItemClickListener {
        void onClick(MENUITEM item, String str);
    }

    // 设置监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        MENUITEM menuitem = null;
        String str = "";
        if (v == v_item1) {
            menuitem = MENUITEM.ITEM1;
            str = "选项卡一";
        } else if (v == v_item2) {
            menuitem = MENUITEM.ITEM2;
            str = "选项卡二";
        } else if (v == v_item3) {
            menuitem = MENUITEM.ITEM3;
            str = "选项卡三";
        }
        if (onItemClickListener != null) {
            onItemClickListener.onClick(menuitem, str);
        }
        dismiss();
    }
}



