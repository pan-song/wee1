package com.bawei.util_dierzhou_lianxi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.util_dierzhou_lianxi.R;
import com.bawei.util_dierzhou_lianxi.bean.BeanData;
import com.bawei.util_dierzhou_lianxi.utils.GlideUtil;
import com.bawei.util_dierzhou_lianxi.weight.MyView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: 潘世松
 * Date: 2019/12/8
 * Time: 17:50
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

        //加载店铺布局
        View view = View.inflate(mContext, R.layout.recy_big_layout, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {

        holder.big_Text.setText(mList.get(i).getShopName());

        SmallAdapter smallAdapter = new SmallAdapter(mList.get(i).getCartlist(), i);
        holder.big_Recy_View.setAdapter(smallAdapter);
        //设置店铺的选中
        holder.big_CheckBox.setChecked(setBigCheck(i));
        holder.big_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoppingBacllBack != null) {
                    shoppingBacllBack.bigCheckClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        CheckBox big_CheckBox;
        TextView big_Text;
        RecyclerView big_Recy_View;

        public Holder(@NonNull View itemView) {
            super(itemView);
            big_CheckBox = itemView.findViewById(R.id.Big_CheckBox);
            big_Text = itemView.findViewById(R.id.Big_Text);
            big_Recy_View = itemView.findViewById(R.id.Big_Recy_View);

            big_Recy_View.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }


    class SmallAdapter extends RecyclerView.Adapter<SmallAdapter.SmallHolder> {

        private List<BeanData.OrderDataBean.CartlistBean> mList;
        private int BigIndex;

        public SmallAdapter(List<BeanData.OrderDataBean.CartlistBean> mList, int bigIndex) {
            this.mList = mList;
            BigIndex = bigIndex;
        }

        @NonNull
        @Override
        public SmallHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = View.inflate(mContext, R.layout.recy_small_layout, null);
            SmallHolder smallHolder = new SmallHolder(view);

            return smallHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SmallHolder smallHolder, final int i) {

            //设置图片
            GlideUtil.loadImade(mList.get(i).getDefaultPic(), smallHolder.small_Image);

            smallHolder.small_ShopName.setText(mList.get(i).getShopName());
            smallHolder.small_Name.setText(mList.get(i).getProductName());
            smallHolder.small_Price.setText("￥" + mList.get(i).getPrice());
            smallHolder.addorRemove.setView_Number(mList.get(i).getCount());

            //设置商家复选框的选中状态
            smallHolder.small_CheckBox.setChecked(mList.get(i).isStatus());
            smallHolder.small_CheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shoppingBacllBack != null) {
                        shoppingBacllBack.smallCheckClick(BigIndex, i);
                    }
                }
            });

            smallHolder.addorRemove.setItemCount(new MyView.ItemCount() {
                @Override
                public void setItemCount(int number) {
                    if (shoppingBacllBack != null) {
                        shoppingBacllBack.smallCheckClickCount(BigIndex,i,number);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class SmallHolder extends RecyclerView.ViewHolder {

            CheckBox small_CheckBox;
            ImageView small_Image;
            TextView small_ShopName, small_Name, small_Price;
            MyView addorRemove;

            public SmallHolder(@NonNull View itemView) {
                super(itemView);

                small_CheckBox = itemView.findViewById(R.id.small_CheckBox);
                small_Image = itemView.findViewById(R.id.small_Image);
                small_ShopName = itemView.findViewById(R.id.small_ShopName);
                small_Name = itemView.findViewById(R.id.small_Name);
                small_Price = itemView.findViewById(R.id.small_Price);
                addorRemove = itemView.findViewById(R.id.addorRemove);

            }
        }
    }

    public interface ShoppingBacllBack {
        void bigCheckClick(int bigIndex);

        void smallCheckClick(int bigIndex, int smallIndex);

        void smallCheckClickCount(int bigIndex, int smallIndex, int number);
    }

    private ShoppingBacllBack shoppingBacllBack;

    public void setShoppingBacllBack(ShoppingBacllBack shoppingBacllBack) {
        this.shoppingBacllBack = shoppingBacllBack;
    }

    //判断店铺是否选中
    public boolean setBigCheck(int bigIndex) {
        boolean flag = true;

        //获取店铺的数据
        BeanData.OrderDataBean orderDataBean = mList.get(bigIndex);
        //循环商家的数据
        for (int i = 0; i < orderDataBean.getCartlist().size(); i++) {
            //判断标示字段是否为true
            if (!orderDataBean.getCartlist().get(i).isStatus()) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    //当我点击店铺的时候
    public void setBigCheckStatus(int bigIndex, boolean isStatus) {
        //获取商家的数据
        List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(bigIndex).getCartlist();
        //循环商家的数据
        for (BeanData.OrderDataBean.CartlistBean cartlistBean : cartlist) {
            cartlistBean.setStatus(isStatus);
        }
    }

    //当我点击商家的时候
    public void setSamllCheck(int bigIndex, int smallIndex, boolean isCheck) {
        //设置选中状态
        mList.get(bigIndex).getCartlist().get(smallIndex).setStatus(isCheck);
    }

    //判断全选的状态
    public boolean isAllCheck() {
        boolean isAllCheck = true;

        //循环所有数据
        for (int i = 0; i < mList.size(); i++) {
            //获取商家的数据
            List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(i).getCartlist();
            //循环商家的数据
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

    //点击全选按钮选中
    public void setAllCheck(boolean isCheck) {
        //循环所有数据
        for (int i = 0; i < mList.size(); i++) {
            //获取商家的数据
            List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(i).getCartlist();
            //循环商家的数据
            for (int j = 0; j < cartlist.size(); j++) {
                //设置选中的状态
                cartlist.get(j).setStatus(isCheck);
            }
        }
    }

    //计算总价格
    public int allPrice() {
        int allPrice = 0;
        //循环所有数据
        for (int i = 0; i < mList.size(); i++) {
            //获取商家的数据
            List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(i).getCartlist();
            //循环商家的数据
            for (int j = 0; j < cartlist.size(); j++) {
                //判断选中的状态
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

        //循环所有数据
        for (int i = 0; i < mList.size(); i++) {
            //获取商家的数据
            List<BeanData.OrderDataBean.CartlistBean> cartlist = mList.get(i).getCartlist();
            //循环商家的数据
            for (int j = 0; j < cartlist.size(); j++) {
                //判断选中的状态
                if (cartlist.get(j).isStatus()) {
                    count += cartlist.get(j).getCount();
                }
            }
        }

        return count;
    }


}
