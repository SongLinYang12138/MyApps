package com.bondex.ysl.pdaapp.bean.loginebean;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LoginBean {


    /**
     * userid : wangzhenying
     * username : 王震鹰
     * salt : 54863945
     * userlevel : 9
     * warehouses : [{"no":1,"SUBSYSTEM_ID":"WMS01","SUBSYSTEM_NAME":"成都邦达吉通仓库"}]
     */

    private String userid;
    private String username;
    private String salt;
    private int userlevel;
    private String password;
    private boolean isLogined;

    private ArrayList<WarehousesBean> warehouses;

    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";
    public static final String SALT = "salt";
    public static final String USER_LEVEL = "userlevel";
    public static final String PASSWORD = "password";
    public static final String ISLOGINED = "isLogined";
    public static final String WAREHOUSES = "wareHouses";




    public static final String[] COLUMNS = new String[]{
            USER_ID,
            USER_NAME,
            SALT,
            USER_LEVEL,
            PASSWORD,
            ISLOGINED,
            WAREHOUSES,
    };

    public ContentValues toContentValue() {

        ContentValues values = new ContentValues();

        values.put(USER_ID, getUserid());
        values.put(USER_NAME, getUsername());
        values.put(USER_LEVEL, getUserlevel());
        values.put(SALT, getSalt());
        values.put(PASSWORD, getPassword());
        values.put(ISLOGINED,isLogined+"");

        JSONArray array = new JSONArray();
        Gson gson = new Gson();

        for (WarehousesBean bean: warehouses) {
           String json = gson.toJson(bean);
            try {
                array.put(new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

         values.put(WAREHOUSES,array.toString());

        return values;
    }


    public static LoginBean toLoginBean(Cursor cursor) {

        LoginBean bean = new LoginBean();

        if (cursor == null) return null;
        if (cursor.getCount() == 0) return null;

        cursor.moveToFirst();
        while (cursor.isLast()) {


            String userId = cursor.getString(cursor.getColumnIndex(USER_ID));
            String userNam = cursor.getString(cursor.getColumnIndex(USER_NAME));
            String userLevel = cursor.getString(cursor.getColumnIndex(USER_LEVEL));
            String salt = cursor.getString(cursor.getColumnIndex(SALT));
            String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
            String isLogined = cursor.getString(cursor.getColumnIndex(ISLOGINED));
            String warsehouse = cursor.getString(cursor.getColumnIndex(WAREHOUSES));

            bean.setUserid(userId);
            bean.setUsername(userNam);
            bean.setUserlevel(Integer.valueOf(userLevel));
            bean.setSalt(salt);
            bean.setPassword(password);
            bean.setLogined(Boolean.parseBoolean(isLogined));

            ArrayList<WarehousesBean> beans = new ArrayList<>();

            Type listType = new TypeToken<ArrayList<WarehousesBean>>(){}.getType();
            Gson gson = new Gson();
            ArrayList<WarehousesBean> result = gson.fromJson(warsehouse, listType);

            bean.setWarehouses(result);

            cursor.moveToNext();
        }
        cursor.close();
        return bean;
    }


    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }


    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }

    public ArrayList<WarehousesBean> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(ArrayList<WarehousesBean> warehouses) {
        this.warehouses = warehouses;
    }


}
