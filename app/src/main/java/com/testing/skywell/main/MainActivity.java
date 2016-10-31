package com.testing.skywell.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.testing.skywell.R;
import com.testing.skywell.architecture.BaseActivity;
import com.testing.skywell.architecture.FragmentInterface;
import com.testing.skywell.architecture.FragmentOperation;
import com.testing.skywell.architecture.Tab;
import com.testing.skywell.architecture.TabGroup;
import com.testing.skywell.helpers.DatabaseProvider;
import com.testing.skywell.main.adapter.MainPagerAdapter;
import com.testing.skywell.main.fragment.VkProfileFragment;
import com.testing.skywell.main.fragment.VkWallFragment;
import com.testing.skywell.utils.FontManager;

import java.util.Stack;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_viewpager)
    ViewPager mViewPager;

    @Bind(R.id.main_tabs)
    TabLayout mTabs;

    @Bind(R.id.tv_toolbar_title)
    TextView mToolbarTitle;

    private MainPagerAdapter mPagerAdapter;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this, this);

        if (getTabManager().isNewInstance()) {
            getTabManager().init(new TabGroup().init(Tab.VK_WALL));
            getTabManager().init(new TabGroup().init(Tab.VK_PROFILE));

            doFragment(FragmentOperation.instance().fragment(VkWallFragment.newInstance()).tab(Tab.VK_WALL).set());
            doFragment(FragmentOperation.instance().fragment(VkProfileFragment.newInstance()).tab(Tab.VK_PROFILE).set());
        }

        setupViewPager();

        mToolbarTitle.setTypeface(FontManager.getInstance().get(FontManager.Fonts.BUNGASAI.toString(), this));
    }

    private void setupViewPager() {
        mPagerAdapter = new MainPagerAdapter(this);
        mPagerAdapter.setOnSwitchListener(new MainPagerAdapter.ISwitchPageListener() {
            @Override
            public void onPageChanged(int position, int id) {
                switch (position) {
                    case (0):
                        getTabManager().get(Tab.VK_WALL).container(id);
                        doFragment(FragmentOperation.instance().tab(Tab.VK_WALL).show());
                        break;

                    case (1):
                        getTabManager().get(Tab.VK_PROFILE).container(id);
                        doFragment(FragmentOperation.instance().tab(Tab.VK_PROFILE).show());
                        break;
                }
            }
        });

        mViewPager.setAdapter(mPagerAdapter);
        mTabs.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabs.setupWithViewPager(mViewPager);
        mTabs.setSelectedTabIndicatorColor(Color.WHITE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DatabaseProvider.clearDB();
    }

    @Override
    public void onBackPressed() {
        TabGroup group = getTabManager().get(Tab.fromId(mViewPager.getCurrentItem()));
        Stack<Fragment> stack = group.getFragmentMap().get(group.getActiveTab());
        Fragment activeFragment = group.getActiveFragment();
        FragmentInterface activeInterface = null;
        try {
            activeInterface = (FragmentInterface) activeFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(activeFragment.toString() + " must implement FragmentInterface");
        }
        if (activeInterface.onBack()) {
            if (stack.size() < 2) {
                if (onBack()) {
                    finish();
                }
            } else {
                doFragment(new FragmentOperation().failsafe().tab(group.getActiveTab()).pop());
            }
        }
    }

    @Override
    public FragmentInterface getActivityContext() {
        TabGroup group = getTabManager().get(Tab.fromId(mViewPager.getCurrentItem()));
        Fragment activeFragment = group.getActiveFragment();
        FragmentInterface activeInterface = null;
        try {
            activeInterface = (FragmentInterface) activeFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(activeFragment.toString() + " must implement FragmentInterface");
        }
        return activeInterface;
    }
}
