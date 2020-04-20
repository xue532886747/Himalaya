package com.example.myhimalaya.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.myhimalaya.R;
import com.example.myhimalaya.application.MyApplication;

public abstract class UILoader extends FrameLayout {

    private View loadingView;
    private View successView;
    private View netWorkErrorView;
    private View emptyView;


    public enum UIState {
        LODING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    public UIState mCurrentStates = UIState.NONE;

    public UILoader(Context context) {
        this(context, null);
    }

    public UILoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        switchUIByCurrentStates();
    }

    public void updateStatus(UIState state) {
        mCurrentStates = state;
        //更新UI
        MyApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStates();
            }
        });
    }


    private void switchUIByCurrentStates() {
        //加载中
        if (loadingView == null) {
            loadingView = getLoadingView();
            addView(loadingView);
        }
        //根据状态设置是否可见
        loadingView.setVisibility(mCurrentStates == UIState.LODING ? VISIBLE : GONE);

        //成功
        if (successView == null) {
            successView = getSuccessView(this);
            addView(successView);
        }
        //根据状态设置是否可见
        successView.setVisibility(mCurrentStates == UIState.SUCCESS ? VISIBLE : GONE);

        //网络错误
        if (netWorkErrorView == null) {
            netWorkErrorView = getNetWorkErrorView();
            addView(netWorkErrorView);
        }
        //根据状态设置是否可见
        netWorkErrorView.setVisibility(mCurrentStates == UIState.NETWORK_ERROR ? VISIBLE : GONE);

        //数据为null的界面
        if (emptyView == null) {
            emptyView = getEmptyError();
            addView(emptyView);
        }
        //根据状态设置是否可见
        emptyView.setVisibility(mCurrentStates == UIState.EMPTY ? VISIBLE : GONE);

    }

    /**
     * 内容为null
     *
     * @return
     */
    private View getEmptyError() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
    }

    private View getNetWorkErrorView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_networkerror_view, this, false);
        LinearLayout network_error = view.findViewById(R.id.network_error);
        network_error.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRetryOnClickListener != null) {
                    onRetryOnClickListener.onRetryClick();
                }
            }
        });
        return view;
    }

    protected abstract View getSuccessView(ViewGroup viewGroup);

    private View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loding_view, this, false);
    }

    private onRetryOnClickListener onRetryOnClickListener;

    public void setOnRetryOnClickListener(UILoader.onRetryOnClickListener onRetryOnClickListener) {
        this.onRetryOnClickListener = onRetryOnClickListener;
    }

    public interface onRetryOnClickListener {
        void onRetryClick();
    }
}
