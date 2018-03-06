package com.rjp.baselistview.pinned;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class PinnedHeaderExpandableAdapter<T extends PinnedModel, C> extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {
    protected List<T> data;
    protected LayoutInflater mInflater;
    protected Context mContext;
    private PinnedHeaderExpandableListView listView;

    public PinnedHeaderExpandableAdapter(Context context, List<T> data, PinnedHeaderExpandableListView listView) {
        this.data = new ArrayList<>();
        if (null != data && !data.isEmpty()) {
            this.data.addAll(data);
        }
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.listView = listView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return initGroupView(groupPosition, isExpanded, convertView, parent);
    }

    /**
     * 处理list 头布局
     */
    protected abstract View initGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return initChildView(getChildType(groupPosition, childPosition), groupPosition, childPosition, convertView, parent);
    }

    /**
     * 处理list Item布局
     */
    public abstract View initChildView(int type, int groupPosition, int childPosition, View convertView, ViewGroup parent);

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getGroupCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < 0) {
            return 0;
        }
        T group = data != null ? data.get(groupPosition) : null;
        if (group != null && group.children != null) {
            return group.children.size();
        }
        return 0;
    }

    @Override
    public T getGroup(int groupPosition) {
        return data != null ? data.get(groupPosition) : null;
    }

    @Override
    public C getChild(int groupPosition, int childPosition) {
        T info = data != null ? data.get(groupPosition) : null;
        if (info != null) {
            List<C> items = info.children;
            if (items != null) {
                return items.get(childPosition);
            }
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1 && !listView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    private SparseIntArray groupStatusMap = new SparseIntArray();

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
