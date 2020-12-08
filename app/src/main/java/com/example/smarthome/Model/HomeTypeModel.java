package com.example.smarthome.Model;

import java.io.Serializable;

public class HomeTypeModel implements Serializable {
    public int image;
    public String nameRoom;

    public HomeTypeModel(int image, String nameRoom) {
        this.image = image;
        this.nameRoom = nameRoom;
    }
}
