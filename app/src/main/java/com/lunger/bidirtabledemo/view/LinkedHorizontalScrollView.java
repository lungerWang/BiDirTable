package com.lunger.bidirtabledemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by Allen on 2015/02/05 15:47
 * 控制联动的HorizontalScrollView
 */
public class LinkedHorizontalScrollView extends HorizontalScrollView {

    private LinkScrollChangeListener listener;

    public LinkedHorizontalScrollView(Context context) {
        super(context);
    }

    public LinkedHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinkedHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollChangeListener(LinkScrollChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != listener)
            listener.onScroll(this, l, t, oldl, oldt);
    }

    /**
     * 控制滑动速度
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 2);
    }

    public interface LinkScrollChangeListener {
        void onScroll(LinkedHorizontalScrollView view, int l, int t, int oldl, int oldt);
    }
}
