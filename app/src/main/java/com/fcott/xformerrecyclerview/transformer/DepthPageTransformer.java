package com.fcott.xformerrecyclerview.transformer;

import android.view.View;

/**
 * Created by fcott on 2017/7/25.
 */

public class DepthPageTransformer implements PageTransformer {
    private float scaleSize = 0.9f;
    @Override
    public void transformPage(View childView, float intervalPercent, float percent) {
        int pageWidth = childView.getWidth();
        if(Math.abs(Math.abs(percent)) > intervalPercent){
            childView.setAlpha(0);
        }else{
            childView.setAlpha(Math.abs(Math.abs(percent) - intervalPercent)/intervalPercent);
        }

        if(percent < 0){
            childView.setTranslationX(0);
        }else if (percent == 0) {
            childView.setTranslationX(0);
            childView.setScaleX(1);
            childView.setScaleY(1);
        } else if (percent > 0) { // (0,1]
            childView.setTranslationX(pageWidth * -percent * 0.6f);

            float scaleFactor = scaleSize
                    + (1 - scaleSize) * (1 - Math.abs(percent));
            childView.setScaleX(scaleFactor);
            childView.setScaleY(scaleFactor);
        }
    }

    public DepthPageTransformer setScaleSize(float scaleSize){
        this.scaleSize = scaleSize;
        return this;
    }
}
