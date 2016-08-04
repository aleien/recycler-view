package ru.yandex.yamblz.ui.fragments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.utils.Utils;

import static ru.yandex.yamblz.utils.Utils.dpToPx;

public class ContentFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;

    private RotateGridLayoutManager layoutManager;
    private ContentAdapter adapter;
    private boolean isDecorated;
    private boolean is30ColumnsSet;
    private boolean isAnimated;

    private final RecyclerView.ItemDecoration decoration = new RecyclerView.ItemDecoration() {
        private final Paint paint = new Paint();
        private final int borderWidth = 15;

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            paint.setColor(Color.WHITE);

            final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

            for (int i = 0; i < parent.getChildCount(); i++) {
                final View child = parent.getChildAt(i);
                if (parent.getChildAdapterPosition(child) % 2 == 0) continue;
                c.drawRect(
                        layoutManager.getDecoratedLeft(child),
                        layoutManager.getDecoratedBottom(child) - dpToPx(borderWidth),
                        layoutManager.getDecoratedRight(child),
                        layoutManager.getDecoratedBottom(child),
                        paint);

            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_column:
                setSpanCount(layoutManager.getSpanCount() + 1);
                return true;
            case R.id.menu_remove_column:
                setSpanCount(layoutManager.getSpanCount() - 1);
                return true;
            case R.id.menu_change_style:
                setItemDecoration(!isDecorated);
                return true;
            case R.id.menu_set_30_columns:
                setup30Columns(false, -1, 5);
                return true;
            case R.id.menu_set_30_columns_optimized:
                setup30Columns(true, dpToPx(500), is30ColumnsSet ? 5 : 300);
                return true;
            case R.id.menu_set_animation:
                isAnimated = !isAnimated;
                layoutManager.setAnimated(isAnimated);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setup30Columns(boolean hasFixedSize, int extraLayoutSpace, int max) {
        setSpanCount(is30ColumnsSet ? 1 : 30);
        rv.setHasFixedSize(hasFixedSize);
        layoutManager.setExtraLayoutSpace(extraLayoutSpace);
        rv.getRecycledViewPool().setMaxRecycledViews(0, max);
        rv.setItemViewCacheSize(max);
        is30ColumnsSet = !is30ColumnsSet;
    }

    private void setSpanCount(int spanCount) {
        if (spanCount != 0) {
            layoutManager.setSpanCount(spanCount);
            rv.getAdapter().notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager = new RotateGridLayoutManager(getContext(), 1);
        rv.setLayoutManager(layoutManager);

        setupAdapter();
        setupItemTouchHelper();
    }

    private void setupItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);
    }

    private void setupAdapter() {
        adapter = new ContentAdapter();
        adapter.setHasStableIds(true);
        rv.setAdapter(adapter);
    }

    private void setItemDecoration(boolean itemDecoration) {
        this.isDecorated = itemDecoration;
        if (isDecorated) {
            rv.addItemDecoration(decoration);
        } else {
            rv.removeItemDecoration(decoration);
        }
    }
}
