package com.LocallyGrownStudios.liquidcommunications.Adapters;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.LocallyGrownStudios.liquidcommunications.Fragments.LiquidContactsFragment;
import com.LocallyGrownStudios.liquidcommunications.Helpers.LiquidContactsBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.List;


public class LiquidContactsAdapter extends ArrayAdapter<LiquidContactsBean> {


    // Set Class Variables

    private Activity activity;
    private List<LiquidContactsBean> contacts;
    private int row;
    private LiquidContactsBean objBean;
    String contactId;
    long number;
    public String contactTextMessages;
    public String textSender;
    LiquidContactsFragment liquidContactsFragment = new LiquidContactsFragment();
    final static int idEDIT_CONTACT = Menu.FIRST + 2;


    // Set Adapter Data and create a new row for each row in the list from LiquidCommunicationsBean

    public LiquidContactsAdapter(Activity act, int row, List<LiquidContactsBean> items) {
        super(act, row, items);
        this.activity = act;
        this.row = row;
        this.contacts = items;
    }


    // Get Data and Set it to View

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        // Set the View and Inflate Local Data

        View view = convertView;
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if ((contacts == null) || ((position + 1) > contacts.size()))
            return view;


        // Set Each Item Within View

        objBean = contacts.get(position);
        holder.name = (TextView) view.findViewById(R.id.txtContactName);
        holder.phoneNo = (TextView) view.findViewById(R.id.txtContactNumber);
        holder.message = (TextView) view.findViewById(R.id.txtLastContact);
        holder.phoneNo.setAutoLinkMask(Linkify.ALL);
        QuickContactBadge badgeSmall = (QuickContactBadge) view.findViewById(R.id.contactBadge);
        final ImageView contactImage = (ImageView) view.findViewById(R.id.contactPhoto);
        final ImageView contactButton = (ImageView) view.findViewById(R.id.imgContactBackground);


        // onClick Listener for Large Contact Button

        contactButton.setOnLongClickListener(new Button.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    contactButton.performHapticFeedback(1);
                    Log.i("MyTag", "Image button is pressed, visible in LogCat");

                    Toast.makeText(v.getContext(), // <- Line changed
                            "The display settings for " + holder.name.getText() + " would appear on long-click.",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });


        // onClick Listener for Text Message Field

        holder.message.setOnLongClickListener(new Button.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    if (holder.message.getText().toString().contains(":")) {
                        holder.message.performHapticFeedback(1);
                        Log.i("MyTag", "Text message is pressed, visible in LogCat");
                        activity.finish();
                    } else {
                        holder.message.performHapticFeedback(1);
                        Log.i("MyTag", "Text message is pressed, visible in LogCat");
                        Toast.makeText(v.getContext(), "Currently no messages for " + holder.name.getText(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });


        //  onClick Listener for Contact Name Field

        holder.name.setOnLongClickListener(new Button.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    textSender = holder.phoneNo.getText().toString();
                    GetContactId(v);
                    holder.message.performHapticFeedback(1);
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, number);
                    intent.setData(contactUri);
                    ((Activity)v.getContext()).startActivityForResult(intent, idEDIT_CONTACT);
                    Log.i("MyTag", "Text message is pressed, visible in LogCat");
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });



        // If value from LiquidCommunicationBean is not empty, Set Text in Text Message Field to its value

        if (holder.name != null && null != objBean.Nameget() && objBean.Nameget().trim().length() > 0) {
            holder.name.setText(Html.fromHtml(objBean.Nameget()));
            contactTextMessages = holder.name.getText().toString();
        }


        // Set Text in Phone Number Field, and Pass Number to Text Messaging Activity

        if (holder.phoneNo != null && null != objBean.PhoneNoget() && objBean.PhoneNoget().trim().length() > 0) {

            holder.phoneNo.setText(Html.fromHtml(objBean.PhoneNoget()));
            textSender = holder.phoneNo.getText().toString();
        }


        // If value from LiquidCommunicationBean is not empty, Set Text Message Field to its value

        if (holder.message != null && null != objBean.LastTextGet() && objBean.LastTextGet().trim().length() > 0) {

            holder.message.setText(Html.fromHtml(objBean.LastTextGet()));

        }


        // If value from LiquidCommunicationBean is not empty, Set the Contact Badge and Image

        if (badgeSmall != null && objBean.ConctactPhotoGet() != null && objBean.ConctactPhotoGet().getByteCount() > 0) {

            contactImage.setImageBitmap(objBean.ConctactPhotoGet());
            badgeSmall.assignContactFromPhone(objBean.PhoneNoget(), true);
            badgeSmall.setMode(ContactsContract.QuickContact.MODE_SMALL);

        }
        else
        {
            Drawable drawable = objBean.DefaultPhotoGet();
            contactImage.setImageDrawable(objBean.DefaultPhotoGet());
            badgeSmall.assignContactFromPhone(objBean.PhoneNoget(), true);
            badgeSmall.setMode(ContactsContract.QuickContact.MODE_SMALL);
        }

        return view;
    }


    // Get the ID of the Contact being pressed

    private void GetContactId(View v) {
        ContentResolver contentResolver = v.getContext().getContentResolver();

        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(textSender));

        String[] projection = new String[] {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID};

        Cursor cursor =
                contentResolver.query(
                        uri,
                        projection,
                        null,
                        null,
                        null);

        if(cursor!=null) {
            while(cursor.moveToNext()){
                String contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
                contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                number = Long.parseLong(contactId);
            }
            cursor.close();
        }

    }




    // Add an Object here for each field you want to populate

    public class ViewHolder {
        public TextView name, phoneNo, message;
    }
}

