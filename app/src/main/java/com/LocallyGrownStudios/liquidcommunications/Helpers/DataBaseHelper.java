package com.LocallyGrownStudios.liquidcommunications.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.LocallyGrownStudios.liquidcommunications.R;


public class DataBaseHelper extends SQLiteOpenHelper {


    // Database name and version

    public static final String dbName = "LiquidCommunications.db";
    public static final int dbVersion = 1;


    // Contacts database table names

    public static final String tableContact = "Contacts";
    public static final String tableMms = "Mms";
    public static final String tableSms = "Sms";
    public static final String tableEmail = "Email";
    public static final String tableSocialMedia = "SocialMedia";


    // Contact table columns

    public static final String contactID = "_id";
    public static final String numPhone = "LQ_Number";
    public static final String email = "LQ_Email";
    public static final String numContacted = "LQ_Contacted";
    public static final String name = "LQ_Name";
    public static final String hasNumber = "LQ_HasNumber";
    public static final String lastContacted = "LQ_LastContacted";
    public static final String hasContacted = "LQ_HasContacted";
    public static final String contactPhoto = "LQ_ContactPhoto";


    // SMS table columns

    public static final String smsLQID = "LQ_id";
    public static final String smsID = "_id";
    public static final String smsThreadID = "LQ_Thread_ID";
    public static final String smsName = "LQ_Name";
    public static final String smsNumber = "LQ_Number";
    public static final String smsMessage = "LQ_Message";
    public static final String smsDate = "LQ_Date";
    public static final String smsDateSent = "LQ_DateSent";
    public static final String smsType = "LQ_Type";
    public static final String smsHasRead = "LQ_HasRead";


    // Mms table columns

    public static final String mmsLQID = "LQ_id";
    public static final String mmsID = "_id";
    public static final String mmsThreadID = "LQ_Thread_ID";
    public static final String mmsDate = "LQ_Date";
    public static final String mmsName = "LQ_Name";
    public static final String mmsNumber = "LQ_Number";
    public static final String mmsMessage = "LQ_Message";
    public static final String mmsPicture = "LQ_Picture";
    public static final String mmsType = "LQ_Type";


    Context context;


    public DataBaseHelper(Context context) {

        super(context, dbName, null, dbVersion);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlContacts = context.getString(R.string.sqlContacts);
        String sqlMms = context.getString(R.string.sqlMms);
        String sqlSms = context.getString(R.string.sqlSms);
        db.execSQL(sqlContacts);
        db.execSQL(sqlMms);
        db.execSQL(sqlSms);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Upgrade to AlterTable before finish

        db.execSQL("drop table if exists" + tableContact);
        db.execSQL("drop table if exists" + tableMms);
        db.execSQL("drop table if exists" + tableSms);
        db.execSQL("drop table if exists" + tableEmail);
        db.execSQL("drop table if exists" + tableSocialMedia);
        this.onCreate(db);

    }

}
