package com.linkhand.lajiaoshipin.utils;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.linkhand.lajiaoshipin.R;
import com.linkhand.lajiaoshipin.base.MyApplication;

public class ImageUtils {

    public static void setImage(ImageView imageView, String path) {
        Glide.with(MyApplication.getInst())
                .load(path)
                .into(imageView);
    }

    public static void setCircleImage(final ImageView imageView, String path) {
        Glide.with(MyApplication.getInst())
                .load(path)
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(MyApplication.getInst().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

}
