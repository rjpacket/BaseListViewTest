package com.rjp.baselistviewtest.okhttp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by afs on 9/29/2015.
 */
public class Params extends JSONObject {

    public void putt(String key, Object value) {
        try {
            put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}