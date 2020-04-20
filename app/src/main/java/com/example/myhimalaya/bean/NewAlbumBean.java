package com.example.myhimalaya.bean;


import com.ximalaya.ting.android.opensdk.datatrasfer.XimalayaResponse;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;


public class NewAlbumBean extends XimalayaResponse {

    private String Name;

    private List<Album> AlbumList;

    private String CategoryId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Album> getAlbumList() {
        return AlbumList;
    }

    public void setAlbumList(List<Album> albumList) {
        AlbumList = albumList;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    @Override
    public String toString() {
        return "NewAlbumBean{" +
                "Name='" + Name + '\'' +
                ", AlbumList=" + AlbumList +
                ", CategoryId='" + CategoryId + '\'' +
                '}';
    }
}
