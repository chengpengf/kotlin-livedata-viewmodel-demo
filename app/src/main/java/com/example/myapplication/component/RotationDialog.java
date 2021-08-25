package com.example.myapplication.component;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.example.myapplication.R;


public class RotationDialog {
    private ProgressDialog pd;

    public RotationDialog(Context act) {
        pd = new ProgressDialog(act, AlertDialog.THEME_HOLO_LIGHT);
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(act.getString(R.string.loading));
    }

    public boolean isShowing() {
        return pd.isShowing();
    }

    public void setRotationDialogTitle(String title) {
        pd.setTitle(title);
    }

    public void setRotationDialogMessage(String msg) {
        pd.setMessage(msg);
    }

    public void show() {
        pd.show();
    }

    public void dismiss() {
        pd.dismiss();
    }
}
