package com.stroage.cloud.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import com.stroage.cloud.StorageCloudApp;

import java.lang.reflect.Field;


public final class DisplayUtils {

    public static float getDimension(int demen) {
        return StorageCloudApp.getContext().getResources().getDimension(demen);
    }

    public static int getColor(int color) {
        return ContextCompat.getColor(StorageCloudApp.getContext(), color);
    }

    public static int getDimen(int dimen) {
        return (int) StorageCloudApp.getContext().getResources().getDimension(dimen);
    }

    public static Drawable getDrawable(int drawable) {
        return ContextCompat.getDrawable(StorageCloudApp.getContext(), drawable);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 尺寸dip
     * @return 像素值
     */
    public static int dip2px(float dpValue) {
        return (int) dp2px(dpValue);
    }

    public static float dp2px(float dpValue) {
        final float scale = StorageCloudApp.getContext().getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 尺寸像素
     * @return DIP值
     */
    public static int px2dip(float pxValue) {
        final float scale = StorageCloudApp.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     *
     * @param pxValue 尺寸像素
     * @return SP值
     */
    public static int px2sp(float pxValue) {
        float fontScale = StorageCloudApp.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     *
     * @param spValue SP值
     * @return 像素值
     */
    public static int sp2px(float spValue) {
        float fontScale = StorageCloudApp.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String getString(int stringValue) {
        return StorageCloudApp.getContext().getResources().getString(stringValue);
    }

    public static String getString(int stringValue, Object...format) {
        return StorageCloudApp.getContext().getResources().getString(stringValue, format);
    }

    private static String sWebViewStr = null;

    /**
     * 获取dialog宽度
     *
     * @return Dialog宽度
     */
    public static int getDialogW() {
        DisplayMetrics dm = getDisplayMetrics();
        int w = dm.widthPixels - 100;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
        return w;
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public static int getScreenW() {
        DisplayMetrics dm = getDisplayMetrics();
        int w = dm.widthPixels;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
        return w;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public static int getScreenH() {
        DisplayMetrics dm = getDisplayMetrics();
        int h = dm.heightPixels;
        return h;
    }



    public static float getDensity() {
        return StorageCloudApp.getContext().getResources().getDisplayMetrics().density;
    }

    public static float getDensityDpi() {
        return StorageCloudApp.getContext().getResources().getDisplayMetrics().densityDpi;
    }

    private static DisplayMetrics getDisplayMetrics() {
        return StorageCloudApp.getContext().getResources().getDisplayMetrics();
    }

    /**
     * Toggle keyboard If the keyboard is visible,then hidden it,if it's
     * invisible,then show it
     *
     * @param context 上下文
     */
    public static void toggleKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());

            return StorageCloudApp.getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getActionBarHeight() {
        int actionBarHeight = dip2px(50);
        TypedValue typedValue = new TypedValue();
        if (StorageCloudApp.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, StorageCloudApp.getContext().getResources().getDisplayMetrics());
        }

        return actionBarHeight;
     }

    public static int getNavigationBarHeight() {
        Resources resources = StorageCloudApp.getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    //获取黄金比例
    public static float getGoldRatioLong(float shortLength) {
        return (float) (shortLength / 0.618);
    }

    public static float getGoldRatioShort(float longLength) {
        return (float) (longLength * 0.618);
    }

}
