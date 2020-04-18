package com.example.myhimalaya.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myhimalaya.R;
import com.example.myhimalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;

public class ViceTitleAdapter extends RecyclerView.Adapter {
    private static final String TAG = "ViceTitleAdapter1";
    private List<Tag> List = new ArrayList<>();
    private Context mContext;
    private int foldNumber;         //-26剩7个 刚好
    private boolean isFold = false; //是否是折叠


    public void setFoldNumber(int number) {
        this.foldNumber = number;
        if (foldNumber == 0) {
            isFold = true;
        } else {
            isFold = false;
        }
    }

    public ViceTitleAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Tag> list) {
        this.List = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_music_vice_title, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (position < List.size() - foldNumber) {
                ((ViewHolder) holder).tv_vice_text.setText(List.get(position).getTagName());
                ((ViewHolder) holder).tv_vice_text.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).iv_vice_text.setVisibility(View.GONE);
            } else {
                ((ViewHolder) holder).tv_vice_text.setVisibility(View.GONE);
                ((ViewHolder) holder).iv_vice_text.setVisibility(View.VISIBLE);
                if (isFold) {
                    Glide.with(mContext).load(R.mipmap.usually_sanjiao_up).into(((ViewHolder) holder).iv_vice_text);
                } else {
                    Glide.with(mContext).load(R.mipmap.usually_sanjiao_down).into(((ViewHolder) holder).iv_vice_text);
                }

                ((ViewHolder) holder).ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onFoldClickListener != null) {
                            onFoldClickListener.isFold(isFold);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return List == null ? 0 : List.size() - foldNumber + 1;
    }

    public interface onFoldClickListener {
        void isFold(boolean isFold);
    }

    private onFoldClickListener onFoldClickListener;

    public void setOnFoldClickListener(ViceTitleAdapter.onFoldClickListener onFoldClickListener) {
        this.onFoldClickListener = onFoldClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_vice_text;
        private ImageView iv_vice_text;
        private LinearLayout ll_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_vice_text = itemView.findViewById(R.id.tv_vice_text);
            iv_vice_text = itemView.findViewById(R.id.iv_vice_text);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }
}
