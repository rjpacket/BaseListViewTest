package com.rjp.baselistviewtest.listview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rjp.baselistviewtest.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.scwang.smartrefresh.layout.header.ClassicsHeader.REFRESH_HEADER_LOADING;
import static com.scwang.smartrefresh.layout.header.ClassicsHeader.REFRESH_HEADER_PULLDOWN;
import static com.scwang.smartrefresh.layout.header.ClassicsHeader.REFRESH_HEADER_REFRESHING;
import static com.scwang.smartrefresh.layout.header.ClassicsHeader.REFRESH_HEADER_RELEASE;
import static com.scwang.smartrefresh.layout.header.ClassicsHeader.REFRESH_HEADER_SECOND_FLOOR;

/**
 * author : Gimpo create on 2018/2/12 13:48
 * email  : jimbo922@163.com
 */

public class DefaultHeader extends LinearLayout implements RefreshHeader {

    private ImageView ivImage;
    private AnimationDrawable mBallAnim;
    private TextView tvMsg;
    private TextView tvTime;
    private long updateTime;

    public DefaultHeader(Context context) {
        this(context, null);
    }

    public DefaultHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_default_head_view, this);
        ivImage = (ImageView) findViewById(R.id.iv_arrow);
        ivImage.setImageResource(R.drawable.ball_anim);
        mBallAnim = (AnimationDrawable) ivImage.getDrawable();

        tvMsg = (TextView) findViewById(R.id.tv_msg);
        tvTime = (TextView) findViewById(R.id.tv_time);
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if(mBallAnim != null) {
            mBallAnim.stop();
        }
        return 0;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                tvMsg.setText(REFRESH_HEADER_PULLDOWN);
                if(mBallAnim != null) {
                    mBallAnim.start();
                }
                if(updateTime != 0){
                    tvTime.setText(parseTime(updateTime));
                    tvTime.setVisibility(VISIBLE);
                }else{
                    tvTime.setVisibility(GONE);
                }
                break;
            case Refreshing:
            case RefreshReleased:
                tvMsg.setText(REFRESH_HEADER_REFRESHING);
                updateTime = System.currentTimeMillis();
                break;
            case ReleaseToRefresh:
                tvMsg.setText(REFRESH_HEADER_RELEASE);
                break;
            case ReleaseToTwoLevel:
                tvMsg.setText(REFRESH_HEADER_SECOND_FLOOR);
                break;
            case Loading:
                tvMsg.setText(REFRESH_HEADER_LOADING);
                break;
        }
    }

    public String parseTime(long time) {
        StringBuffer sb = new StringBuffer();
        sb.append("上次刷新：");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        String updateTime = sdf.format(new Date(time));
        String today = sdf.format(new Date());
        String[] date1 = updateTime.split(" ");
        String[] date2 = today.split(" ");
        if (date1[0].equals(date2[0])) {
            sb.append("今天 " + date1[1]);
        } else {
            sb.append(updateTime);
        }
        return sb.toString();
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    //---------------------------------------------------------------------------------------------------------

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
