package com.example.tahumarket.helper;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tahumarket.activity.LoginActivity;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by WSeven7 on 1/2/2017.
 */

public final class Config {
    public static final String BASE_URL = "";
    public static final String BASE_URL_API_PRODUK = "http://www.ecollectcpu.com:88/api/product/poststaging/";
    public static final String BASE_URL_API_ORDER = "http://www.ecollectcpu.com:88/api/salesorders/poststaging/";

    private static final String API = "api/";
    public static final String BASE_URL_API = BASE_URL + API;

    public static final String UPLOAD_FOLDER = "uploads/";
    public static final String BASE_URL_UPLOADS = BASE_URL + UPLOAD_FOLDER;
    public static final String FIREBASE_URL = "https://dazzling-torch-6878.firebaseio.com/ABS/";

    public static final String TOAST_AN_ERROR = "Mohon maaf, terjadi kendala jaringan / server";

    public static final String SHARED_PREF_NAME = "Tahu";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_SUPER_ADMIN = "superAdmin001";
    public static final String CONFIG_URL_PRODUK = "produk";
    public static final String CONFIG_URL_ORDER = "order";
    public static final String CONFIG_PPN = "ppn";
    public static final String CONFIG_DISKON = "diskon";

    public static final String LOGIN_NAME_SHARED_PREF = "NAME";
    public static final String LOGIN_ID_SHARED_PREF = "ID";
    public static final String LOGIN_TIME = "TIME";
    public static final String LOGIN_ADDRESS_SHARED_PREF = "ADDRESS";
    public static final String LOGIN_CITY_SHARED_PREF = "CITY";
    public static final String LOGIN_ZIP_CODE_SHARED_PREF = "ZIP CODE";
    public static final String LOGIN_TOKEN_SHARED_PREF = "TOKEN";
    public static final String LOGIN_EMAIL_SHARED_PREF = "EMAIL";
    public static final String LOGIN_PHONE_SHARED_PREF = "PHONE";
    public static final String LOGIN_GROUP_NAME_SHARED_PREF = "GROUP";
    public static final String LOGIN_GROUP_ID_SHARED_PREF = "GROUP_ID";
    public static final String LOGIN_STATUS_SHARED_PREF = "loggedin";
    public static final String LOGIN_AVATAR_SHARED_PREF = "AVATAR";
    public static final String LOGIN_EXTRA_01_SHARED_PREF = "EXTRA_01";
    public static final String LOGIN_EXTRA_02_SHARED_PREF = "EXTRA_02";
    public static final String LOGIN_EXTRA_03_SHARED_PREF = "EXTRA_03";
    public static final String LOGIN_EXTRA_04_SHARED_PREF = "EXTRA_04";
    public static final String LOGIN_EXTRA_05_SHARED_PREF = "EXTRA_05";


    public static final String SHARED_PREF_NAME_BIAYA_SINKRON = "Biaya Sinkron";
    public static final String BS_KODE = "KODE";
    public static final String BS_ORDER_ID_SPINNER = "ORDER_ID";
    public static final String BS_NOMINAL = "NOMINAL";
    public static final String BS_INFO = "INFO";
    public static final String BS_IMG_PATH_BIAYA = "IMG";

    public static final String FCM_TOKEN_REGISTRATION_COMPLETE = "FCM_TOKEN_REGISTRATION_COMPLETE";
    public static final String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF_TAG_TOKEN = "SHARED_PREF_TAG_TOKEN";

    public static final String RESPONSE_STATUS_FIELD = "STATUS";
    public static final String RESPONSE_STATUS_VALUE_SUCCESS = "SUCCESS";
    public static final String RESPONSE_STATUS_VALUE_ERROR = "ERROR";
    public static final String RESPONSE_MESSAGE_FIELD = "MESSAGE";
    public static final String RESPONSE_PAYLOAD_FIELD = "PAYLOAD";

    public static final String ERROR_NETWORK = "Periksa kembali jaringan Anda";

    public static final int KEYWORD_SEARCH_MIN_LENGTH = 4;

    //nav item

    //Camera request code
    //utk full size image capture
    public static final int PERMISSION_REQUEST_CAMERA = 1777;

    public static final int PERMISSION_LOCATION = 1;
    public static final int PERMISSION_CALL = 2;
    public static final int PERMISSION_WRITE_EXST = 3;
    public static final int PERMISSION_READ_EXST = 4;
    public static final int PERMISSION_CAMERA = 5;
    public static final int PERMISSION_ACCOUNTS = 6;
    public static final int PERMISSION_GPS_SETTINGS = 7;
    public static final int PERMISSION_SEND_SMS = 8;

    //File request code
    public static final int PICK_FILE_REQUEST = 1;

