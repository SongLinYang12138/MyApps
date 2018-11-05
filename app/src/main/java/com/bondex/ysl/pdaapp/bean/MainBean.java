package com.bondex.ysl.pdaapp.bean;

public class MainBean {

    private String title;
    private  int resourceId;
    private String name;



    public MainBean(){

    }
    public MainBean(String title,String name,int resourceId){

        this.title = title;
        this.name = name;
        this.resourceId = resourceId;

    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }


    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
