package com.stroage.cloud.utils;

import android.app.Activity;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by YiMuTian on 2017/7/3.
 */

public class DialogFactory {

    private static MaterialDialog mDialog;


    public static void showLoading(Activity activity) {
        showProgressDialog("加载中请稍后···",activity);
    }


    public static void showLoading(String content ,Activity activity) {
        showProgressDialog(content,activity);
    }


    public static void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public static void showDialog(String message, Activity activity){
        mDialog = new MaterialDialog.Builder(activity)
                .content(message)
                .title("提示")
                .negativeText("取消")
                .positiveText("确定")
                .show();
    }

    public static void showProgressIndeterminateStyle(Activity activity){
        mDialog =new MaterialDialog.Builder(activity)
                .title("加载中")
                .content("请稍后")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();
    }

    public static void showProgressDialog(String content, Activity activity) {
        if(mDialog!=null&&mDialog.isShowing()){
            return;
        }
        if (!activity.isFinishing()) {
            mDialog = new MaterialDialog.Builder(activity)
                    //.title(R.string.progress_dialog)
                    .content(content)
                    .progress(true, 0)
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            mDialog = null;
                        }
                    }).show();
        }
    }

}
