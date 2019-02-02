package com.dev.mygatedemo.realmdatabase;

import android.content.Context;
import android.util.Log;

import com.dev.mygatedemo.model.UserModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmController {
    public static final String TAG = "RealmController";
    private static RealmController instance;
    private static Realm realm;

    public void initRealm(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(0).deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
        realm.setAutoRefresh(true);
    }

    public RealmController() {
    }


    public static RealmController getInstance() {
        if (instance == null)
            instance = new RealmController();
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.setAutoRefresh(true);
    }

    public void saveUser(final UserModel model) {
        try {
            realm.beginTransaction();
            realm.copyToRealm(model);
            realm.commitTransaction();
        } catch (Exception e) {
            Log.d(TAG, "Inside save exception : " + e.getMessage());
        }

    }

    public List<UserModel> getAllUsers() {
        List<UserModel> users = null;
        try {
            RealmResults<UserModel> usersList = realm.where(UserModel.class).findAll();
            users = realm.copyFromRealm(usersList);
        } catch (Exception e) {
        }
        return users;
    }
}
