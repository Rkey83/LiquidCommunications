package com.LocallyGrownStudios.liquidcommunications.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.LocallyGrownStudios.liquidcommunications.Adapters.LiquidContactsAdapter;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.ContactProvider;
import com.LocallyGrownStudios.liquidcommunications.General.Converters;
import com.LocallyGrownStudios.liquidcommunications.Helpers.LiquidContactsBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.ArrayList;
import java.util.List;

public class LiquidContactsFragment extends Fragment {

    Context context;
    private List<LiquidContactsBean> listLiquidContacts = new ArrayList<LiquidContactsBean>();
    public static ListView lstvwLiquidContacts;
    String strContactsNumber;
    Bitmap btmpContactPhoto;

    public static LiquidContactsFragment newInstance(int id) {
        LiquidContactsFragment fragLQC = new LiquidContactsFragment();
        Bundle args = new Bundle();
        args.putInt("arg_ID", id);
        fragLQC.setArguments(args);
        return fragLQC;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = this.getActivity();
        View viewLQC = inflater.inflate(R.layout.fragment_liquid_contacts, container, false);

        return viewLQC;
    }


    // When the Fragment has been created

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Create View, get Context and XML

        super.onViewCreated(view, savedInstanceState);
        context = this.getActivity();
        lstvwLiquidContacts = (ListView) view.findViewById(R.id.contactListView);

        // If the view does not have a saved state
        if (savedInstanceState == null) {

            // Get Contacts to fill QuickConnect

            GetContacts();

            // Send the values to the list adapter

            LiquidContactsAdapter quickConnectAdapter = new LiquidContactsAdapter(this.getActivity(), R.layout.list_item_liquid_contacts, listLiquidContacts);
            lstvwLiquidContacts.setAdapter(quickConnectAdapter);
        }

    }

    public void GetContacts() {

        Cursor cursor = context.getContentResolver().query(ContactProvider.contactUri, null, "LQ_HasNumber", null, null);

        while (cursor.moveToNext()) {

            // For each entry in the database that matches our search criteria

            LiquidContactsBean beanLiquidContacts = new LiquidContactsBean();

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
                btmpContactPhoto = Converters.adjustOpacity(btmpContactPhoto, 215);
                beanLiquidContacts.ContactPhotoSet(btmpContactPhoto);
            }

            // Otherwise set contacts photo to default image
            else {
                Drawable drwDefaultImage = getResources().getDrawable(R.drawable.default_contact_image);
                beanLiquidContacts.DefaultPhotoSet(drwDefaultImage);
            }

            // Set Values found to an array

            beanLiquidContacts.Nameset(strContactsName);
            beanLiquidContacts.PhoneNoset(strContactsNumber);
            beanLiquidContacts.LastTextSet(strContactsEmailAddress);

            // Add the array to a list

            listLiquidContacts.add(beanLiquidContacts);
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
