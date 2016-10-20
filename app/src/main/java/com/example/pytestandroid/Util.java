package com.example.pytestandroid;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by swifty on 20/10/2016.
 */
public class Util {

    public static Byte[] convert(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        int i = 0;
        for (byte b : bytesPrim) bytes[i++] = b; // Autoboxing
        return bytes;
    }

    public static float getPosXofScreen(ImageView cardView) {
        int[] pos = new int[2];
        cardView.getLocationOnScreen(pos);
        return pos[0];
    }

    public static float getPosYofScreen(ImageView cardView) {
        int[] pos = new int[2];
        cardView.getLocationOnScreen(pos);
        return pos[1];
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        return height;
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        return width;
    }
}
