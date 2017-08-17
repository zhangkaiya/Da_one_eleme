package com.jiyun.da_one_eleme.controller.Fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.Activity.Shopping_car_Activity;
import com.jiyun.da_one_eleme.controller.base.BaseFragment;
import com.jiyun.da_one_eleme.modle.adapter.LeftAdapter;
import com.jiyun.da_one_eleme.modle.adapter.RightAdapter;
import com.jiyun.da_one_eleme.modle.bean.Beanmerchandise;
import com.jiyun.da_one_eleme.modle.bean.Beanmerchandisesecond;
import com.jiyun.da_one_eleme.modle.bezier.BezierTypeEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Shopping_Fragment extends BaseFragment implements RightAdapter.Iface, View.OnClickListener {
    private ListView lv_left;
    private ListView lv_right;
    private String url = "http://123.206.14.104:8080/TakeoutService/goods?sellerId=101";
    //左边ListView的Adapter
    private LeftAdapter leftAdapter;
    //左边的数据存储
    private List<String> listtitle = new ArrayList<String>();
    //左边数据的标志
    private List<Boolean> flagArray;
    //右边的ListView的Adapter
    private RightAdapter rightAdapter;
    //是否滑动标志位
    private Boolean isScroll = false;
    private List<List<Beanmerchandisesecond.ListBean>> listss = new ArrayList<>();
    private RelativeLayout shopp_ing_re;
    private ImageView shop_car;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopping_;
    }

    @Override
    protected void initListener() {
        shop_car.setOnClickListener(this);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isScroll = false;
                for (int i = 0; i < listtitle.size(); i++) {
                    if (i == position) {
                        flagArray.set(i, true);
                    } else {
                        flagArray.set(i, false);
                    }
                }
                //更新
                leftAdapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    //查找
                    rightSection += rightAdapter.getCountForSection(i) + 1;
                }
                //显示到rightSection所代表的标题
                lv_right.setSelection(rightSection);
            }
        });
        lv_right.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (lv_right.getLastVisiblePosition() == (lv_right.getCount() - 1)) {
                            lv_left.setSelection(ListView.FOCUS_DOWN);
                        }
                        // 判断滚动到顶部
                        if (lv_right.getFirstVisiblePosition() == 0) {
                            lv_left.setSelection(0);
                        }
                        break;
                }

            }

            int y = 0;
            int x = 0;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < listss.size(); i++) {
                        if (i == rightAdapter.getSectionForPosition(lv_right.getFirstVisiblePosition())) {
                            flagArray.set(i, true);
                            //获取当前标题的标志位
                            x = i;
                        } else {
                            flagArray.set(i, false);
                        }
                    }
                    if (x != y) {
                        leftAdapter.notifyDataSetChanged();
                        //将之前的标志位赋值给y，下次判断
                        y = x;
                    }
                } else {
                    isScroll = true;
                }
            }
        });
    }

    @Override
    protected void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://123.206.14.104:8080/TakeoutService/goods?sellerId=101").build();

                try {
                    Response execute = client.newCall(request).execute();
//                    Log.e("TAG",execute.body().string());

                    Gson gson = new Gson();
                    Beanmerchandise beanmerchandise1 = gson.fromJson(execute.body().string(), Beanmerchandise.class);
                    String data1 = beanmerchandise1.getData();

                    Log.e("TAG", data1);

                    JsonParser parser = new JsonParser();
                    JsonArray asJsonArray = parser.parse(data1).getAsJsonArray();
                    for (JsonElement element : asJsonArray) {
                        Beanmerchandisesecond beanmerchandisesecond = gson.fromJson(element, Beanmerchandisesecond.class);

                        List<Beanmerchandisesecond.ListBean> list = beanmerchandisesecond.getList();
                        listtitle.add(beanmerchandisesecond.getName());
                        listss.add(list);
                        Log.e("11111111", listtitle.size() + "");
                        Log.e("11111111", listss.size() + "");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                leftAdapter.notifyDataSetChanged();
                                rightAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        flagArray.add(true);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);
        flagArray.add(false);


    }

    @Override
    protected void initView(View view) {
        shopp_ing_re = (RelativeLayout) view.findViewById(R.id.shopp_ing_re);
        shop_car = (ImageView) view.findViewById(R.id.shop_car);
        lv_left = (ListView) view.findViewById(R.id.lv_left);
        lv_right = (ListView) view.findViewById(R.id.lv_right);

        flagArray = new ArrayList<>();
        leftAdapter = new LeftAdapter(getActivity(), listtitle, flagArray);
        lv_left.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter(getActivity(), listtitle, listss);
        lv_right.setAdapter(rightAdapter);
        rightAdapter.getIface(this);


    }


    @Override
    public void add(View v, int section, int position) {
        //贝塞尔起始数据点
        int[] startPosition = new int[2];
        //贝塞尔结束数据点
        int[] endPosition = new int[2];
        //控制点
        int[] recyclerPosition = new int[2];

        v.getLocationInWindow(startPosition);
        //shop_car  购物车的view
        shop_car.getLocationInWindow(endPosition);
        //recycler 你的recyclerview
        lv_right.getLocationInWindow(recyclerPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controllF = new PointF();

        startF.x = startPosition[0];
        startF.y = startPosition[1] - recyclerPosition[1];
        endF.x = endPosition[0];
        endF.y = endPosition[1] - recyclerPosition[1];
        controllF.x = endF.x;
        controllF.y = startF.y;

        //Myapp.activity 你的上下文
        final ImageView imageView = new ImageView(getActivity());
        //goods_re 你最外层的view
        shopp_ing_re.addView(imageView);
        //这里可以自己选择一张图片
        Glide.with(getContext()).load(listss.get(section).get(position).getIcon()).into(imageView);
        //  imageView.setImageResource(R.drawable.shopcar);
        imageView.getLayoutParams().width = 100;
        imageView.getLayoutParams().height = 100;
        imageView.setVisibility(View.VISIBLE);
        imageView.setX(startF.x);
        imageView.setY(startF.y);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });


        //shoping_car 你的购物车view
        ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(shop_car, "scaleX", 0.6f, 1.0f);
        ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(shop_car, "scaleY", 0.6f, 1.0f);
        objectAnimatorX.setInterpolator(new AccelerateInterpolator());
        objectAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimatorX).with(objectAnimatorY).after(valueAnimator);
        set.setDuration(800);
        set.start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                //goods_re  你的最外层view
                shopp_ing_re.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Shopping_car_Activity.class);
        startActivity(intent);
    }
}