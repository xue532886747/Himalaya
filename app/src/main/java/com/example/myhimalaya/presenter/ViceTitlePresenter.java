package com.example.myhimalaya.presenter;

import android.support.annotation.Nullable;

import com.example.myhimalaya.bean.NewAlbumBean;
import com.example.myhimalaya.interfaces.ITitleCallBack;
import com.example.myhimalaya.interfaces.ITtilePresenter;
import com.example.myhimalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbumsList;
import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViceTitlePresenter implements ITtilePresenter {

    private static final String TAG = "ViceTitlePresenter";
    private ITitleCallBack iTitleCallBack;


    @Override
    public void getViceTitle(String category_id, String type) {
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put(DTransferConstants.CATEGORY_ID, category_id);
        map1.put(DTransferConstants.TYPE, type);
        CommonRequest.getTags(map1, new IDataCallBack<TagList>() {
            @Override
            public void onSuccess(@Nullable TagList tagList) {
                assert tagList != null;
                List<Tag> list = tagList.getTagList();
                LogUtil.d(TAG, "tagList = " + list.toString());
                if (iTitleCallBack != null) {
                    iTitleCallBack.getViceTitleLoad(tagList);
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void getBanner(String category_id) {
        /**
         * 获取轮播图
         */
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, category_id);
        CommonRequest.getCategoryBannersV2(map, new IDataCallBack<BannerV2List>() {
            @Override
            public void onSuccess(@Nullable BannerV2List bannerV2List) {
                if (iTitleCallBack != null) {
                    iTitleCallBack.getBannerLoad(bannerV2List);
                }
                LogUtil.d(TAG, "bannerV2List = " + bannerV2List.toString());
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "bannerV2List =  i" + i + " , s = " + s);
            }
        });

    }

    @Override
    public void getCategoryRecommend(String category_id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, category_id);
        map.put(DTransferConstants.DISPLAY_COUNT, "6");
        CommonRequest.getCategoryRecommendAlbums(map, new IDataCallBack<CategoryRecommendAlbumsList>() {
            @Override
            public void onSuccess(@Nullable CategoryRecommendAlbumsList categoryRecommendAlbumsList) {
                List<CategoryRecommendAlbums> categoryRecommendAlbumses = categoryRecommendAlbumsList.getCategoryRecommendAlbumses();
                iTitleCallBack.getCategoryRecommend(categoryRecommendAlbumses);

            }

            @Override
            public void onError(int i, String s) {

            }

        });

    }

    @Override
    public void getCommand(String category_id, String display_count) {

    }

    @Override
    public void setViceCallBack(ITitleCallBack iTitleCallBack) {
        this.iTitleCallBack = iTitleCallBack;
    }
}
