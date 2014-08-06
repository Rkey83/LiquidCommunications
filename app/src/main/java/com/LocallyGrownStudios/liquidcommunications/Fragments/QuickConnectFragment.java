package com.LocallyGrownStudios.liquidcommunications.Fragments;


// Import these resources
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.LocallyGrownStudios.liquidcommunications.Adapters.QuickConnectAdapter;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.ContactProvider;
import com.LocallyGrownStudios.liquidcommunications.General.Converters;
import com.LocallyGrownStudios.liquidcommunications.Helpers.QuickConnectBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.ArrayList;
import java.util.List;

public class QuickConnectFragment extends Fragment {


    // Set Class Objects

    Context context;
    private List<QuickConnectBean> listQuickConnect = new ArrayList<QuickConnectBean>();
    public static ListView lstvwQuickConnect;
    String strContactsNumber;



    // Method to create a new instance of the fragment from another class

    public static QuickConnectFragment newInstance() {

        QuickConnectFragment fragQC = new QuickConnectFragment();
        Bundle args = new Bundle();
        fragQC.setArguments(args);
        return fragQC;
    }

    // On the creation of the view find the following layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        context = this.getActivity();
        View viewQC = inflater.inflate(R.layout.fragment_quick_connect, container, false);


        return viewQC;
    }


    // When the Fragment has been created

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Create View, get Context and XML
        super.onViewCreated(view, savedInstanceState);
        context = this.getActivity();
        lstvwQuickConnect = (ListView) view.findViewById(R.id.contactListView);

        // Get Contacts to fill QuickConnect

        GetContacts();

        // Send the values to the list adapter

        QuickConnectAdapter quickConnectAdapter = new QuickConnectAdapter(this.getActivity(), R.layout.list_item_quick_connect, listQuickConnect);
        lstvwQuickConnect.setAdapter(quickConnectAdapter);

    }



    // Method to get contact info for QuickConnect

    public void GetContacts() {

        Bitmap btmpContactPhoto;

        // Start the Cursor to query the database. Filter the list by the column LQ_HasContacted and sort by the column LQ_Contacted

        Cursor cursor = context.getContentResolver().query(ContactProvider.contactUri, null, "LQ_HasContacted", null, "LQ_Contacted" + " DESC");

        // While there is another entry in the database, move to it

        while (cursor.moveToNext()) {

            // For each entry in the database that matches our search criteria

            QuickConnectBean objContact = new QuickConnectBean();

            // Get the entry from the column LQ_Number
           strContactsNumber = cursor.getString(4);

            // Run Method getContactsPhoneNumber using the data retrieved
            getContactsPhoneNumber();

            // Get the entry from the column LQ_Name
            String strContactsName = cursor.getString(1);

            // Get the entry from the column LQ_Email
            String strContactsEmailAddress = cursor.getString(5);

            // If no data is found, set value to No Email
            if (strContactsEmailAddress == null) {
                strContactsEmailAddress = "No Email";
            }

            //Get the entry for LQ_Photo
            byte[] bytContactPhoto = cursor.getBlob(2);

            // If column LQ_Photo is not empty, convert the ByteArray to a bitmap and set it to btmpContactPhoto
            if (bytContactPhoto != null) {
                btmpContactPhoto = BitmapFactory.decodeByteArray(bytContactPhoto, 0, bytContactPhoto.length);
                btmpContactPhoto = Bitmap.createScaledBitmap(btmpContactPhoto, 124, 124, true);
                objContact.ContactPhotoSet(btmpContactPhoto);
            }

            // Otherwise set contacts photo to default image
            else {
               Drawable drwDefaultImage = getResources().getDrawable(R.drawable.default_contact_image);
                objContact.DefaultPhotoSet(drwDefaultImage);
            }

            // Set Values found to an array

            objContact.Nameset(strContactsName);
            objContact.PhoneNoset(strContactsNumber);
            objContact.LastTextSet(strContactsEmailAddress);

            // Add the array to a list

            listQuickConnect.add(objContact);
        }

        // Close the cursor
        cursor.close();
    }


    // Method to parse and format phone numbers

    private void getContactsPhoneNumber() {

        // If the phone number has a value

        if (strContactsNumber != null) {

            // If the phone number contains a ",", it is an array

            if (strContactsNumber.contains(",")) {

                // Parse the array for the selected phone type

                strContactsNumber = Converters.getFromMultiPhoneNumbers(strContactsNumber, 2);

            } else {

                // Otherwise just remove the phone type characters from the string
                strContactsNumber = strContactsNumber.split("_")[0];

            }

            // Once the phone number has been parsed, format the number for display
           strContactsNumber = Converters.formatPhoneNumber(strContactsNumber);
        }
    }
}