    public static final String RESPONSE_PAYLOAD_API_ACTION = "API_ACTION";
    public static final String RESPONSE_PAYLOAD_API_ACTION_LOGOUT = "LOGOUT";

    public static final String EVENT_BUS_RELOAD = "EB_RELOAD";

//    public static boolean isNetworkAvailable(Context ctx) {
//        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//        // if no network is available networkInfo will be null
//        // otherwise check if we are connected
//        if (networkInfo != null && networkInfo.isConnected()) {
//            return true;
//        }
//        return false;
//    }

    public static void forceLogout(Context context) {
        //Getting out shared preferences
        SharedPreferences preferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Getting editor
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Config.LOGIN_STATUS_SHARED_PREF, false);
        editor.putString(Config.LOGIN_ID_SHARED_PREF, "");
        editor.putString(Config.LOGIN_ADDRESS_SHARED_PREF, "");
        editor.putString(Config.LOGIN_TIME, "");
        editor.putString(Config.LOGIN_CITY_SHARED_PREF, "");
        editor.putString(Config.LOGIN_ZIP_CODE_SHARED_PREF, "");
        editor.putString(Config.LOGIN_NAME_SHARED_PREF, "");
        editor.putString(Config.LOGIN_GROUP_NAME_SHARED_PREF, "");
        editor.putString(Config.LOGIN_GROUP_ID_SHARED_PREF, "");
        editor.putString(Config.LOGIN_TOKEN_SHARED_PREF, "");
        editor.putString(Config.LOGIN_EMAIL_SHARED_PREF, "");
        editor.putString(Config.LOGIN_PHONE_SHARED_PREF, "");
        editor.putString(Config.LOGIN_AVATAR_SHARED_PREF, "");
        editor.putString(Config.LOGIN_EXTRA_01_SHARED_PREF, "");
        editor.putString(Config.LOGIN_EXTRA_02_SHARED_PREF, "");
        editor.putString(Config.LOGIN_EXTRA_03_SHARED_PREF, "");
        editor.putString(Config.LOGIN_EXTRA_04_SHARED_PREF, "");
        editor.putString(Config.LOGIN_EXTRA_05_SHARED_PREF, "");

        //Saving the sharedpreferences
        editor.commit();

