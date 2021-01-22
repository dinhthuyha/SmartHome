package com.example.smarthome.Model;

public class FutureAndCodeModel {
    String name;
    FirebaseModel model;

    public FutureAndCodeModel(String name, FirebaseModel model) {
        this.name = name;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public FirebaseModel getModel() {
        return model;
    }
}
