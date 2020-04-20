package com.example.myhimalaya.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myhimalaya.R;
import com.example.myhimalaya.bean.NewAlbumBean;
import com.example.myhimalaya.utils.LogUtil;
import com.example.myhimalaya.views.MyGridView;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.CategoryRecommendAlbums;

import java.util.ArrayList;
import java.util.List;

public class AllClassificationAdapter extends RecyclerView.Adapter {
    private static final String TAG = "AllClassificationAdapter";
    private Context mContext;
    private List<CategoryRecommendAlbums> List = new ArrayList<>();


    public AllClassificationAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<CategoryRecommendAlbums> list) {
        this.List = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_classification, parent, false);
        holder = new ViewHolder(view);
        LogUtil.d(TAG, "onCreateViewHolder().....");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            LogUtil.d(TAG, "onBindViewHolder().....");
            ((ViewHolder) holder).tv_catagroy_name.setText(List.get(position).getDisPlayTagName());
            if (List.get(position).isHasMore()) {
                ((ViewHolder) holder).tv_catagroy_more.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder) holder).tv_catagroy_more.setVisibility(View.GONE);
            }
            GridViewAdapter adapter = new GridViewAdapter(List.get(position).getAlbumList());
            ((ViewHolder) holder).gridview.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }

    @Override
    public int getItemCount() {
        return List == null ? 0 : List.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private MyGridView gridview;
        private TextView tv_catagroy_name;
        private TextView tv_catagroy_more;

        public ViewHolder(View itemView) {
            super(itemView);
            gridview = itemView.findViewById(R.id.gridview);
            tv_catagroy_name = itemView.findViewById(R.id.tv_catagroy_name);
            tv_catagroy_more = itemView.findViewById(R.id.tv_catagroy_more);
        }
    }


    class GridViewAdapter extends BaseAdapter {

        private List<Album> gList = new ArrayList<>();
        private LayoutInflater inflater;
        private ViewHolder1 viewHolder1;

        public GridViewAdapter(List<Album> list) {
            inflater = LayoutInflater.from(mContext);
            this.gList = list;

        }

        @Override
        public int getCount() {
            return gList == null ? 0 : gList.size();
        }

        @Override
        public Object getItem(int position) {
            return gList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LogUtil.d(TAG, "convertView().....");
                viewHolder1 = new ViewHolder1();
                convertView = inflater.inflate(R.layout.adp_ablum, parent, false);
                viewHolder1.tv_album_intro = convertView.findViewById(R.id.tv_album_intro);
                viewHolder1.iv_cover_url_large = convertView.findViewById(R.id.iv_cover_url_large);
                viewHolder1.tv_play_count = convertView.findViewById(R.id.tv_play_count);
                convertView.setTag(viewHolder1);
            } else {
                viewHolder1 = (ViewHolder1) convertView.getTag();
            }
            LogUtil.d(TAG, "gList.get(position).getAlbumIntro() = " + gList.get(position).getAlbumIntro());
            viewHolder1.tv_album_intro.setText(gList.get(position).getAlbumTitle());
            viewHolder1.tv_play_count.setText(gList.get(position).getPlayCount() + "");
            Glide.with(mContext).load(gList.get(position).getCoverUrlLarge()).into(viewHolder1.iv_cover_url_large);
            return convertView;
        }

        class ViewHolder1 {
            private TextView tv_album_intro;        //专辑简介
            private TextView tv_play_count;            //专辑播放次数
            private ImageView iv_cover_url_large;   //专辑封面大，无则返回空字符串""

        }

    }
}
