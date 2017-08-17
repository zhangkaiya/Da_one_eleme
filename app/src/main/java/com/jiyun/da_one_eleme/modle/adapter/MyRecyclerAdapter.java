package com.jiyun.da_one_eleme.modle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.modle.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by 张凯雅 on 2017/8/12.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter {
    private Context context;
    private final int ZERO = 0;
    private final int ONE = 1;
    ArrayList<DataBean.BodyBean> list;
    private ViewHoudler viewHoudler;
    private ViewHolderTwo viewholdertwo;

    public MyRecyclerAdapter(Context context, ArrayList<DataBean.BodyBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTyp) {
        if (viewTyp == ZERO) {
            View inflate = View.inflate(context, R.layout.recycler_item1, null);
            viewHoudler = new ViewHoudler(inflate);
            return viewHoudler;
        } else {
            View inflate = View.inflate(context, R.layout.recycler_item2, null);
            viewholdertwo = new ViewHolderTwo(inflate);
            return viewholdertwo;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == ZERO) {
            viewHoudler = (ViewHoudler) holder;
            viewHoudler.textView.setText(list.get(position).getSeller().getName());

        } else {
            viewholdertwo= (ViewHolderTwo) holder;
            viewholdertwo.text_onemai.setText((String) list.get(position).getRecommendInfos().get(0));
            viewholdertwo.text_oneken.setText((String) list.get(position).getRecommendInfos().get(1));
            viewholdertwo.text_onezhou.setText((String) list.get(position).getRecommendInfos().get(2));
            viewholdertwo.text_twomai.setText((String) list.get(position).getRecommendInfos().get(3));
            viewholdertwo.text_twoken.setText((String) list.get(position).getRecommendInfos().get(4));
            viewholdertwo.text_twozhou.setText((String) list.get(position).getRecommendInfos().get(5));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == ZERO) {
            return ZERO;
        } else {
            return ONE;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHoudler extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHoudler(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.name);
        }

    }
    class ViewHolderTwo extends RecyclerView.ViewHolder{
        TextView text_onemai;
        TextView text_oneken;
        TextView text_onezhou;
        TextView text_twomai;
        TextView text_twoken;
        TextView text_twozhou;
        public ViewHolderTwo(View itemView) {
            super(itemView);
            text_oneken= (TextView) itemView.findViewById(R.id.text_oneken);
            text_onemai= (TextView) itemView.findViewById(R.id.text_onemai);
            text_onezhou= (TextView) itemView.findViewById(R.id.text_onezhou);
            text_twomai= (TextView) itemView.findViewById(R.id.text_twomai);
            text_twoken= (TextView) itemView.findViewById(R.id.text_oneken);
            text_twozhou= (TextView) itemView.findViewById(R.id.text_twozhou);

        }
    }
}
