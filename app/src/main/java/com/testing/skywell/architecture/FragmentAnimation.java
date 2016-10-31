package com.testing.skywell.architecture;

import android.support.annotation.AnimRes;
import android.support.v4.app.FragmentTransaction;

public class FragmentAnimation {
    private int mEnter;
    private int mExit;
    private int mPopEnter;
    private int mPopExit;
    private int mTransit;

    public FragmentAnimation() {
        mEnter = -1;
        mExit = -1;
        mPopEnter = -1;
        mPopExit = -1;
        mTransit = FragmentTransaction.TRANSIT_UNSET;
    }

    public static FragmentAnimation instance() {
        return new FragmentAnimation();
    }

    public static FragmentAnimation instance(@AnimRes int enter, @AnimRes int exit) {
        return new FragmentAnimation(enter, exit);
    }

    public static FragmentAnimation instance(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        return new FragmentAnimation(enter, exit, popEnter, popExit);
    }

    public FragmentAnimation(@AnimRes int enter, @AnimRes int exit) {
        this();
        mEnter = enter;
        mExit = exit;
    }

    public FragmentAnimation(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        this();
        mEnter = enter;
        mExit = exit;
        mPopEnter = popEnter;
        mPopExit = popExit;
    }

    public void animate(FragmentTransaction transaction){
        if(mTransit!= FragmentTransaction.TRANSIT_UNSET){
            transaction.setTransition(mTransit);
        }
        if(mEnter>=0 && mExit>=0 && mPopEnter>=0 && mPopExit>=0){
            transaction.setCustomAnimations(mEnter,mExit,mPopEnter,mPopExit);
        }else if(mEnter>=0 && mExit>=0){
            transaction.setCustomAnimations(mEnter,mExit);
        }
    }

    public int getTransit() {
        return mTransit;
    }

    public void setTransit(int transit) {
        mTransit = transit;
    }

    public int getEnter() {
        return mEnter;
    }

    public void setEnter(int enter) {
        mEnter = enter;
    }

    public int getExit() {
        return mExit;
    }

    public void setExit(int exit) {
        mExit = exit;
    }

    public int getPopEnter() {
        return mPopEnter;
    }

    public void setPopEnter(int popEnter) {
        mPopEnter = popEnter;
    }

    public int getPopExit() {
        return mPopExit;
    }

    public void setPopExit(int popExit) {
        mPopExit = popExit;
    }

}
