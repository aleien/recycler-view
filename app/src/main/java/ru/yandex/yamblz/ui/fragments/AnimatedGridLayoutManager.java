package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by aleien on 03.08.16.
 */

public class AnimatedGridLayoutManager extends GridLayoutManager {
    public AnimatedGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public AnimatedGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public AnimatedGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }
}
