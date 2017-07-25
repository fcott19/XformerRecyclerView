package com.fcott.xformerrecyclerview.transformer;

import android.view.View;

/**
 * Created by fcott on 2017/7/25.
 */

public class RotateDownPageTransformer implements PageTransformer {
    private float mRot;
    private  float rotMax = 20.0f;
    @Override
    public void transformPage(View childView, float intervalPercent, float percent) {
        if (percent <= percent || percent <= 1)
        {
            if (percent < 0)
            {
                mRot = (rotMax * percent);
                childView.setPivotX(childView.getMeasuredWidth() * 0.5f);
                childView.setPivotY( childView.getMeasuredHeight());
                childView.setRotation( mRot);
            } else
            {
                mRot = (rotMax * percent);
                childView.setPivotX( childView.getMeasuredWidth() * 0.5f);
                childView.setPivotY( childView.getMeasuredHeight());
                childView.setRotation( mRot);
            }
        }
    }

    public RotateDownPageTransformer setRotMax(float rotMax) {
        this.rotMax = rotMax;
        return this;
    }
}
