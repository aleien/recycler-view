package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static ru.yandex.yamblz.utils.Utils.dpToPx;

class BorderItemDecoration extends RecyclerView.ItemDecoration {
    private final int padding = dpToPx(15);
    private final Paint paint = new Paint();

    {
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(dpToPx(3));
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            if (shouldDecorate(parent.getChildAdapterPosition(child))) continue;
            decorateChild(c, child);
        }
    }

    private void decorateChild(Canvas c, View child) {
        float movementX = child.getTranslationX();
        float movementY = child.getTranslationY();

        c.drawRect(child.getLeft() + padding + movementX,
                child.getTop() + padding + movementY,
                child.getRight() - padding + movementX,
                child.getBottom() - padding + movementY,
                paint);
    }

    private boolean shouldDecorate(int position) {
        return position % 2 == 0;
    }
}
