package com.dev.mygatedemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private Uri photoURI;
    private String imageFilePath;
    private List<UserModel> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RealmController.getInstance().initRealm(pContext);

        getUsers();
    }

    private void getUsers() {
        users = RealmController.getInstance().getAllUsers();
        if (users != null && users.size() > 0) {
            pAppLogs.d(pTAG, "Count : " + users.size());
            pAppLogs.d(pTAG, "Count adapter : " + adapter);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            } else {
                adapter = new UserAdapter(pContext, users);
            }
            userRecyclerView.addItemDecoration(new DividerItemDecoration(pContext, DividerItemDecoration.VERTICAL));
            userRecyclerView.setLayoutManager(new LinearLayoutManager(pContext));
            userRecyclerView.setAdapter(adapter);
        }

    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void canGo() {
        //Permissions ok, go to next flow step.
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "Inside onActivityResult : " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            Log.d("TAG", "Inside onActivityResult 1 : " + data);
            Log.d("TAG", "Inside onActivityResult 2 : " + data.getExtras());
            if (data != null && data.getExtras() != null) {
                try {
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    Log.d("TAG", "Inside image 1 : " + imageBitmap);
                    Log.d("TAG", "Inside image 2 : " + photoURI);
                    Log.d("TAG", "Inside image 3 : " + imageFilePath);
                    UserModel model = new UserModel();
                    model.setName("User");
                    Random rnd = new Random();
                    int number = rnd.nextInt(999999);
                    model.setPasscode(String.valueOf(number));
                    model.setImg(String.valueOf(photoURI));
                    RealmController.getInstance().saveUser(model);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getUsers();
                        }
                    });
                } catch (Exception e) {
                    Log.d("tag", "error : " + e.getMessage());
                }
            }
        }
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
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, "com.dev.mygatedemo.provider", photoFile);
                pictureIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE, photoURI);
                startActivityForResult(pictureIntent, Constants.KEY_CAPTURE_IMAGE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListActivityPermissionsDispatcher.canGoWithPermissionCheck(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        ListActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


}
