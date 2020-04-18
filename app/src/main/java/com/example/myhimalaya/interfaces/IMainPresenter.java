package com.example.myhimalaya.interfaces;

public interface IMainPresenter {
    /**
     * 获取首页标题
     */
    void getMainTitle();

    /**
     * 这个方法用于注册ui的回调
     *
     * @param iMainCallBack
     */
    void registerViewCallBack(IMainCallBack iMainCallBack);

    /**
     * 取消ui 的回调注册
     *
     * @param iMainCallBack
     */
    void unRegisterViewCallBack(IMainCallBack iMainCallBack);
}
