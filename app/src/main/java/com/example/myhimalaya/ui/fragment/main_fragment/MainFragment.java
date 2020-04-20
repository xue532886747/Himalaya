package com.example.myhimalaya.ui.fragment.main_fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.myhimalaya.R;
import com.example.myhimalaya.adapters.IndicatorAdapter;
import com.example.myhimalaya.adapters.MainTitleViewPagerAdapter;
import com.example.myhimalaya.base.BaseFragment;
import com.example.myhimalaya.interfaces.IMainCallBack;
import com.example.myhimalaya.presenter.MainPresenter;
import com.example.myhimalaya.ui.fragment.shouye_fragment.ShouyeAmuseMentFragment;
import com.example.myhimalaya.ui.fragment.shouye_fragment.ShouyeGameFragment;
import com.example.myhimalaya.ui.fragment.shouye_fragment.ShouyeHistoryFragment;
import com.example.myhimalaya.ui.fragment.shouye_fragment.ShouyeMusicFragment;
import com.example.myhimalaya.ui.fragment.shouye_fragment.ShouyeSoundBookFragment;
import com.example.myhimalaya.ui.fragment.shouye_fragment.ShouyeTalkShowFragment;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.category.Category;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseFragment implements IMainCallBack {

    private static final String TAG = "MainFragment";
    private MagicIndicator mMagicIndicator;
    private CommonNavigator mCommonNavigator;
    private Context mContext;
    private IndicatorAdapter mIndicatorAdapter;
    private ViewPager mView_pager;
    private List<String> mList = new ArrayList<>();
    private MainTitleViewPagerAdapter mainTitleViewPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MainPresenter mainPresenter;
    private ShouyeMusicFragment shouyeMusicFragment;
    private ShouyeHistoryFragment shouyeHistoryFragment;
    private ShouyeTalkShowFragment shouyeTalkShowFragment;
    private ShouyeAmuseMentFragment shouyeAmuseMentFragment;
    private ShouyeGameFragment shouyeGameFragment;
    private ShouyeSoundBookFragment shouyeSoundBookFragment;

    @Override
    public void initView(View mView) {
        setFragment();
        mFragments.add(shouyeMusicFragment);
        mFragments.add(shouyeHistoryFragment);
        mFragments.add(shouyeTalkShowFragment);
        mFragments.add(shouyeAmuseMentFragment);
        mFragments.add(shouyeGameFragment);
        mFragments.add(shouyeSoundBookFragment);
        setTitle();
        mMagicIndicator = mView.findViewById(R.id.main_indicator);
        mView_pager = mView.findViewById(R.id.view_pager);
        mainTitleViewPagerAdapter = new MainTitleViewPagerAdapter(getChildFragmentManager(), mFragments);
        mView_pager.setAdapter(mainTitleViewPagerAdapter);
        mView_pager.setSelected(true);
        mMagicIndicator.setBackgroundColor(0);
        mIndicatorAdapter = new IndicatorAdapter(mContext);
        mCommonNavigator = new CommonNavigator(getActivity());
        mIndicatorAdapter.setmList(mList);
        mCommonNavigator.setAdapter(mIndicatorAdapter);
        mMagicIndicator.setNavigator(mCommonNavigator);
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mView_pager);
        mainPresenter = MainPresenter.getsInstance();
        mainPresenter.registerViewCallBack(this);
        mainPresenter.getMainTitle();           //获取小标题

    }

    private void setFragment() {
        shouyeMusicFragment = new ShouyeMusicFragment();
        shouyeHistoryFragment = new ShouyeHistoryFragment();
        shouyeTalkShowFragment = new ShouyeTalkShowFragment();
        shouyeAmuseMentFragment = new ShouyeAmuseMentFragment();
        shouyeGameFragment = new ShouyeGameFragment();
        shouyeSoundBookFragment = new ShouyeSoundBookFragment();
    }

    @Override
    public void initEvent() {
        mIndicatorAdapter.setOnIndicatorTabClickListener(new IndicatorAdapter.onIndicatorTabClickListener() {
            @Override
            public void onTabClick(int position) {
                if (mView_pager != null) {
                    mView_pager.setCurrentItem(position);
                }
            }
        });
    }

    private void setTitle() {
        mList.add("音乐");
        mList.add("历史");
        mList.add("相声评书");
        mList.add("娱乐");
        mList.add("游戏动漫");
        mList.add("有声书");
    }


    @Override
    public int getLayoutId() {
        mContext = getActivity();
        return R.layout.main_fragment_main;
    }

    @Override
    public void onMainTitleListLoad(List<Category> list) {
        for (Category category : list) {

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mainPresenter != null) {
            mainPresenter.unRegisterViewCallBack(this);
        }
    }
}
