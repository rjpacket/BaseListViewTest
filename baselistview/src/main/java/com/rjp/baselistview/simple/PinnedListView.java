package com.rjp.baselistview.simple;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.rjp.baselistview.BaseListView;
import com.rjp.baselistview.R;
import com.rjp.baselistview.pinned.PinnedHeaderExpandableAdapter;
import com.rjp.baselistview.pinned.PinnedHeaderExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Gimpo create on 2018/3/2 14:25
 * email  : jimbo922@163.com
 */

public abstract class PinnedListView<T> extends BaseListView<T> {
    public List<T> mDatas = new ArrayList<>();
    public PinnedHeaderExpandableListView listView;
    private PinnedHeaderExpandableAdapter listAdapter;

    public PinnedListView(Context context) {
        super(context);
    }

    public PinnedListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void inflateLayout() {
        layoutInflater.inflate(R.layout.layout_pinned_list_view, this);
    }

    @Override
    protected void initChildView() {
        listView = (PinnedHeaderExpandableListView) findViewById(R.id.list_view);
        listAdapter = getPinnedListAdapter();
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    public void setHeaderView(int layoutId){
        listView.setHeaderView(layoutInflater.inflate(layoutId, listView, false));
    }

    public void notifyDataSetChanged() {
        listAdapter = getPinnedListAdapter();
        listView.setAdapter(listAdapter);
    }

    protected abstract PinnedHeaderExpandableAdapter getPinnedListAdapter();
}
