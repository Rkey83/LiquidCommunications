package com.LocallyGrownStudios.liquidcommunications.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.LocallyGrownStudios.liquidcommunications.R;


public class ContactHelper extends SQLiteOpenHelper {

    public static final String dbName = "LiquidCommunications.db";
    public static final int dbVersion = 1;
    public static final String tableContact = "Contacts";
    public static final String tableSmsMms = "SmsMms";
    public static final String tableEmail = "Email";
    public static final String socialMedia = "SocialMedia";
    public static final String contactID = "_id";
    public static final String numPhone = "LQ_Number";
    public static final String email = "LQ_Email";
    public static final String numContacted = "LQ_Contacted";
    public static final String name = "LQ_Name";
    public static final String hasNumber = "LQ_HasNumber";
    public static final String lastContacted = "LQ_LastContacted";
    public static final String hasContacted = "LQ_HasContacted";
    public static final String contactPhoto = "LQ_ContactPhoto";
    Context context;


    public ContactHelper(Context context) {

        super(context, dbName, null, dbVersion);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = context.getString(R.string.sqlContacts);
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Upgrade to AlterTable before finish

        db.execSQL("drop table if exists" + tableContact);
        this.onCreate(db);

    }

}
