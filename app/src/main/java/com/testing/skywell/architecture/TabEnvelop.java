package com.testing.skywell.architecture;

import java.io.Serializable;

public class TabEnvelop implements Serializable {
    private Tab tab;
    private int stackSize;

    public TabEnvelop(Tab tab, int stackSize) {
        this.tab = tab;
        this.stackSize = stackSize;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public int getStackSize() {
        return stackSize;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }
}
