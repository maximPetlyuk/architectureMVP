package com.testing.skywell.helpers.navigation;

import android.content.Context;

import com.testing.skywell.main.MainActivity;

/**
 * Created by Maxim on 28.10.2016.
 */

public class Navigator {

    private static Navigator mInstance;

    private Navigator() {
    }

    public static Navigator getInstance() {
        if (mInstance == null) {
            mInstance = new Navigator();
        }

        return mInstance;
    }

    public void navigateToMainScreen(Context context) {
        if (context != null) {
            context.startActivity(MainActivity.newInstance(context));
        }
    }
}
