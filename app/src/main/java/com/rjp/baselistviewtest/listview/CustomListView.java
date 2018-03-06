package com.rjp.baselistviewtest.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.rjp.baselistview.simple.SimpleListView;
import com.rjp.baselistviewtest.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;

import java.util.List;

/**
 * author : Gimpo create on 2018/2/26 10:45
 * email  : jimbo922@163.com
 */

public abstract class CustomListView<T> extends SimpleListView<T> {

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected RefreshFooter getRefreshFooter() {
        return null;
    }

    @Override
    protected RefreshHeader getRefreshHeader() {
        return new DefaultHeader(mContext);
    }

    @Override
    protected View getEmptyView() {
        return LayoutInflater.from(mContext).inflate(R.layout.layout_empty, null);
    }

    @Override
    protected void filterData(List<T> mDatas) {

    }

    /**
     * 新增 listView 的点击回调
     * @param parent parent
     * @param view view
     * @param position position
     * @param id id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void pageNext() {
        mPage ++;
    }

    @Override
    protected boolean hasMoreData(List<T> list) {
        return list.size() >= mPageSize;
    }
}
