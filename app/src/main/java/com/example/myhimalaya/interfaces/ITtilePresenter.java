package com.example.myhimalaya.interfaces;

public interface ITtilePresenter {
    /**
     * 根据id和type获取到副标题
     *
     * @param category_id
     * @param type
     */
    void getViceTitle(String category_id, String type);

    /**
     * 获取轮播图
     *
     * @param category_id
     */
    void getBanner(String category_id);

    /**
     * 根据分类和标签获取某个分类某个标签下的专辑列表
     *
     * @param category_id
     */
    void getCategoryRecommend(String category_id);

    /**
     * 此接口用于获取某个分类某个声音下的热门声音列表
     *
     * @param category_id
     * @param page        返回第几页，必须大于等于1，不填默认为1
     * @param count       每页多少条，默认20，最多不超过200
     */
    void getTrackHot(String category_id, int page, int count);

    void setViceCallBack(ITitleCallBack iTitleCallBack);


}
