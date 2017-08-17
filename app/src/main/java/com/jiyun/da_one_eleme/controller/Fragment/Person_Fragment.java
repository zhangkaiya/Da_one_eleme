package com.jiyun.da_one_eleme.controller.Fragment;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiyun.da_one_eleme.R;
import com.jiyun.da_one_eleme.controller.Activity.Lodding_Activity;
import com.jiyun.da_one_eleme.controller.Activity.MainActivity;
import com.jiyun.da_one_eleme.controller.base.BaseFragment;
import com.squareup.picasso.Picasso;

import java.io.File;

public class Person_Fragment extends BaseFragment implements View.OnClickListener {
    private TextView textView4;
    private ImageView head;
    private Bitmap mBitmap;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;
    ///////广播/////////////
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            textView4.setText(intent.getStringExtra("name"));
            Picasso.with(getActivity()).load(intent.getStringExtra("url")).into(head);
            textView4.setOnClickListener(null);
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_geren;
    }

    @Override
    protected void initView(View view) {
        //////////广播/////////////////
        IntentFilter filter = new IntentFilter("cccccc");
        getActivity().registerReceiver(receiver, filter);

        ////////id/////////////////
        textView4 = (TextView) view.findViewById(R.id.textView4);
        head = (ImageView) view.findViewById(R.id.head);

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        textView4.setOnClickListener(this);
        head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView4:
                startActivity(new Intent(getActivity(), Lodding_Activity.class));
                break;
            case R.id.head:
                showChoosePicDialog();
                break;
        }
    }

    /**
     * 显示修改图片的对话框
     */
    private void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("添加图片");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "temp_image.jpg"));
                        // 将拍照所得的相片保存到SD卡根目录
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）
            head.setImageBitmap(mBitmap);//显示图片
            //在这个地方可以写上上传该图片到服务器的代码，后期将单独写一篇这方面的博客，敬请期待...
        }
    }


    private String url = "http://123.206.14.104:8080/FileUploadDemo/FileUploadServlet";

//    private void uploadImage(ArrayList<ImageItem> pathList) {
//        httpUtil.postFileRequest(url, null, pathList, new MyStringCallBack() {
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                super.onError(call, e, id);
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                super.onResponse(response, id);
//                //返回图片的地址
//                Log.e("---------------->", response.toString());
//            }
//        });
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
