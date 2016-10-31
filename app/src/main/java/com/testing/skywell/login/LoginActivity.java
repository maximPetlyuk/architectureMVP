package com.testing.skywell.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.testing.skywell.R;
import com.testing.skywell.helpers.navigation.Navigator;
import com.testing.skywell.login.presenter.VkLoginPresenter;
import com.testing.skywell.login.view.VkLoginView;
import com.testing.skywell.utils.Networks;
import com.testing.skywell.utils.UiUtils;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Maxim on 27.10.2016.
 */

public class LoginActivity extends AppCompatActivity implements VkLoginView {

    private VkLoginPresenter mLoginPresenter;

    @Bind(R.id.iv_vk_spy)
    ImageView mSpyLogo;

    @Bind(R.id.btn_vk_login)
    RelativeLayout mVkLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this, this);
        mLoginPresenter = new VkLoginPresenter(this);
    }

    @Override
    public void doVkLogin() {
        if (Networks.isNetworkAvailable(this)) {
            VKSdk.login(this, VKScope.WALL);
        } else {
            UiUtils.showToast(this, getString(R.string.network_connection_error));
        }
    }

    @Override
    public void onLogoClick() {
        UiUtils.showToast(this, getString(R.string.vk_logo_text));
    }

    @Override
    public void loginSuccessful(VKAccessToken token) {
        Navigator.getInstance().navigateToMainScreen(this);
        finish();
    }

    @Override
    public void loginError(VKError error) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mLoginPresenter != null) {
            mLoginPresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mLoginPresenter != null) {
            mLoginPresenter.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mLoginPresenter != null) {
            mLoginPresenter.onDestroy();
        }
    }

    @OnClick(R.id.iv_vk_spy)
    void onVkLogoClick() {
        if (mLoginPresenter != null) {
            mLoginPresenter.onLogoClick();
        }
    }

    @OnClick(R.id.btn_vk_login)
    void onVkLoginBtnClick() {
        if (mLoginPresenter != null) {
            mLoginPresenter.loginUser();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                if (mLoginPresenter != null) {
                    mLoginPresenter.onLoginSuccessful(res);
                }
            }

            @Override
            public void onError(VKError error) {
                if (mLoginPresenter != null) {
                    mLoginPresenter.onLoginError(error);
                }
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
