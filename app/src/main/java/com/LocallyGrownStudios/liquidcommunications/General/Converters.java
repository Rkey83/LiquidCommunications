package com.LocallyGrownStudios.liquidcommunications.General;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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





}
