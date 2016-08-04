package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


class RotateGridLayoutManager extends GridLayoutManager {
    private boolean isAnimated;
    private int extraLayoutSpace = -1;

    RotateGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
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

    void setAnimated(boolean animated) {
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

    void setExtraLayoutSpace(int extraLayoutSpace) {
        this.extraLayoutSpace = extraLayoutSpace;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if (extraLayoutSpace > 0) return extraLayoutSpace;
        return super.getExtraLayoutSpace(state);
    }
}
