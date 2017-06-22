package com.gengqiquan.adapter.interfaces;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by 耿 on 2016/9/7.
 */
public interface Holder {
    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId);

    /**
     * 为TextView设置字符�?
     *
     * @param viewId
     * @param text
     * @return
     */
    public Holder setText(int viewId, String text) ;
    /**
     * 为View设置点击事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public Holder setClick(int viewId, View.OnClickListener listener) ;

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public Holder setImageResource(int viewId, int drawableId);

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public Holder setImageBitmap(int viewId, Bitmap bm);

    public View getItemView();
}
