package com.testing.skywell.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Maxim on 11.05.2016.
 */
public class FontManager {

    public enum Fonts {
        BUNGASAI ("fonts/Bungasai.ttf");

        private String fontName;

        Fonts(String data) {
            this.fontName = data;
        }

        @Override
        public String toString() {
            return fontName;
        }
    }

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();
    private static FontManager mFontManager;

    public static FontManager getInstance() {
        if (mFontManager == null) {
            mFontManager = new FontManager();
        }

        return mFontManager;
    }

    public Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}
