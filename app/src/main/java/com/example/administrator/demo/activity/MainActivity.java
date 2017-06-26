package com.example.administrator.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.demo.R;
import com.example.administrator.demo.fragment.ChartFragment;
import com.example.administrator.demo.fragment.ContentFragment;
import com.example.administrator.demo.fragment.FoundFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.vp_viewpager)
    ViewPager vpViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vpViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tlTabs.setupWithViewPager(vpViewpager);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        int pagecount = 3;
        String[] tabbName = {"聊天", "通讯录", "发现"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {

                return ChartFragment.newInstance("", "");
            } else if (position == 1) {
                return ContentFragment.newInstance("", "");

            } else if (position == 2) {

                return FoundFragment.newInstance("", "");
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return pagecount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabbName[position];
        }
    }


}
