package com.example.myhimalaya.ui.fragment.shouye_fragment;

import android.content.Context;

import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myhimalaya.R;

import com.example.myhimalaya.adapters.AllClassificationAdapter;
import com.example.myhimalaya.adapters.HotTracksAdapter;
import com.example.myhimalaya.adapters.ViceTitleAdapter;
import com.example.myhimalaya.base.BaseFragment;
import com.example.myhimalaya.bean.NewAlbumBean;
import com.example.myhimalaya.interfaces.ITitleCallBack;
import com.example.myhimalaya.presenter.ViceTitlePresenter;
import com.example.myhimalaya.utils.GlideImageLoader;
import com.example.myhimalaya.utils.LogUtil;
import com.example.myhimalaya.views.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbumsList;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackHotList;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 音乐的fragment
 */
public class ShouyeMusicFragment extends BaseFragment implements ITitleCallBack, UILoader.onRetryOnClickListener {
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
    private RecyclerView recycle_view_hottracks;
    private HotTracksAdapter hotTracksAdapter;
    private List<Track> tracksList = new ArrayList<>();
    private UILoader uiLoader;


    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        uiLoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(inflater, container);
            }
        };
        mContext = getActivity();
        if (uiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) uiLoader.getParent()).removeView(uiLoader);
        }

        uiLoader.setOnRetryOnClickListener(this);
        return uiLoader;
    }

//    @Override
//    public int getLayoutId() {
//        return R.layout.fg_have_sounds_book;
//    }

    @Override
    public void initView(View mView) {
        mBanner = mView.findViewById(R.id.banner);
        initViewTitle(mView);
        initViewClassification(mView);
        initViewHotTracks(mView);
        initViewPresenter();
    }

    private View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fg_have_sounds_book, container, false);
        return view;
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
        recycle_view_music.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = UIUtil.dip2px(view.getContext(), 10);

            }
        });
        viceTitleAdapter = new ViceTitleAdapter(mContext);
        recycle_view_music.setAdapter(viceTitleAdapter);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recycle_view_music.setHasFixedSize(true);
        recycle_view_music.setNestedScrollingEnabled(false);
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
        recycle_view_classification.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = UIUtil.dip2px(view.getContext(), 10);

            }
        });
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recycle_view_music.setHasFixedSize(true);
        recycle_view_music.setNestedScrollingEnabled(false);
        allClassificationAdapter = new AllClassificationAdapter(mContext);
        recycle_view_classification.setAdapter(allClassificationAdapter);
    }


    /**
     * 热门实例化
     *
     * @param mView
     */
    private void initViewHotTracks(View mView) {
        recycle_view_hottracks = mView.findViewById(R.id.recycle_view_hottracks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle_view_hottracks.setLayoutManager(linearLayoutManager);
        recycle_view_hottracks.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = UIUtil.dip2px(view.getContext(), 10);

            }
        });
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recycle_view_music.setHasFixedSize(true);
        recycle_view_music.setNestedScrollingEnabled(false);
        hotTracksAdapter = new HotTracksAdapter(mContext);
        recycle_view_hottracks.setAdapter(hotTracksAdapter);
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
        viceTitlePresenter.getTrackHot("2", 1, 20);
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
        LogUtil.d(TAG, "走了几次");
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
    }

    /**
     * 推荐内容
     *
     * @param categoryRecommendAlbumses
     */
    @Override
    public void getCategoryRecommend(List<CategoryRecommendAlbums> categoryRecommendAlbumses) {
        allClassificationAdapter.setData(categoryRecommendAlbumses);
        allClassificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void getHotTracks(TrackHotList trackHotList) {
        List<Track> tracks = trackHotList.getTracks();
        tracksList.addAll(tracks);
        hotTracksAdapter.setData(tracksList);
        hotTracksAdapter.notifyDataSetChanged();
        uiLoader.updateStatus(UILoader.UIState.SUCCESS);
    }

    @Override
    public void onNetWorkError() {
        uiLoader.updateStatus(UILoader.UIState.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        uiLoader.updateStatus(UILoader.UIState.EMPTY);
    }

    @Override
    public void onLoding() {
        uiLoader.updateStatus(UILoader.UIState.LODING);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mBanner.stopAutoPlay();
    }

    /**
     * 网络不佳的时候 用户点击重试
     */
    @Override
    public void onRetryClick() {
        if (viceTitlePresenter!=null) {
            viceTitlePresenter.getViceTitle("2", "0");
            viceTitlePresenter.getBanner("2");
            viceTitlePresenter.getCategoryRecommend("2");
            viceTitlePresenter.getTrackHot("2", 1, 20);
        }

    }
}
