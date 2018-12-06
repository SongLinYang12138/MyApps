package com.bondex.ysl.pdaapp.bean;

/**
 * date: 2018/11/7
 * Author: ysl
 * description:
 */
public class MenuBean {

    private String name;
    private String flag;

    public MenuBean(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public String getFlag() {
        return flag == null ? "" : flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? "" : flag;
    }
}
