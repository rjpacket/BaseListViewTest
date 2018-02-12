package com.rjp.baselistviewtest.okhttp;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONUtil {

    public static <T> T getData(String key, String jsonString, Class<T> cls) {
        return JSON.parseObject(getJSONString(key, jsonString), cls);
    }

    public static <T> T getData(String jsonString, Class<T> cls) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return JSONObject.parseObject(jsonString, cls);
        } catch (Exception e) {
            return null;
        }
    }

    //    public static <T> List<T> getList(Object obj, Class<T> clszz) {
    //        getList()
    //
    //        return getList(jsonString, clszz);
    //    }

    public static <T> List<T> getList(String jsonString, Class<T> clszz) {
        return JSONArray.parseArray(jsonString, clszz);
    }

    public static <T> List<T> getList(String key, String jsonString, Class<T> cls) {
        return JSONArray.parseArray(getJSONString(key, jsonString), cls);
    }

    public static List<String> getList(String key, String jsonString) {
        return getListString(getJSONString(key, jsonString));
    }

    public static List<Map<String, Object>> getListMap(String key, String jsonString) {
        return getListMap(getJSONString(key, jsonString));
    }

    public static String getJSONString(String key, String jsonStringString) {
        return JSONObject.parseObject(jsonStringString).getString(key);
    }

    public static <T> Map<String, List<T>> getKeyMap(String successResult, Class<T> clszz) {
        Map<String, List<T>> map = new HashMap<String, List<T>>();
        try {
            JSONObject userJsonObj = JSONObject.parseObject(successResult);
            Set<String> setString = userJsonObj.keySet();
            for (String key : setString) {
                map.put(key, getList(key, successResult, clszz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static <T> Map<String, List<T>> getKeyMap1(String successResult, Class<T> clszz) {
        Map<String, List<T>> map = new HashMap<>();
        try {
            JSONObject userJsonObj = JSONObject.parseObject(successResult);
            Set<String> setString = userJsonObj.keySet();
            for (String key : setString) {
                map.put(key, getList(userJsonObj.getString(key), clszz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static <T> List<T> getListFromKey(String successResult, Class<T> clszz) {
        List<T> list = new ArrayList<T>();
        try {
            JSONObject userJsonObj = JSONObject.parseObject(successResult);
            Set<String> setString = userJsonObj.keySet();
            for (String key : setString) {
                list.addAll(getList(key, successResult, clszz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取嵌套的list
     *
     * @param successResult
     * @param clszz
     *
     * @return
     */
    public static <T> List<List<T>> getExpandList(String successResult, Class<T> clszz) {
        List<List<T>> list = new ArrayList<List<T>>();
        try {
            JSONArray array = JSONArray.parseArray(successResult);
            if (array != null) {
                for (int i = 0; i < array.size(); i++) {
                    list.add(getList(array.getString(i), clszz));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static <T> List<T> getListFromKeyMap(String successResult, Class<T> clszz) {
        List<T> list = new ArrayList<T>();
        Map<String, List<T>> map = getKeyMap(successResult, clszz);
        if (map != null) {
            for (Map.Entry<String, List<T>> mapSet : map.entrySet()) {
                list.addAll(mapSet.getValue());
            }
        }
        return list;
    }

    public static <T> List<T> paserAllFriends(String ectityResult, Class<T> clszz) {
        List<T> allListUser = new ArrayList<T>();
        JSONObject jsonObject = JSONObject.parseObject(ectityResult);
        Set<String> key = jsonObject.keySet();
        for (String resultKey : key) {
            List<T> listUser = getListFromKeyMap(getJSONString(resultKey, ectityResult), clszz);
            allListUser.addAll(listUser);
        }
        return allListUser;
    }

    public static <T> Map<String, T> getMap(String jsonString, Class<T> clszz, String... keys) {
        Map<String, T> map = new HashMap<String, T>();
        JSONObject jObject = JSONObject.parseObject(jsonString);
        for (String key : keys) {
            map.put(key, jObject.getObject(key, clszz));
        }
        return map;
    }


    public static LinkedHashMap<String, Object> getLinkedMap(Map<String, Object> map, String key) {
        Object o = map.get(key);
        if (map == null || TextUtils.isEmpty(key) || o == null) {
            return null;
        }

        if (o instanceof  org.json.JSONObject) {
            return getLinkedMap((org.json.JSONObject) o);
        } else {
            return getLinkedMap((JSONObject) o);
        }
    }

    public static LinkedHashMap<String, Object> getLinkedMap(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }

        Set<String> set = jsonObject.keySet();
        Iterator<String> keyIter = set.iterator();
        String key;
        Object value;
        LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();
        while (keyIter.hasNext()) {
            key = keyIter.next();
            value = jsonObject.get(key);
            valueMap.put(key, value);
        }
        return valueMap;
    }

    public static LinkedHashMap<String, Object> getLinkedMap(org.json.JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }

        try {
            @SuppressWarnings("unchecked") Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LinkedHashMap<String, Object> getLinkedMap(String jsonString) {
        org.json.JSONObject jsonObject;
        try {
            jsonObject = new org.json.JSONObject(jsonString);
            @SuppressWarnings("unchecked") Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> getMap(Map<String, Object> map, String key) {
        if (map == null || TextUtils.isEmpty(key)) {
            return null;
        }

        return getMap(map.get(key));
    }

    public static Map<String, Object> getMap(Object data) {
        if (data == null || TextUtils.isEmpty(data.toString())) {
            return null;
        }
        return (Map<String, Object>) data;
    }


    public static Map<String, Object> getMap(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        org.json.JSONObject jsonObject;
        try {
            jsonObject = new org.json.JSONObject(jsonString);
            @SuppressWarnings("unchecked") Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Map<String, Object>> getListMap(Map<String, Object> data, String key) {
        if (data == null || TextUtils.isEmpty(key) || data.get(key) == null) {
            return null;
        }

        return getListMap((String) data.get(key));
    }


    /**
     * 把json 转换为 ArrayList 形式
     */
    public static List<Map<String, Object>> getListMap(String jsonString) {
        List<Map<String, Object>> list = null;
        try {

            org.json.JSONArray jsonArray = new org.json.JSONArray(jsonString);
            String jsonObject;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getString(i);
                list.add(getMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, Object>> getListMap(JSONArray jsonArray) {
        List<Map<String, Object>> list = null;
        try {
            Object obj;
            list = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                obj = jsonArray.get(i);
                list.add((Map<String, Object>) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, Object>> getListMap(Object data, String key) {
        if (data == null || TextUtils.isEmpty(key)) {
            return null;
        }
        return getListMap(((JSONObject) data).get(key));
    }

    public static List<Map<String, Object>> getListMap(Object data) {
        if (data == null) {
            return null;
        }

        return (List<Map<String, Object>>) data;
    }

    /**
     * 把json 转换为 ArrayList 形式
     *
     * @return
     */
    public static List<String> getListString(String jsonString) {
        List<String> list = null;
        try {
            org.json.JSONArray jsonArray = new org.json.JSONArray(jsonString);
            String jsonObject;
            list = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getString(i);
                list.add(jsonObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
