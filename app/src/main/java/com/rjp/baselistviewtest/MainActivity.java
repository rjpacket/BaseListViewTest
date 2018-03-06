package com.rjp.baselistviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.rjp.baselistviewtest.listview.AListView;

public class MainActivity extends Activity {

    private AListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        OpenPrizeListView layout = (OpenPrizeListView) findViewById(R.id.open_list_view);
//        layout.requestData();
        listView = (AListView) findViewById(R.id.alist_view);
        listView.setHeaderView(R.layout.layout_group);

        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.requestData();
            }
        });
    }
}
