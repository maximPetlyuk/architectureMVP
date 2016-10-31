package com.testing.skywell.helpers;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class PicassoImageLoader {

    public static void loadImageAndCenterInside(Context context, String url, ImageView imgv) {
        Picasso.with(context).load(url).fit().centerInside().into(imgv);
    }

    public static void loadImage(Context context, String url, ImageView imgv) {
        Picasso.with(context).load(url).fit().centerCrop().into(imgv);
    }

    public static void loadImage(Context context, String url, ImageView imgv, int placeholderResId, int width, int height) {
        Picasso.with(context).load(url).placeholder(placeholderResId).error(placeholderResId).resize(width, height).into(imgv);
    }

    public static void loadImage(Context context, int resID, ImageView imgv) {
        Picasso.with(context).load(resID).fit().into(imgv);
    }

    public static void loadImage(Context context, String url, ImageView imgv, int placeholder) {
        Picasso.with(context).load(url).placeholder(placeholder).fit().skipMemoryCache().into(imgv);
    }

    public static void loadImage(Context context, Uri uri, ImageView imgv) {
        Picasso.with(context).load(uri).fit().centerCrop().into(imgv);
    }

    public static void loadImage(Context context, Uri uri, ImageView imgv, int resId) {
        Picasso.with(context).load(uri).placeholder(resId).fit().centerCrop().into(imgv);
    }

    public static void loadImage(Context context, String url, ImageView imgv, Callback callback) {
        Picasso.with(context).load(url).fit().centerCrop().into(imgv, callback);
    }

    public static void loadImageMultiple(final Context context, Uri uri, final Uri uri2, final ImageView imgv, final int resId) {

        Picasso.with(context)
                .load(uri)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imgv, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(uri2)
                                .placeholder(resId)
                                .into(imgv);
                    }
                });
    }
}
