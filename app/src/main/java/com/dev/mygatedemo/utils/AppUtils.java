package com.dev.mygatedemo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.dev.mygatedemo.Constants;
import com.dev.mygatedemo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class AppUtils {
    private static AppUtils instance;

    private AppUtils() {

    }

    public static AppUtils getInstance() {
        if (instance == null) {
            synchronized (AppUtils.class) {
                if (instance == null) {
                    instance = new AppUtils();
                }
            }
        }
        return instance;
    }

    public static Object getValueFromData(Object data) {
        return (data == null) ? "" : capitalizeString(data.toString());
    }

    public static String capitalizeString(String s) {
        if (s != null && s.length() > 0) {
            return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
        } else
            return s;
    }

    public static void createAndShowDialog(Context context) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.permission_camera_never_ask_again)
                .setPositiveButton(R.string.button_allow, (dialog, button) -> openAppPermissionSettings(context))
                .setNegativeButton(R.string.button_deny, (DialogInterface dialog, int button) -> {
                    dialog.cancel();
                    System.exit(0);
                })
                .show();
    }

    private static void openAppPermissionSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", "com.dev.gati", null);
        intent.setData(uri);
        context.startActivity(intent);
    }


    public static File getStorageFile() {
        File directory = null;
        //if there is no SD card, create new directory objects to make directory on device
        if (Environment.getExternalStorageState() == null) {
            //create new file directory object
            directory = new File(Environment.getDataDirectory().toString());
            if (!directory.exists()) {
                directory.mkdir();
            }
            // if phone DOES have sd card
        } else if (Environment.getExternalStorageState() != null) {
            // search for directory on SD card
            directory = new File(Environment.getExternalStorageDirectory().toString());
            if (!directory.exists()) {
                directory.mkdir();
            }
        }// end of SD card checking
        return directory;
    }

    public static void resizeCaptureImage(final Uri mImageUri, final File photo, final Context context) {
        context.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = context.getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
            compressBitmapInFile(bitmap, photo);
        } catch (Exception e) {

        }
    }

    private static void compressBitmapInFile(final Bitmap bitmap, File photo) {
        try {
            if (photo != null && photo.exists()) {
                photo.delete();
            }
            FileOutputStream fos = new FileOutputStream(photo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, fos);
        } catch (Exception e) {
        }
    }


}