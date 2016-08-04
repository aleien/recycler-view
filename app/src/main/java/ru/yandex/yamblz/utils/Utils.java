package ru.yandex.yamblz.utils;

import android.content.res.Resources;

public class Utils {
    private Utils() {

    }

    public static int dpToPx(int dp) {
        return (int) Resources.getSystem().getDisplayMetrics().density * dp;
    }
}
