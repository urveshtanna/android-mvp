package com.urvesh.android_arch_mvp.tools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewUtils {

    private static String TAG = "ViewUtils";
    private static ProgressDialog progressDialog;
    public static final int SCALE_TYPE_FIT = 2;
    public static final int SCALE_TYPE_CENTER_CROP = 0;
    public static final int SCALE_TYPE_CENTER_INSIDE = 1;
    public static final int SCALE_TYPE_DEFAULT = 3;

    public static void handleViewsVisibility(List<View> viewList, View... params) {
        /* Creating the dialoglist of view id to be displayed */
        List<Integer> idsToShow = new ArrayList<>();
        if (params != null) {
            for (View param : params) {
                idsToShow.add(param.getId());
            }
        }
        for (View v : viewList) {
            if (idsToShow.contains(v.getId()))
                v.setVisibility(View.VISIBLE);
            else
                v.setVisibility(View.GONE);
        }
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public Bitmap getBitmapFromFilePath(String filePath) {
        File image = new File(filePath);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            return BitmapFactory.decodeFile(image.getAbsolutePath(), options);
        } catch (OutOfMemoryError e) {
            //Crashlytics.log("MyGallery: " + e.getMessage());//TODO enable crashlytics
            e.printStackTrace();
        }
        return null;
    }

    public static void closeKeyboard(Context context) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getRootView().getWindowToken(), 0);
        } catch (Exception e) {
            Logger.exception(TAG, e);
        }
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertSpToPixels(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


    public static Bitmap imageOreintationValidator(Bitmap bitmap, String path) {

        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }

    public static void loadImage(Context mContext, String fileName, @DrawableRes int placeholder, int scaleType, ImageView imageView) {
        if (fileName != null)
            switch (scaleType) {
                case 0:
                    Picasso.with(mContext)
                            .load(fileName)
                            .placeholder(ContextCompat.getDrawable(mContext, placeholder))
                            .error(ContextCompat.getDrawable(mContext, placeholder))
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(imageView);
                    break;
                case 1:
                    Picasso.with(mContext)
                            .load(fileName)
                            .centerInside()
                            .placeholder(ContextCompat.getDrawable(mContext, placeholder))
                            .error(ContextCompat.getDrawable(mContext, placeholder))
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(imageView);
                    break;
                case 2:
                    Picasso.with(mContext)
                            .load(fileName)
                            .fit()
                            .placeholder(ContextCompat.getDrawable(mContext, placeholder))
                            .error(ContextCompat.getDrawable(mContext, placeholder))
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(imageView);
                    break;
                default:
                    Picasso.with(mContext)
                            .load(fileName)
                            .placeholder(ContextCompat.getDrawable(mContext, placeholder))
                            .error(ContextCompat.getDrawable(mContext, placeholder))
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(imageView);
                    break;

            }
    }

    public static void loadImage(Context mContext, String fileName, int scaleType, ImageView imageView) {
        if (fileName != null)
            switch (scaleType) {
                case 0:
                    Picasso.with(mContext)
                            .load(fileName)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(imageView);
                    break;
                case 1:
                    Picasso.with(mContext)
                            .load(fileName)
                            .centerInside()
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(imageView);
                    break;
                case 2:
                    Picasso.with(mContext)
                            .load(fileName)
                            .fit()
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(imageView);
                    break;
                default:
                    Picasso.with(mContext)
                            .load(fileName)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .into(imageView);
                    break;

            }
    }

    public static void showProgressDialog(Context mContext, String message) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static void changeTabsFont(TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    Typeface type = Typeface.createFromAsset(tabLayout.getContext().getAssets(), "fonts/Montserrat-Bold.ttf");
                    ((TextView) tabViewChild).setTypeface(type);
                }
            }
        }
    }

    public static int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
