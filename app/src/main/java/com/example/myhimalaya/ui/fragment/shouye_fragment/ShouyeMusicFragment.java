package com.example.myhimalaya.ui.fragment.shouye_fragment;

import android.content.Context;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myhimalaya.R;

import com.example.myhimalaya.adapters.ViceTitleAdapter;
import com.example.myhimalaya.base.BaseFragment;
import com.example.myhimalaya.interfaces.ITitleCallBack;
import com.example.myhimalaya.presenter.ViceTitlePresenter;
import com.example.myhimalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;


/**
 * 音乐的fragment
 */
public class ShouyeMusicFragment extends BaseFragment implements ITitleCallBack {
    private static final String TAG = "ShouyeMusicFragment";
    private Context mContext;
    private RecyclerView recycle_view_music;
    private LinearLayoutManager linearLayoutManager;
    private ViceTitlePresenter viceTitlePresenter;
    private ViceTitleAdapter viceTitleAdapter;


    @Override
    public void initView(View mView) {
        recycle_view_music = mView.findViewById(R.id.recycle_view_music);
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
        viceTitlePresenter = ViceTitlePresenter.getsInstance();
        viceTitlePresenter.setViceCallBack(this);
        viceTitlePresenter.getViceTitle("2", "0");

    }

    @Override
    public void initEvent() {
        viceTitleAdapter.setOnFoldClickListener(new ViceTitleAdapter.onFoldClickListener() {
            @Override
            public void isFold(boolean isFold) {
                if (isFold) {       //true 需要折叠
                    viceTitleAdapter.setFoldNumber(26);
                } else {              //需要打开
                    viceTitleAdapter.setFoldNumber(0);
                }
                viceTitleAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getLayoutId() {
        mContext = getActivity();
        return R.layout.fg_have_sounds_book;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void getViceTitleLoad(TagList tagList) {
        LogUtil.d(TAG, "tagList = " + tagList.toString());
        List<Tag> list = tagList.getTagList();
        if (viceTitleAdapter != null) {
            viceTitleAdapter.setData(list);
            viceTitleAdapter.setFoldNumber(26);
            viceTitleAdapter.notifyDataSetChanged();
        }
    }
}
