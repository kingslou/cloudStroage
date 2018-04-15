package com.stroage.cloud.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stroage.cloud.R;


/**
 * Created by mertsimsek on 15/01/17.
 */

public class DialogBuilder {

    public static MaterialDialog materialDialog;

    public static void showLoading(Context context){
        progressDialog(context,R.string.str_loading,R.string.str_loading);
    }

    public static void progressDialog(Context context, int title, int content) {
         if(materialDialog!=null && materialDialog.isShowing()){
             materialDialog.dismiss();
             materialDialog = null;
         }
         materialDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .canceledOnTouchOutside(false).progressIndeterminateStyle(true)
                .progress(true, 0).show();
    }

    public static void infoDialog(Context context, int title, int content) {
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null;
        }
        materialDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(R.string.dialog_action_ok)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .positiveColorRes(R.color.colorPrimary).show();
    }

    public static void hideDialog(){
        if(materialDialog!=null && materialDialog.isShowing()){
            materialDialog.dismiss();
            materialDialog = null;
        }
    }

}
