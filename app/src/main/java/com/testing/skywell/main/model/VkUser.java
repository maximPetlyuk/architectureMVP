package com.testing.skywell.main.model;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Maxim on 31.10.2016.
 */

@Table(name = "user")
public class VkUser extends Model {

    @Column(name = "first_name")
    private String mFirstName = "";

    @Column(name = "last_name")
    private String mLastName = "";

    @Column(name = "photo_url")
    private String mPhotoUrl;

    @Column(name = "nickname")
    private String mNickname = "";

    @Column(name = "status")
    private String mStatus;

    @Column(name = "city")
    private String mCity;

    @Column(name = "education")
    private String mEducation;

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCity() {
        return mCity;
    }

    public String getUsername() {
        StringBuilder mBuilder = new StringBuilder();

        mBuilder.append(mFirstName);

        if (!TextUtils.isEmpty(mNickname)) {
            mBuilder.append(" ");
            mBuilder.append(mNickname);
            mBuilder.append(" ");
        }

        mBuilder.append(mLastName);

        return mBuilder.toString();
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public void setNickname(String nickname) {
        mNickname = nickname;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public void setEducation(String education) {
        mEducation = education;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getEducation() {
        return mEducation;
    }
}
