package org.tuares.cars.utils;

import android.text.TextUtils;

import com.salesforce.androidsdk.smartsync.util.Constants;

/**
 * Created by gashby on 07.08.2017.
 */

public class TextUtil {

    public static String sanitizeText(String text) {
        if (TextUtils.isEmpty(text) || text.equals(Constants.NULL_STRING)) {
            return Constants.EMPTY_STRING;
        }
        return text;
    }
}
