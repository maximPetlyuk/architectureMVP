package com.testing.skywell.architecture;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FragmentOperation {

    public enum OPERATION {SET, PUSH, ADD, REPLACE, REMOVE, SHOW, POP, MODIFY, SET_AND_SHOW};

    private Tab mTab;
    private Fragment mFragment;
    private OPERATION mAction;
    private boolean mFailsafe;
    private Bundle mBundle;
    private FragmentAnimation mAnimation;

    public static FragmentOperation instance() {
        return new FragmentOperation();
    }

    public FragmentOperation tab(Tab tab) {
        setTab(tab);
        return this;
    }

    public FragmentOperation fragment(Fragment fragment) {
        setFragment(fragment);
        return this;
    }

    public FragmentOperation action(OPERATION operation) {
        setAction(operation);
        return this;
    }

    public FragmentOperation failsafe() {
        setFailsafe(true);
        return this;
    }

    public FragmentOperation animation(FragmentAnimation animation) {
        setAnimation(animation);
        return this;
    }

    public FragmentOperation bundle(Bundle bundle) {
        setBundle(bundle);
        return this;
    }

    public FragmentOperation set() {
        return action(OPERATION.SET);
    }

    public FragmentOperation push() {
        return action(OPERATION.PUSH);
    }

    public FragmentOperation add() {
        return action(OPERATION.ADD);
    }

    public FragmentOperation replace() {
        return action(OPERATION.REPLACE);
    }

    public FragmentOperation remove() {
        return action(OPERATION.REMOVE);
    }

    public FragmentOperation show() {
        return action(OPERATION.SHOW);
    }

    public FragmentOperation pop() {
        return action(OPERATION.POP);
    }

    public FragmentOperation modify() {
        return action(OPERATION.MODIFY);
    }

    public FragmentOperation setAndShow() {
        return action(OPERATION.SET_AND_SHOW);
    }

    public Tab getTab() {
        return mTab;
    }

    public void setTab(Tab tab) {
        mTab = tab;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public OPERATION getAction() {
        return mAction;
    }

    public void setAction(OPERATION action) {
        mAction = action;
    }

    public boolean isFailsafe() {
        return mFailsafe;
    }

    public void setFailsafe(boolean failsafe) {
        mFailsafe = failsafe;
    }

    public FragmentAnimation getAnimation() {
        return mAnimation;
    }

    public void setAnimation(FragmentAnimation animation) {
        mAnimation = animation;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }
}
