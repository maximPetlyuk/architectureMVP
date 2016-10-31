package com.testing.skywell.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.testing.skywell.R;
import com.testing.skywell.architecture.AppBaseFragment;
import com.testing.skywell.helpers.DatabaseProvider;
import com.testing.skywell.helpers.VkRequestManager;
import com.testing.skywell.main.adapter.WallPostAdapter;
import com.testing.skywell.main.model.VkWallPost;
import com.testing.skywell.main.presenter.VkWallPresenter;
import com.testing.skywell.main.view.VkWallView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Maxim on 28.10.2016.
 */

public class VkWallFragment extends AppBaseFragment implements VkWallView {

    @Bind(R.id.rl_root_content)
    RelativeLayout mRootContentView;

    @Bind(R.id.view_progress)
    View mProgressView;

    @Bind(R.id.rv_wall_post)
    RecyclerView mWallPostsRV;

    @Bind(R.id.swipe_layout)
    SwipyRefreshLayout mSwipeLayout;

    @Bind(R.id.tv_download_error)
    TextView mDownloadError;

    private VkWallPresenter mWallPresenter;
    private WallPostAdapter mWallAdapter;
    private int mStartPosition;

    public VkWallFragment() {
    }

    public static VkWallFragment newInstance() {
        VkWallFragment fragment = new VkWallFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mFragmentView = inflater.inflate(R.layout.frgm_vk_wall, container, false);

        ButterKnife.bind(this, mFragmentView);

        setupRecyclerView();
        setupRefreshLayout();

        mWallPresenter = new VkWallPresenter(this);
        mWallPresenter.loadWall(mStartPosition);
        mWallPresenter.showLoading();

        return mFragmentView;
    }

    private void setupRefreshLayout() {
        mSwipeLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (mWallPresenter == null) {
                    return;
                }

                if (SwipyRefreshLayoutDirection.TOP == direction) {
                    mStartPosition = 0;
                } else {
                    mStartPosition = mWallAdapter != null ? mWallAdapter.getItemCount() : 0;
                }

                mSwipeLayout.setRefreshing(false);
                mWallPresenter.loadWall(mStartPosition);
            }
        });
    }

    private void setupRecyclerView() {
        mWallAdapter = new WallPostAdapter(getContext());

        mWallPostsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mWallPostsRV.setAdapter(mWallAdapter);
    }

    @Override
    public void loadWallPosts(final int startPosition) {
        List<VkWallPost> mCachePosts = DatabaseProvider.getWallCache();
        if (mCachePosts != null && !mCachePosts.isEmpty()) {
            if (mWallPresenter != null) {
                mWallPresenter.renderWall(mCachePosts, startPosition);
                return;
            }
        }

        VkRequestManager.getWall(startPosition, new VkRequestManager.IWallRequestListener() {
            @Override
            public void onSuccessful(List<VkWallPost> mWallPostList) {
                if (mWallPresenter != null) {
                    mWallPresenter.renderWall(mWallPostList, startPosition);
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (mWallPresenter != null) {
                    mWallPresenter.showError();
                }
            }
        });
    }

    @Override
    public void renderWallPosts(List<VkWallPost> mWallPostList, int startPosition) {
        if (mWallAdapter != null) {
            mStartPosition = startPosition;
            mWallPresenter.hideLoading();
            mWallAdapter.setWallPostList(mWallPostList, mStartPosition);
        }
    }

    @Override
    public void showLoadingError() {
        mProgressView.setVisibility(View.GONE);
        mRootContentView.setVisibility(View.GONE);
        mDownloadError.setVisibility(View.VISIBLE);
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
