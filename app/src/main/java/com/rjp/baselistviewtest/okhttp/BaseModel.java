package com.rjp.baselistviewtest.okhttp;

import java.util.List;

/**
 * @Author：RJP on 2017/5/4 20:38
 */

public class BaseModel {
    private String msg;

    private Object data;

    private List<Object> popup;

    private String errorcode;

    private String servertime;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }

    public List<Object> getPopup() {
        return popup;
    }

    public void setPopup(List<Object> popup) {
        this.popup = popup;
    }
}
