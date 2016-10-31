package com.testing.skywell.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testing.skywell.R;
import com.testing.skywell.helpers.PicassoImageLoader;
import com.testing.skywell.main.model.VkWallPost;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Maxim on 31.10.2016.
 */

public class WallPostAdapter extends RecyclerView.Adapter<WallPostAdapter.WallPostHolder> {

    private List<VkWallPost> mWallPostList;
    private LayoutInflater mInflater;
    private Context mContext;

    public WallPostAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mWallPostList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return (mWallPostList != null) ? mWallPostList.size() : 0;
    }

    @Override
    public WallPostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WallPostHolder(mInflater.inflate(R.layout.item_wall_post, parent, false));
    }

    @Override
    public void onBindViewHolder(WallPostHolder holder, final int position) {
        holder.update(mWallPostList.get(position));
    }

    public void setWallPostList(List<VkWallPost> wallPostList, int startPosition) {
        if (startPosition == 0 && !wallPostList.isEmpty()) {
            mWallPostList.clear();
        }

        mWallPostList.addAll(startPosition, wallPostList);
        notifyItemRangeInserted(mWallPostList.size(), wallPostList.size());
    }

    class WallPostHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_likes_count)
        TextView mLikesCount;

        @Bind(R.id.tv_reposts_count)
        TextView mRepostsCount;

        @Bind(R.id.tv_post_text)
        TextView mPostText;

        @Bind(R.id.iv_post_image)
        ImageView mPostImage;

        WallPostHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void update(VkWallPost post) {
            mLikesCount.setText(String.valueOf(post.getLikesCount()));
            mRepostsCount.setText(String.valueOf(post.getRepostsCount()));
            mPostText.setText(post.getPostText());

            if (post.getAttachmentType() == -1) {
                mPostImage.setVisibility(View.GONE);
                mPostText.setText(mContext.getString(R.string.incompatible_attachment));
                return;
            }

            if (!TextUtils.isEmpty(post.getPhotoUrl())) {
                mPostImage.setVisibility(View.VISIBLE);
                PicassoImageLoader.loadImage(mContext, post.getPhotoUrl(), mPostImage);
            } else {
                mPostImage.setVisibility(View.GONE);
            }
        }
    }
}
