package com.vanke.zhuze.module.home;

import android.support.v4.app.Fragment;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.vanke.libvanke.base.BaseActivity;
import com.vanke.zhuze.R;
import com.vanke.zhuze.data.common.CommonFragmentAdapter;
import com.vanke.zhuze.data.common.TabEntity;
import com.vanke.zhuze.widget.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabSelectListener {


    @BindView(R.id.bottom_tab_layout)
    CommonTabLayout mBottomTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;

    @Override
    protected void initViewsAndEvents() {
        initTab();
    }

    private void initTab() {
        mBottomTabLayout.setTabData(getTabEntityList());
        mBottomTabLayout.setOnTabSelectListener(this);

        CommonFragmentAdapter adapter = new CommonFragmentAdapter(getSupportFragmentManager(), getFragments());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void onTabSelect(int position) {
        mViewPager.setCurrentItem(position, true);
    }

    @Override
    public void onTabReselect(int position) {

    }

    /**
     * 获取Tab数据，暂时写死，后期可以通过后台控制
     *
     * @return
     */
    private ArrayList<CustomTabEntity> getTabEntityList() {
        ArrayList<CustomTabEntity> list = new ArrayList<>();
        String[] array = getResources().getStringArray(R.array.array_home_tab);
        list.add(new TabEntity(array[0], R.mipmap.tabbar_home_active, R.mipmap.tabbar_home_normal));
        list.add(new TabEntity(array[1], R.mipmap.tabbar_service_active, R.mipmap.tabbar_service_normal));
        list.add(new TabEntity(array[2], R.mipmap.tabbar_housekeeper_active, R.mipmap.tabbar_housekeeper_normal));
        list.add(new TabEntity(array[3], R.mipmap.tabbar_community_active, R.mipmap.tabbar_community_normal));
        list.add(new TabEntity(array[3], R.mipmap.tabbar_me_active, R.mipmap.tabbar_me_normal));
        return list;
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());
        return fragments;
    }

}
