package com.testing.skywell;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.vk.sdk.VKSdk;

/**
 * Created by Maxim on 28.10.2016.
 */

public class SkywellApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(this);
        ActiveAndroid.initialize(this);
    }
}
