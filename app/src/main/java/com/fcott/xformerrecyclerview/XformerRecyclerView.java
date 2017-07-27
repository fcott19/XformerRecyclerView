package com.fcott.xformerrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.fcott.xformerrecyclerview.transformer.PageTransformer;

/**
 * Created by fcott on 2017/7/25.
 */
public class XformerRecyclerView extends RecyclerView {
    private static final int FLING_MAX_VELOCITY = 8000; // 默认最大瞬时滑动速度

    private FixLinearSnapHelper mLinearSnapHelper = new FixLinearSnapHelper();//居中滑动
    private PageTransformer mPageTransformer;//动画控制器
    private int flingMaxVelocity = FLING_MAX_VELOCITY;
    private int normalLeftMargin = 0;//item的默认leftmargin
    private int normalRightMargin = 0;
    private int normalTopMargin = 0;
    private int normalBottomMargin = 0;
    private int itemWidth = 0;//item宽度
    private int itemHeight = 0;//item高度
    private int mWidth = 0;//XformerRecyclerView宽度
    private int mHeight = 0;//XformerRecyclerView高度

    public XformerRecyclerView(Context context) {
        this(context,null);
    }

    public XformerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public XformerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置最大滑动速度
     * @param flingMaxVelocity  最大滑动速度
     */
    public void setFlingMaxVelocity(int flingMaxVelocity){
        this.flingMaxVelocity = flingMaxVelocity;
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
            final int childCount = getAdapter().getItemCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getLayoutManager().findViewByPosition(i);
                if(child == null)continue;

                measureItemMargin(child);//根据tem的位置添加不同的margin

                float transformPos;
                float intervalPercent;
                if (getLayoutManager().canScrollHorizontally()) {
                    transformPos = (float) ((child.getLeft()+child.getRight())/2 - getScrollX()) / (getMeasuredWidth() - getPaddingLeft() - getPaddingRight())-0.5f;
                    intervalPercent = (float) (child.getMeasuredWidth()+child.getPaddingLeft()+child.getPaddingRight()) / (getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
                } else {
                    transformPos = (float) ((child.getTop()+child.getBottom())/2 - getScrollY()) / (getMeasuredHeight() - getPaddingTop() - getPaddingBottom())-0.5f;
                    intervalPercent = (float) (child.getMeasuredHeight()+child.getPaddingTop()+child.getPaddingBottom()) / (getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
                }
                mPageTransformer.transformPage(child,intervalPercent * 2, transformPos * 2);//都乘2，比例不变，便于计算
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if(getChildAt(0) != null && itemWidth == 0){
            View child = getChildAt(0);
            itemWidth = child.getWidth();
            itemHeight = child.getHeight();
            if(getLayoutManager().getPosition(child) == 0){
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                normalLeftMargin = lp.leftMargin;
                normalRightMargin = lp.rightMargin;
                normalTopMargin = lp.topMargin;
                normalBottomMargin = lp.bottomMargin;
                measureItemMargin(child);
                mLinearSnapHelper.attachToRecyclerView(this);
            }
        }
    }

    /**
     * 计算每一页的margin 如果第一页和最后一页需要居中，则需要在左侧（右侧）添加margin
     * @param itemView recycleview中的item
     */
    private void measureItemMargin(final View itemView) {
        if(itemWidth == 0)
            return;

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        int position = getLayoutManager().getPosition(itemView);//根据item确定位置

        if (position == 0) {
            if(getLayoutManager().canScrollHorizontally()){//水平方向第一页，在左侧添加margin 使其居中
                int leftMargin = (mWidth - itemWidth + normalLeftMargin - normalRightMargin) / 2 ;
                lp.setMargins(leftMargin, normalTopMargin, normalRightMargin, normalBottomMargin);
            }else {
                int topMargin = (mHeight - itemHeight + normalTopMargin - normalBottomMargin) / 2 ;
                lp.setMargins(normalRightMargin, topMargin, normalRightMargin, normalBottomMargin);
            }
        } else if (position == getAdapter().getItemCount() - 1) {
            if(getLayoutManager().canScrollHorizontally()){
                int rightMargin = (mWidth - itemWidth + normalRightMargin - normalLeftMargin) / 2 ;
                lp.setMargins(normalLeftMargin, normalTopMargin, rightMargin, normalBottomMargin);
            }else {
                int bottomMargin = (mHeight - itemHeight + normalBottomMargin - normalTopMargin) / 2 ;
                lp.setMargins(normalLeftMargin, normalTopMargin, normalRightMargin, bottomMargin);
            }
        } else {
            lp.setMargins(normalLeftMargin, normalTopMargin, normalRightMargin, normalBottomMargin);
        }
        itemView.setLayoutParams(lp);
    }
}
