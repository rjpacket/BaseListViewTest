package com.rjp.baselistviewtest.okhttp;

/**
 * 类名称：OriginResponse
 * 类描述：首页banner返回数据bean
 *
 * @version: 1.7.3
 * @author: CuiLongFei
 * @time: 2017/5/31 13:04
 */
public class OriginResponse {

    private String status;
    private String msg;
    private String count;
    private String data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
