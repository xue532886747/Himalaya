package com.example.myhimalaya.ui.fragment.shouye_fragment;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myhimalaya.R;
import com.example.myhimalaya.adapters.ViceTitleAdapter;
import com.example.myhimalaya.base.BaseFragment;
import com.example.myhimalaya.bean.NewAlbumBean;
import com.example.myhimalaya.interfaces.ITitleCallBack;
import com.example.myhimalaya.presenter.ViceTitlePresenter;
import com.example.myhimalaya.utils.GlideImageLoader;
import com.example.myhimalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.TrackHotList;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class ShouyeGameFragment extends BaseFragment implements ITitleCallBack {
    private static final String TAG = "ShouyeGameFragment";
    private Context mContext;
    private RecyclerView recycle_view_music;
    private LinearLayoutManager linearLayoutManager;
    private ViceTitlePresenter viceTitlePresenter;
    private ViceTitleAdapter viceTitleAdapter;
    private int number;
    private Banner mBanner;
    private List<String> mBannerList = new ArrayList<>();

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fg_have_sounds_book, container, false);
        return view;
    }

    @Override
    public void initView(View mView) {
        recycle_view_music = mView.findViewById(R.id.recycle_view_music);
        mBanner = mView.findViewById(R.id.banner);
        linearLayoutManager = new GridLayoutManager(mContext, 4);
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
        viceTitlePresenter = new ViceTitlePresenter();
        viceTitlePresenter.setViceCallBack(this);
        viceTitlePresenter.getViceTitle("24", "0");
        viceTitlePresenter.getBanner("24");
    }

    @Override
    public void initEvent() {
//        viceTitleAdapter.setOnFoldClickListener(new ViceTitleAdapter.onFoldClickListener() {
//            @Override
//            public void isFold(boolean isFold) {
//                if (isFold) {       //true 需要折叠
//                    viceTitleAdapter.setFoldNumber(number);
//                } else {              //需要打开
//                    viceTitleAdapter.setFoldNumber(0);
//                }
//                viceTitleAdapter.notifyDataSetChanged();
//            }
//        });
    }

//    @Override
//    public int getLayoutId() {
//        mContext = getActivity();
//        return R.layout.fg_have_sounds_book;
//    }


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
        if(mBannerList!=null){
            mBannerList.clear();
        }
        List<BannerV2> bannerV2s = bann.getBannerV2s();
        for (BannerV2 bannerV2 : bannerV2s) {
            mBannerList.add(bannerV2.getBannerUrl());
            LogUtil.d(TAG, TAG + "mBannerList = " + bannerV2.getBannerUrl());
        }
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(mBannerList);
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();
    }

    @Override
    public void getCategoryRecommend(List<CategoryRecommendAlbums> categoryRecommendAlbumses) {

    }

    @Override
    public void getHotTracks(TrackHotList trackHotList) {

    }

    @Override
    public void onNetWorkError() {

    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void onLoding() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mBanner.stopAutoPlay();
    }
}
