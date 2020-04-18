package com.example.myhimalaya.presenter;

import android.support.annotation.Nullable;

import com.example.myhimalaya.interfaces.ITitleCallBack;
import com.example.myhimalaya.interfaces.ITtilePresenter;
import com.example.myhimalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViceTitlePresenter implements ITtilePresenter {

    private static final String TAG = "ViceTitlePresenter";
    private ITitleCallBack iTitleCallBack;

    /**
     * 懒汉式 单例模式
     */
    private ViceTitlePresenter() {
    }

    private static ViceTitlePresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static ViceTitlePresenter getsInstance() {
        if (sInstance == null) {
            synchronized (ViceTitlePresenter.class) {
                if (sInstance == null) {

                    sInstance = new ViceTitlePresenter();
                }
            }
        }
        return sInstance;
    }


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
                iTitleCallBack.getViceTitleLoad(tagList);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void setViceCallBack(ITitleCallBack iTitleCallBack) {
        this.iTitleCallBack = iTitleCallBack;
    }
}
