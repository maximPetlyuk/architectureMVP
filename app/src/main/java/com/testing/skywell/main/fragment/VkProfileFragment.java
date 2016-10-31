package com.testing.skywell.main.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.testing.skywell.R;
import com.testing.skywell.architecture.AppBaseFragment;
import com.testing.skywell.helpers.PicassoImageLoader;
import com.testing.skywell.helpers.VkRequestManager;
import com.testing.skywell.main.model.VkUser;
import com.testing.skywell.main.presenter.VkUserPresenter;
import com.testing.skywell.main.view.VkUserView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Maxim on 28.10.2016.
 */

public class VkProfileFragment extends AppBaseFragment implements VkUserView {

    @Bind(R.id.rl_root_content)
    RelativeLayout mRootContentView;

    @Bind(R.id.view_progress)
    View mProgressView;

    @Bind(R.id.iv_user_image)
    ImageView mUserImage;

    @Bind(R.id.tv_user_name)
    TextView mUsername;

    @Bind(R.id.tv_user_city)
    TextView mUserCity;

    @Bind(R.id.tv_user_education)
    TextView mUserEducation;

    private VkUserPresenter mUserPresenter;

    public static VkProfileFragment newInstance() {
        VkProfileFragment fragment = new VkProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.frgm_vk_profile, container, false);

        ButterKnife.bind(this, mFragmentView);

        mUserPresenter = new VkUserPresenter(this);
        mUserPresenter.loadUserData();

        return mFragmentView;
    }

    @Override
    public void loadUserProfile() {
        VkRequestManager.getUserInfo(new VkRequestManager.IUserRequestListener() {
            @Override
            public void onSuccessful(VkUser user) {
                if (mUserPresenter != null) {
                    mUserPresenter.renderUser(user);
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void renderUser(VkUser user) {
        mUsername.setText(user.getUsername());
        mUserCity.setText(user.getCity());
        mUserEducation.setText(user.getEducation());

        if (!TextUtils.isEmpty(user.getPhotoUrl())) {
            PicassoImageLoader.loadImage(
                    getContext(),
                    user.getPhotoUrl(),
                    mUserImage);
        }
    }

    @Override
    public void showLoading() {
        showProgressView(true);
    }

    @Override
    public void hideLoading() {
        showProgressView(false);
    }

    private void showProgressView(boolean show) {
        if (mProgressView != null && mRootContentView != null) {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRootContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
