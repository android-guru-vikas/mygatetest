package com.dev.mygatedemo.Glide;

import android.content.Context;
import android.widget.ImageView;


public class GlideController {}

//    public static void displayCircularImage(final Context context, String url, final ImageView imageView) {}
//        Glide.with(context).asBitmap().load(url).placeholder(R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).thumbnail(0.1f).diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                imageView.setImageDrawable(circularBitmapDrawable);
//            }
//        });
//    }

//    private static Bitmap getBitMap(Bitmap b, int reqWidth, int reqHeight) {
//        Matrix m = new Matrix();
//        m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
//        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
//    }
//}
