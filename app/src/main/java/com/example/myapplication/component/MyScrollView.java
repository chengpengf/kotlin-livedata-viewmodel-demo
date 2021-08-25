package com.example.myapplication.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * 类名： MyScrollView
 */

public class MyScrollView extends ScrollView {

    private OnScrollistener onScrollistener;

    private float startX;
    private float startY;
    private float mTouchSlop;

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context) {
        this(context, null);
    }

    public OnScrollistener getOnScrollistener() {
        return onScrollistener;
    }

    public void setOnScrollistener(OnScrollistener onScrollistener) {
        this.onScrollistener = onScrollistener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollistener != null) {
            onScrollistener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    public interface OnScrollistener {

        void onScrollChange(ScrollView who, int l, int t, int oldl, int oldt);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX;
                float distanceY;
                if (ev.getY() > startX) {
                    distanceX = Math.abs(ev.getX() - startX);
                    distanceY = Math.abs(ev.getY() - startY);
                } else {
                    distanceX = Math.abs(startX - ev.getX());
                    distanceY = Math.abs(startY - ev.getY());
                }
                if (distanceX > mTouchSlop && distanceX > distanceY) {  //判断为横向滑动
                    return false;
                }

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
