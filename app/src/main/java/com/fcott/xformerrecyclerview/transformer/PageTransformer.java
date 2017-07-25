package com.fcott.xformerrecyclerview.transformer;

import android.view.View;

/**
 * Created by fcott on 2017/7/25.
 */

public interface PageTransformer {
    /**
     *
     * @param childView
     * @param intervalPercent 每个childview之间相差的percent
     * @param percent  位置系数，percent为0时，view于屏幕正中，percent为-1时，view的中心为（0，height/2）,percent为1时，view的中心为（width，height/2）
     */
    void transformPage(View childView, final float intervalPercent, float percent);
}
