package com.rjp.baselistviewtest.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.rjp.baselistview.simple.PinnedListView;
import com.rjp.baselistviewtest.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;

/**
 * author : Gimpo create on 2018/3/2 15:03
 * email  : jimbo922@163.com
 */

public abstract class CustomPinnedListView<T> extends PinnedListView<T> {
    public CustomPinnedListView(Context context) {
        super(context);
    }

    public CustomPinnedListView(Context context, @Nullable AttributeSet attrs) {
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
    protected void resetFirstPage() {

    }

    @Override
    protected View getEmptyView() {
        return LayoutInflater.from(mContext).inflate(R.layout.layout_empty, null);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
