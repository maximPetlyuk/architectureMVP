package com.testing.skywell.architecture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

public class BaseActivity extends AppCompatActivity implements FragmentInterface {

    public static final String BASE_ACTIVITY_TAB_MANAGER = "BASE_ACTIVITY_TAB_MANAGER";
    private TabManager mTabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTabManager = new TabManager(this);
        mTabManager.restoreState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public synchronized void doFragment(FragmentOperation operation) {
        if (operation.getTab() == null) {
            TabGroup group = getTabManager().get();
            operation.setTab(group.getActiveTab());
        }
        TabGroup group = getTabManager().get(operation.getTab());
        Stack<Fragment> stack = group.getFragmentMap().get(operation.getTab());

        if (operation.isFailsafe()) {
            if (getActivityContext() == null) {
                return;
            }
        }

        switch (operation.getAction()) {
            case SET:
                stack.removeAllElements();
                changeBundle(operation.getFragment(), operation.getBundle());
                stack.add(operation.getFragment());
                break;
            case PUSH:
                changeBundle(operation.getFragment(), operation.getBundle());
                stack.add(operation.getFragment());
                doFragment(operation.show());
                break;
            case POP:
                stack.pop();
                changeBundle(stack.peek(), operation.getBundle());
                doFragment(operation.show());
                break;
            case ADD:
                changeBundle(operation.getFragment(), operation.getBundle());
                stack.add(operation.getFragment());
                break;
            case REPLACE:
                stack.pop();
                changeBundle(operation.getFragment(), operation.getBundle());
                stack.add(operation.getFragment());
                doFragment(operation.show());
                break;
            case REMOVE:
                stack.pop();
                changeBundle(stack.peek(), operation.getBundle());
                break;
            case SHOW:
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (operation.getAnimation() != null) {
                    operation.getAnimation().animate(transaction);
                }
                changeBundle(stack.peek(), operation.getBundle());

                transaction.replace(group.getContainer(), stack.peek());
                transaction.commit();
                group.setActiveFragment(stack.peek());
                group.setActiveTab(operation.getTab());
                break;
            case MODIFY:
                changeBundle(stack.peek(), operation.getBundle());
                break;
            case SET_AND_SHOW:
                stack.removeAllElements();
                changeBundle(operation.getFragment(), operation.getBundle());
                stack.add(operation.getFragment());
                doFragment(operation.show());
        }
    }

    private void changeBundle(Fragment fragment, Bundle bundle) {
        if (fragment == null || bundle == null) {
            return;
        }
        if (fragment.getArguments() == null) {
            return;
        }
        Bundle changes = fragment.getArguments();
        changes.putAll(bundle);
        fragment.setArguments(changes);
    }

    @Override
    public TabManager getTabManager() {
        return mTabManager;
    }

    @Override
    public void onBackPressed() {
        TabGroup group = getTabManager().get();
        Stack<Fragment> stack = group.getFragmentMap().get(group.getActiveTab());
        Fragment activeFragment = group.getActiveFragment();
        FragmentInterface activeInterface = null;
        try {
            activeInterface = (FragmentInterface) activeFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(activeFragment.toString() + " must implement FragmentInterface");
        }
        if (activeInterface.onBack()) {
            if (stack.size() < 2) {
                if (onBack())
                    finish();
            } else {
                doFragment(new FragmentOperation().failsafe().tab(group.getActiveTab()).pop());
            }
        }
    }

    @Override
    public boolean onBack() {
        return true;
    }

    @Override
    public FragmentInterface getActivityContext() {
        TabGroup group = getTabManager().get();
        Fragment activeFragment = group.getActiveFragment();
        FragmentInterface activeInterface = null;
        try {
            activeInterface = (FragmentInterface) activeFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(activeFragment.toString() + " must implement FragmentInterface");
        }
        return activeInterface;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(BASE_ACTIVITY_TAB_MANAGER, getTabManager().getBundle());
    }

}
