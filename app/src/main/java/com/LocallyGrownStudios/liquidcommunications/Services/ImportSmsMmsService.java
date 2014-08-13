package com.LocallyGrownStudios.liquidcommunications.Services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

import com.LocallyGrownStudios.liquidcommunications.General.Converters;
import com.LocallyGrownStudios.liquidcommunications.Helpers.DataBaseHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Date;

public class ImportSmsMmsService extends IntentService {

    // Class Objects

    ContentValues valuesMms;
    SQLiteDatabase contactDb;
    DataBaseHelper dataBaseHelper;
    Uri mmsUri = Uri.parse("content://mms/");
    Uri mmsPartUri = Uri.parse("content://mms/part");
    int id, smsType, smsID, smsHasRead, smsThreadId, smsLQID, i, mmsType;
    String mmsId, smsName, smsNumber, smsMessage, smsDate, smsDateSent, mmsBody, mmsName, mmsNumber, mmsDate, mmsThreadID;
    Cursor cursorSms, cursorContactName, cursorGetMms, cursorMmsContent;
    byte[] mmsImage;


    // Set an empty constructor for Service and name it

    public ImportSmsMmsService() {
        super("ImportSmsMmsService");
    }

    // on creation of the Service

    @Override
    public void onCreate() {

        super.onCreate();
    }


    // On start of the Service

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    // Handle intents from Activities

    @Override
    protected void onHandleIntent(Intent intent) {

        // Open the Database and write to it

        dataBaseHelper = new DataBaseHelper(ImportSmsMmsService.this);
        ContentValues valuesSms = new ContentValues();
        valuesMms = new ContentValues();
        contactDb = dataBaseHelper.getWritableDatabase();

        // Get Mms and Sms information

        getMms();


        Uri uri = Uri.parse("content://sms");
        cursorSms = getContentResolver().query(uri, null, null, null, "_id" + " ASC");
        i = 1;
        while (cursorSms.moveToNext()) {

            getSms();

            valuesSms.put(DataBaseHelper.smsLQID, smsLQID);
            valuesMms.put(DataBaseHelper.smsID, smsID);
            valuesSms.put(DataBaseHelper.smsThreadID, smsThreadId);
            valuesSms.put(DataBaseHelper.smsName, smsName);
            valuesSms.put(DataBaseHelper.smsNumber, smsNumber);
            valuesSms.put(DataBaseHelper.smsMessage, smsMessage);
            valuesSms.put(DataBaseHelper.smsType, smsType);
            valuesSms.put(DataBaseHelper.smsDate, smsDate);
            valuesSms.put(DataBaseHelper.smsDateSent, smsDateSent);
            valuesSms.put(DataBaseHelper.smsHasRead, smsHasRead);

            contactDb.insertWithOnConflict(DataBaseHelper.tableSms, null, valuesSms, SQLiteDatabase.CONFLICT_REPLACE);
            i++;
            Log.e("SMS : ", "Value Added");
        }
        cursorSms.close();
    }

    private void getSms() {

        int ID = cursorSms.getInt(cursorSms.getColumnIndex("_id"));

        if (ID != 0) {

            smsID = ID;
            smsLQID = i;

        } else {

            smsID = 0;
        }

        String number = cursorSms.getString(cursorSms.getColumnIndex("address"));

        if (number != null) {
            smsNumber = number;
            smsName = findContactName(number);
        } else {

            smsNumber = null;
        }

        int threadID = cursorSms.getInt(cursorSms.getColumnIndex("thread_id"));

        if (threadID != 0) {

            smsThreadId = threadID;
        } else {

            smsThreadId = 0;
        }

        String dateSent = cursorSms.getString(cursorSms.getColumnIndex("date_sent"));

        if (dateSent != null) {

            smsDateSent = dateSent;
        } else {

            smsDateSent = null;
        }

        int smsRead = cursorSms.getInt(cursorSms.getColumnIndex("read"));

        if (smsRead != 0) {

            smsHasRead = smsRead;
        } else {

            smsHasRead = 0;
        }


        int type = cursorSms.getInt(cursorSms.getColumnIndex("type"));

        if (type > 0) {

            smsType = type;
        } else {

            smsType = 0;
        }


        Long date = cursorSms.getLong(cursorSms.getColumnIndex("date"));

        if (date != null) {

            long thisdate = date;
            smsDate = Converters.getDate(thisdate,"EEE MMM dd HH:mm:ss zzz yyyy" );
        } else {

            smsDate = null;
        }

        String message = cursorSms.getString(cursorSms.getColumnIndex("body"));

        if (message != null) {


            smsMessage = message;
        } else {

            smsMessage = null;
        }
    }


