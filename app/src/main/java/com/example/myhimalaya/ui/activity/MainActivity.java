package com.example.myhimalaya.ui.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.myhimalaya.R;
import com.example.myhimalaya.adapters.IndicatorAdapter;
import com.example.myhimalaya.adapters.MainTitleViewPagerAdapter;
import com.example.myhimalaya.base.BaseActivity;
import com.example.myhimalaya.interfaces.IMainCallBack;
import com.example.myhimalaya.presenter.MainPresenter;
import com.example.myhimalaya.ui.fragment.HaveSoundsBookFragment;
import com.example.myhimalaya.utils.LogUtil;
import com.jaeger.library.StatusBarUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.myhimalaya.utils.Constants.GET_TITLE;

public class MainActivity extends BaseActivity implements IMainCallBack {

    private static final String TAG = "MainActivity";
    private MagicIndicator mMagicIndicator;
    private CommonNavigator mCommonNavigator;
    private Context mContext;
    private IndicatorAdapter mIndicatorAdapter;
    private ViewPager mView_pager;
    private List<String> mList = new ArrayList<>();
    private MainTitleViewPagerAdapter mainTitleViewPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MainPresenter mainPresenter;

    /**
     * 接收喜马拉雅回调 刷新指示器
     */
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_TITLE:
                    mIndicatorAdapter.setmList(mList);
                    mIndicatorAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected int setLayoutId() {
        mContext = this;
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        immersive();
        for (int i = 0; i < 31; i++) {
            mFragments.add(new HaveSoundsBookFragment());
        }
        mMagicIndicator = findViewById(R.id.main_indicator);
        mView_pager = findViewById(R.id.view_pager);
        mainTitleViewPagerAdapter = new MainTitleViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mView_pager.setAdapter(mainTitleViewPagerAdapter);
        mView_pager.setSelected(true);
        mMagicIndicator.setBackgroundColor(0);
        mIndicatorAdapter = new IndicatorAdapter(mContext);
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigator.setAdapter(mIndicatorAdapter);
        mMagicIndicator.setNavigator(mCommonNavigator);
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mView_pager);
        mainPresenter = MainPresenter.getsInstance();
        mainPresenter.registerViewCallBack(this);
        mainPresenter.getMainTitle();
    }

    @Override
    public void initData() {
        initTitle();
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


    /**
     * 此接口用于获取点播资源的内容分类，如：“有声小说”、“相声评书”等。 这块要抽出来了
     */
    private void initTitle() {

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put(DTransferConstants.CATEGORY_ID, "2");
        map1.put(DTransferConstants.TYPE, "0");
        CommonRequest.getTags(map1, new IDataCallBack<TagList>() {
            @Override
            public void onSuccess(@Nullable TagList tagList) {
                assert tagList != null;
                List<Tag> list = tagList.getTagList();
                LogUtil.d(TAG, "tagList = " + list.toString());
            }

            @Override
            public void onError(int i, String s) {

            }
        });


        Map<String, String> map2 = new HashMap<String, String>();
        map2.put(DTransferConstants.CATEGORY_ID, "2");
        map2.put(DTransferConstants.TAG_NAME, "欧美");
        map2.put(DTransferConstants.CALC_DIMENSION, "1");
        CommonRequest.getAlbumList(map2, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(@Nullable AlbumList albumList) {
                LogUtil.d(TAG, "albumList = " + albumList.toString());
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 沉浸式白色
     */
    private void immersive() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        StatusBarUtil.setLightMode(this);
    }

    /**
     * 获取列表
     * @param list
     */
    @Override
    public void onMainTitleListLoad(List<Category> list) {
        for (Category category : list) {
            mList.add(category.getCategoryName());
            Message message = Message.obtain();
            message.what = GET_TITLE;
            mHandler.sendMessage(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 取消注册 避免内存泄露
         */
        if (mainPresenter != null) {
            mainPresenter.unRegisterViewCallBack(this);
        }

    }
}
