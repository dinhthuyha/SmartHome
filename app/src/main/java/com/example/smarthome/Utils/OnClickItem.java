package com.example.smarthome.Utils;

import com.example.smarthome.Model.HomeTypeModel;

public class OnClickItem {
    public HomeTypeModel homeTypeModel;
    public int pos;

    public OnClickItem(HomeTypeModel homeTypeModel, int pos) {
        this.homeTypeModel = homeTypeModel;
        this.pos = pos;
    }

    public OnClickItem(HomeTypeModel homeTypeModel) {
    }
}
