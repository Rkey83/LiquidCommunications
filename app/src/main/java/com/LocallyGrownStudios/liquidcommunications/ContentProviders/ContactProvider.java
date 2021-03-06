package com.LocallyGrownStudios.liquidcommunications.ContentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.LocallyGrownStudios.liquidcommunications.Helpers.DataBaseHelper;

public final class ContactProvider extends ContentProvider {

    public static final String Contact_Table = "Contacts";
    public static final String AUTHORITY = "content://com.LocallyGrownStudios.ContentProviders.ContactProvider";
    public static final  Uri contactUri = Uri.parse(AUTHORITY);
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase contactDb;

    public ContactProvider() {
    }


    @Override
    public boolean onCreate() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        dataBaseHelper = new DataBaseHelper(getContext());
        contactDb = dataBaseHelper.getWritableDatabase();
        Cursor cursor = contactDb.query(DataBaseHelper.tableContact,projection,selection,selectionArgs,null,null,sortOrder);

        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        values = new ContentValues();


        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(Uri uri) {

        if (uri.getLastPathSegment() == null){
            return "vnd.android.cursor.item/vnd.LocallyGrownStudios.Provider";
        }
        else{
            return "vnd.android.cursor.dir/vnd.LocallyGrownStudios.Provider";
        }
    }


}
