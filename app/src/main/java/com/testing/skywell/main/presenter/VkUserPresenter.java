package com.testing.skywell.main.presenter;

import com.testing.skywell.architecture.IPresenterLifeCycle;
import com.testing.skywell.main.model.VkUser;
import com.testing.skywell.main.view.VkUserView;

/**
 * Created by Maxim on 31.10.2016.
 */

public class VkUserPresenter implements IPresenterLifeCycle {

    private VkUserView mUserView;

    public VkUserPresenter(VkUserView vkUser) {
        this.mUserView = vkUser;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    public void loadUserData() {
        if (mUserView != null) {
            mUserView.loadUserProfile();
        }
    }

    public void renderUser(VkUser user) {
        if (mUserView != null) {
            mUserView.renderUser(user);
        }
    }
}
