package com.example.myhimalaya.interfaces;

import com.ximalaya.ting.android.opensdk.model.banner.BannerV2List;
import com.ximalaya.ting.android.opensdk.model.category.Category;

import java.util.List;

public interface IMainCallBack {
    /**
     * 获取到首页的标题内容
     *
     * @param list
     */
    void onMainTitleListLoad(List<Category> list);



}
