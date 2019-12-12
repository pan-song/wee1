package com.bawei.util_dierzhou_lianxi2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.util_dierzhou_lianxi2.R;
import com.bawei.util_dierzhou_lianxi2.bean.BeanData;
import com.bawei.util_dierzhou_lianxi2.utils.GlideUtil;
import com.bawei.util_dierzhou_lianxi2.weight.MyView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 23:21
 */
public class MyBigAdapter extends RecyclerView.Adapter<MyBigAdapter.Holder> {

    private Context mContext;
    private List<BeanData.OrderDataBean> mList;

    public MyBigAdapter(Context mContext, List<BeanData.OrderDataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //加载店铺的布局
        View view = View.inflate(mContext, R.layout.recy_big_layout, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        holder.Big_Text.setText(mList.get(i).getShopName());

        SmallAdapter smallAdapter = new SmallAdapter(mList.get(i).getCartlist(), i);
        holder.Big_Recy_View.setAdapter(smallAdapter);

        holder.Big_CheckBox.setChecked(setbigCheck(i));
        holder.Big_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoppingCallBack != null) {
                    shoppingCallBack.bigCheckClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        CheckBox Big_CheckBox;
        TextView Big_Text;
        RecyclerView Big_Recy_View;

        public Holder(@NonNull View itemView) {
            super(itemView);
            Big_CheckBox = itemView.findViewById(R.id.Big_CheckBox);
            Big_Text = itemView.findViewById(R.id.Big_Text);
            Big_Recy_View = itemView.findViewById(R.id.Big_Recy_View);

            Big_Recy_View.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    class SmallAdapter extends RecyclerView.Adapter<SmallAdapter.SmallHolder> {

        private List<BeanData.OrderDataBean.CartlistBean> mList;
        private int bigIndex;

        public SmallAdapter(List<BeanData.OrderDataBean.CartlistBean> mList, int bigIndex) {
            this.mList = mList;
            this.bigIndex = bigIndex;
        }

        @NonNull
        @Override
        public SmallAdapter.SmallHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            //加载商品的布局
            View view = View.inflate(mContext, R.layout.recy_small_layout, null);
            SmallHolder smallHolder = new SmallHolder(view);
            return smallHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SmallAdapter.SmallHolder smallHolder, final int i) {

            GlideUtil.loadImade(mList.get(i).getDefaultPic(), smallHolder.small_Image);
            smallHolder.small_ShopName.setText(mList.get(i).getShopName() + "");
            smallHolder.small_Name.setText(mList.get(i).getProductName() + "");
            smallHolder.small_Price.setText(mList.get(i).getPrice() + "");
            smallHolder.addorRemove.setView_number(mList.get(i).getCount());

            smallHolder.small_CheckBox.setChecked(mList.get(i).isStatus());
            smallHolder.small_CheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shoppingCallBack != null) {
                        shoppingCallBack.smallCheckClick(bigIndex, i);
                    }
                }
            });

            smallHolder.addorRemove.setItemCount(new MyView.ItemCount() {
                @Override
                public void setItemCount(int number) {
                    shoppingCallBack.smallCheckClickCount(bigIndex,i,number);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class SmallHolder extends RecyclerView.ViewHolder {
            CheckBox small_CheckBox;
            TextView small_ShopName, small_Name, small_Price;
            ImageView small_Image;
            MyView addorRemove;

            public SmallHolder(@NonNull View itemView) {
                super(itemView);

                small_CheckBox = itemView.findViewById(R.id.small_CheckBox);
                small_ShopName = itemView.findViewById(R.id.small_ShopName);
                small_Name = itemView.findViewById(R.id.small_Name);
                small_Price = itemView.findViewById(R.id.small_Price);
                small_Image = itemView.findViewById(R.id.small_Image);
                addorRemove = itemView.findViewById(R.id.addorRemove);

            }
        }
    }

    public interface ShoppingCallBack {
        void bigCheckClick(int bigIndex);

        void smallCheckClick(int bigIndex, int smallIndex);

        void smallCheckClickCount(int bigIndex, int smallIndex, int number);
    }

    private ShoppingCallBack shoppingCallBack;

    public void setShoppingCallBack(ShoppingCallBack shoppingCallBack) {
        this.shoppingCallBack = shoppingCallBack;
    }

    //判断店铺是否要选中
    public boolean setbigCheck(int bigIndex) {
        boolean flag = true;
        //获取店铺的数据
        BeanData.OrderDataBean orderDataBean = mList.get(bigIndex);
        //循环
        for (int i = 0; i < orderDataBean.getCartlist().size(); i++) {
            if (!orderDataBean.getCartlist().get(i).isStatus()) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    //当我点击店铺复选框时
    public void setBigCheckStatus(int bigIndex, boolean isStatus) {
        //获取商品的数据
        List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(bigIndex).getCartlist();
        for (BeanData.OrderDataBean.CartlistBean cartlistBean : cartlist) {
            cartlistBean.setStatus(isStatus);
        }
    }

    //点击商品选中
    public void setSmallCheck(int bigIndex, int smallIndex, boolean isCheck) {
        mList.get(bigIndex).getCartlist().get(smallIndex).setStatus(isCheck);
    }

    //当我点击Activity全选的时候
    public boolean isAllCheck() {
        boolean isAllCheck = true;

        //循环所有数据
        for (int i = 0; i < mList.size(); i++) {
            //获取里层的数据
            List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(i).getCartlist();
            //循环里层的数据
            for (int j = 0; j < cartlist.size(); j++) {
                //判断标识字段的状态
                if (!cartlist.get(j).isStatus()) {
                    isAllCheck = false;
                    return isAllCheck;
                }
            }
        }

        return isAllCheck;
    }

    //点击全选让复选框选中
    public void setAllCheck(boolean isCheck) {
        //循环所有的数据
        for (int i = 0; i < mList.size(); i++) {
            //获取里层的数据
            List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(i).getCartlist();
            //循环里层的数据
            for (int j = 0; j < cartlist.size(); j++) {
                cartlist.get(j).setStatus(isCheck);
            }
        }
    }

    //计算总价格
    public int allPrice() {
        int allPrice = 0;

        for (int i = 0; i < mList.size(); i++) {
            List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(i).getCartlist();
            for (int j = 0; j < cartlist.size(); j++) {
                if (cartlist.get(j).isStatus()) {
                    allPrice += cartlist.get(j).getPrice() * cartlist.get(j).getCount();
                }
            }
        }

        return allPrice;
    }

    //计算总数量
    public int allCount() {
        int count = 0;

        for (int i = 0; i < mList.size(); i++) {
            List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(i).getCartlist();
            for (int j = 0; j < cartlist.size(); j++) {
                if (cartlist.get(j).isStatus()) {
                    count += cartlist.get(j).getCount();
                }
            }
        }

        return count;
    }
}
