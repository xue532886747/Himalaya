package com.example.myhimalaya.ui.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myhimalaya.R;
import com.example.myhimalaya.adapters.MainTitleViewPagerAdapter;
import com.example.myhimalaya.base.BaseActivity;
import com.example.myhimalaya.ui.fragment.main_fragment.MainFindFragment;
import com.example.myhimalaya.ui.fragment.main_fragment.MainFragment;
import com.example.myhimalaya.ui.fragment.main_fragment.MainHearFragment;
import com.example.myhimalaya.ui.fragment.main_fragment.MainMineFragment;
import com.example.myhimalaya.ui.fragment.main_fragment.MainPlayFragment;
import com.example.myhimalaya.utils.AdapterUtil;
import com.example.myhimalaya.utils.LogUtil;
import com.jaeger.library.StatusBarUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbumsList;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;
import com.ximalaya.ting.android.opensdk.model.album.RelativeAlbums;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.banner.RankBannerList;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.metadata.MetaDataList;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.TrackHotList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity1";
    private Context mContext;
    private ViewPager mView_pager;
    private MainTitleViewPagerAdapter mainTitleViewPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private LinearLayout ll_home, ll_hear, ll_play, ll_find, ll_mine;
    private MainFragment mainFragment;
    private MainHearFragment mainHearFragment;
    private MainPlayFragment mainPlayFragment;
    private MainFindFragment mainFindFragment;
    private MainMineFragment mainMineFragment;

    @Override
    protected int setLayoutId() {
        mContext = this;
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        immersive();
        AdapterUtil.setCustomDensity(this, getApplication());        //适配
        mView_pager = findViewById(R.id.app_act_main_vp);
        ll_home = findViewById(R.id.ll_home);
        ll_hear = findViewById(R.id.ll_hear);
        ll_play = findViewById(R.id.ll_play);
        ll_find = findViewById(R.id.ll_find);
        ll_mine = findViewById(R.id.ll_mine);
        mainFragment = new MainFragment();
        mainHearFragment = new MainHearFragment();
        mainPlayFragment = new MainPlayFragment();
        mainFindFragment = new MainFindFragment();
        mainMineFragment = new MainMineFragment();
        mFragments.add(mainFragment);
        mFragments.add(mainHearFragment);
        mFragments.add(mainPlayFragment);
        mFragments.add(mainFindFragment);
        mFragments.add(mainMineFragment);
        mainTitleViewPagerAdapter = new MainTitleViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mView_pager.setAdapter(mainTitleViewPagerAdapter);
        mView_pager.setSelected(true);
        mView_pager.setCurrentItem(0);
        tabSelected(ll_home);
        initTitle();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        ll_home.setOnClickListener(this);
        ll_hear.setOnClickListener(this);
        ll_play.setOnClickListener(this);
        ll_find.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
        mView_pager.addOnPageChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页
            case R.id.ll_home:
                setPageSelected(0, ll_home);
                break;
            //我听
            case R.id.ll_hear:
                setPageSelected(1, ll_hear);
                break;
            //播放
            case R.id.ll_play:
                setPageSelected(2, ll_play);
                break;
            //发现
            case R.id.ll_find:
                setPageSelected(3, ll_find);
                break;
            //我的
            case R.id.ll_mine:
                setPageSelected(4, ll_mine);
                break;
            default:
                setPageSelected(0, null);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                setPageSelected(0, ll_home);
                break;
            case 1:
                setPageSelected(1, ll_hear);
                break;
            case 2:
                setPageSelected(2, ll_play);
                break;
            case 3:
                setPageSelected(3, ll_find);
                break;
            case 4:
                setPageSelected(4, ll_mine);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    /**
     * Tab选中
     *
     * @param linearLayout
     */
    private void tabSelected(LinearLayout linearLayout) {
        ll_home.setSelected(false);
        ll_hear.setSelected(false);
        ll_play.setSelected(false);
        ll_find.setSelected(false);
        ll_mine.setSelected(false);
        linearLayout.setSelected(true);
    }

    public void setPageSelected(int pageNumber, LinearLayout linearLayout) {
        if (linearLayout != null) {
            if (mView_pager != null) {
                mView_pager.setCurrentItem(pageNumber);
                tabSelected(linearLayout);
            }

        } else {
            StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
            mView_pager.setCurrentItem(0);
            tabSelected(ll_home);
        }
    }


    private void initTitle() {



//        Map<String, String> map1 = new HashMap<String, String>();
//        map1.put(DTransferConstants.ALBUMID, "30613477");
//        CommonRequest.getRelativeAlbums(map1, new IDataCallBack<RelativeAlbums>() {
//            @Override
//            public void onSuccess(@Nullable RelativeAlbums relativeAlbums) {
//                List<Album> relativeAlbumList = relativeAlbums.getRelativeAlbumList();
//
//            }
//
//            @Override
//            public void onError(int i, String s) {
//
//            }
//        });

//        /**
//         * 这个对应的是经典老歌等
//         */
//        Map<String, String> map = new HashMap<String, String>();
//        map.put(DTransferConstants.CATEGORY_ID, "2");
//        map.put(DTransferConstants.TAG_NAME, "老歌");
//        map.put(DTransferConstants.CALC_DIMENSION, "1");
//        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
//            @Override
//            public void onSuccess(@Nullable AlbumList albumList) {
//                List<Album> albums = albumList.getAlbums();
//
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                LogUtil.d(TAG, "tagList1 = " + "i = " + i + ",s = " + s);
//            }
//        });

    }

    /**
     * 沉浸式白色
     */
    private void immersive() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        StatusBarUtil.setLightMode(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
