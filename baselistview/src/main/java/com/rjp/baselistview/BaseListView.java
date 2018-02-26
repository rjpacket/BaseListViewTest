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
    public int mPage;
    public int mPageSize;
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
        emptyView = getEmptyView();
        if(emptyView != null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            emptyView.setLayoutParams(params);
            emptyView.setVisibility(GONE);
            addView(emptyView);
        }

        refreshLayout = (SmartRefreshLayout) findViewById(R.id.pull_to_refresh_layout);
        refreshLayout.setDragRate(0.5f); //阻尼
        refreshLayout.setReboundDuration(300); // 回弹时间
        refreshLayout.setRefreshHeader(getRefreshHeader());
        refreshLayout.setEnableOverScrollDrag(false);
        RefreshFooter refreshFooter = getRefreshFooter();
        if(refreshFooter != null) {
            refreshLayout.setRefreshFooter(refreshFooter);
        }else{
            refreshLayout.setEnableLoadMore(false);
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshLayout) {
                resetFirstPage();
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
        resetFirstPage();
    }

    /**
     * 处理请求到的数据
     * @param data
     */
    public void dealSuccessData(Object data){
        if (isFirstPage()) {
            refreshLayout.finishRefresh();
            refreshLayout.setNoMoreData(false);
            mDatas.clear();
        }else{
            refreshLayout.finishLoadMore();
        }
        List<T> list = parseData(data);
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
     * 设置刷新底部布局
     * @return RefreshFooter
     */
    protected abstract RefreshFooter getRefreshFooter();

    /**
     * 设置刷新头部布局
     * @return RefreshHeader
     */
    protected abstract RefreshHeader getRefreshHeader();

    /**
     * 重置刷新页码
     */
    protected abstract void resetFirstPage();

    /**
     * 获取空的页面展示
     * @return View
     */
    protected abstract View getEmptyView();

    /**
     * 获取listview适配器
     * @return BaseAdapter
     */
    protected abstract BaseAdapter getListAdapter();

    /**
     * 请求数据
     */
    protected abstract void requestData();

    /**
     * 筛选数据
     */
    protected abstract void filterData(List<T> mDatas);

    /**
     * 页数自增
     */
    protected abstract void pageNext();

    /**
     * 是否还有更多数据
     * @return boolean
     */
    protected abstract boolean hasMoreData(List<T> list);

    /**
     * 如果是首页请求到的数据
     * @return boolean
     */
    protected abstract boolean isFirstPage();

    /**
     * 转化数据
     * @param data 后台请求数据
     * @return List<T>
     */
    protected abstract List<T> parseData(Object data);
}
