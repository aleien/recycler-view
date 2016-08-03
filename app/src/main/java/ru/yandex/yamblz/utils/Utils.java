package ru.yandex.yamblz.utils;

import android.content.res.Resources;

/**
 * Created by aleien on 03.08.16.
 */

public class Utils {
    private Utils() {

    }

    public static int dpToPx(int dp) {
        return (int) Resources.getSystem().getDisplayMetrics().density * dp;
    }
}
