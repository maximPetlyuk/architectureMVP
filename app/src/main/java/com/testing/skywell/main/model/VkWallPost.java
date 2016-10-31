package com.testing.skywell.main.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Maxim on 30.10.2016.
 */
@Table(name = "wall_posts")
public class VkWallPost extends Model {

    @Column(name = "post_id")
    private int mPostId;

    @Column(name = "post_text")
    private String mPostText = "";

    @Column(name = "reports_count")
    private int mReportsCount;

    @Column(name = "likes_count")
    private int mLikesCount;

    @Column(name = "photo_url")
    private String mPhotoUrl = "";

    @Column(name = "timestamp")
    private long mTimestamp;

    @Column(name = "user_id")
    private int mUserId;

    @Column(name = "attachment_type")
    private int mAttachmentType;

    public int getAttachmentType() {
        return mAttachmentType;
    }

    public int getReportsCount() {
        return mReportsCount;
    }

    public void setAttachmentType(int attachmentType) {
        mAttachmentType = attachmentType;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setPostId(int postId) {
        mPostId = postId;
    }

    public void setPostText(String postText) {
        mPostText = postText;
    }

    public void setReportsCount(int reportsCount) {
        mReportsCount = reportsCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public String getPhotoUrl() {
        return mPhotoUrl.replace("\\", "");
    }

    public int getPostId() {
        return mPostId;
    }

    public String getPostText() {
        return mPostText;
    }

    public int getRepostsCount() {
        return mReportsCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }
}
