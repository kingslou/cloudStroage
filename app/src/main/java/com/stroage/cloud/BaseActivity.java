package com.stroage.cloud;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * @author Administrator
 * @date 创建时间 2018/5/2
 * @Description
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected MaterialDialog mDialog,materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("come in onCreate","0");
        super.onCreate(savedInstanceState);
    }

    public void showLoading() {
        showProgressDialog(R.string.str_loading);
    }

    public void showLoading(int strId) {
        showProgressDialog(strId);
    }

    public void hideLoading() {
        if (mDialog != null) {
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }


    public void showErrorMsg(int strId, DialogInterface.OnClickListener listener) {
        if (!isFinishing()) {
            new AlertDialogWrapper.Builder(this)
                    //.setTitle(R.string.title)
                    .setMessage(strId)
                    .setNegativeButton(R.string.str_ok, listener)
                    .setCancelable(false)
                    .show();
        }
    }


    public void showErrorMsg(String msg) {
        if (!isFinishing()) {
            new AlertDialogWrapper.Builder(this)
                    .setTitle(R.string.str_tip)
                    .setMessage(msg)
                    .setNegativeButton(R.string.str_ok, null)
                    .setCancelable(false)
                    .show();
        }
    }

    public void showErrorTipsDialog(String errorInfo){
        if(materialDialog!=null && materialDialog.isShowing()){
            return;
        }
        materialDialog =  new MaterialDialog.Builder(this)
                .title("警 告")
                .content(errorInfo)
                .positiveText("关 闭")
                .neutralText("设置时间")
                .titleGravity(GravityEnum.START)
                .buttonsGravity(GravityEnum.CENTER)
                .cancelable(false)
                .limitIconToDefaultSize()
                .maxIconSize(30)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                        finish();
                    }
                }).onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
//                        finish();
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(materialDialog!=null){
                            materialDialog = null;
                        }
                    }
                })
                .show();
    }

    public void showProgressDialog(int strId) {
        if(this.mDialog!=null&&this.mDialog.isShowing()){
            return;
        }
        if (!isFinishing()) {
            this.mDialog = new MaterialDialog.Builder(this)
                    //.title(R.string.progress_dialog)
                    .content(strId)
                    .progress(true, 0)
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            mDialog = null;
                        }
                    })
                    .show();
        }
    }
}
