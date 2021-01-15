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

    public OnClickItem(HomeTypeModel homeTypeModel) {
    }
}
