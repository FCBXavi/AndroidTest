package com.example.quxiaopeng.maptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

public class MainActivity extends AppCompatActivity{

    private MapView mMapView = null;
    private AMap mMap;

    private TextView tvAddress;
    private Button mBtnSearch;

    private PoiModel mCurrentPosition = new PoiModel();
    private PoiModel mBasicPoiData = new PoiModel();

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
    private GeocodeSearch mGeoSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.map);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        mGeoSearch = new GeocodeSearch(this);
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PoiActivity.class));
            }
        });

        mGeoSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                tvAddress.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                addMarker();
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        mMapView.onCreate(savedInstanceState);
        mMap = mMapView.getMap();
        initAMap();
        initUiSetting();
        initLocationClient();
    }

    private void initAMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setMyLocationEnabled(true);

        mMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                LatLonPoint target = new LatLonPoint(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude);
                mCurrentPosition.longitude = target.getLongitude();
                mCurrentPosition.latitude = target.getLatitude();
                Log.i("jonathan", "longitude:" + target.getLongitude() + " latitude: "+ target.getLatitude());
                RegeocodeQuery query = new RegeocodeQuery(target, 200,GeocodeSearch.AMAP);
                mGeoSearch.getFromLocationAsyn(query);
            }
        });

    }

    private void initUiSetting() {
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(false);
    }

    private void initLocationClient() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(false);
//        mLocationOption.setInterval(1000);
//        mLocationOption.setNeedAddress(true);
//        mLocationOption.setWifiActiveScan(false);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    Log.i("jonathan", "longitude:" + aMapLocation.getLongitude() + " latitude: "+ aMapLocation.getLatitude());
//                    MarkerOptions markerOption = new MarkerOptions();
//                    markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red_big));
//                    markerOption.position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
//                    markerOption.title(aMapLocation.getStreet()).snippet(aMapLocation.getAddress());
//                    markerOption.draggable(true);
//                    Marker marker = mMap.addMarker(markerOption);
//                    marker.hideInfoWindow();
                    mBasicPoiData.longitude = aMapLocation.getLongitude();
                    mBasicPoiData.latitude = aMapLocation.getLatitude();
                    mBasicPoiData.province = aMapLocation.getProvince();
                    mBasicPoiData.cityCode = aMapLocation.getCityCode();
                    mBasicPoiData.city = aMapLocation.getCity();
                    mBasicPoiData.district = aMapLocation.getDistrict();
                    String address;
                    if (TextUtils.isEmpty(aMapLocation.getAddress())) {
                        address = "定位失败";
                    } else {
                        address = aMapLocation.getAddress();
                    }
                    mBasicPoiData.addressTitle = "当前位置: " + address;
                    Log.i("jonathan", "gd: " + aMapLocation.getAddress());
                    mBasicPoiData.accuracy = aMapLocation.getAccuracy();
                    mBasicPoiData.addressDetail = aMapLocation.getAddress();
                    tvAddress.setText(mBasicPoiData.addressTitle);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 16));
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("jonathan","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
                mLocationClient.stopLocation();
            }
        });

        mLocationClient.startLocation();
    }

    private void addMarker() {
        mMap.clear();
        Marker marker;
        MarkerOptions markerOption = new MarkerOptions();
        if (mBasicPoiData != null) {
            markerOption = new MarkerOptions();
//            markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.attend_blue_dot));
            markerOption.anchor(0.5f, 0.5f);
            markerOption.position(new LatLng(mBasicPoiData.latitude, mBasicPoiData.longitude));
            markerOption.draggable(false);
            marker = mMap.addMarker(markerOption);
            marker.hideInfoWindow();
        }
        //红色大头针
        markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_red_big));
        markerOption.anchor(0.5f, 1.0f);
        markerOption.position(new LatLng(0, 0));
        markerOption.draggable(false);
        marker = mMap.addMarker(markerOption);
        marker.hideInfoWindow();
        marker.setPositionByPixels(mMapView.getWidth() / 2,
                mMapView.getHeight() / 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
    }

}
