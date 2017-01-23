package com.urvesh.android_arch_mvp.tools;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.urvesh.android_arch_mvp.BuildConfig;
import com.urvesh.android_arch_mvp.R;
import com.urvesh.android_arch_mvp.domain.JsonKeys;
import com.urvesh.android_arch_mvp.domain.model.ErrorModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Utils {

    private static String TAG = "Utils";
    private static Dialog progressDialog;

    private static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        if (useIPv4) {
                            if ((addr instanceof Inet4Address))
                                return addr.getHostAddress().toUpperCase();
                        } else {
                            if ((addr instanceof Inet6Address))
                                return addr.getHostAddress().toUpperCase();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.exception(TAG, e);
        }
        return "";
    }

    public static void displayErrorMsg(Context context, ErrorModel responseModel) {
        switch (responseModel.getStatusCode()) {
            case -1://No Internet
                break;
            case 0:
                break;
            case 200:// status "OK"
                break;
            case 201:// status "Created"
                break;
            case 300:// status "Multiple Choices"
                break;
            case 301:// status "Moved Permanently"
                break;
            case 400:// status "Bad Request"
                break;
            case 401:// status "Unauthorized"
                Utils.userLogout(context);
                break;
            case 403:
                break;
            case 404:// status "Not Found"
                break;
            case 405://status "Method Not Allowed"
                break;
            case 500:// status "Internal Server Error"
                break;
            case 502:// status "Bad Gateway"
                break;
            case 503:// status "Service Unavailable"
                break;
            case 504:// status "Gateway Timeout" || "Socket TimeOut"
                break;
            default:
                break;
        }

    }


    public static String getDeviceId(Context context) {
        String aid = "";
        try {
            aid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (aid == null) {
                TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                aid = TelephonyMgr.getDeviceId();
            }
            if (aid == null) {
                WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                aid = wm.getConnectionInfo().getMacAddress();
            }
        } catch (Exception e) {
            Logger.exception("DeviceId", e);
        }
        return aid;
    }

    public static boolean isInternetConnected(Context context) {
        return isInternetConnected(context, true);
    }

    public static int getCurrentVersionCode(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isInternetConnected(Context context, boolean showMsg) {
        boolean networkConnected = isNetworkAvailable(context);
        if (!networkConnected && showMsg)
            displayErrorMsg(context, new ErrorModel(-1, context.getString(R.string.no_internet_connection)));
        return networkConnected;
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }


    public static String getRoundOffValue(float value) {
        float newvalue = BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        if ((newvalue - (int) value) != 0)
            return newvalue + "";
        else
            return ((int) newvalue) + "";
    }

    public static String capWords(String sentence) {
        try {
            char[] array = sentence.trim().toCharArray();
            // Uppercase first letter.
            if (array.length > 0) {
                array[0] = Character.toUpperCase(array[0]);

                // Uppercase all letters that follow a whitespace character.
                for (int i = 1; i < array.length; i++) {
                    if (Character.isWhitespace(array[i - 1])) {
                        array[i] = Character.toUpperCase(array[i]);
                    }
                }
                // Result.
                return new String(array);
            } else {
                return "";
            }
        } catch (Exception e) {
            Logger.exception(sentence + " <- Word", e);
            return "";
        }
    }

    public static File createFile(Context context, Bitmap bitmap) {
        File f = new File(context.getCacheDir(), "cover_image");
        try {
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos;
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight,
                                            ScalingUtilities.ScalingLogic scalingLogic) {
        if (unscaledBitmap != null) {
            Rect srcRect = ScalingUtilities.calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                    dstWidth, dstHeight, scalingLogic);
            Rect dstRect = ScalingUtilities.calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(),
                    dstWidth, dstHeight, scalingLogic);
            Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
            return scaledBitmap;
        } else {
            return null;
        }
    }

    private static final int WIDTH_PX = 200;
    private static final int HEIGHT_PX = 80;

    public static boolean isLanguageSupported(Context context, String text) {
        int w = WIDTH_PX, h = HEIGHT_PX;
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        Bitmap orig = bitmap.copy(conf, false);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize((int) (14 * scale));

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawText(text, x, y, paint);
        boolean res = !orig.sameAs(bitmap);
        orig.recycle();
        bitmap.recycle();
        return res;
    }

    public static void clearNotifications() {
        //NotificationManager notificationManager = (NotificationManager) MainApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.cancelAll();
    }


    public static void getToAppsSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static Bitmap getBitmapFromFilePath(String filePath) {
        File image = new File(filePath);
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            return BitmapFactory.decodeFile(image.getAbsolutePath(), options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void userLogout(Context context) {
        new StoreSession(context).clearData();
        Intent intent = new Intent();//Activity to start with
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static Uri getLocalDrawableUri(Context mContext, String fileName) {
        return Uri.parse("android.resource://" + mContext.getPackageName() + "/drawable/" + fileName);
    }

    public static HashMap<String, String> additionParams(Context context) {
        //Additional data if needed to pass in rest call
        HashMap<String, String> params = new HashMap<>();
        params.put(JsonKeys.APP_VERSION, String.valueOf(BuildConfig.VERSION_CODE));
        params.put(JsonKeys.APP_IP_ADDRESS, Utils.getIPAddress(true));
        params.put(JsonKeys.USER_AGENT, "android");
        params.put(JsonKeys.USER_AGENT_VERSION, String.valueOf(android.os.Build.VERSION.SDK_INT));
        params.put(JsonKeys.APP_PACKAGE, BuildConfig.APPLICATION_ID);
        params.put(JsonKeys.DEVICE_ID, getDeviceId(context));
        return params;
    }
}