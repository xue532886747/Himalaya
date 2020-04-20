package com.example.myhimalaya.interfaces;

import com.example.myhimalaya.bean.NewAlbumBean;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbumsList;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;

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
     * 专辑
     */
    void getCategoryRecommend(List<CategoryRecommendAlbums> categoryRecommendAlbumses);




}