        SharedPreferences biaya = context.getSharedPreferences(Config.SHARED_PREF_NAME_BIAYA_SINKRON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorBiaya = biaya.edit();
        editorBiaya.putBoolean(Config.SHARED_PREF_NAME_BIAYA_SINKRON, false);
        editorBiaya.putString(Config.BS_KODE, "");
        editorBiaya.putString(Config.BS_ORDER_ID_SPINNER, "");
        editorBiaya.putString(Config.BS_NOMINAL, "");
        editorBiaya.putString(Config.BS_INFO, "");
        editorBiaya.putString(Config.BS_IMG_PATH_BIAYA, "");

        editorBiaya.commit();

        Toast.makeText(context, "Anda telah logout dari aplikasi.\nUntuk mengakses beberapa fitur, Anda harus login terlebih dahulu", Toast.LENGTH_LONG).show();
        //Starting login activity
        Intent intent = new Intent(context.getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context, final String permissionType) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M) {
            if(permissionType.equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                if (ContextCompat.checkSelfPermission(context, permissionType) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionType)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Ijin Akses");
                        alertBuilder.setMessage("Aplikasi memerlukan ijin akses ruang penyimpanan");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            private void doNothing() {

                            }

                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_READ_EXST);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();

                        //Toast.makeText(context, "HIT #1", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_READ_EXST);
                        //Toast.makeText(context, "HIT #2", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
                else {
                    return true;
                }
            }
            else if(permissionType.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (ContextCompat.checkSelfPermission(context, permissionType) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionType)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Ijin Akses");
                        alertBuilder.setMessage("Aplikasi memerlukan ijin akses ruang penyimpanan");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            private void doNothing() {

                            }

                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_WRITE_EXST);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }
                    else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_WRITE_EXST);
                    }
                    return false;
                }
                else {
                    return true;
                }
            }
            else if(permissionType.equalsIgnoreCase(Manifest.permission.CAMERA)) {
                if (ContextCompat.checkSelfPermission(context, permissionType) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionType)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Ijin Akses");
                        alertBuilder.setMessage("Aplikasi memerlukan ijin akses kamera");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            private void doNothing() {

                            }

                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_CAMERA);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();

                        //Toast.makeText(context, "HIT #1", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_CAMERA);

                        //Toast.makeText(context, "HIT #2", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
                else {
                    return true;
                }
            }
            else if(permissionType.equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION) || permissionType.equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (ContextCompat.checkSelfPermission(context, permissionType) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionType)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Ijin Akses");
                        alertBuilder.setMessage("Aplikasi memerlukan ijin akses lokasi");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            private void doNothing() {

                            }

                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_LOCATION);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }
                    else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_LOCATION);
                    }
                    return false;
                }
                else {
                    return true;
                }
            }
            else if(permissionType.equalsIgnoreCase(Manifest.permission.CALL_PHONE)) {
                if (ContextCompat.checkSelfPermission(context, permissionType) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionType)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Ijin Akses");
                        alertBuilder.setMessage("Aplikasi memerlukan ijin akses telepon");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            private void doNothing() {

                            }

                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_CALL);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }
                    else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_CALL);
                    }
                    return false;
                }
                else {
                    return true;
                }
            }
            else if(permissionType.equalsIgnoreCase(Manifest.permission.SEND_SMS)) {
                if (ContextCompat.checkSelfPermission(context, permissionType) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionType)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Ijin Akses");
                        alertBuilder.setMessage("Aplikasi memerlukan ijin akses mengirim SMS");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            private void doNothing() {

                            }

                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_SEND_SMS);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    }
                    else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permissionType}, PERMISSION_SEND_SMS);
                    }
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    }

    public static String getRegisterId(Context ctx) {
        //SharedPreferences sharedPreferences = ctx.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //String regId = sharedPreferences.getString(Config.SHARED_PREF_REGISTER_ID, "");

        //getting unique id for device
        String regId = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);

        return regId;
    }

    //helper full size image from camera
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        // BEST QUALITY MATCH
        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static String getMetadata(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString(name);
            }
        }
        catch (PackageManager.NameNotFoundException e) {
            return null;
        }

        return null;
    }

    public static boolean isGPSAvailable(Context ctx){
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        return lm != null && (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public static boolean checkEmailFormat(String email) {
        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            return false;
        }
        else {
            return true;
        }
    }

    public static String randomString(int len) {
        final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random RANDOM = new Random();

        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }

    public static String formatYMD(int year, int month, int date) {
        String formattedDate = "";

        formattedDate += Integer.toString(year);
        formattedDate += "-";

        if(month < 10) {
            formattedDate += "0" + Integer.toString(month);
        }
        else {
            formattedDate += Integer.toString(month);
        }
        formattedDate += "-";

        if(date < 10)   {
            formattedDate += "0" + Integer.toString(date);
        }
        else {
            formattedDate += Integer.toString(date);
        }

        return formattedDate;
    }

    public static String formatDMY(int year, int month, int date) {
        String formattedDate = "";

        if(date < 10)   {
            formattedDate += "0" + Integer.toString(date);
        }
        else {
            formattedDate += Integer.toString(date);
        }
        formattedDate += "-";

        if(month < 10) {
            formattedDate += "0" + Integer.toString(month);
        }
        else {
            formattedDate += Integer.toString(month);
        }
        formattedDate += "-";

        formattedDate += Integer.toString(year);

        return formattedDate;
    }

    public static String formatCustomTime(int hour, int minute) {
        String formattedTime = "";

        if(hour < 10) {
            formattedTime = "0" + Integer.toString(hour);
        }
        else {
            formattedTime = Integer.toString(hour);
        }

        formattedTime += ":";

        if(minute < 10) {
            formattedTime += "0" + Integer.toString(minute);
        }
        else {
            formattedTime += Integer.toString(minute);
        }

        return formattedTime;
    }

    public static String formatCustomDate(int year, int month, int date) {
        String formattedDate = "";

        if(date < 10)   {
            formattedDate = "0" + Integer.toString(date);
        }
        else {
            formattedDate = Integer.toString(date);
        }

        if(month == 1)  formattedDate += " Januari ";
        if(month == 2)  formattedDate += " Februari ";
        if(month == 3)  formattedDate += " Maret ";
        if(month == 4)  formattedDate += " April ";
        if(month == 5)  formattedDate += " Mei ";
        if(month == 6)  formattedDate += " Juni ";
        if(month == 7)  formattedDate += " Juli ";
        if(month == 8)  formattedDate += " Agustus ";
        if(month == 9)  formattedDate += " September ";
        if(month == 10)  formattedDate += " Oktober ";
        if(month == 11)  formattedDate += " November ";
        if(month == 12)  formattedDate += " Desember ";

        return (formattedDate + "" + year);
    }

    public static int getIndex(ArrayList<ItemOption> list, Spinner spinner, String searchId){
        for (int i=0;i<spinner.getCount();i++){
            if (list.get(i).getOptId().equalsIgnoreCase(searchId)){
                return i;
            }
        }

        return 0;
    }
}
