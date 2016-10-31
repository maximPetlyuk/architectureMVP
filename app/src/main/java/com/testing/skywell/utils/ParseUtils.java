package com.testing.skywell.utils;

import com.testing.skywell.main.model.VkUser;
import com.testing.skywell.main.model.VkWallPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 30.10.2016.
 */

public class ParseUtils {

    // Json fields in the 'wall.get' response
    private static final String RESPONSE = "response";
    private static final String POST_ITEMS = "items";
    private static final String POST_ID = "id";
    private static final String POST_TEXT = "text";
    private static final String REPOST_OBJECT = "reposts";
    private static final String REPOST_COUNT = "count";
    private static final String ATTACHMENT = "attachments";
    private static final String ATTACHMENT_TYPE = "type";
    private static final String PHOTO_OBJECT = "photo";
    private static final String PHOTO_URL_604 = "photo_604";
    private static final String PHOTO_URL_130 = "photo_130";

    // Json fields in the 'getUser' response
    private static final String FIRST_NAME = "first_name";
    private static final String NICKNAME = "nickname";
    private static final String LAST_NAME = "last_name";
    private static final String PROFILE_PHOTO = "photo_400_orig";
    private static final String UNIVERCITY_NAME = "university_name";
    private static final String CITY_OBJECT = "city";
    private static final String CITY = "title";


    public static List<VkWallPost> getWallPosts(String rawJson) {

        List<VkWallPost> mParsedPosts = new ArrayList<>();

        try {
            JSONObject mMainObject = new JSONObject(rawJson);

            if (hasField(mMainObject, RESPONSE)) {
                JSONObject mResponseData = mMainObject.getJSONObject(RESPONSE);

                JSONArray mPostItems = mResponseData.getJSONArray(POST_ITEMS);

                for (int i = 0; i < mPostItems.length(); i++) {
                    JSONObject mPostJson = mPostItems.getJSONObject(i);

                    VkWallPost mPost = new VkWallPost();

                    if (hasField(mPostJson, POST_ID)) {
                        mPost.setPostId(mPostJson.getInt(POST_ID));
                    }

                    if (hasField(mPostJson, POST_TEXT)) {
                        mPost.setPostText(mPostJson.getString(POST_TEXT));
                    }

                    if (hasField(mPostJson, REPOST_OBJECT)) {
                        JSONObject mRepostJson = mPostJson.getJSONObject(REPOST_OBJECT);
                        if (hasField(mRepostJson, REPOST_COUNT)) {
                            mPost.setReportsCount(mRepostJson.getInt(REPOST_COUNT));
                        }
                    }

                    // Just for test we take the only image from the post
                    if (hasField(mPostJson, ATTACHMENT)) {
                        JSONArray mAttachmentItems = mPostJson.getJSONArray(ATTACHMENT);
                        for (int y = 0; y < mAttachmentItems.length(); y++) {
                            JSONObject mAttachmentJson = mAttachmentItems.getJSONObject(y);

                            // Now only photos are compatible...
                            if (!mAttachmentJson.getString(ATTACHMENT_TYPE).equalsIgnoreCase("photo")) {
                                mPost.setAttachmentType(-1);
                                continue;
                            }

                            if (hasField(mAttachmentJson, PHOTO_OBJECT)) {
                                JSONObject mPhotoJson = mAttachmentJson.getJSONObject(PHOTO_OBJECT);
                                if (hasField(mPhotoJson, PHOTO_URL_604)) {
                                    mPost.setPhotoUrl(mPhotoJson.getString(PHOTO_URL_604));
                                    break;
                                }
                            }
                        }
                    }

                    mParsedPosts.add(mPost);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mParsedPosts;
    }

    public static VkUser getUserInfo(String rawJson) {
        VkUser vkUser = new VkUser();

        try {
            JSONObject mMainObject = new JSONObject(rawJson);

            JSONArray mAccountsItems = mMainObject.getJSONArray(RESPONSE);
            JSONObject mUserJson = mAccountsItems.getJSONObject(0);

            if (hasField(mUserJson, FIRST_NAME)) {
                vkUser.setFirstName(mUserJson.getString(FIRST_NAME));
            }

            if (hasField(mUserJson, LAST_NAME)) {
                vkUser.setLastName(mUserJson.getString(LAST_NAME));
            }

            if (hasField(mUserJson, NICKNAME)) {
                vkUser.setNickname(mUserJson.getString(NICKNAME));
            }

            if (hasField(mUserJson, UNIVERCITY_NAME)) {
                vkUser.setEducation(mUserJson.getString(UNIVERCITY_NAME));
            }

            if (hasField(mUserJson, PROFILE_PHOTO)) {
                vkUser.setPhotoUrl(mUserJson.getString(PROFILE_PHOTO));
            }

            if (hasField(mUserJson, CITY_OBJECT)) {
                JSONObject mCityObject = mUserJson.getJSONObject(CITY_OBJECT);
                if (hasField(mCityObject, CITY)) {
                    vkUser.setCity(mCityObject.getString(CITY));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return vkUser;
    }

    private static boolean hasField(JSONObject object, String fieldName) {
        return object.has(fieldName) && !object.isNull(fieldName);
    }
}
