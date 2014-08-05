package com.LocallyGrownStudios.liquidcommunications.ContentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.LocallyGrownStudios.liquidcommunications.Helpers.ContactHelper;

public final class ContactProvider extends ContentProvider {

    public static final String Contact_Table = "Contacts";
    public static final String AUTHORITY = "content://com.LocallyGrownStudios.ContentProviders.ContactProvider";
    public static final  Uri contactUri = Uri.parse(AUTHORITY);
    ContactHelper contactHelper;
    SQLiteDatabase contactDb;

    public ContactProvider() {
    }


    @Override
    public boolean onCreate() {

        ContactHelper contactHelper = new ContactHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        contactHelper = new ContactHelper(getContext());
        contactDb = contactHelper.getWritableDatabase();
        Cursor cursor = contactDb.query(ContactHelper.tableContact,projection,selection,selectionArgs,null,null,sortOrder);

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
