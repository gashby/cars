package org.tuares.cars.utils;

/**
 * Created by gashby on 06.11.2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import static org.tuares.cars.utils.LocalConstants.CONTACT_COLORS;

// Static class ImageUtility

    public class ImageUtility
    {

        public static String encodeBitmap(Bitmap g) {
            return encodeBase64Bitmap(g);
        }

        private static String encodeBase64Bitmap(Bitmap g) {

            String encodedImage ="";

            if ( g!= null )
            {
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                g.compress(Bitmap.CompressFormat.PNG, 100, boas ); //bm is the bitmap object
                byte[] byteArrayImage = boas .toByteArray();
                encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

            }
            return encodedImage;

        }

        private static byte[] decodeBase64 (String s)
        {
            return android.util.Base64.decode(s, android.util.Base64.DEFAULT);
        }

        private static Bitmap decodeByteArray (byte[] decoded)
        {
            return BitmapFactory.decodeByteArray(decoded,0,decoded.length);
        }

        public static Bitmap decodeBitmap (String s)
        {
            return  decodeByteArray ( decodeBase64(s) );
        }

        public void setBubbleColor(TextView tv, String firstName) {
            firstName = firstName.trim();
            int code = 0;
            if (!TextUtils.isEmpty(firstName)) {
                for (int i = 0; i < firstName.length(); i++) {
                    code += firstName.charAt(i);
                }
            }
            int colorIndex = code % CONTACT_COLORS.length;
            int color = CONTACT_COLORS[colorIndex];
            final GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(color);
            drawable.setShape(GradientDrawable.OVAL);
            tv.setBackground(drawable);
        }

    }