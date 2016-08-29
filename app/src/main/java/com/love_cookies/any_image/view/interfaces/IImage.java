package com.love_cookies.any_image.view.interfaces;

import com.love_cookies.any_image.model.bean.ImageBean;

/**
 * Created by xiekun on 2016/8/29.
 *
 * 图片页View接口
 */
public interface IImage {
    /**
     * 获取图片列表
     */
    void getImageList();

    /**
     * 获取图片列表成功
     * @param imageBean
     */
    void getImageListSuccess(ImageBean imageBean);

    /**
     * 获取图片列表失败
     */
    void getImageListFailed();
}
