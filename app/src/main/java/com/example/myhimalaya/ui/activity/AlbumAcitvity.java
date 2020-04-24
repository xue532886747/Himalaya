package com.example.myhimalaya.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myhimalaya.R;
import com.example.myhimalaya.base.BaseActivity;
import com.example.myhimalaya.views.UILoader;
import com.jaeger.library.StatusBarUtil;

public class AlbumAcitvity extends BaseActivity {
    private UILoader uiLoader;
    private RecyclerView recyclerView;
    private Context mContext;

    @Override
    protected int setLayoutId() {
        mContext = this;
        return R.layout.activity_album;
    }



    @Override
    public void initView() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        StatusBarUtil.setDarkMode(this);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(AlbumAcitvity.this).inflate(R.layout.item_behavior, parent, false);
                ViewHolder viewHolder = new ViewHolder(itemView);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 100;
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                public ViewHolder(View itemView) {
                    super(itemView);
                }
            }

        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
