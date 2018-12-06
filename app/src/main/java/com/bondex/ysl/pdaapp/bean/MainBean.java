package com.bondex.ysl.pdaapp.bean;

import java.util.ArrayList;

public class MainBean {

    private String title;

    private ArrayList<MenuBean> menuList = new ArrayList<>();

    public MainBean(String title, ArrayList<MenuBean> menuList) {
        this.title = title;
        this.menuList = menuList;
    }

    public MainBean(){

    }

    public ArrayList<MenuBean> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuBean> menuList) {
        this.menuList = menuList;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }
}
