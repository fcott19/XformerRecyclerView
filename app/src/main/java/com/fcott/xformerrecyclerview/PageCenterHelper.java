package com.fcott.xformerrecyclerview;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fcott on 2017/7/25.
 * 帮助第一页和最后一页居中
 */
public class PageCenterHelper {
    private static boolean hasInitNormalMargin = false;
    private static boolean hasInitParent = false;
    private static int normalLeftMargin = 0;
    private static int normalRightMargin = 0;
    private static ViewGroup parent;

    private PageCenterHelper() {

    }

    public static void onCreateViewHolder(ViewGroup viewGroup) {
        if (hasInitParent)
            return;
        parent = viewGroup;
    }

    public static void onBindViewHolder(View itemView, final int position, int itemCount) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();

        if (!hasInitNormalMargin) {
            hasInitNormalMargin = true;
            normalLeftMargin = lp.leftMargin;
            normalRightMargin = lp.rightMargin;
        }

        if (position == 0) {
            int leftMargin = (parent.getWidth() - lp.width + normalLeftMargin - lp.rightMargin) / 2 ;
            lp.setMargins(leftMargin, lp.topMargin, normalRightMargin, lp.bottomMargin);
            itemView.setLayoutParams(lp);
        } else if (position == itemCount - 1) {
            int rightMargin = (parent.getWidth() - lp.width + normalRightMargin - lp.leftMargin) / 2 ;;
            lp.setMargins(normalLeftMargin, lp.topMargin, rightMargin, lp.bottomMargin);
            itemView.setLayoutParams(lp);
        } else {
            lp.setMargins(normalLeftMargin, lp.topMargin, normalRightMargin, lp.bottomMargin);
            itemView.setLayoutParams(lp);
        }
    }

}
