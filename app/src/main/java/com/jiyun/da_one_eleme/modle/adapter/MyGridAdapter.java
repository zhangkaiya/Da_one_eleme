package com.jiyun.da_one_eleme.modle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.modle.bean.DataBean;

import java.util.List;

/**
 * Created by 张凯雅 on 2017/8/12.
 */

public class MyGridAdapter extends BaseAdapter {
    private Context context;
    List<DataBean.HeadBean.CategorieListBean> categorieListBeen;

    public MyGridAdapter(Context context, List<DataBean.HeadBean.CategorieListBean> categorieListBeen) {
        this.context = context;
        this.categorieListBeen = categorieListBeen;
    }

    @Override
    public int getCount() {
        return categorieListBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(context,R.layout.grid_item,null);
            viewHolder=new ViewHolder();
            viewHolder.image= (ImageView) convertView.findViewById(R.id.image);
            viewHolder.text= (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(categorieListBeen.get(position).getName());
        Glide.with(context).load(categorieListBeen.get(position).getPic()).into(viewHolder.image);
        return convertView;
    }
    class ViewHolder{
        ImageView image;
        TextView text;

    }
}
