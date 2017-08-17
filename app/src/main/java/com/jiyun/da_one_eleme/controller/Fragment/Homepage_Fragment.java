package com.jiyun.da_one_eleme.controller.Fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.Activity.Map_Activity;
import com.jiyun.da_one_eleme.controller.Activity.Order_Shopping_Activity;
import com.jiyun.da_one_eleme.controller.base.BaseFragment;
import com.jiyun.da_one_eleme.modle.Okhttp.CallBack;
import com.jiyun.da_one_eleme.modle.Okhttp.ImplOkhttp;
import com.jiyun.da_one_eleme.modle.Okhttp.Okhttp;
import com.jiyun.da_one_eleme.modle.adapter.MyGridAdapter;
import com.jiyun.da_one_eleme.modle.adapter.MyRecyclerAdapter;
import com.jiyun.da_one_eleme.modle.bean.Bean;
import com.jiyun.da_one_eleme.modle.bean.DataBean;
import com.jiyun.da_one_eleme.modle.code.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Homepage_Fragment extends BaseFragment implements CallBack{

    private String url="http://123.206.14.104:8080/TakeoutService/home?latitude=116.30142&longitude=40.05087";
    private Banner banner;
    private GridView grid_view;
    private RecyclerView recycler;
    private List<DataBean.HeadBean.PromotionListBean> promotlist=new ArrayList<>();
    private List<String> imagelist=new ArrayList<>();
    private List<DataBean.HeadBean.CategorieListBean> categorieListBeen;
    private RecyclerAdapterWithHF hf;
    private TextView textView3;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shouye;
    }

    @Override
    protected void initView(View view) {
        Okhttp okhttp=new ImplOkhttp();
        okhttp.doget(url,this);

        banner = (Banner) view.findViewById(R.id.banner);

        grid_view = (GridView) view.findViewById(R.id.grid_view);

        recycler = (RecyclerView) view.findViewById(R.id.recycler);

        textView3 = (TextView) view.findViewById(R.id.textView3);


    }

    @Override
    protected void loadData() {


    }

    @Override
    protected void initListener() {

    textView3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(),Map_Activity.class);
            startActivityForResult(intent,0);
        }
    });

    }

    @Override
    public void success(final String s) {

        Gson gson=new Gson();

        Bean bean = gson.fromJson(s, Bean.class);
        Gson gson1=new Gson();
        DataBean bean1 = gson1.fromJson(bean.getData(), DataBean.class);
        List<DataBean.HeadBean.PromotionListBean> promotionList = bean1.getHead().getPromotionList();

        promotlist.addAll(promotionList);
        for (int i = 0; i <3 ; i++) {

            String pic = promotlist.get(i).getPic();

            imagelist.add(pic);
        }

        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imagelist);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.start();

        categorieListBeen = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            categorieListBeen.add(bean1.getHead().getCategorieList().get(i));
    }
        categorieListBeen.get(0).getName();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                MyGridAdapter gridAdapter=new MyGridAdapter(getActivity(), categorieListBeen);
                grid_view.setAdapter(gridAdapter);
            }
        });


        final ArrayList<DataBean.BodyBean> list = new ArrayList<>();
        int size = bean1.getBody().size();
        for (int i=0 ; i< size;i++){
            list.add(bean1.getBody().get(i));
        }
        recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        MyRecyclerAdapter adapter=new MyRecyclerAdapter(getActivity(),list);
        hf = new RecyclerAdapterWithHF(adapter);
        recycler.setAdapter(hf);
        hf.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder vh, int position) {
                Intent intent=new Intent(getActivity(), Order_Shopping_Activity.class);
                intent.putExtra("name",list.get(position).getSeller().getName());
                startActivity(intent);
            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0&&resultCode==2){
            textView3.setText(data.getStringExtra("title"));
        }
    }
}

