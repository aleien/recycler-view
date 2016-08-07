package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.yandex.yamblz.utils.Utils;

class MoveItemDecoration extends RecyclerView.ItemDecoration {
    @NonNull private Pair<Integer, Integer> lastMovedItems = new Pair<>(-1, -1);
    @NonNull private final Paint paint = new Paint();

    {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(Utils.dpToPx(15));
        paint.setStyle(Paint.Style.STROKE);
    }

    void setLastMovedItems(int firstPosition, int lastPosition) {
        lastMovedItems = new Pair<>(firstPosition, lastPosition);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (shouldDecorate(parent.getChildAdapterPosition(child))) {
                drawBorder(c, child);
            }
        }
    }

    private void drawBorder(Canvas c, View child) {
        float movementX = child.getTranslationX();
        float movementY = child.getTranslationY();

        c.drawRect(child.getLeft() + movementX,
                child.getTop() + movementY,
                child.getRight() + movementX,
                child.getBottom() + movementY,
                paint);
    }

    private boolean shouldDecorate(int position) {
        return lastMovedItems.first == position
                || lastMovedItems.second == position;
    }
}
