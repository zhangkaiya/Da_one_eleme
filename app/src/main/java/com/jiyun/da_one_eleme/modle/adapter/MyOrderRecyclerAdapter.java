package com.jiyun.da_one_eleme.modle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.modle.bean.OrderDataBean;

import java.util.List;

/**
 * Created by 张凯雅 on 2017/8/13.
 */

public class MyOrderRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<OrderDataBean> dataBeen;
    private ViewHolder viewHolder;

    public MyOrderRecyclerAdapter(Context context, List<OrderDataBean> dataBeen) {
        this.context = context;
        this.dataBeen = dataBeen;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.order_recycler_item,null);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolder= (ViewHolder) holder;
        viewHolder.order_item_tv_name.setText(dataBeen.get(position).getSeller().getName());
        viewHolder.order_item_tv_time.setText(dataBeen.get(position).getDetail().getTime());
        if (dataBeen.get(position).getType().equals("10")){
            viewHolder.order_item_tv_type.setText("未支付");
        }else{
            viewHolder.order_item_tv_type.setText("已送达");
        }            Glide.with(context).load(dataBeen.get(position).getSeller().getPic()).into(viewHolder.order_item_iv_pic);


    }

    @Override
    public int getItemCount() {
        return dataBeen.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

         TextView order_item_tv_name;
         TextView order_item_tv_time;
         TextView order_item_tv_type;
         ImageView order_item_iv_pic;

        public ViewHolder(View itemView) {
            super(itemView);
            order_item_tv_name= (TextView) itemView.findViewById(R.id.order_item_tv_name);
            order_item_tv_time = (TextView) itemView.findViewById(R.id.order_item_tv_time);
            order_item_tv_type = (TextView) itemView.findViewById(R.id.order_item_tv_type);
            order_item_iv_pic = (ImageView) itemView.findViewById(R.id.order_item_iv_pic);
        }
    }

}
