package com.fcott.xformerrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.View;

import com.fcott.xformerrecyclerview.transformer.PageTransformer;

/**
 * Created by fcott on 2017/7/25.
 */
public class XformerRecyclerView extends RecyclerView {
    private static final int FLING_MAX_VELOCITY = 8000; // 默认最大瞬时滑动速度
    public enum FLING_MODE{
        SCROLL, //滑动一次 持续滚动
        ONEPAGE //滑动一次 滚动一页
    }

    private FixLinearSnapHelper mLinearSnapHelper = new FixLinearSnapHelper();
    private SnapHelper pagerSnapHelper = new PagerSnapHelper();
    private PageTransformer mPageTransformer;
    private int flingMaxVelocity = FLING_MAX_VELOCITY;

    public XformerRecyclerView(Context context) {
        this(context,null);
    }

    public XformerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public XformerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * 设置滚动模式
     * @param flingMode 滚动模式
     */
    public void setFlingMode(FLING_MODE flingMode){
        if(flingMode == FLING_MODE.ONEPAGE){
            pagerSnapHelper.attachToRecyclerView(this);
        }else {
            mLinearSnapHelper.attachToRecyclerView(this);
        }
    }

    /**
     * 设置最大滑动速度
     * @param flingMaxVelocity  最大滑动速度
     */
    public void setFlingMaxVelocity(int flingMaxVelocity){
        this.flingMaxVelocity = flingMaxVelocity;
    }

    /**
     * 只能横向滑动
     * @param layout
     */
    @Override
    public void setLayoutManager(LayoutManager layout) {
        return;
    }

    /**
     * 重写fling方法 控制最大滑动速度
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityX = solveVelocity(velocityX);
        velocityY = solveVelocity(velocityY);
        return super.fling(velocityX, velocityY);
    }

    private int solveVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, flingMaxVelocity);
        } else {
            return Math.max(velocity, -flingMaxVelocity);
        }
    }

    /**
     * 设置切换动画
     * @param transformer
     */
    public void setPageTransformer(PageTransformer transformer) {
        this.mPageTransformer = transformer;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        if (mPageTransformer != null) {
            final int scrollX = getScrollX();
            final int childCount = getAdapter().getItemCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getLayoutManager().findViewByPosition(i);

                if(child == null)continue;
                final float transformPos = (float) ((child.getLeft()+child.getRight())/2 - scrollX) / (getMeasuredWidth() - getPaddingLeft() - getPaddingRight())-0.5f;
                final float intervalPercent = (float) (child.getMeasuredWidth()+child.getPaddingLeft()+child.getPaddingRight()) / (getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
                mPageTransformer.transformPage(child,intervalPercent * 2, transformPos * 2);
            }
        }
    }

}
