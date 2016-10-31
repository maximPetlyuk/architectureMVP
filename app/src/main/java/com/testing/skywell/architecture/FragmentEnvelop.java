package com.testing.skywell.architecture;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentEnvelop{
    private String FRAGMENT_ENVELOP_CLASS = "FRAGMENT_ENVELOP_CLASS";
    private String FRAGMENT_ENVELOP_BUNDLE = "FRAGMENT_ENVELOP_BUNDLE";

    private Bundle mBundle;
    private String mString;

    public static FragmentEnvelop getInstance(Bundle bundle){
        return new FragmentEnvelop(bundle);
    }

    public static FragmentEnvelop getInstance(Fragment fragment){
        return new FragmentEnvelop(fragment);
    }

    public FragmentEnvelop(Fragment fragment){
        mString = fragment.getClass().getName();
        mBundle = fragment.getArguments();
    }

    public FragmentEnvelop(Bundle bundle){
        mString = bundle.getString(FRAGMENT_ENVELOP_CLASS);
        mBundle = bundle.getBundle(FRAGMENT_ENVELOP_BUNDLE);
    }

    public Fragment getFragment(){
        Fragment fragment = null;
        try {
            Class<?> clazz = Class.forName(mString);
            fragment = (Fragment) clazz.newInstance();
        } catch (Exception e) {
            fragment = new Fragment();
            e.printStackTrace();
        }
        fragment.setArguments(mBundle);
        return fragment;
    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putBundle(FRAGMENT_ENVELOP_BUNDLE, mBundle);
        bundle.putString(FRAGMENT_ENVELOP_CLASS, mString);
        return bundle;
    }

}
