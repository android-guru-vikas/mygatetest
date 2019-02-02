package com.dev.mygatedemo;

import android.content.Context;

import io.realm.Realm;

public class RealmHandler {
    public static Realm realm;

    public static void initializeRealm(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }
}