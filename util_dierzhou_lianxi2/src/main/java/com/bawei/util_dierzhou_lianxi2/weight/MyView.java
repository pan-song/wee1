package com.bawei.util_dierzhou_lianxi2.weight;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.util_dierzhou_lianxi2.R;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 23:29
 */
public class MyView extends LinearLayout implements View.OnClickListener {

    private TextView view_add;
    private TextView view_number;
    private TextView view_remove;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.add_remove, this);
        initView();
    }

    public void setView_number(int number) {
        view_number.setText(number + "");
    }

    private void initView() {
        view_add = findViewById(R.id.view_Add);
        view_number = findViewById(R.id.view_Number);
        view_remove = findViewById(R.id.view_Remove);

        view_add.setOnClickListener(this);
        view_remove.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int count = Integer.parseInt(view_number.getText().toString());
        switch (view.getId()) {
            case R.id.view_Add:
                count++;
                view_number.setText(count+"");
                if (itemCount != null) {
                    itemCount.setItemCount(count);
                }
                break;
            case R.id.view_Remove:
                if(count>1){
                    count--;
                    view_number.setText(count+"");
                    if (itemCount != null) {
                        itemCount.setItemCount(count);
                    }
                }else{
                    Toast.makeText(getContext(), "数量不能小于1", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public interface ItemCount{
        void setItemCount(int number);
    }
    private ItemCount itemCount;

    public void setItemCount(ItemCount itemCount) {
        this.itemCount = itemCount;
    }
}
