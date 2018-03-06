package com.rjp.baselistviewtest.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.rjp.baselistview.pinned.PinnedHeaderExpandableAdapter;
import com.rjp.baselistviewtest.bean.ClassRoom;
import com.rjp.baselistviewtest.bean.Student;

import java.util.ArrayList;

/**
 * author : Gimpo create on 2018/3/2 15:35
 * email  : jimbo922@163.com
 */

public class AListView extends CustomPinnedListView<ClassRoom> {
    public AListView(Context context) {
        super(context);
    }

    public AListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected PinnedHeaderExpandableAdapter getPinnedListAdapter() {
        return new PinnedListAdapter(mContext, mDatas, listView);
    }

    @Override
    public void requestData() {
        mDatas.add(addStu(3));
        mDatas.add(addStu(4));
        mDatas.add(addStu(3));
        mDatas.add(addStu(6));
        notifyDataSetChanged();
        refreshLayout.finishRefresh();
    }

    private ClassRoom addStu(int i) {
        ArrayList<Student> children = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            children.add(new Student());
        }
        ClassRoom classRoom = new ClassRoom(children);
        return classRoom;
    }
}
