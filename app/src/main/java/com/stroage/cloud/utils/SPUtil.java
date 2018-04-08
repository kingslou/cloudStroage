package com.stroage.cloud.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.stroage.cloud.AppConfig;

import java.util.Random;

/**
 * @ClassName: SharedPreferencesUtil
 * @Description: Util class for SharedPreferences operations
 */
public class SPUtil {

    private static final String APP_VERSION_CODE_KEY = "APP_VERSION_CODE";
    private static final String APP_VERSION_KEY = "APP_VERSION";

    /**
     * @param mContext
     * @param key
     * @Description: set String value for key
     */
    public static void setStringContentPreferences(Context mContext, String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value == null ? "" : value);
        editor.apply();
    }

//    /**
//     * @param mContext
//     * @param key
//     * @param value
//     * @Description: Save String value to SP with encryption
//     */
//    public static void saveStringWithEncryption(Context mContext, String key, String value) {
//        String desKey = getRandomString(8);
//        setStringContentPreferences(mContext, key, desKey.substring(0, 4) + DesUtil.encode(desKey, value) + desKey.substring(4, 8));
//    }


    /**
     * @param mContext
     * @param key
     * @Description: get String value by key
     */
    public static String getStringContentPreferences(Context mContext, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

//    /**
//     * @param mContext
//     * @param key
//     * @return
//     * @Description: Get String value with decryption
//     */
//    public static String getStringWithDecryption(Context mContext, String key) {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
//        String desStr = sharedPreferences.getString(key, null);
//        return DesUtil.decode(getRandomPrivateKey(desStr), getEncryptedMessage(desStr));
//    }

    /**
     * @param mContext
     * @param key
     * @return
     * @Description: check if SP contains String value by key
     */
    public static boolean hasStringValue(Context mContext, String key) {
        String value = getStringContentPreferences(mContext, key);
        return !TextUtils.isEmpty(value);

    }

    /**
     * @param mContext
     * @param key
     * @Description: set long value for key
     */
    public static void setLongContentPreferences(Context mContext, String key, long value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * @param mContext
     * @param key
     * @Description: get long value by key
     */
    public static long getLongContentPreferences(Context mContext, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        long value = sharedPreferences.getLong(key, -1);
        return value;
    }

    /**
     * @param mContext
     * @param key
     * @Description: set boolean value for key
     */
    public static void setBooleanPreferences(Context mContext, String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * @param mContext
     * @param key
     * @Description: get boolean value by key
     */
    public static boolean getBooleanPreferences(Context mContext, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(key, false);
        return value;
    }

    /**
     * @param mContext
     * @param key
     * @Description: get boolean value by key,default value is true
     */
    public static boolean getBooleanPreferencesDefaultTrue(Context mContext, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(key, true);
        return value;
    }

    /**
     * @param mContext
     * @Description: Clear all data of SharedPreference file for this Project
     */
    public static void clearAllDataWithinSP(Context mContext) {
        mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }

    /**
     * @param mContext
     * @param key
     * @Description: remove data from SP by key
     */
    public static void clearDataByKey(Context mContext, String key) {
        mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE).edit().remove(key).commit();
    }


    /**
     * @param length
     * @return
     * @Description: Get random string with length
     */
    private static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @param desStr
     * @return
     * @Description: Extract random private key from encrypted message
     */
    private static String getRandomPrivateKey(String desStr) {
        String key = "";
        if (desStr.length() > 4) {
            key = desStr.substring(0, 4) + desStr.substring(desStr.length() - 4, desStr.length());
        }
        return key;
    }

    /**
     * @param desStr
     * @return
     * @Description: Get encrypted message from value string
     */
    private static String getEncryptedMessage(String desStr) {
        String msg = "";
        if (desStr.length() > 4) {
            msg = desStr.substring(4, desStr.length() - 4);
        }
        return msg;
    }

    public static void setVersionCode(Context mContext, int versionCode) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(APP_VERSION_CODE_KEY, versionCode);
        editor.apply();
    }

    public static int getVersionCode(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(AppConfig.SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        int version = sharedPreferences.getInt(APP_VERSION_CODE_KEY, 0);
        return version;
    }
}
