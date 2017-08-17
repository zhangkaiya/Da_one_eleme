package com.jiyun.da_one_eleme.modle.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.modle.bean.Beanmerchandisesecond;
import com.jiyun.da_one_eleme.modle.twolinkage.CustomizeLVBaseAdapter;

import java.util.List;

import static com.jiyun.da_one_eleme.R.id.lv_customize_item_oldprice;

/**
 * Created by ASUS on 2017/8/13.
 */

public class RightAdapter extends CustomizeLVBaseAdapter {
    //上下文
    private Context mContext;
    //标题
    private List<String> listtitle;
    //内容
    private List<List<Beanmerchandisesecond.ListBean>> listss;
    private LayoutInflater inflater;
    private Iface iface;



    public interface Iface{
        void add(View v,int section,int position);
    }

    public void getIface(Iface iface) {
        this.iface = iface;
    }

    public RightAdapter(Context mContext, List<String> listtitle, List<List<Beanmerchandisesecond.ListBean>> listss) {
        this.mContext = mContext;
        this.listtitle = listtitle;
        this.listss = listss;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public Object getItem(int section, int position) {
        return listss.get(section).get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return listtitle.size();
    }

    @Override
    public int getCountForSection(int section) {
        return listss.get(section).size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            //加载
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_customize_item_right,null);
            //绑定
            holder.lv_customize_item_image = (ImageView) convertView.findViewById(R.id.lv_customize_item_image);
            holder.lv_customize_item_title = (TextView) convertView.findViewById(R.id.lv_customize_item_title);
            holder.lv_customize_item_content = (TextView) convertView.findViewById(R.id.lv_customize_item_content);
            holder.lv_customize_item_sell = (TextView) convertView.findViewById(R.id.lv_customize_item_sell);
            holder.lv_customize_item_newprice = (TextView) convertView.findViewById(R.id.lv_customize_item_newprice);
            holder.lv_customize_item_oldprice = (TextView) convertView.findViewById(lv_customize_item_oldprice);
            //横线
            holder.lv_customize_item_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.add=(ImageView)convertView.findViewById(R.id.add);
            holder.minus= (ImageView) convertView.findViewById(R.id.minus);
            holder.lv_customize_item_number= (TextView) convertView.findViewById(R.id.lv_customize_item_number);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        //设置内容
        holder.lv_customize_item_title.setText(listss.get(section).get(position).getName());
        holder.lv_customize_item_content.setText(listss.get(section).get(position).getForm());
        holder.lv_customize_item_sell.setText(listss.get(section).get(position).getMonthSaleNum()+"份");
        holder.lv_customize_item_newprice.setText((int) listss.get(section).get(position).getNewPrice()+"");
        holder.lv_customize_item_oldprice.setText("￥:"+listss.get(section).get(position).getOldPrice()+"");
        Glide.with(mContext).load(listss.get(section).get(position).getIcon()).into(holder.lv_customize_item_image);

        final List<Beanmerchandisesecond.ListBean> beanlist = listss.get(section);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = beanlist.get(position).getCount();

                if (iface!=null){
                    iface.add(v,section,position);
                }
                num++;
                holder.lv_customize_item_number.setText(num + "");
                beanlist.get(position).setCount(num);


            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c = beanlist.get(position).getCount();
                if (c == 0) {
                    Toast.makeText(mContext, "当前数量为0", Toast.LENGTH_SHORT).show();
                } else {
                    c--;
                    holder.lv_customize_item_number.setText(c + "");
                    beanlist.get(position).setCount(c);
                }
            }
    });
        return convertView;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        HeaderViewHolder holder = null;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            //加载
            convertView = inflater.inflate(R.layout.lv_customize_item_header, parent, false);
            //绑定
            holder.lv_customize_item_header_text = (TextView) convertView.findViewById(R.id.lv_customize_item_header_text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //不可点击
        convertView.setClickable(false);
        //设置标题
        holder.lv_customize_item_header_text.setText(listtitle.get(section));
        return convertView;
    }

    class ChildViewHolder {

        private ImageView lv_customize_item_image;
        private TextView lv_customize_item_title;
        private TextView lv_customize_item_content;
        private TextView lv_customize_item_sell;
        private TextView lv_customize_item_newprice;
        private TextView lv_customize_item_oldprice;
        //增减商品数量按钮
        private ImageView add;
        private ImageView minus;
        public TextView lv_customize_item_number;
    }

    class HeaderViewHolder {
        //标题
        private TextView lv_customize_item_header_text;
    }

}
