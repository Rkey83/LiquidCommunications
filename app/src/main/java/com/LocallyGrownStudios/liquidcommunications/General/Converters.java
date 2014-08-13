package com.LocallyGrownStudios.liquidcommunications.General;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Converters {


    // Class objects
    public static String strSeparator = " , ";
    public static String[] arr;
    public static String phoneNumber;


    // Combine values from an Array into a single String and separate with value of strSeparator.
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }


    // Separate a String when divided by the srtSeparator value and create an Array with values.
    public static String[] convertStringToArray(String str){

        arr = str.split(strSeparator);
        return arr;
    }


    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        DateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static long convertDateToLong(String string){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        long curMillis;
        Date curDate = null;
        try {
            curDate = formatter.parse(string);
            curMillis = curDate.getTime();
            return curMillis;

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static String getFromMultiPhoneNumbers(String string, int type){

        String[] stringArr = convertStringToArray(string);

        for (int x = 0; x < stringArr.length; x ++){

            string = stringArr[x];

            if (string.contains("_" + type)){

                phoneNumber = string;
                break;
            }
            else{

                phoneNumber = stringArr[0];
            }
        }

        return phoneNumber.split("_")[0];
    }

    public static String formatPhoneNumber(String string){

        if (string != null) {

            if (string.contains("(")) {

                return string;

            } else {

                if (string.length() <= 8) {

                    return string;

                }

                if (string.length() >= 10 && string.length() < 11) {

                    String areaCodeRaw = string.substring(0, 3);
                    String numberProper = string.substring(3, 6);
                    String numberLast = string.substring(6);
                    String contactsNumber = "(" + areaCodeRaw + ")" + " " + numberProper + "-" + numberLast;
                    string = contactsNumber;

                }

                else {

                    String remIntCode = string.substring(2);
                    String areaCodeRaw = remIntCode.substring(0, 3);
                    String numberProper = remIntCode.substring(3, 6);
                    String numberLast = remIntCode.substring(6);
                    String contactsNumber = "(" + areaCodeRaw + ")" + " " + numberProper + "-" + numberLast;
                    string = contactsNumber;

                }
            }
        }
        return string;
    }

    public static Date convertStringToDate (String string){

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static Bitmap adjustOpacity(Bitmap bitmap, int opacity)
    {
        Bitmap mutableBitmap = bitmap.isMutable()
                ? bitmap
                : bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        int colour = (opacity & 0xFF) << 24;
        canvas.drawColor(colour, PorterDuff.Mode.DST_IN);
        return mutableBitmap;
    }


    public static String stripNumberFormatiing(String string){

        if (string.contains("(")){
            string = string.replaceAll("[^0-9]", "");
        }
        return string;
    }


}
