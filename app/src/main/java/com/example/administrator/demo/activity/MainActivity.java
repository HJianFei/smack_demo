package com.example.administrator.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.fragment.ChartFragment;
import com.example.administrator.demo.fragment.ContentFragment;
import com.example.administrator.demo.fragment.FoundFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tl_tabs)
    TabLayout tlTabs;
    @BindView(R.id.vp_viewpager)
    ViewPager vpViewpager;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.btn_add_friend)
    ImageView btnAddFriend;
    @BindView(R.id.btn_menu)
    ImageView btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vpViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tlTabs.setupWithViewPager(vpViewpager);

    }

    @OnClick({R.id.btn_search, R.id.btn_add_friend, R.id.btn_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                break;
            case R.id.btn_add_friend:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_menu:
                break;
        }
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
