package com.example.smarthome.Utils;

import com.example.smarthome.Model.HomeTypeModel;

public class OnClickItem {
    public HomeTypeModel homeTypeModel;
    public int pos;
    public String id;

    public OnClickItem(HomeTypeModel homeTypeModel, int pos, String id) {
        this.homeTypeModel = homeTypeModel;
        this.pos = pos;
        this.id=id;
    }

    public HomeTypeModel getHomeTypeModel() {
        return homeTypeModel;
    }

    public void setHomeTypeModel(HomeTypeModel homeTypeModel) {
        this.homeTypeModel = homeTypeModel;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OnClickItem(HomeTypeModel homeTypeModel) {
    }
}
