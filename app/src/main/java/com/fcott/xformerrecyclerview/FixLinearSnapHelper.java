package com.fcott.xformerrecyclerview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fcott on 2017/7/25.
 */

public class FixLinearSnapHelper extends LinearSnapHelper {

    private OrientationHelper mVerticalHelper;

    private OrientationHelper mHorizontalHelper;

    private RecyclerView mRecyclerView;

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];

        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToCenter(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToCenter(targetView, getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        this.mRecyclerView = recyclerView;
        super.attachToRecyclerView(recyclerView);
    }

    private int distanceToCenter(View targetView, OrientationHelper helper) {
        //如果已经滚动到尽头 并且判断是否是第一个item或者是最后一个，直接返回0，不用多余的滚动了
        if ((helper.getDecoratedStart(targetView) == 0 && mRecyclerView.getChildAdapterPosition(targetView) == 0)
                || (helper.getDecoratedEnd(targetView) == helper.getEndAfterPadding()
                && mRecyclerView.getChildAdapterPosition(targetView) == mRecyclerView.getAdapter().getItemCount() - 1) )
            return 0;

        int viewCenter = helper.getDecoratedStart(targetView) + (helper.getDecoratedEnd(targetView) - helper.getDecoratedStart(targetView))/2;
        int correctCenter = (helper.getEndAfterPadding() - helper.getStartAfterPadding())/2;
        return viewCenter - correctCenter;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return findCenterView(layoutManager, getVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            return findCenterView(layoutManager, getHorizontalHelper(layoutManager));
        }
        return null;
    }

    /**
     * 寻找离屏幕中心最近的childview 因为第一页和最后一页需要居中 前面在（以水平方向第一页为例）左侧添加了margin
     * 现在计算childview的宽度时，应把添加上去的margin去掉
     * @param layoutManager
     * @param helper
     * @return
     */
    private View findCenterView(RecyclerView.LayoutManager layoutManager,
                                OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        final int center;
        if (layoutManager.getClipToPadding()) {
            center = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        } else {
            center = helper.getEnd() / 2;
        }
        int absClosest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childCenter = helper.getDecoratedStart(child) +
                    (helper.getDecoratedMeasurement(child) / 2);
            if(i == 0){//首页
                int margin;
                if(layoutManager.canScrollHorizontally()){//计算水平方向第一页在左侧的margin
                    margin = ((ViewGroup.MarginLayoutParams)child.getLayoutParams()).leftMargin;
                }else {//计算水垂直向第一页在上侧的margin
                    margin = ((ViewGroup.MarginLayoutParams)child.getLayoutParams()).topMargin;
                }
                childCenter = helper.getDecoratedStart(child) + margin + (helper.getDecoratedMeasurement(child)-margin)/2;

            }else if(i == childCount - 1){//尾页
                int margin;
                if(layoutManager.canScrollHorizontally()){//计算水平方向最后一页在右侧的margin
                    margin = ((ViewGroup.MarginLayoutParams)child.getLayoutParams()).rightMargin;
                }else {//计算垂直方向最后一页在下侧的margin
                    margin = ((ViewGroup.MarginLayoutParams)child.getLayoutParams()).bottomMargin;
                }
                childCenter = helper.getDecoratedStart(child) + (helper.getDecoratedMeasurement(child)-margin)/2;
            }
            int absDistance = Math.abs(childCenter - center);

            /** if child center is closer than previous closest, set it as closest  **/
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

}