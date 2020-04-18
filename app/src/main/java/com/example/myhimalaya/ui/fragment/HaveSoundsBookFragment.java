package com.example.myhimalaya.ui.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.myhimalaya.R;
import com.example.myhimalaya.adapters.IndicatorAdapter;
import com.example.myhimalaya.adapters.MainTitleViewPagerAdapter;
import com.example.myhimalaya.base.BaseFragment;
import com.example.myhimalaya.interfaces.IMainCallBack;
import com.example.myhimalaya.presenter.MainPresenter;
import com.ximalaya.ting.android.opensdk.model.category.Category;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import static com.example.myhimalaya.utils.Constants.GET_TITLE;


/**
 * 有声书的fragment
 */
public class HaveSoundsBookFragment extends BaseFragment  implements IMainCallBack {
    private static final String TAG = "HaveSoundsBookFragment";

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
    public void initView(View mView) {
        for (int i = 0; i < 31; i++) {
            mFragments.add(new HaveSoundsBookFragment());
        }
        mMagicIndicator = mView.findViewById(R.id.main_indicator);
        mView_pager = mView.findViewById(R.id.view_pager);
        mainTitleViewPagerAdapter = new MainTitleViewPagerAdapter(getChildFragmentManager(), mFragments);
        mView_pager.setAdapter(mainTitleViewPagerAdapter);
        mView_pager.setSelected(true);
        mMagicIndicator.setBackgroundColor(0);
        mIndicatorAdapter = new IndicatorAdapter(mContext);
        mCommonNavigator = new CommonNavigator(getActivity());
        mCommonNavigator.setAdapter(mIndicatorAdapter);
        mMagicIndicator.setNavigator(mCommonNavigator);
        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mView_pager);
        mainPresenter = MainPresenter.getsInstance();
        mainPresenter.registerViewCallBack(this);
        mainPresenter.getMainTitle();


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

    @Override
    public int getLayoutId() {
        mContext= getActivity();
        return R.layout.fg_have_sounds_book;
    }

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
    public void onDestroy() {
        super.onDestroy();
        if (mainPresenter != null) {
            mainPresenter.unRegisterViewCallBack(this);
        }

    }
}
