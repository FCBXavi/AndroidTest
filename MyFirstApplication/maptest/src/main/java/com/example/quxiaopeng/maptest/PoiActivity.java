package com.example.quxiaopeng.maptest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import SwipeRefreshView.HeaderAndFooterRecyclerViewAdapter;
import SwipeRefreshView.LoadingFooter;
import SwipeRefreshView.MySwiperRefreshView;

/**
 * Created by quxiaopeng on 16/8/17.
 */

public class PoiActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, MySwiperRefreshView.OnLoadListener {

    private MySwiperRefreshView mSwipeRefreshView;
    private RecyclerView mRecyclerView;
    private EditText mEtSearch;
    private PoiAdapter mAdapter;
    private List<PoiModel> mList = new ArrayList<>();
    private String mItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);
        mSwipeRefreshView = (MySwiperRefreshView) findViewById(R.id.swipe_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.poi_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mItem = String.valueOf(s);
                mList.clear();
                mSwipeRefreshView.setState(LoadingFooter.State.Normal);
                search(mItem);
            }
        });

        mAdapter = new PoiAdapter(this, mList);
        mRecyclerView.setAdapter(new HeaderAndFooterRecyclerViewAdapter(mAdapter));
        mSwipeRefreshView.setOnRefreshListener(this);
        mSwipeRefreshView.setOnLoadListener(this);

    }

    private void search(String keyWord) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "","北京");
        query.setPageSize(20);
        int currentPage = mList.size() / 20;
        query.setPageNum(currentPage);
//        query.setCityLimit(true);
        PoiSearch poiSearch = new PoiSearch(this, query);
        final String item = mItem;
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                if (!TextUtils.equals(item, mItem)){
                    return;
                }
                mSwipeRefreshView.setRefreshing(false);
                Log.i("quxiaopeng", "size" + poiResult.getPois().size());
                List<PoiItem> poiItems = poiResult.getPois();
                if (poiItems.size() == 0 && mList.size() != 0) {
                    mSwipeRefreshView.setState(LoadingFooter.State.TheEnd);
                    return;
                }
                for (PoiItem item : poiItems) {
                    PoiModel poiData = new PoiModel();
                    poiData.latitude = item.getLatLonPoint().getLatitude();
                    poiData.longitude = item.getLatLonPoint().getLongitude();
                    poiData.addressTitle = item.getTitle();
                    poiData.addressDetail = item.getSnippet();
                    poiData.province = item.getProvinceName();
                    poiData.city = item.getCityName();
                    poiData.district = item.getAdName();
                    if (!TextUtils.isEmpty(poiData.addressTitle) && !TextUtils.isEmpty(poiData.addressDetail)) {
                        mList.add(poiData);
                    }
                }
                mSwipeRefreshView.setState(LoadingFooter.State.Normal);

//                if (mPoiList.size() == 0 && mCurrentPoi != null) {
//                    showEmptyView(getString(R.string.outdoor_search_range_out_of_bound));
//                } else {
//                    mTxtSearchResult.setVisibility(View.VISIBLE);
//                    hideEmptyView();
//                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onLoad() {
        search(mEtSearch.getText().toString());
    }

    @Override
    public void onRefresh() {
        mList.clear();
        mSwipeRefreshView.setState(LoadingFooter.State.Normal);
        search(mEtSearch.getText().toString());
    }
}
