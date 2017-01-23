package com.urvesh.android_arch_mvp.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.urvesh.android_arch_mvp.R;

public class HelperClass {

    private static String TAG = "HelperClass";

    public static void showMessage(Context context, String message) {
        try {
            showSnackBar(context, message);
        } catch (Exception e) {
            showToastBar(context, message);
            Logger.exception(TAG, e);
        }
    }

    public static void showSnackBar(Context context, String message) {
        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_background));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showToastBar(Context context, String message) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.view_custom_toast_layout, null);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        int actionBarHeight = context.getResources().getDimensionPixelSize(R.dimen.design_toast_height) + ViewUtils.dpToPx(context, 4);
        toast.setGravity(Gravity.BOTTOM, 0, actionBarHeight);
        toast.setView(layout);//setting the view of custom toast layout
        ((TextView) toast.getView().findViewById(R.id.custom_toast_message)).setText(message);
        toast.show();
    }

    public static void showErrorMessage(Context context, String message) {
        try {
            showErrorSnackBar(context, message);
        } catch (Exception e) {
            showToastBar(context, message);
            Logger.exception(TAG, e);
        }
    }

    public static void showErrorSnackBar(Context context, String message) {
        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_error_background));
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/Muli-Regular.ttf");
        textView.setTypeface(type);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        // calculate actionbar height
        TypedValue tv = new TypedValue();
        int topBarHeight = 0;
        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            //int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
            topBarHeight = ViewUtils.getStatusBarHeight(context);
        }
        // set margin
        params.setMargins(0, topBarHeight, 0, 0);

        view.setLayoutParams(params);
        snackbar.show();
    }

    public static void copyToClipboard(Context context, String codeToCopy, String toastMessage) {
        if (codeToCopy != null && !codeToCopy.isEmpty()) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(codeToCopy, codeToCopy);
            clipboard.setPrimaryClip(clip);
            HelperClass.showToastBar(context, toastMessage);
        }
    }
}
