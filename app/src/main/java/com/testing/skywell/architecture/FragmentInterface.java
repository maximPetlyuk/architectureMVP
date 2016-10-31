package com.testing.skywell.architecture;


public interface FragmentInterface {
    public boolean onBack();
    public FragmentInterface getActivityContext();
    public void doFragment(FragmentOperation operation);
    public TabManager getTabManager();
}
