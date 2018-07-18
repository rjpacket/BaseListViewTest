package com.rjp.baselistview.simple;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.rjp.baselistview.BaseListView;
import com.rjp.baselistview.R;

import java.util.List;

/**
 * author : Gimpo create on 2018/3/2 12:01
 * email  : jimbo922@163.com
 */

public abstract class SimpleListView<T> extends BaseListView<T> {
    public ListView listView;
    private BaseAdapter listAdapter;


    public SimpleListView(Context context) {
        super(context);
    }

    public SimpleListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void inflateLayout() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_simple_list_view, this);
    }

    @Override
    protected void initChildView() {
        listView = (ListView) findViewById(R.id.list_view);
        listAdapter = getListAdapter();
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    /**
     * 处理请求到的数据
     * @param list
     */
    public void dealSuccessData(List<T> list){
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
     * 处理失败的数据，主要是结束刷新
     */
    public void dealFailureData(){
        if (isFirstPage()) {
            refreshLayout.finishRefresh();
        }else{
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 刷新数据
     */
    public void notifyData() {
        if(listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
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
     * 获取listview适配器
     * @return BaseAdapter
     */
    protected abstract BaseAdapter getListAdapter();

    /**
     * 筛选数据
     */
    protected abstract void filterData(List<T> mDatas);

    /**
     * 是否还有更多数据
     * @return boolean
     */
    protected abstract boolean hasMoreData(List<T> list);

    /**
     * 页数自增
     */
    protected abstract void pageNext();

    /**
     * 如果是首页请求到的数据
     * @return boolean
     */
    protected abstract boolean isFirstPage();
}
