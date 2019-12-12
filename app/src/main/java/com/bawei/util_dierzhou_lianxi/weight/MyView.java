package com.bawei.util_dierzhou_lianxi.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.util_dierzhou_lianxi.R;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 18:09
 */
public class MyView extends LinearLayout implements View.OnClickListener {

    private TextView view_add, view_Number, view_Remove;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载自定义的布局
        LayoutInflater.from(context).inflate(R.layout.add_remove, this);
        initView();
    }

    public void setView_Number(int number) {
        view_Number.setText(number + "");
    }

    private void initView() {

        view_add = findViewById(R.id.view_Add);
        view_Number = findViewById(R.id.view_Number);
        view_Remove = findViewById(R.id.view_Remove);

        view_add.setOnClickListener(this);
        view_Remove.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        int count = Integer.parseInt(view_Number.getText().toString());

        switch (view.getId()) {
            case R.id.view_Add:

                //当我点击加时
                count++;
                view_Number.setText(count + "");
                if (itemCount != null) {
                    itemCount.setItemCount(count);
                }
                break;
            case R.id.view_Remove:
                //当我点击－时
                if (count > 1) {
                    count--;
                    view_Number.setText(count + "");
                    if (itemCount != null) {
                        itemCount.setItemCount(count);
                    }
                } else {
                    Toast.makeText(getContext(), "数量不能小于1", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public interface ItemCount {
        void setItemCount(int number);
    }

    private ItemCount itemCount;

    public void setItemCount(ItemCount itemCount) {
        this.itemCount = itemCount;
    }
}
