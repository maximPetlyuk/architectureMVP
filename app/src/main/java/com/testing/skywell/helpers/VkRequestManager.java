package com.testing.skywell.helpers;

import android.support.annotation.NonNull;

import com.testing.skywell.main.model.VkUser;
import com.testing.skywell.main.model.VkWallPost;
import com.testing.skywell.utils.ParseUtils;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.List;

/**
 * Created by Maxim on 30.10.2016.
 */

public class VkRequestManager {

    public interface IWallRequestListener {
        void onSuccessful(List<VkWallPost> mWallPostList);

        void onError(String errorMessage);
    }

    public interface IUserRequestListener {
        void onSuccessful(VkUser user);

        void onError(String errorMessage);
    }

    public static void getWall(final int startPosition, @NonNull final IWallRequestListener requestListener) {
        VKRequest getWallRequest = VKApi.wall().get(VKParameters.from(
                VKApiConst.OWNER_ID, VKAccessToken.currentToken().userId,
                VKApiConst.EXTENDED, 1,
                VKApiConst.OFFSET, startPosition));
        getWallRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                List<VkWallPost> mWallPostList = ParseUtils.getWallPosts(response.json.toString());

                if (startPosition == 0) {
                    DatabaseProvider.rewriteWallCacheInTransaction(mWallPostList);
                } else {
                    DatabaseProvider.saveWallPosts(mWallPostList, true);
                }

                requestListener.onSuccessful(mWallPostList);
            }

            @Override
            public void onError(VKError error) {

                requestListener.onError(error.errorMessage);
            }
        });
    }

    public static void getUserInfo(@NonNull final IUserRequestListener listener) {
        VKRequest userInfoRequest = VKApi.users().get(VKParameters.from(
                VKApiConst.FIELDS, "photo_400_orig,nickname,city,status,education,verified"));

        userInfoRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VkUser vkUser = ParseUtils.getUserInfo(response.json.toString());
                DatabaseProvider.rewriteUser(vkUser);

                listener.onSuccessful(vkUser);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);

                listener.onError(error.errorMessage);
            }
        });
    }
}
