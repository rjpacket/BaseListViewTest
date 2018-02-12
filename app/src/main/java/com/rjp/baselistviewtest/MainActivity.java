package com.rjp.baselistviewtest;

import android.app.Activity;
import android.os.Bundle;

import com.rjp.baselistviewtest.listview.OpenPrizeListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OpenPrizeListView layout = (OpenPrizeListView) findViewById(R.id.open_list_view);
    }
}
