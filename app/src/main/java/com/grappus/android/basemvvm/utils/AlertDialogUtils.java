package com.grappus.android.basemvvm.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.grappus.android.basemvvm.R;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class AlertDialogUtils {


    private static Dialog alertDialog;

    public interface CustomDialogListener {

        void positiveButtonClicked();

        void negativeButtonClicked();
    }


    //Dismiss alert dialog
    public static void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) alertDialog.cancel();
    }


    //Use this dialog with custom dialog layout & dynamic texts for message and buttons
    public static void showCustomAlertDialog(Context context, String alertMessage
            , CustomDialogListener listener, @Nullable String... btnMessages) {
        if (context == null) return;
        dismissAlertDialog();

        alertDialog = new Dialog(context);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlideUpSlideDown;
        alertDialog.setContentView(R.layout.layout_custom_alert_dialog);

        ConstraintLayout clDialog = alertDialog.findViewById(R.id.cl_alert_dialog);
        TextView tvAlertMessage = alertDialog.findViewById(R.id.tv_alert_message);
        Button btnNo = alertDialog.findViewById(R.id.btn_no);
        Button btnYes = alertDialog.findViewById(R.id.btn_yes);

        tvAlertMessage.setText(alertMessage);

        if (btnMessages != null && btnMessages.length > 0) {
            btnYes.setText(btnMessages[0]);
        }
        if (btnMessages != null && btnMessages.length > 1) {
            btnNo.setVisibility(View.VISIBLE);
            btnNo.setText(btnMessages[1]);
        }

        alertDialog.setCancelable(btnMessages == null || btnMessages.length == 0);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        clDialog.setOnTouchListener((view, motionEvent) -> {
            if (btnMessages == null || btnMessages.length == 0) alertDialog.dismiss();
            return btnMessages == null || btnMessages.length == 0;
        });

        btnYes.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (listener != null) listener.positiveButtonClicked();
        });
        btnNo.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (listener != null) listener.negativeButtonClicked();
        });
    }
}