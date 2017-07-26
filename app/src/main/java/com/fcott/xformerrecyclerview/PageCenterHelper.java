package com.fcott.xformerrecyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fcott on 2017/7/25.
 * 帮助第一页和最后一页居中
 */
public class PageCenterHelper {
    private static boolean hasInitNormalMargin = false;
    private static int normalLeftMargin = 0;
    private static int normalRightMargin = 0;
    protected static int itemWidth = 0;
    protected static int parentWidth = 0;

    private PageCenterHelper() {

    }

    public static void onBindViewHolder(final View itemView, final int position, int itemCount) {
        if(itemWidth == 0)
            return;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();

        if (!hasInitNormalMargin) {
            hasInitNormalMargin = true;
            normalLeftMargin = lp.leftMargin;
            normalRightMargin = lp.rightMargin;
        }

        if (position == 0) {
            int leftMargin = (parentWidth - itemWidth + normalLeftMargin - lp.rightMargin) / 2 ;
            lp.setMargins(leftMargin, lp.topMargin, normalRightMargin, lp.bottomMargin);
            itemView.setLayoutParams(lp);
        } else if (position == itemCount - 1) {
            int rightMargin = (parentWidth - itemWidth + normalRightMargin - lp.leftMargin) / 2 ;
            lp.setMargins(normalLeftMargin, lp.topMargin, rightMargin, lp.bottomMargin);
            itemView.setLayoutParams(lp);
        } else {
            lp.setMargins(normalLeftMargin, lp.topMargin, normalRightMargin, lp.bottomMargin);
            itemView.setLayoutParams(lp);
        }
    }

}
