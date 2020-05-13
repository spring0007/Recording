package com.android.recording.util;

import android.content.Context;
import android.text.TextUtils;
import java.util.Locale;
import java.text.NumberFormat;

import android.view.View;

import com.android.recording.R;

public class Utils {

    public static String makeTimeString4MillSec(Context context, int millSec) {
        String str = "";
        int hour = 0;
        int minute = 0;
        int second = 0;
        second =  Math.round((float)millSec/1000);
        if (second > 59) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 59) {
            hour = minute / 60;
            minute = minute % 60;
        }
        str = (hour < 10 ? "0" + hour : hour) + ":"
                + (minute < 10 ? "0" + minute : minute) + ":"
                + (second < 10 ? "0" + second : second);
        if(hour == 0 && minute ==0 && second == 0){
            str = "< "+ context.getString(R.string.less_than_one_second);
        }
        return str;
    }
    public static boolean isRtl() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
    }
    public static String getNumberFormattedQuantityString(Context context, int id, int quantity) {
        final String localizedQuantity = NumberFormat.getInstance().format(quantity);
        return context.getResources().getQuantityString(id, quantity, localizedQuantity);
    }
}
