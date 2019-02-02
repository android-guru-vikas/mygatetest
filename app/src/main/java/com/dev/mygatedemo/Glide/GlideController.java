package com.dev.mygatedemo.Glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.dev.mygatedemo.R;

import java.io.IOException;


public class GlideController {
    public static void loadImage(ImageView imageView, Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            GlideApp.with(imageView.getContext())
                    .load(bitmap)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
