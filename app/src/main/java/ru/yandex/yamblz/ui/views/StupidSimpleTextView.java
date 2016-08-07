package ru.yandex.yamblz.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import ru.yandex.yamblz.utils.Utils;

/**
 * Created by aleien on 07.08.16.
 * Plain text view that is purposed solely for solving textview optimization problem.
 */

public class StupidSimpleTextView extends View {
    private CharSequence text = "";
    private final Paint textPaint = new Paint();
    private final int padding = Utils.dpToPx(32);

    public StupidSimpleTextView(Context context) {
        super(context);
        init();
    }

    public StupidSimpleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(70);
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, 0, text.length() - 1, 0, padding, textPaint);
    }
}
