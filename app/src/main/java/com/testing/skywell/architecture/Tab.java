package com.testing.skywell.architecture;

public enum Tab {
    VK_WALL(0), VK_PROFILE(1);

    private final int mId;

    Tab() {
        mId = -1;
    }
    
    Tab(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public static Tab fromId(int id) {
        for (Tab type : Tab.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public static Tab fromName(String name) {
        for (Tab type : Tab.values()) {
            if (type.toString().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
