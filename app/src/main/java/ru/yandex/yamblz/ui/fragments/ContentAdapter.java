package ru.yandex.yamblz.ui.fragments;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ru.yandex.yamblz.R;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {

    private final Random rnd = new Random();
    private final List<Integer> colors = new ArrayList<>();

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(createColorForPosition(position));
        holder.setClickListener(l -> {
            if (holder.getAdapterPosition() != NO_POSITION)
                updateColorForPosition(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    private Integer createColorForPosition(int position) {
        if (position >= colors.size()) {
            colors.add(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        }
        return colors.get(position);
    }

    private Integer updateColorForPosition(int position) {
        colors.set(position, Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        notifyItemChanged(position);
        return colors.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // Derp
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void swap(int firstPosition, int secondPosition){
        Collections.swap(colors, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);
    }

    void remove(int position) {
        colors.remove(position);
        notifyItemRemoved(position);
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
        WeakReference<View.OnClickListener> listener;

        ContentHolder(View itemView) {
            super(itemView);
        }

        void bind(Integer color) {
            itemView.setBackgroundColor(color);
            ((TextView) itemView).setText("#".concat(Integer.toHexString(color).substring(2)));
            itemView.setOnClickListener(v -> {
                if (listener != null && listener.get() != null) {
                    listener.get().onClick(v);
                }
            });
        }

        void setClickListener(View.OnClickListener listener) {
            this.listener = new WeakReference<>(listener);
        }

    }
}
