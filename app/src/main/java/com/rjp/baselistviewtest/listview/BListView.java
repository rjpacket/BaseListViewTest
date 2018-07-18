package com.rjp.baselistviewtest.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.BaseAdapter;

import com.rjp.baselistviewtest.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

/**
 * author : Gimpo create on 2018/5/24 10:51
 * email  : jimbo922@163.com
 */
public class BListView extends CustomListView<String> {
    public BListView(Context context) {
        super(context);
    }

    public BListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected BaseAdapter getListAdapter() {
        return new CommonAdapter<String>(mContext, R.layout.item_list_view, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        };
    }

    @Override
    protected boolean isFirstPage() {
        return false;
    }

    @Override
    protected void resetFirstPage() {

    }

    @Override
    protected void requestData() {
        mDatas.add("1");
        mDatas.add("2");
        mDatas.add("3");
        mDatas.add("4");
        notifyData();
        refreshLayout.finishLoadMore();
    }
}
