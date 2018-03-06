package com.rjp.baselistview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * author : Gimpo create on 2018/2/11 11:05
 * email  : jimbo922@163.com
 */

public abstract class BaseListView<T> extends LinearLayout implements AdapterView.OnItemClickListener{
    public Context mContext;
    public SmartRefreshLayout refreshLayout;
    public int mPage;
    public int mPageSize;
    public View emptyView;
    public LayoutInflater layoutInflater;

    public BaseListView(Context context) {
        this(context, null);
    }

    public BaseListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
        initView(context);
    }

    private void initView(Context context) {
        inflateLayout();
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

        resetFirstPage();
        initChildView();
    }

    protected abstract void initChildView();

    protected abstract void inflateLayout();

    /**
     * 获取刷新底部布局
     * @return RefreshFooter
     */
    protected abstract RefreshFooter getRefreshFooter();

    /**
     * 获取刷新头部布局
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
     * 请求数据
     */
    protected abstract void requestData();
}
