package com.jiyun.da_one_eleme.controller.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.jiyun.da_one_eleme.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Map_Activity extends Activity  implements GeocodeSearch.OnGeocodeSearchListener {
    private static final int CODE_FOR_WRITE_PERMISSION = 0;
    private MapView mapView;
    private AMap aMap;
    public AMapLocationClientOption mLocationOption = null;
    private double lat;
    private double lon;

    public AMapLocationClient mLocationClient = null;
    private MarkerOptions markerOptions;
    private GeocodeSearch geocoderSearch;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        check();


        init();
        final Geocoder geocoder = new Geocoder(this);
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {


            @Override
            public void onMapClick(LatLng latLng) {
                aMap.clear();
                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                geocoderSearch = new GeocodeSearch(Map_Activity.this);
                geocoderSearch.setOnGeocodeSearchListener(Map_Activity.this);//和上面一样
                LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
                markerOptions.visible(true);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bk_icon_select_location));
                markerOptions.icon(bitmapDescriptor);
                aMap.addMarker(markerOptions);


            }
        });

        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                if (title != null) {
                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    setResult(2, intent);
                    Toast.makeText(Map_Activity.this, title, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("title", s);
                    setResult(2, intent);
                    Toast.makeText(Map_Activity.this, s, Toast.LENGTH_SHORT).show();
                    finish();
                }
                return false;
            }
        });
    }


    public void check() {

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION);
        int hasGrantedPermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //这个判断是用来审验是否获取了权限
        if (hasGrantedPermission == PackageManager.PERMISSION_GRANTED && hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            //已经获取了权限不需要再次申请，可以直接在这里进行你需要的操作
//初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);
        }//需要弹出dialog让用户手动赋予权限
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION}, CODE_FOR_WRITE_PERMISSION);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 200:


                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //申请权限成功在这里做操作
                    mLocationClient = new AMapLocationClient(getApplicationContext());
                    mLocationClient.setLocationListener(mLocationListener);
                } else {

                    //申请权限失败在这里做操作再次申请
                    check();
                }
                return;

        }
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        setUpMap();

    }

    private void setUpMap() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();
                    Log.v("pcw", "lat : " + lat + " lon : " + lon);
                    Log.v("pcw", "Country : " + amapLocation.getCountry() + " province : " + amapLocation.getProvince() + " City : " + amapLocation.getCity() + " District : " + amapLocation.getDistrict());

                    // 设置当前地图显示为当前位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 19));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title(amapLocation.getAoiName());
                    markerOptions.visible(true);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker));
                    markerOptions.icon(bitmapDescriptor);
                    aMap.addMarker(markerOptions);

                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                s = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            }
        }

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

}


