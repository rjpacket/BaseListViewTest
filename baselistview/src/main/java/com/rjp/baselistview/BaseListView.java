package com.rjp.baselistview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Gimpo create on 2018/2/11 11:05
 * email  : jimbo922@163.com
 */

public abstract class BaseListView<T> extends LinearLayout {
    public Context mContext;
    public SmartRefreshLayout refreshLayout;
    private ListView listView;
    private BaseAdapter listAdapter;
    public List<T> mDatas = new ArrayList<>();
    public int mPage = 0;
    public int mPageSize = 5;
    private View emptyView;

    public BaseListView(Context context) {
        this(context, null);
    }

    public BaseListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_base_list_view, this);
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        emptyView.setLayoutParams(params);
        emptyView.setVisibility(GONE);
        addView(emptyView);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.pull_to_refresh_layout);
        refreshLayout.setDragRate(0.5f); //阻尼
        refreshLayout.setReboundDuration(300); // 回弹时间
        refreshLayout.setRefreshHeader(new DefaultHeader(mContext));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                mPage = 0;
                requestData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                requestData();
            }
        });

        listView = (ListView) findViewById(R.id.list_view);
        listAdapter = getListAdapter();
        listView.setAdapter(listAdapter);
        requestData();
    }

    /**
     * 处理请求到的数据
     * @param data
     */
    public void dealSuccessData(Object data){
        List<T> list = parseData(data);
        if (isFirstPage()) {
            refreshLayout.finishRefresh();
            refreshLayout.setNoMoreData(false);
            mDatas.clear();
        }else{
            refreshLayout.finishLoadMore();
        }
        if (hasMoreData(list)) {
            pageNext();
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }
        if (list.size() == 0 && isFirstPage()) {
            showEmptyView();
        } else {
            hideEmptyView();
            mDatas.addAll(list);
        }
        filterData(mDatas);
        listAdapter.notifyDataSetChanged();
    }

    /**
     * 隐藏空页面
     */
    private void hideEmptyView() {
        emptyView.setVisibility(GONE);
        refreshLayout.setVisibility(VISIBLE);
    }

    /**
     * 显示空数据页面
     */
    protected void showEmptyView(){
        refreshLayout.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
    }

    /**
     * 筛选数据
     * @param mDatas
     */
    protected void filterData(List<T> mDatas){

    }

    /**
     * 页数自增
     */
    protected void pageNext(){
        mPage ++;
    }

    /**
     * 是否还有更多数据
     * @return
     */
    protected boolean hasMoreData(List<T> list){
        return list.size() >= mPageSize;
    }

    /**
     * 如果是首页请求到的数据
     * @return
     */
    protected boolean isFirstPage(){
        return mPage == 0;
    }

    /**
     * 转化数据
     * @param data
     * @return
     */
    protected abstract List<T> parseData(Object data);

    /**
     * 设置头布局
     * @param headerView
     */
    public void setHeaderView(RefreshHeader headerView){
        refreshLayout.setRefreshHeader(headerView);
    }

    /**
     * 设置底部布局
     * @param footerView
     */
    public void setFooterView(RefreshFooter footerView){
        refreshLayout.setRefreshFooter(footerView);
    }

    protected abstract BaseAdapter getListAdapter();

    /**
     * 请求数据
     */
    protected abstract void requestData();

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public void setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }
}
