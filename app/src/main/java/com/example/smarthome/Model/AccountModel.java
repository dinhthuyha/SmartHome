package com.example.smarthome.Model;

public class AccountModel {

    String mail;
    String pass;

    public AccountModel(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }
}
