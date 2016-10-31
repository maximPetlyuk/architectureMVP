package com.testing.skywell.architecture;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class TabManager {
    private static final String TAB_MANAGER_SIZE = "TAB_MANAGER_SIZE";

    private Activity mActivity;
    private ArrayList<TabGroup> mTabGroup;
    private boolean mRestore;

    public TabManager() {
        mTabGroup = new ArrayList<TabGroup>();
    }

    public TabManager(Activity activity) {
        this();
        setActivity(activity);
    }

    public TabManager init(TabGroup group) {
        mTabGroup.add(group);
        return this;
    }

    public TabManager add(TabGroup group) {
        remove(group);
        mTabGroup.add(group);
        return this;
    }

    public TabManager remove(TabGroup group) {
        List<TabGroup> old = getFilteredTabGroup(mTabGroup, group);
        mTabGroup.removeAll(old);
        return this;
    }

    public TabGroup get() {
        if (mTabGroup.size() > 1) {
            throw new RuntimeException(String.format("More then one fragment container in Activity '%s' please use get(Tab,int)", mActivity.getClass().getSimpleName()));
        } else if (mTabGroup.size() == 0) {
            throw new RuntimeException(String.format("TabGroup not found in Activity '%s' please add one", mActivity.getClass().getSimpleName()));
        }
        return mTabGroup.get(0);
    }

    public TabGroup get(Tab tab) {
        List<TabGroup> filter = getFilteredTabGroup(mTabGroup, tab);
        if (filter.size() > 1) {
            throw new RuntimeException(String.format("More then one fragment container in Activity '%s' please use get(Activity,Tab,int)", mActivity.getClass().getSimpleName()));
        } else if (filter.size() == 0) {
            throw new RuntimeException(String.format("Tab '%s' not found in Activity '%s' please add one", tab.toString(), mActivity.getClass().getSimpleName()));
        }
        return filter.get(0);
    }

    public TabGroup get(Tab tab, int container) {
        List<TabGroup> filter = getFilteredTabGroup(mTabGroup, tab);
        filter = getFilteredTabGroup(filter, container);
        if (filter.size() > 1) {
            throw new RuntimeException(String.format("Collection is broken !!! More one instance of (Activity, Tab, container)", mActivity.getClass().getSimpleName()));
        } else if (filter.size() == 0) {
            throw new RuntimeException(String.format("Tab '%s' not found in Activity '%s' please add one", tab.toString(), mActivity.getClass().getSimpleName()));
        }
        return filter.get(0);
    }

    public List<TabGroup> getFilteredTabGroup(List<TabGroup> group, TabGroup object) {
        List<TabGroup> filter = getFilteredTabGroup(group, object.getContainer());
        //filter = getFilteredTabGroup(filter, object.getTag());
        return filter;
    }

    public List<TabGroup> getFilteredTabGroup(List<TabGroup> group, Tab tab) {
        List<TabGroup> filterList = new ArrayList<TabGroup>();
        if (null == group) {
            return filterList;
        }
        for (TabGroup object : group) {
            if (object.getFragmentMap().containsKey(tab)) {
                filterList.add(object);
            } else {
                continue;
            }
        }
        return filterList;
    }


    public List<TabGroup> getFilteredTabGroup(List<TabGroup> group, int container) {
        List<TabGroup> filterList = new ArrayList<TabGroup>();
        if (null == group) {
            return filterList;
        }
        for (TabGroup object : group) {
            if (object.getContainer() == container) {
                filterList.add(object);
            } else {
                continue;
            }
        }
        return filterList;
    }

    public List<TabGroup> getFilteredTabGroup(List<TabGroup> group, String tag) {
        List<TabGroup> filterList = new ArrayList<TabGroup>();
        if (null == group) {
            return filterList;
        }
        for (TabGroup object : group) {
            if (object.getTag().equals(tag)) {
                filterList.add(object);
            } else {
                continue;
            }
        }
        return filterList;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_MANAGER_SIZE,mTabGroup.size());
        for(int i=0;i<mTabGroup.size();i++){
            bundle.putBundle(Integer.toString(i),mTabGroup.get(i).getBundle());
        }
        return bundle;
    }

    public boolean restoreState(Bundle savedInstanceState) {
        if(savedInstanceState==null){
            setRestore(false);
            return false;
        }
        Bundle bundle = savedInstanceState.getBundle(BaseActivity.BASE_ACTIVITY_TAB_MANAGER);
        int tabGroupSize = bundle.getInt(TAB_MANAGER_SIZE);
        ArrayList<TabGroup> list = new ArrayList<TabGroup>();
        for(int i=0;i<tabGroupSize;i++){
            Bundle group = bundle.getBundle(Integer.toString(i));
            TabGroup tabGroup = new TabGroup(group);
            list.add(tabGroup);
        }
        setRestore(list.size()!=0);
        if(list != null){
            mTabGroup = list;
            return true;
        }
        return false;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public boolean isRestore() {
        return mRestore;
    }

    public boolean isNewInstance() {
        return !mRestore;
    }

    public void setRestore(boolean restore) {
        mRestore = restore;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s: Activity '%s' Restore '%s'",this.getClass().getSimpleName(), mActivity==null?"null":mActivity.getClass().getSimpleName(), Boolean.toString(mRestore)));
        builder.append("\n");
        if(mTabGroup!=null){
            for(TabGroup list:mTabGroup){
                builder.append(list.toString());
            }
        }else{
            builder.append("TabGroup is null");
        }
        return builder.toString();
    }
}
