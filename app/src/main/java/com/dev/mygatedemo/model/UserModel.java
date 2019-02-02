package com.dev.mygatedemo.model;

import io.realm.RealmObject;

public class UserModel extends RealmObject {

    public UserModel() {
    }

    private String name, passcode, img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", passcode='" + passcode + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
