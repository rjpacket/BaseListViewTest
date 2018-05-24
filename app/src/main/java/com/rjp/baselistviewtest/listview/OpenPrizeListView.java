package com.rjp.baselistviewtest.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.BaseAdapter;

import com.rjp.baselistviewtest.OpenPrizeModel;
import com.rjp.baselistviewtest.R;
import com.rjp.baselistviewtest.okhttp.BaseModel;
import com.rjp.baselistviewtest.okhttp.NetUtils;
import com.rjp.baselistviewtest.okhttp.Params;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Map;

/**
 * author : Gimpo create on 2018/2/11 16:45
 * email  : jimbo922@163.com
 */

public class OpenPrizeListView extends CustomListView<OpenPrizeModel> {


    public OpenPrizeListView(Context context) {
        super(context);
    }

    public OpenPrizeListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void resetFirstPage() {
        mPage = 1;
        mPageSize = 6;
    }

    @Override
    protected boolean isFirstPage() {
        return mPage == 1;
    }

    @Override
    protected Class<OpenPrizeModel> getModelType() {
        return OpenPrizeModel.class;
    }

    @Override
    protected BaseAdapter getListAdapter() {
        return new CommonAdapter<OpenPrizeModel>(mContext, R.layout.item_list_view, mDatas) {
            @Override
            protected void convert(ViewHolder viewHolder, OpenPrizeModel item, int position) {

            }
        };
    }

    @Override
    public void requestData() {
        Params params = new Params();
        params.putt("lotteryType", "1001");
        params.putt("pageIndex", mPage);
        params.putt("maxResult", mPageSize);
        Log.d("----params---->", params.toString());
        NetUtils.getInstance().request1(mContext, "wincodeHistory", params, new NetUtils.NetCallBack1() {
            @Override
            public void onSuccess(String code, BaseModel result) {
                Map<String, Object> map = (Map<String, Object>) result.getData();
                dealSuccessData(map.get("wincodes"));
            }
        });
    }
}
