package com.testing.skywell.main.view;

import com.testing.skywell.main.model.VkWallPost;

import java.util.List;

/**
 * Created by Maxim on 27.10.2016.
 */

public interface VkWallView extends LoadDataView {

    void loadWallPosts(int startPosition);

    void renderWallPosts(List<VkWallPost> mWallPostList, int startPosition);

    void showLoadingError();
}
