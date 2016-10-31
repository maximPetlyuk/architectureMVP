package com.testing.skywell.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Maxim on 28.10.2016.
 */

public class UiUtils {

    public static void showToast(Context context, String text) {
        if (context == null) {
            return;
        }

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
