package com.example.myhimalaya.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myhimalaya.R;
import com.example.myhimalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

public class HotTracksAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HotTracksAdapter";
    private Context mContext;
    private List<Track> mList = new ArrayList<>();

    public HotTracksAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Track> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_hottracks, parent, false);
        holder = new ViewHolder(view);
        LogUtil.d(TAG, "onCreateViewHolder().....");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).tv_kind.setText(mList.get(position).getTrackTitle());
            ((ViewHolder) holder).tv_track_intro.setText(mList.get(position).getTrackIntro());
            Glide.with(mContext).load(mList.get(position).getCoverUrlLarge()).into(((ViewHolder) holder).iv_cover_url_large);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_cover_url_large;
        private TextView tv_kind;
        private TextView tv_track_intro;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_cover_url_large = itemView.findViewById(R.id.iv_cover_url_large);
            tv_kind = itemView.findViewById(R.id.tv_kind);
            tv_track_intro = itemView.findViewById(R.id.tv_track_intro);
        }
    }
}
