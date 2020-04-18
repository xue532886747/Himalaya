package com.example.myhimalaya.presenter;

import android.support.annotation.Nullable;

import com.example.myhimalaya.interfaces.IMainCallBack;
import com.example.myhimalaya.interfaces.IMainPresenter;
import com.example.myhimalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPresenter implements IMainPresenter {

    private static final String TAG = "MainPresenter";
    private List<IMainCallBack> mCallBacks = new ArrayList<>();

    /**
     * 懒汉式 单例模式
     */
    private MainPresenter() {
    }

    private static MainPresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static MainPresenter getsInstance() {
        if (sInstance == null) {
            synchronized (MainPresenter.class) {
                if (sInstance == null) {
                    sInstance = new MainPresenter();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取title内容
     */
    @Override
    public void getMainTitle() {
        Map<String, String> map = new HashMap<String, String>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {


            @Override
            public void onSuccess(@Nullable CategoryList categoryList) {
                if (categoryList != null) {
                    List<Category> categories = categoryList.getCategories();
                    handlerTitleResult(categories);
//                    for (Category category : categories) {
//                        mList.add(category.getCategoryName());
//
//                    }
                }
            }


            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG, "i" + i + "s=" + s);
            }
        });
    }

    private void handlerTitleResult(List<Category> categories) {
        if (mCallBacks != null) {
            for (IMainCallBack mCallBack : mCallBacks) {
                mCallBack.onMainTitleListLoad(categories);
            }
        }
    }

    @Override
    public void registerViewCallBack(IMainCallBack iMainCallBack) {
        if (!mCallBacks.contains(iMainCallBack)) {
            mCallBacks.add(iMainCallBack);
        }
    }

    @Override
    public void unRegisterViewCallBack(IMainCallBack iMainCallBack) {
        if (mCallBacks != null) {
            mCallBacks.remove(iMainCallBack);
        }
    }
}
