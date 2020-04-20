package com.example.myhimalaya.ui.fragment.main_fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myhimalaya.R;
import com.example.myhimalaya.base.BaseFragment;

public class MainFindFragment extends BaseFragment {
    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.main_fragment_shouye, container, false);
        return view;
    }

    @Override
    public void initView(View mView) {

    }

    @Override
    public void initEvent() {

    }

//    @Override
//    public int getLayoutId() {
//        return R.layout.main_fragment_shouye;
//    }
}
