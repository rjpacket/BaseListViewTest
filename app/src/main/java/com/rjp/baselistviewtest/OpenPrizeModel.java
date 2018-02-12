package com.rjp.baselistviewtest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by RJP on 2016/8/10.
 */
public class OpenPrizeModel implements Serializable {

    /**
     * lotteryType : 2007
     * phase : 16062207
     * wincode : 01,02,03,04,05
     * drawTime : 2016-06-22
     */

    private String lotteryType;
    private String phase;
    private String wincode;
    private String drawTime;

    private List<String> tips;

    public OpenPrizeModel() {
    }

    public OpenPrizeModel(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getWincode() {
        return wincode;
    }

    public void setWincode(String wincode) {
        this.wincode = wincode;
    }

    public String getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }

    public List<String> getTips() {
        return tips;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }
}
