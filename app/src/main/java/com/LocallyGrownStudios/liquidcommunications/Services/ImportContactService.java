package com.LocallyGrownStudios.liquidcommunications.Services;

import android.app.Service;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;
import com.LocallyGrownStudios.liquidcommunications.General.Converters;
import com.LocallyGrownStudios.liquidcommunications.Helpers.ContactHelper;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.*;
import static android.provider.ContactsContract.CommonDataKinds.*;

public class ImportContactService extends Service {

    // Class Objects

    SQLiteDatabase contactDb;
    ContactHelper contactHelper;
    Cursor cursorContacts, cursorPhone, cursorEmail;
    Bitmap btmpContactPhoto, btmpContactImage;
    byte[] bytContactPhoto;
    String strName, strPhoneNumber, strPhoneType, strEmailId, strEmail, strPhoneId, strAllNumbers, strAllEmails, strLastContacted, strContactPhoto;
    String[] arrNumbers, arrEmails;
    int intHasNumber, intContactID, intTimesContacted, intHasContacted;
    Long lnglastContacted;
    Uri uriContacts, uriPhone, uriEmail;
    List<String> listPhoneNumber = new ArrayList<String>();
    List<String> listEmails = new ArrayList<String>();
    InputStream inputStream;



    @Override
    public void onCreate() {
        super.onCreate();
    }



    // On start of the Service

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Open DataBase and write to it

        contactHelper = new ContactHelper(ImportContactService.this);
        contactDb = contactHelper.getWritableDatabase();
        ContentValues contactValues = new ContentValues();

        // Set URI's(tables);

        uriContacts = Contacts.CONTENT_URI;
        uriPhone = Phone.CONTENT_URI;
        uriEmail = Email.CONTENT_URI;

        // Contact ID's

        strPhoneId = Phone.CONTACT_ID;
        strEmailId = Email.CONTACT_ID;

        // Start Contact Cursor and Move to first; (TableToLookIn, Projection, Selection, SelectionArgs, SortBy)

        cursorContacts = getContentResolver().query(uriContacts, null, null, null, null);
        cursorContacts.moveToFirst();

        // While another Object exists move to the next

        while (cursorContacts.moveToNext()) {


            // Get contact info
            getContacts();

            // Set values for DataBase
            contactValues.put(ContactHelper.contactID, intContactID);
            contactValues.put(ContactHelper.name, strName);
            contactValues.put(ContactHelper.hasNumber, intHasNumber);
            contactValues.put(ContactHelper.contactPhoto, bytContactPhoto);
            contactValues.put(ContactHelper.hasContacted, intHasContacted);
            contactValues.put(ContactHelper.numPhone, strAllNumbers);
            contactValues.put(ContactHelper.numContacted, intTimesContacted);
            contactValues.put(ContactHelper.lastContacted, strLastContacted);
            contactValues.put(ContactHelper.email, strAllEmails);

            // Insert values into DataBase
            contactDb.insertWithOnConflict(ContactHelper.tableContact, null, contactValues, SQLiteDatabase.CONFLICT_REPLACE);

            // Clear lists PhoneNumber and Emails
            listPhoneNumber.clear();
            listEmails.clear();
        }

        // Close Contact Cursor
        cursorContacts.close();


        // Make Toast
        Toast.makeText(this, "Contacts DataBase Created", Toast.LENGTH_LONG).show();

        // If called as a Method, return ..
        return super.onStartCommand(intent, flags, startId);
    }


    // Get Contact Information

    private void getContacts() {

        // Get data from Contact Cursor

        intContactID = cursorContacts.getInt(cursorContacts.getColumnIndex(Contacts._ID));
        strName = cursorContacts.getString(cursorContacts.getColumnIndex(Contacts.DISPLAY_NAME));
        intHasNumber = Integer.parseInt(cursorContacts.getString(cursorContacts.getColumnIndex(Contacts.HAS_PHONE_NUMBER)));

        // If contact has a Phone Number

        if (intHasNumber > 0) {

            // Open the Phone Cursor

            cursorPhone = getContentResolver().query(uriPhone, null, strPhoneId + "= ?", new String[]{Integer.toString(intContactID)}, null);

            // If a Phone Number is a valid column, move to first position

            if (cursorPhone.moveToFirst()) {

                //  Check for Photo

                strContactPhoto = cursorPhone.getString(cursorPhone.getColumnIndex(Phone.PHOTO_FILE_ID));

                // If Photo exists

                    if (strContactPhoto != null) {

                        // Get Photo and Convert to Byte[]

                        inputStream = Contacts.openContactPhotoInputStream(this.getContentResolver(), ContentUris.withAppendedId(Contacts.CONTENT_URI, intContactID));
                        btmpContactPhoto = (BitmapFactory.decodeStream(inputStream));
                        btmpContactImage = Bitmap.createBitmap(btmpContactPhoto);
                        bytContactPhoto = Converters.getBytes(btmpContactImage);

                    }

                // Check whether contact has been contacted

                intTimesContacted = cursorPhone.getInt(cursorPhone.getColumnIndex(Phone.TIMES_CONTACTED));


                // If yes set to 1
                if (intTimesContacted > 0){
                    intHasContacted = 1;
                }

                // Otherwise set to 0
                else{
                    intHasContacted = 0;
                }

                // Get Last time contacted
                lnglastContacted = cursorPhone.getLong(cursorPhone.getColumnIndex(Phone.LAST_TIME_CONTACTED));

                // If contact has been contacted, convert long to a Date
                if (lnglastContacted != 0) {
                    strLastContacted = Converters.getDate(lnglastContacted, "dd/MM/yyyy hh:mm:ss.SSS");
                }

                // Get Phone Number, Type and type, add to an Array List

                strPhoneType = cursorPhone.getString(cursorPhone.getColumnIndex(Phone.TYPE));
                strPhoneNumber = cursorPhone.getString(cursorPhone.getColumnIndex(Phone.NUMBER));
                listPhoneNumber.add(strPhoneNumber + "_" + strPhoneType);

                // For each number contact has, get Number and Type and add to the Array List

                while (cursorPhone.moveToNext()) {
                    strPhoneType = cursorPhone.getString(cursorPhone.getColumnIndex(Phone.TYPE));
                    strPhoneNumber = cursorPhone.getString(cursorPhone.getColumnIndex(Phone.NUMBER));
                    listPhoneNumber.add(strPhoneNumber + "_" + strPhoneType);

                }

                // Set all values to the Array List and convert it to a String
                arrNumbers = listPhoneNumber.toArray(new String[listPhoneNumber.size()]);
                strAllNumbers = Converters.convertArrayToString(arrNumbers);
            }

            // Close Phone Cursor and Open Email Cursor
            cursorPhone.close();
            cursorEmail = getContentResolver().query(uriEmail, null,strEmailId + " = ?", new String[] {Integer.toString(intContactID)}, null );

            // If contact has an Email move to first position

            if (cursorEmail.moveToFirst()){

                // Get Email and set to an Array List
                strEmail = cursorEmail.getString(cursorEmail.getColumnIndex(Email.DATA));
                listEmails.add(strEmail);

                //For each Email contact has, set Email to the ArrayList
                while(cursorEmail.moveToNext()){
                    strEmail = cursorEmail.getString(cursorEmail.getColumnIndex(Email.DATA));
                    listEmails.add(strEmail);
                }

                // Set all valuse to the Array  List and convert it to a String
                arrEmails = listEmails.toArray(new String[listEmails.size()]);
                strAllEmails = Converters.convertArrayToString(arrEmails);
            }


        }

        // Close Email Cursor
        cursorEmail.close();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
