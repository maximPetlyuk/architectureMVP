package com.testing.skywell.login.view;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKError;

/**
 * Created by Maxim on 27.10.2016.
 */

public interface VkLoginView {

    void doVkLogin();

    void onLogoClick();

    void loginSuccessful(VKAccessToken res);

    void loginError(VKError error);
}
