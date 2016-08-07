package ru.yandex.yamblz.ui.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import ru.yandex.yamblz.R;

public class ContentFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.span_slider)
    SeekBar spanSlider;

    private RotateGridLayoutManager layoutManager;
    private ContentAdapter adapter;

    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private final BorderItemDecoration borderDecoration = new BorderItemDecoration();
    private final MoveItemDecoration moveItemDecoration = new MoveItemDecoration();

    private void setupOptimizations(boolean isOptimized, int extraLayoutSpace, int max) {
        rv.setHasFixedSize(isOptimized);
        layoutManager.setExtraLayoutSpace(extraLayoutSpace);
        rv.getRecycledViewPool().setMaxRecycledViews(0, max);
        rv.setItemViewCacheSize(max);
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
        rv.addItemDecoration(moveItemDecoration);

        setupAdapter();
        setupItemTouchHelper();
    }

    private void setupItemTouchHelper() {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelper(adapter, moveItemDecoration);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);
    }

    private void setupAdapter() {
        adapter = new ContentAdapter();
        adapter.setHasStableIds(true);
        rv.setAdapter(adapter);
        setSpanSlider();
    }

    @OnCheckedChanged(R.id.checkbox_decorations)
    public void onSetDecorationsChecked(boolean isDecorated) {
        if (isDecorated) {
            rv.addItemDecoration(borderDecoration, 1);
        } else {
            rv.removeItemDecoration(borderDecoration);
        }
    }

    @OnCheckedChanged(R.id.checkbox_animations)
    public void onSetAnimationsChecked(boolean isAnimated) {
        layoutManager.setAnimated(isAnimated);
    }

    @OnCheckedChanged(R.id.checkbox_optimization)
    public void onOptimizationsChecked(boolean isOptimized) {
        setupOptimizations(isOptimized, isOptimized ? screenHeight / 2 : -1, isOptimized ? 300 : 5);
    }

    private void setSpanSlider() {
        spanSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (progress > 0) {
                        layoutManager.setSpanCount(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rv.getAdapter().notifyDataSetChanged();
            }
        });
    }


}
