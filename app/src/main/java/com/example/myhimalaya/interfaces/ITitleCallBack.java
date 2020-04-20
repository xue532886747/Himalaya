package com.example.myhimalaya.interfaces;

import com.example.myhimalaya.bean.NewAlbumBean;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbumsList;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.TrackHotList;

import java.util.List;

public interface ITitleCallBack {

    /**
     * 获取副标题内容
     *
     * @param tagList
     */
    void getViceTitleLoad(TagList tagList);

    /**
     * 获取轮播图
     *
     * @param bann
     */
    void getBannerLoad(BannerV2List bann);

    /**
     * 根据分类推荐
     */
    void getCategoryRecommend(List<CategoryRecommendAlbums> categoryRecommendAlbumses);

    /**
     * 获取某个分类某个声音下的热门声音列表
     *
     * @param trackHotList
     */
    void getHotTracks(TrackHotList trackHotList);

    /**
     * 网络错误
     */
    void onNetWorkError();

    /**
     *  数据为null
     */
    void onEmpty();

    /**
     * 正在加载
     */
    void onLoding();
}
