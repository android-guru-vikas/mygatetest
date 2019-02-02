package com.dev.mygatedemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.dev.mygatedemo.Constants;
import com.dev.mygatedemo.R;
import com.dev.mygatedemo.UserAdapter;
import com.dev.mygatedemo.model.UserModel;
import com.dev.mygatedemo.realmdatabase.RealmController;
import com.dev.mygatedemo.utils.AppUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ListActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.userRecyclerView)
    RecyclerView userRecyclerView;
    @BindView(R.id.addUserFab)
    FloatingActionButton addUserFab;
    private UserAdapter adapter;
    private String pictureFilePath;
    private List<UserModel> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RealmController.getInstance().initRealm(pContext);

        getUsers();
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void canGo() {
        //Permissions ok, go to next flow step.
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA})
    void deniedPermissionsFlow() {
        pAppLogs.d(pTAG, "Denied");
        ListActivityPermissionsDispatcher.canGoWithPermissionCheck(this);
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA})
    void onNeverAskAgain() {
        AppUtils.createAndShowDialog(pContext);
    }

    @OnClick(R.id.addUserFab)
    void addUser() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File pictureFile = null;
        try {
            pictureFile = getPictureFile();
        } catch (IOException ex) {

            return;
        }
        if (pictureFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this, pContext.getPackageName() + ".provider", pictureFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(cameraIntent, Constants.KEY_CAPTURE_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                UserModel model = new UserModel();
                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                model.setPasscode(String.valueOf(number));
                model.setImg(pictureFilePath);
                RealmController.getInstance().saveUser(model);
                users.add(model);
                if (adapter != null) {
                    adapter.notifyItemInserted(users.size());
                } else {
                    getUsers();
                }
            } catch (Exception e) {
                pAppLogs.d(pTAG, "Error : " + e.getMessage());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListActivityPermissionsDispatcher.canGoWithPermissionCheck(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ListActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "MyGate" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile, ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }

    private void getUsers() {
        try {
            users = RealmController.getInstance().getAllUsers();
            if (users != null && users.size() > 0) {
                adapter = new UserAdapter(pContext, users);
                userRecyclerView.setAdapter(adapter);
                userRecyclerView.addItemDecoration(new DividerItemDecoration(pContext, DividerItemDecoration.VERTICAL));
                userRecyclerView.setLayoutManager(new LinearLayoutManager(pContext));
            }
        } catch (Exception e) {
            pAppLogs.d(pTAG, "Exception : " + e.getMessage());
        }
    }
}
