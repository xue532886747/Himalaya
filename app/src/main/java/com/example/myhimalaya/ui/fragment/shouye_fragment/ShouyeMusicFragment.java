package com.example.myhimalaya.ui.fragment.shouye_fragment;

import android.content.Context;

import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.myhimalaya.R;

import com.example.myhimalaya.adapters.AllClassificationAdapter;
import com.example.myhimalaya.adapters.ViceTitleAdapter;
import com.example.myhimalaya.base.BaseFragment;
import com.example.myhimalaya.bean.NewAlbumBean;
import com.example.myhimalaya.interfaces.ITitleCallBack;
import com.example.myhimalaya.presenter.ViceTitlePresenter;
import com.example.myhimalaya.utils.GlideImageLoader;
import com.example.myhimalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbumsList;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 音乐的fragment
 */
public class ShouyeMusicFragment extends BaseFragment implements ITitleCallBack {
    private static final String TAG = "ShouyeMusicFragment";
    private Context mContext;
    private RecyclerView recycle_view_music;
    private ViceTitlePresenter viceTitlePresenter;
    private ViceTitleAdapter viceTitleAdapter;
    private int number;
    private Banner mBanner;
    private List<String> mBannerList = new ArrayList<>();
    private RecyclerView recycle_view_classification;
    private AllClassificationAdapter allClassificationAdapter;
    private RecyclerView recycle_view_recommand;

    @Override
    public void initView(View mView) {
        mBanner = mView.findViewById(R.id.banner);
        initViewTitle(mView);
        initViewClassification(mView);
        initViewRecommand(mView);
        initViewPresenter();
    }

    /**
     * 小标题的实例化
     *
     * @param mView
     */
    private void initViewTitle(View mView) {
        recycle_view_music = mView.findViewById(R.id.recycle_view_music);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 4);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_view_music.setLayoutManager(linearLayoutManager);
        viceTitleAdapter = new ViceTitleAdapter(mContext);
        recycle_view_music.setAdapter(viceTitleAdapter);
        recycle_view_music.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = UIUtil.dip2px(view.getContext(), 10);

            }
        });
    }

    /**
     * 专辑的实例化
     *
     * @param mView
     */
    private void initViewClassification(View mView) {
        recycle_view_classification = mView.findViewById(R.id.recycle_view_classification);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_view_classification.setLayoutManager(linearLayoutManager);
        allClassificationAdapter = new AllClassificationAdapter(mContext);
        recycle_view_classification.setAdapter(allClassificationAdapter);
        recycle_view_classification.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = UIUtil.dip2px(view.getContext(), 10);

            }
        });
    }


    /**
     * 推荐的实例化
     *
     * @param mView
     */
    private void initViewRecommand(View mView) {
        recycle_view_recommand = mView.findViewById(R.id.recycle_view_recommand);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_view_recommand.setLayoutManager(linearLayoutManager);
        recycle_view_recommand.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = UIUtil.dip2px(view.getContext(), 10);

            }
        });
    }

    /**
     * presenter的实例化
     */
    private void initViewPresenter() {
        viceTitlePresenter = new ViceTitlePresenter();
        viceTitlePresenter.setViceCallBack(this);
        viceTitlePresenter.getViceTitle("2", "0");
        viceTitlePresenter.getBanner("2");
        viceTitlePresenter.getCategoryRecommend("2");

    }

    @Override
    public void initEvent() {
        viceTitleAdapter.setOnFoldClickListener(new ViceTitleAdapter.onFoldClickListener() {
            @Override
            public void isFold(boolean isFold) {
                if (isFold) {       //true 需要折叠
                    viceTitleAdapter.setFoldNumber(number);
                } else {              //需要打开
                    viceTitleAdapter.setFoldNumber(0);
                }
                viceTitleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getLayoutId() {
        mContext = getActivity();
        return R.layout.fg_have_sounds_book;
    }

    @Override
    public void getViceTitleLoad(TagList tagList) {
        LogUtil.d(TAG, "tagList = " + tagList.toString());
        List<Tag> list = tagList.getTagList();
        if (viceTitleAdapter != null) {
            viceTitleAdapter.setData(list);
            if (list.size() > 8) {
                number = list.size() - 7;
                viceTitleAdapter.setFoldNumber(number);
            } else {
                viceTitleAdapter.setFoldNumber(0);
            }
            viceTitleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getBannerLoad(BannerV2List bann) {
        if (mBannerList != null) {
            mBannerList.clear();
        }
        List<BannerV2> bannerV2s = bann.getBannerV2s();
        LogUtil.d(TAG, "TAG 原来的数据等于 = " + bannerV2s.size());
        for (BannerV2 bannerV2 : bannerV2s) {
            mBannerList.add(bannerV2.getBannerUrl());
        }
        LogUtil.d(TAG, TAG + "TAG 重新创建的集合 = " + mBannerList.size());
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(mBannerList);
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                LogUtil.d(TAG, "1mBannerList = " + mBannerList.get(i));
                LogUtil.d(TAG, "1mBannerList.size() = " + mBannerList.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void getCategoryRecommend(List<CategoryRecommendAlbums> categoryRecommendAlbumses) {
        allClassificationAdapter.setData(categoryRecommendAlbumses);
        allClassificationAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mBanner.stopAutoPlay();
    }
}
