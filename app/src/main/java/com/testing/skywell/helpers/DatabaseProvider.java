package com.testing.skywell.helpers;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.testing.skywell.main.model.VkUser;
import com.testing.skywell.main.model.VkWallPost;

import java.util.List;

/**
 * Created by Maxim on 31.10.2016.
 */

public class DatabaseProvider {

    public static void saveWallPosts(List<VkWallPost> data, boolean inTransaction) {
        if (data == null)
            return;

        if (inTransaction) {
            ActiveAndroid.beginTransaction();
        }

        for (int i = 0; i < data.size(); i++) {
            data.get(i).save();
        }

        if (inTransaction) {
            ActiveAndroid.setTransactionSuccessful();
            ActiveAndroid.endTransaction();
        }
    }

    public static void rewriteWallCacheInTransaction(List<VkWallPost> data) {
        if (data == null)
            return;

        ActiveAndroid.beginTransaction();

        List<VkWallPost> mWallPosts = getWallCache();

        if (mWallPosts != null || !mWallPosts.isEmpty()) {
            for (VkWallPost post : mWallPosts) {
                post.delete();
            }
        }

        for (int i = 0; i < data.size(); i++) {
            data.get(i).save();
        }

        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();
    }

    public static List<VkWallPost> getWallCache() {
        return new Select().from(VkWallPost.class).execute();
    }

    public static void rewriteUser(VkUser data) {
        if (data == null)
            return;

        if (data.getId() != null) {
            data.delete();
        }

        data.save();
    }

    public static void clearDB() {
        List<VkWallPost> mWallPosts = getWallCache();

        if (mWallPosts != null || !mWallPosts.isEmpty()) {
            for (VkWallPost post : mWallPosts) {
                post.delete();
            }
        }

        VkUser mUser = new Select().from(VkUser.class).executeSingle();
        if (mUser != null) {
            mUser.delete();
        }
    }
}
