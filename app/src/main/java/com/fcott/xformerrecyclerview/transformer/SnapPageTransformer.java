package com.fcott.xformerrecyclerview.transformer;

import android.view.View;

/**
 * Created by fcott on 2017/7/25.
 */

public class SnapPageTransformer implements PageTransformer {
    private float minAlpha = 0.5f;
    private float scaleSize = 0.9f;
    @Override
    public void transformPage(View page,final float intervalPercent, float percent) {
        float scaleFactor = (1 - scaleSize) * (1 - Math.abs(percent)) + scaleSize;
        if (scaleFactor < scaleSize) {
            scaleFactor = scaleSize;
        }
        page.setScaleX(scaleFactor);
        page.setScaleY(scaleFactor);
        page.setAlpha(minAlpha + (scaleFactor - scaleSize)
                / (1 - scaleSize) * (1 - minAlpha));
    }

    public SnapPageTransformer setMinAlpha(float minAlpha) {
        this.minAlpha = minAlpha;
        return this;
    }

    public SnapPageTransformer setScaleSize(float scaleSize) {
        this.scaleSize = scaleSize;
        return this;
    }
}
