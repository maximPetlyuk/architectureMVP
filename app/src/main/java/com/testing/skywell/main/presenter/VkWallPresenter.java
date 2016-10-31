package com.testing.skywell.main.presenter;

import com.testing.skywell.architecture.IPresenterLifeCycle;
import com.testing.skywell.main.model.VkWallPost;
import com.testing.skywell.main.view.VkWallView;

import java.util.List;

/**
 * Created by Maxim on 27.10.2016.
 */

public class VkWallPresenter implements IPresenterLifeCycle {

    private VkWallView mWallView;

    public VkWallPresenter(VkWallView view) {
        this.mWallView = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        mWallView = null;
    }

    public void showLoading() {
        if (mWallView != null) {
            mWallView.showLoading();
        }
    }

    public void hideLoading() {
        if (mWallView != null) {
            mWallView.hideLoading();
        }
    }

    public void renderWall(List<VkWallPost> mWallPostList, int startPosition) {
        if (mWallView != null) {
            mWallView.renderWallPosts(mWallPostList, startPosition);
        }
    }

    public void loadWall(int startPosition) {
        if (mWallView != null) {
            mWallView.loadWallPosts(startPosition);
        }
    }

    public void showError() {
        if (mWallView != null) {
            mWallView.showLoadingError();
        }
    }
}
