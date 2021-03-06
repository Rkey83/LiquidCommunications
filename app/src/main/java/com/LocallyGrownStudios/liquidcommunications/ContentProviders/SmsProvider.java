package com.LocallyGrownStudios.liquidcommunications.ContentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.LocallyGrownStudios.liquidcommunications.Helpers.DataBaseHelper;

public class SmsProvider extends ContentProvider {

    public static final String Sms_Table = "Sms";
    public static final String Authority = "content://com.LocallyGrownStudios.ContentProviders.SmsProvider";
    public static final Uri smsUri = Uri.parse(Authority);
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase smsDb;


    public SmsProvider() {
    }

    @Override
    public boolean onCreate() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        dataBaseHelper = new DataBaseHelper(getContext());
        smsDb = dataBaseHelper.getWritableDatabase();
        Cursor cursor = smsDb.query(DataBaseHelper.tableSms,projection,selection,selectionArgs,null,null,sortOrder);


        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        if (uri.getLastPathSegment() == null) {
            return "vnd.android.cursor.item/vnd.LocallyGrownStudios.Provider";
        } else {
            return "vnd.android.cursor.dir/vnd.LocallyGrownStudios.Provider";
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
