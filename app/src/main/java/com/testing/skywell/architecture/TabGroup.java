package com.testing.skywell.architecture;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class TabGroup {
    private String TAB_GROUP_STATE_CONTAINER = "TAB_GROUP_STATE_CONTAINER";
    private String TAB_GROUP_STATE_TAG = "TAB_GROUP_STATE_TAG";
    private String TAB_GROUP_STATE_ACTIVE_FRAGMENT = "TAB_GROUP_STATE_ACTIVE_FRAGMENT";
    private String TAB_GROUP_STATE_ACTIVE_TAB = "TAB_GROUP_STATE_ACTIVE_TAB";
    private String TAB_GROUP_STATE_TAB_LIST = "TAB_GROUP_STATE_TAB_LIST";

    private int mContainer;
    private String mTag;
    private HashMap<Tab, Stack<Fragment>> mFragmentMap;
    private Fragment mActiveFragment;
    private Tab mActiveTab;


    public static TabGroup newInstance() {
        TabGroup group = new TabGroup();
        return group;
    }

    public TabGroup(){
        setFragmentMap(new HashMap<Tab, Stack<Fragment>>());
    }

    public TabGroup(Bundle bundle){
        mContainer = bundle.getInt(TAB_GROUP_STATE_CONTAINER);
        mTag = bundle.getString(TAB_GROUP_STATE_TAG);
        mActiveFragment = FragmentEnvelop.getInstance(bundle.getBundle(TAB_GROUP_STATE_ACTIVE_FRAGMENT)).getFragment();
        mActiveTab = Tab.fromName(bundle.getString(TAB_GROUP_STATE_ACTIVE_TAB));

        ArrayList<TabEnvelop> tabs = (ArrayList<TabEnvelop>) bundle.getSerializable(TAB_GROUP_STATE_TAB_LIST);

        mFragmentMap = new HashMap<Tab, Stack<Fragment>>();

        for(TabEnvelop tab:tabs){
            Stack<Fragment> stack = new Stack<Fragment>();
            for(int i=0;i<tab.getStackSize();i++){
                Bundle bundleFragment = bundle.getBundle(getFragmentBundleName(tab.getTab(), i));
                FragmentEnvelop bundleEnvelop = FragmentEnvelop.getInstance(bundleFragment);
                stack.add(bundleEnvelop.getFragment());
            }
            mFragmentMap.put(tab.getTab(), stack);
        }
    }

    public TabGroup container(int container){
        setContainer(container);
        return this;
    }

    public TabGroup tag(String tag){
        setTag(tag);
        return this;
    }

    public int getContainer() {
        return mContainer;
    }

    public void setContainer(int container) {
        mContainer = container;
    }

    public HashMap<Tab, Stack<Fragment>> getFragmentMap() {
        return mFragmentMap;
    }

    public void setFragmentMap(HashMap<Tab, Stack<Fragment>> fragmentMap) {
        mFragmentMap = fragmentMap;
    }

    public TabGroup init(Tab tab) {
        mFragmentMap.put(tab,new Stack<Fragment>());
        mActiveTab = tab;
        return this;
    }

    public TabGroup put(Tab tab, Stack<Fragment> stack) {
        mFragmentMap.put(tab, stack);
        return this;
    }

    public Stack<Fragment> get(Tab tab) {
        return mFragmentMap.get(tab);
    }

    public TabGroup remove(Tab tab) {
        mFragmentMap.remove(tab);
        return this;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public Fragment getActiveFragment() {
        return mActiveFragment;
    }

    public void setActiveFragment(Fragment activeFragment) {
        mActiveFragment = activeFragment;
    }

    public Tab getActiveTab() {
        return mActiveTab;
    }

    public void setActiveTab(Tab activeTab) {
        mActiveTab = activeTab;
    }

    public int size(){
        return mFragmentMap.size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s: Tag '%s' Container '%d' Tab '%s' Fragment '%s'", this.getClass().getSimpleName(), mTag, mContainer, mActiveTab == null ? "null" : mActiveTab.toString(), mActiveFragment == null ? "null" : mActiveFragment.getClass().getSimpleName()));
        builder.append("\nTABS:\n");

        if(mFragmentMap!=null){
            Iterator<Tab> it = mFragmentMap.keySet().iterator();
            while (it.hasNext())
            {
                Tab tab = it.next();
                builder.append(String.format("Tab '%s'\n",tab.toString()));
                Stack<Fragment> stack = mFragmentMap.get(tab);

                if(stack!=null){
                    Iterator<Fragment> iter = stack.iterator();
                    while (iter.hasNext()){
                        Fragment fragment = iter.next();
                        builder.append(fragment.toString());
                        builder.append("\n");
                    }
                }else{
                    builder.append("Stack is null");
                }
            }
        }else{
            builder.append("FragmentMap is null");
        }
        builder.append("\n");
        return builder.toString();
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(TAB_GROUP_STATE_CONTAINER, mContainer);
        bundle.putString(TAB_GROUP_STATE_TAG, mTag);
        bundle.putBundle(TAB_GROUP_STATE_ACTIVE_FRAGMENT, FragmentEnvelop.getInstance(mActiveFragment).getBundle());
        bundle.putString(TAB_GROUP_STATE_ACTIVE_TAB, mActiveTab.toString());
        // save tab list
        ArrayList<TabEnvelop> tabs = getTabList();
        bundle.putSerializable(TAB_GROUP_STATE_TAB_LIST, tabs);
        for(TabEnvelop tab:tabs){
            Stack<Fragment> stack = mFragmentMap.get(tab.getTab());
            for(int i=0;i<stack.size();i++){
                Fragment fragment = stack.get(i);
                FragmentEnvelop envelop = new FragmentEnvelop(fragment);
                bundle.putBundle(getFragmentBundleName(tab.getTab(),i),envelop.getBundle());
            }
        }
        return bundle;
    }

    private ArrayList<TabEnvelop> getTabList(){
        ArrayList<TabEnvelop> array = new ArrayList<TabEnvelop>();
        Iterator<Tab> tabIterator = mFragmentMap.keySet().iterator();
        while (tabIterator.hasNext()){
            Tab tab = tabIterator.next();
            Stack<Fragment> stack = mFragmentMap.get(tab);
            array.add(new TabEnvelop(tab,stack.size()));
        }
        return array;
    };

    private String getFragmentBundleName(Tab tab, int size){
        return tab.toString().concat(Integer.toString(size));
    }

}
