package com.example.myapplication.component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoScrollRecycleView extends RecyclerView {
    private boolean canScrollVertically = false;
    private boolean canScrollHorizontally = false;

    public NoScrollRecycleView(Context context) {
        this(context, null, 0);
    }

    public NoScrollRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoScrollRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //默认封装竖直线性布局
        //复写滑动判断，解决scrollview嵌套滑动惯性失效的问题
        setLayoutManager(new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically() && canScrollVertically;
            }

            @Override
            public boolean canScrollHorizontally() {
                return super.canScrollHorizontally() && canScrollHorizontally;
            }
        });
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (canScrollVertically) {
            super.onMeasure(widthSpec, heightSpec);
        } else if (canScrollHorizontally) {
            super.onMeasure(widthSpec, heightSpec);
        } else {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthSpec, expandSpec);
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    public boolean isCanScrollVertically() {
        return canScrollVertically;
    }

    public void setCanScrollVertically(boolean canScrollVertically) {
        this.canScrollVertically = canScrollVertically;
    }

    public boolean isCanScrollHorizontally() {
        return canScrollHorizontally;
    }

    public void setCanScrollHorizontally(boolean canScrollHorizontally) {
        this.canScrollHorizontally = canScrollHorizontally;
    }
}