    private void getMms() {

        final String[] projection = new String[]{"_id", "ct_t"};
        Uri uri = Uri.parse("content://mms/");
        cursorGetMms = getContentResolver().query(uri, projection, null, null, null);
        while (cursorGetMms.moveToNext()) {
            String string = cursorGetMms.getString(cursorGetMms.getColumnIndex("ct_t"));

            if ("application/vnd.wap.multipart.related".equals(string)) {

                mmsId = cursorGetMms.getString(cursorGetMms.getColumnIndex(Telephony.MmsSms._ID));
                i = Integer.decode(mmsId);

                String selection = "_id = " + mmsId;

                Cursor cursor = getContentResolver().query(mmsUri, null, selection, null, null);

                getMmsContentType();

                cursor.close();
            }

            if (mmsBody != null){
                mmsType = 1;
                if (mmsImage != null){
                    mmsType = 3;
                }
            }
            else if (mmsImage != null){
                mmsType = 2;
            }


            valuesMms.put(DataBaseHelper.mmsID, mmsId);
            valuesMms.put(DataBaseHelper.mmsLQID, i);
            valuesMms.put(DataBaseHelper.mmsDate, mmsDate);
            valuesMms.put(DataBaseHelper.mmsThreadID, mmsThreadID);
            valuesMms.put(DataBaseHelper.mmsMessage, mmsBody);
            valuesMms.put(DataBaseHelper.mmsPicture, mmsImage);
            valuesMms.put(DataBaseHelper.mmsType, mmsType);
            valuesMms.put(DataBaseHelper.mmsName, mmsName);
            valuesMms.put(DataBaseHelper.mmsNumber, mmsNumber);

            contactDb.insertWithOnConflict(DataBaseHelper.tableMms, null, valuesMms, SQLiteDatabase.CONFLICT_REPLACE);
            Log.e("MMS : ", "Value Added");
        }
        cursorGetMms.close();
    }


    private void getMmsContentType() {
        String selectionPart = "mid=" + mmsId;
        cursorMmsContent = getContentResolver().query(mmsPartUri, null, selectionPart, null, null);
        mmsBody = null;
        mmsImage = null;
        while (cursorMmsContent.moveToNext()) {
            String partId = cursorMmsContent.getString(cursorMmsContent.getColumnIndex("_id"));
            String type = cursorMmsContent.getString(cursorMmsContent.getColumnIndex("ct"));
            if ("text/plain".equals(type)) {
                String data = cursorMmsContent.getString(cursorMmsContent.getColumnIndex("_data"));
                if (data != null) {
                    mmsBody = getMmsText(partId);
                } else {
                    mmsBody = cursorMmsContent.getString(cursorMmsContent.getColumnIndex("text"));
                }
            }
            if ("image/jpeg".equals(type) || "image/bmp".equals(type) || "image/gif".equals(type) || "image/jpg".equals(type) || "image/png".equals(type)) {
                Bitmap bitmap = getMmsImage(partId);
                bitmap = Bitmap.createBitmap(bitmap);
                mmsImage = Converters.getBytes(bitmap);
                byte[] bytes = mmsImage;
            }
        }

        cursorMmsContent.close();
        getMmsExtras();
        mmsNumber = getAddressNumber(getApplicationContext(), mmsId);
        if (mmsNumber.contains("insert")) {
            mmsNumber = "sent";
            mmsName = null;
        } else {
            mmsNumber = Converters.stripNumberFormatiing(mmsNumber);
            if (mmsNumber.startsWith("1")) {
                mmsNumber = mmsNumber.substring(1);
                mmsName = findContactName(mmsNumber);
            } else if (mmsNumber.startsWith("+")) {
                mmsNumber = mmsNumber.substring(2);
                mmsName = findContactName(mmsNumber);
            } else {
                mmsNumber = Converters.stripNumberFormatiing(mmsNumber);
                mmsName = findContactName(mmsNumber);
            }
        }
    }


    public void getMmsExtras() {

        String selectionPart = "_id=" + mmsId;
        Cursor cursor = getContentResolver().query(Uri.parse("content://mms"), null, selectionPart, null, null);
        cursor.moveToFirst();
        long timestamp = cursor.getLong(2) * 1000;
        Date date = new Date(timestamp);
        mmsDate = date.toString();
        mmsThreadID = cursor.getString(cursor.getColumnIndex("thread_id"));
        cursor.close();
    }


    public static String getAddressNumber(Context context, String id) {
        String addrSelection = "type=137 AND msg_id=" + id;
        String uriStr = MessageFormat.format("content://mms/{0}/addr", id);
        Uri uriAddress = Uri.parse(uriStr);
        String[] columns = {"address"};
        Cursor cursor = context.getContentResolver().query(uriAddress, columns,
                addrSelection, null, null);
        String address = "";
        String val;
        if (cursor.moveToFirst()) {
            do {
                val = cursor.getString(cursor.getColumnIndex("address"));
                if (val != null) {
                    address = val;
                    // Use the first one found if more than one
                    break;
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        // return address.replaceAll("[^0-9]", "");
        return address;
    }


    private String getMmsText(String id) {
        Uri partURI = Uri.parse(mmsPartUri + id);
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = getContentResolver().openInputStream(partURI);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                String temp = reader.readLine();
                while (temp != null) {
                    sb.append(temp);
                    temp = reader.readLine();
                }
            }
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }


    private Bitmap getMmsImage(String _id) {
        Uri partURI = Uri.parse("content://mms/part/" + _id);
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = getContentResolver().openInputStream(partURI);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return bitmap;
    }


    private String findContactName(String number) {

        String name = null;

        // define the columns I want the query to return
        String[] projection = new String[]{
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.PhoneLookup._ID};
        // encode the phone number and build the filter URI
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        // query time
        cursorContactName = getContentResolver().query(contactUri, projection, null, null, null);
        if (cursorContactName != null) {
            if (cursorContactName.moveToFirst()) {
                name = cursorContactName.getString(cursorContactName.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            }
            cursorContactName.close();
        }
        return name;
    }


    // Use if you want to bind service to an Activity

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
