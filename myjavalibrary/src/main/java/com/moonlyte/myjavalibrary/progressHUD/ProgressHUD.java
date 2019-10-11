package com.moonlyte.myjavalibrary.progressHUD;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.moonlyte.myjavalibrary.R;


public class ProgressHUD extends Dialog {

    public ProgressHUD(Context context) {
        super(context);
    }

    public ProgressHUD(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_hud);
    }

    @SuppressWarnings("deprecation")
    public static ProgressHUD show(Context context,
                                   boolean indeterminate, boolean cancelable,
                                   OnCancelListener cancelListener) {

        ProgressHUD dialog = new ProgressHUD(context, R.style.MyDialog);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
        return dialog;
    }
}
