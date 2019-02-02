package com.dev.mygatedemo.model;

import io.realm.RealmModel;
import io.realm.RealmObject;

public class UserModel extends RealmObject {

    // Number from witch the sms was send

    private String img;
    // SMS text body
    private String name, passcode;

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
}
