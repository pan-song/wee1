package com.bawei.util_dierzhou_lianxi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.util_dierzhou_lianxi.adapter.MyBigAdapter;
import com.bawei.util_dierzhou_lianxi.base.BaseActivity;
import com.bawei.util_dierzhou_lianxi.base.BasePresenter;
import com.bawei.util_dierzhou_lianxi.bean.BeanData;
import com.bawei.util_dierzhou_lianxi.presenter.PresenterImpl;
import com.bawei.util_dierzhou_lianxi.url.MyUrls;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private CheckBox All_CheckBox;
    private TextView All_Price;
    private TextView All_Count;
    private MyBigAdapter myBigAdapter;
    private List<BeanData.OrderDataBean> mList = new ArrayList<>();
    private RecyclerView recy_view;

    @Override
    protected void startCoding() {

        myBigAdapter = new MyBigAdapter(this, mList);
        recy_view.setAdapter(myBigAdapter);
        mPresenter.startRequest(MyUrls.SHOPURL, BeanData.class);

        myBigAdapter.setShoppingBacllBack(new MyBigAdapter.ShoppingBacllBack() {
            @Override
            public void bigCheckClick(int bigIndex) {
                //获取选中状态
                boolean b = myBigAdapter.setBigCheck(bigIndex);
                //设置选中
                myBigAdapter.setBigCheckStatus(bigIndex, !b);
                //刷新
                myBigAdapter.notifyDataSetChanged();
                allCalculation();
            }

            @Override
            public void smallCheckClick(int bigIndex, int smallIndex) {
                //获取选中状态
                boolean status = mList.get(bigIndex).getCartlist().get(smallIndex).isStatus();
                myBigAdapter.setSamllCheck(bigIndex, smallIndex, !status);
                myBigAdapter.notifyDataSetChanged();
                allCalculation();
            }

            @Override
            public void smallCheckClickCount(int bigIndex, int smallIndex, int number) {
                mList.get(bigIndex).getCartlist().get(smallIndex).setCount(number);
                myBigAdapter.notifyDataSetChanged();
                allCalculation();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.All_CheckBox:
                //获取全选的状态
                boolean allCheck = myBigAdapter.isAllCheck();
                //调用全选的方法
                myBigAdapter.setAllCheck(!allCheck);
                //给全选按钮设置全选
                All_CheckBox.setChecked(!allCheck);
                //设置总价格
                All_Price.setText("￥："+myBigAdapter.allPrice());
                //设置总数量
                All_Count.setText("总数量："+myBigAdapter.allCount());
                //刷新适配器
                myBigAdapter.notifyDataSetChanged();
                break;
        }
    }
    public void allCalculation(){
        //获取全选的状态
        boolean allCheck = myBigAdapter.isAllCheck();
        //给全选按钮设置全选
        All_CheckBox.setChecked(allCheck);
        //设置总价格
        All_Price.setText("￥："+myBigAdapter.allPrice());
        //设置总数量
        All_Count.setText("总数量："+myBigAdapter.allCount());
    }

    @Override
    protected BasePresenter initPresenter() {
        return new PresenterImpl();
    }

    @Override
    protected void initViews() {
        recy_view = findViewById(R.id.recy_View);
        All_CheckBox = (CheckBox) findViewById(R.id.All_CheckBox);
        All_Price = (TextView) findViewById(R.id.All_Price);
        All_Count = (TextView) findViewById(R.id.All_Count);

        All_CheckBox.setOnClickListener(this);

        recy_view.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    public void onSuccess(Object o) {
        if (o instanceof BeanData) {

            Log.e("TAG", o.toString());
            mList.addAll(((BeanData) o).getOrderData());
            myBigAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String error) {

    }


}
