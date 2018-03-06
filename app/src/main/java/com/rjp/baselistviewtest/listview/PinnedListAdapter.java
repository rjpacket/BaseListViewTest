package com.rjp.baselistviewtest.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rjp.baselistview.pinned.PinnedHeaderExpandableAdapter;
import com.rjp.baselistview.pinned.PinnedHeaderExpandableListView;
import com.rjp.baselistviewtest.R;
import com.rjp.baselistviewtest.bean.ClassRoom;
import com.rjp.baselistviewtest.bean.Student;

import java.util.List;

/**
 * author : Gimpo create on 2018/3/2 15:15
 * email  : jimbo922@163.com
 */

public class PinnedListAdapter extends PinnedHeaderExpandableAdapter<ClassRoom, Student> {
    public PinnedListAdapter(Context context, List<ClassRoom> data, PinnedHeaderExpandableListView listView) {
        super(context, data, listView);
    }

    @Override
    protected View initGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_group, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ClassRoom item = getGroup(groupPosition);
        return convertView;
    }

    public static class ViewHolder {
        public final TextView name;
        public final TextView time;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.name);
            time = (TextView) v.findViewById(R.id.time);
        }
    }

    @Override
    public View initChildView(int type, int groupPosition, int childPosition, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_child, parent, false);
            convertView.setTag(new ChildHolder(convertView));
        }
        ChildHolder holder = (ChildHolder) convertView.getTag();
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "child", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public static class ChildHolder {
        public final ImageView image;
        public final TextView name;

        public ChildHolder(View v) {
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {

    }
}
