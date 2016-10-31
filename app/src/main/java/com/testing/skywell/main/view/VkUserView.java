package com.testing.skywell.main.view;

import com.testing.skywell.main.model.VkUser;

/**
 * Created by Maxim on 31.10.2016.
 */

public interface VkUserView extends LoadDataView {

    void loadUserProfile();

    void renderUser(VkUser user);
}
