package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import ru.yandex.yamblz.utils.Utils;

/**
 * Created by aleien on 04.08.16.
 */

public class RotateGridLayoutManager extends GridLayoutManager {
    private static final int DEFAULT_EXTRA_LAYOUT_SPACE = Utils.dpToPx(500);
    private int extraLayoutSpace = -1;
    private boolean isAnimated;


    public RotateGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RotateGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate) {
        return super.requestChildRectangleOnScreen(parent, child, rect, immediate);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        flipChild(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        flipChild(child);
    }

    public void setAnimated(boolean animated) {
        isAnimated = animated;
    }

    private void flipChild(View child) {
        if (isAnimated) {
            child.animate()
                    .setDuration(2000)
                    .setStartDelay(300)
                    .rotationX(360)
                    .start();
        }
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if (extraLayoutSpace > 0) {
            return extraLayoutSpace;
        }
        return DEFAULT_EXTRA_LAYOUT_SPACE;
    }
}
