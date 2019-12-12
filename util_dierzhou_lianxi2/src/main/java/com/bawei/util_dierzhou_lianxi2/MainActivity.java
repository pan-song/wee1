package com.bawei.util_dierzhou_lianxi2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.util_dierzhou_lianxi2.adapter.MyBigAdapter;
import com.bawei.util_dierzhou_lianxi2.app.MyApplication;
import com.bawei.util_dierzhou_lianxi2.base.BaseActivity;
import com.bawei.util_dierzhou_lianxi2.base.BasePresenter;
import com.bawei.util_dierzhou_lianxi2.bean.BeanData;
import com.bawei.util_dierzhou_lianxi2.bean.MyBean;
import com.bawei.util_dierzhou_lianxi2.dao.DaoSession;
import com.bawei.util_dierzhou_lianxi2.dao.MyBeanDao;
import com.bawei.util_dierzhou_lianxi2.presenter.PresenterImpl;
import com.bawei.util_dierzhou_lianxi2.url.MyUrls;
import com.bawei.util_dierzhou_lianxi2.utils.NetUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recy_View;
    private CheckBox All_CheckBox;
    private TextView All_Price;
    private TextView All_Count;
    private List<BeanData.OrderDataBean> mList = new ArrayList<>();
    private MyBigAdapter myBigAdapter;
    private MyBeanDao myBeanDao;
    private Button but_commit;
    private boolean allCheck;

    @Override
    protected void startCoding() {

        if (NetUtil.getInstance().hasNet(this)) {
            mPresenter.startRequest(MyUrls.SHOPURL,BeanData.class);

            getData();
        }else{
            getDataBase();
        }


    }

    private void getDataBase() {

        List<MyBean> myBeans = myBeanDao.loadAll();
        MyBean myBean = myBeans.get(0);
        Gson gson = new Gson();

        BeanData beanData = gson.fromJson(myBean.getJsonStr(), BeanData.class);
        mList.addAll(beanData.getOrderData());
        getData();
        myBigAdapter.notifyDataSetChanged();
    }

    private void getData() {
        myBigAdapter = new MyBigAdapter(this,mList);
        recy_View.setAdapter(myBigAdapter);


        myBigAdapter.setShoppingCallBack(new MyBigAdapter.ShoppingCallBack() {
            @Override
            public void bigCheckClick(int bigIndex) {
                boolean b = myBigAdapter.setbigCheck(bigIndex);
                myBigAdapter.setBigCheckStatus(bigIndex,!b);
                myBigAdapter.notifyDataSetChanged();
                onAllcuution();
            }

            @Override
            public void smallCheckClick(int bigIndex, int smallIndex) {
                boolean status = mList.get(bigIndex).getCartlist().get(smallIndex).isStatus();
                myBigAdapter.setSmallCheck(bigIndex,smallIndex,!status);
                myBigAdapter.notifyDataSetChanged();
                onAllcuution();
            }

            @Override
            public void smallCheckClickCount(int bigIndex, int smallIndex, int number) {
                mList.get(bigIndex).getCartlist().get(smallIndex).setCount(number);
                myBigAdapter.notifyDataSetChanged();
                onAllcuution();
            }
        });
    }

    @Override
    protected BasePresenter iniPresenter() {
        return new PresenterImpl();
    }

    @Override
    protected void initViews() {
        recy_View = (RecyclerView) findViewById(R.id.recy_View);
        All_CheckBox = (CheckBox) findViewById(R.id.All_CheckBox);
        All_Price = (TextView) findViewById(R.id.All_Price);
        All_Count = (TextView) findViewById(R.id.All_Count);
        but_commit = findViewById(R.id.but_commit);

        but_commit.setOnClickListener(this);
        All_CheckBox.setOnClickListener(this);

        recy_View.setLayoutManager(new LinearLayoutManager(this));


        myBeanDao = MyApplication.getDaoSession().getMyBeanDao();

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    public void onSuccess(Object o,String json) {
        if (o instanceof BeanData) {

            MyBean myBean = new MyBean();
            myBean.setJsonStr(json);
            myBeanDao.insert(myBean);

            mList.addAll(((BeanData) o).getOrderData());
            myBigAdapter.notifyDataSetChanged();




        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.All_CheckBox:
                allCheck = myBigAdapter.isAllCheck();
                myBigAdapter.setAllCheck(!allCheck);
                All_CheckBox.setChecked(!allCheck);
                All_Price.setText("总价格："+myBigAdapter.allPrice());
                All_Count.setText("总数量："+myBigAdapter.allCount());
                myBigAdapter.notifyDataSetChanged();
                break;
            case R.id.but_commit:

                Intent intent = new Intent(this,SettlementActivity.class);
                startActivity(intent);

                break;
        }
    }
    public void onAllcuution(){
        boolean allCheck = myBigAdapter.isAllCheck();
        All_CheckBox.setChecked(allCheck);
        All_Price.setText("总价格："+myBigAdapter.allPrice());
        All_Count.setText("总数量："+myBigAdapter.allCount());
    }

}
