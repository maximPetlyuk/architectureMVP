package com.testing.skywell.main.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Maxim on 28.10.2016.
 */

public class MainPagerAdapter extends PagerAdapter {

    private ISwitchPageListener mSwitchPageListener;
    private Context mContext;
    private int position;

    public interface ISwitchPageListener {
        void onPageChanged(int position, int layoutId);
    }

    public MainPagerAdapter(Context context) {
        mContext = context;
    }

    public void setOnSwitchListener(ISwitchPageListener listener) {
        mSwitchPageListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        this.position = position;
        FrameLayout layout = new FrameLayout(collection.getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layout.setId(0x300 + position);

        if (mSwitchPageListener != null) {
            mSwitchPageListener.onPageChanged(position, layout.getId());
        }

        layout.setLayoutParams(layoutParams);
        collection.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public int getCount() {
        return 2;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Wall";

            case 1:
            default:
                return " Profile";
        }
    }
}
