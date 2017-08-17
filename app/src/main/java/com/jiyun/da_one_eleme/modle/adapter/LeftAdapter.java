package com.jiyun.da_one_eleme.modle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiyun.da_one_eleme.R;

import java.util.List;

/**
 * Created by ASUS on 2017/8/13.
 */

public class LeftAdapter extends BaseAdapter {



    private Context mContext;
    //标题
    private List<String> listtitle;
    //标志
    private List<Boolean> flagArray;
    private LayoutInflater inflater;

    public LeftAdapter(Context mContext, List<String> listtitle, List<Boolean> flagArray) {
        this.listtitle = listtitle;
        this.flagArray = flagArray;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return listtitle.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            //加载
            convertView = inflater.inflate(R.layout.lv_item_left, parent, false);
            //绑定
            holder.lv_left_item_text = (TextView) convertView.findViewById(R.id.lv_left_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        holder.lv_left_item_text.setText(listtitle.get(position));
        //根据标志位，设置背景颜色
        if (flagArray.get(position)) {
            holder.lv_left_item_text.setBackgroundColor(Color.rgb(255, 255, 255));
        } else {
            holder.lv_left_item_text.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    class ViewHolder {
        private TextView lv_left_item_text;
    }
}
