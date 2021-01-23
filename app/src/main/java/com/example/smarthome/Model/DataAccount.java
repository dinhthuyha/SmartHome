package com.example.smarthome.Model;

public class DataAccount {
    String email;
    String phone;
    String name;

    public DataAccount(String email, String phone, String name) {
        this.email = email;
        this.phone = phone;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public DataAccount() {
    }
}
