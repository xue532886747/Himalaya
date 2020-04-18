package com.example.myhimalaya.interfaces;

public interface ITtilePresenter {
    /**
     * 根据id和type获取到副标题
     *
     * @param category_id
     * @param type
     */
    void getViceTitle(String category_id, String type);

    void setViceCallBack(ITitleCallBack iTitleCallBack);
}
