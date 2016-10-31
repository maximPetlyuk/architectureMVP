package com.testing.skywell.login.presenter;

import com.testing.skywell.architecture.IPresenterLifeCycle;
import com.testing.skywell.login.view.VkLoginView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKError;

/**
 * Created by Maxim on 27.10.2016.
 */

public class VkLoginPresenter implements IPresenterLifeCycle {

    private VkLoginView mParentView;

    public VkLoginPresenter(VkLoginView parentView) {
        this.mParentView = parentView;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
        this.mParentView = null;
    }

    public void loginUser() {
        if (mParentView != null) {
            mParentView.doVkLogin();
        }
    }

    public void onLogoClick() {
        if (mParentView != null) {
            mParentView.onLogoClick();
        }
    }

    public void onLoginSuccessful(VKAccessToken res) {
        if (mParentView != null) {
            mParentView.loginSuccessful(res);
        }
    }

    public void onLoginError(VKError error) {
        if (mParentView != null) {
            mParentView.loginError(error);
        }
    }
}
