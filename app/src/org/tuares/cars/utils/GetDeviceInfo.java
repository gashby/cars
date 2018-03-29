package org.tuares.cars.utils;

/**
 * Created by gashby on 23.02.2018.
 */

import android.content.Context;
import android.content.res.Configuration;

import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings.Secure;


public class GetDeviceInfo {

    protected Context mContext;

    GetDeviceInfo(Context context) {
        mContext = context;
    }

    public final static String GetDeviceId(Context mContext) {
        String deviceId = Secure.getString(mContext.getContentResolver(),
                Secure.ANDROID_ID);
        return deviceId;
    }

    public final static boolean IsOrientationLandscape(Context mContext) {
        return mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public final static boolean IsLargeScreenDevice(Context mContext) {
        return ((mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public final static boolean IsXLargeScreenDevice(Context mContext) {
        return ((mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
    }

    public final static boolean IsMultiPane(Context mContext) {
        boolean IsMultiPane = false;

        int mScreenType = getScreenType(mContext);

        switch (mScreenType) {

            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                IsMultiPane = false;
                break;

            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                IsMultiPane = false;
                break;

            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                IsMultiPane = true;
                break;

            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                IsMultiPane = true;
                break;

            default:
                IsMultiPane = false;
                break;
        }

        return IsMultiPane;
    }


    private static int getScreenType(Context mContext) {
        return mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }
}