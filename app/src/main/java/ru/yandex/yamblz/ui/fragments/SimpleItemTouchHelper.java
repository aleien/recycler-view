package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import timber.log.Timber;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;
import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;
import static android.support.v7.widget.helper.ItemTouchHelper.UP;

/**
 * Created by aleien on 31.07.16.
 */

class SimpleItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private final ContentAdapter adapter;
    private Paint p = new Paint();

    SimpleItemTouchHelper(ContentAdapter adapter) {
        super(UP | DOWN | LEFT | RIGHT, RIGHT);
        this.adapter = adapter;
    }

    // Фууууууу
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.remove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View itemView = viewHolder.itemView;
            float threshold = getSwipeThreshold(viewHolder);

            int alpha = Math.min(255, (int) (255 * dX / (threshold * recyclerView.getWidth())));
            p.setARGB(alpha, 255, 0, 0);
            c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), (float) itemView.getLeft() + dX,
                    (float) itemView.getBottom(), p);

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

//    @Override
//    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
//            View itemView = viewHolder.itemView;
//            float threshold = getSwipeThreshold(viewHolder);
//
//            int alpha = (int) (255 * 0.3f);
//            p.setARGB(alpha, 255, 0, 0);
//            c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), (float) itemView.getRight(),
//                    (float) itemView.getBottom(), p);
//
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        } else if (actionState == ACTION_STATE_IDLE) {
//            View itemView = viewHolder.itemView;
//            p.setARGB(0, 255, 0, 0);
//            c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), (float) itemView.getRight(),
//                    (float) itemView.getBottom(), p);
//        }
//    }
}
