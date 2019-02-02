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
        Log.d(TAG, "Inside saveUser : " + model.toString());
        try {
            realm.executeTransactionAsync(realm -> realm.insertOrUpdate(model));
        } catch (Exception e) {
            Log.d(TAG, "Inside save exception : " + e.getMessage());
        }

    }

    public void deleteAll() {
        try {
            realm.executeTransactionAsync(realm -> realm.deleteAll());
        } catch (Exception e) {
            Log.d(TAG, "Inside delete all : " + e.getMessage());
        }
    }

    public List<UserModel> getAllUsers() {
        realm = RealmController.getInstance().getRealm();
        List<UserModel> users = null;
        try {
            RealmResults<UserModel> docketList = realm.where(UserModel.class).findAll();
            users = realm.copyFromRealm(docketList);
        } catch (Exception e) {
            Log.d(TAG, "Inside getAllUsers exception : " + e.getMessage());
        }
        return users;
    }

}
