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
     * 获取运营人员为某个分类配置的标签维度专辑推荐模块列表
     *
     * @param category_id
     * @param display_count
     */

    void getCommand(String category_id, String display_count);

    void setViceCallBack(ITitleCallBack iTitleCallBack);


}
