package com.example.myhimalaya.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.myhimalaya.R;
import com.example.myhimalaya.views.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 指示器
 */
public class IndicatorAdapter extends CommonNavigatorAdapter {
    private Context mContext;
    private onIndicatorTabClickListener onIndicatorTabClickListener;
    public IndicatorAdapter(Context context) {
        this.mContext = context;
    }

    private List<String> mList = new ArrayList<>();

    public void setmList(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setText(mList.get(index));
        simplePagerTitleView.setTextSize(18);
        simplePagerTitleView.setNormalColor(Color.GRAY);
        simplePagerTitleView.setSelectedColor(Color.BLACK);
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIndicatorTabClickListener!=null) {
                    onIndicatorTabClickListener.onTabClick(index);
                }
            }
        });

        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        BezierPagerIndicator indicator = new BezierPagerIndicator(context);
        indicator.setColors(R.color.red_f51600);
        return indicator;
    }


    public void setOnIndicatorTabClickListener(IndicatorAdapter.onIndicatorTabClickListener onIndicatorTabClickListener) {
        this.onIndicatorTabClickListener = onIndicatorTabClickListener;
    }

    public interface onIndicatorTabClickListener {
        void onTabClick(int position);
    }
}
