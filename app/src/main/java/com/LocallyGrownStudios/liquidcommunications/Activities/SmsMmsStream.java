package com.LocallyGrownStudios.liquidcommunications.Activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LocallyGrownStudios.liquidcommunications.Adapters.SmsMmsStreamAdapter;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.MmsProvider;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.SmsProvider;
import com.LocallyGrownStudios.liquidcommunications.Fragments.QuickConnectFragment;
import com.LocallyGrownStudios.liquidcommunications.General.Converters;
import com.LocallyGrownStudios.liquidcommunications.Helpers.DataBaseHelper;
import com.LocallyGrownStudios.liquidcommunications.Helpers.SmsMmsReceivedBean;
import com.LocallyGrownStudios.liquidcommunications.Helpers.SmsMmsSentBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.ArrayList;
import java.util.Date;

public class SmsMmsStream extends Activity implements View.OnClickListener {

    // Set Class Variables
    public Context context;
    String textSender, strSmsName;
    String returnNumber;
    public ArrayList<Object> textlist = new ArrayList<Object>();
    static SmsMmsStreamAdapter adapter;
    ListView listviewTextMessages;
    String sendersNumber, mmsMessage, strSmsDate, strMmsDate;
    Date smsDate, mmsDate;
    String body, saved;
    EditText messageToSend;
    Cursor cursorSms, cursorMms;
    int smsCount, mmsCount;


    BroadcastReceiver smsStream = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();
                saved = intent.getStringExtra("saver");
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sendersNumber = messages[i].getDisplayOriginatingAddress();
                }

                SmsMessage sms = messages[0];
                try {
                    if (messages.length == 1 || sms.isReplace()) {
                        body = sms.getDisplayMessageBody();
                    } else {
                        StringBuilder bodyText = new StringBuilder();
                        for (int i = 0; i < messages.length; i++) {
                            bodyText.append(messages[i].getMessageBody());
                        }
                        body = bodyText.toString();
                    }

                    if (sendersNumber.contains(returnNumber)) {
                        UpdateData();
                    }
                } catch (Exception e) {

                }
            }
        }
    };


    // Execute the Following Code on Creation

    @Override

    protected void onCreate(Bundle savedInstanceState) {


        // Set Activity, ListView, and PhoneCursor. Recall Last State on Creation

        super.onCreate(savedInstanceState);
        registerReceiver(smsStream, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        setContentView(R.layout.activity_sms_mms_stream);
        messageToSend = (EditText) findViewById(R.id.messageEnter);
        Bundle extras = getIntent().getExtras();
        listviewTextMessages = (ListView) findViewById(R.id.listTextMessages);
        TextView streamName = (TextView) findViewById(R.id.streamName);
        TextView streamNumber = (TextView) findViewById(R.id.streamNumber);
        context = this;
        final Button sendBtn = (Button) findViewById(R.id.buttonSend);

        sendBtn.setOnClickListener(this);

        if (extras != null) {
            textSender = extras.getString("senderNumber");
        }

        ContentValues updateValues = new ContentValues();
        textSender = Converters.stripNumberFormatiing(textSender);
        String covnersationNumber = Converters.formatPhoneNumber(textSender);
        streamNumber.setText(covnersationNumber);
        cursorSms = context.getContentResolver().query(SmsProvider.smsUri, null, "LQ_Number=" + textSender , null, "LQ_id" + " ASC");
        cursorMms = context.getContentResolver().query(MmsProvider.mmsUri, null, "LQ_Number=" + textSender , null, "LQ_id" + " ASC");

        mmsCount = cursorMms.getCount();
        smsCount = cursorSms.getCount();

        //While PhoneCursor Moves to Next Position

        if (smsCount >= mmsCount) {
            cursorMms.moveToFirst();
            while (cursorSms.moveToNext()) {

                getDateSent();

                if (cursorMms.getPosition() < mmsCount){

                    if (smsDate.before(mmsDate)){

                        getSms();

                    }
                    else if (smsDate.after(mmsDate)) {

                        getMms();
                        cursorMms.moveToNext();
                        cursorSms.moveToPrevious();
                        if (cursorSms.getPosition() == 0){
                            cursorSms.moveToPosition(-1);
                        }


                    }
                }
                else if (cursorMms.getPosition() > mmsCount - 1){

                    getSms();

                }
            }
        }
        else if (mmsCount > smsCount) {
            cursorSms.moveToFirst();
            while (cursorMms.moveToNext()) {

                getDateSent();

                if (cursorSms.getPosition() < smsCount){

                    if (mmsDate.before(smsDate)){

                        getMms();

                    }
                    else if (mmsDate.after(smsDate)){

                        getSms();
                        cursorSms.moveToNext();
                        cursorMms.moveToPrevious();
                        if (cursorSms.getPosition() == 0){
                            cursorSms.moveToPosition(-1);
                        }
                    }
                }
                else if (cursorSms.getPosition() > smsCount - 1){

                    getMms();

                }
            }
        }


        // Close SmsMms Cursor and send data to ContactAdapter


        adapter = (new SmsMmsStreamAdapter(getApplicationContext(), 0, textlist));
        listviewTextMessages.setAdapter(adapter);
        SmsMmsReceivedBean rec = new SmsMmsReceivedBean();
        rec.setSmsMessage(returnNumber);
        cursorSms.moveToFirst();
        streamName.setText(strSmsName);
        cursorSms.close();
        cursorMms.close();
    }


    public void getDateSent(){

        if (cursorSms.getPosition() < smsCount) {
            strSmsDate = cursorSms.getString(7);
            smsDate = Converters.convertStringToDate(strSmsDate);
        }
        else {
            strSmsDate = null;
        }
        if (cursorMms.getPosition() < mmsCount){
            strMmsDate = cursorMms.getString(3);
            mmsDate = Converters.convertStringToDate(strMmsDate);
        }
        else {
            strMmsDate = "none";
        }
    }



    public void getSms(){

        String strSmsBody = cursorSms.getString(5);
        String strSmsType = cursorSms.getString(6);
        strSmsName = cursorSms.getString(3);
        if (strSmsBody != null) {
            if (strSmsType.contains("1")) {
                SmsMmsReceivedBean rec = new SmsMmsReceivedBean();
                rec.setSmsMessage(strSmsBody);
                textlist.add(rec);
            } else {
                SmsMmsSentBean sent = new SmsMmsSentBean();
                sent.setSmsMessage(strSmsBody);
                textlist.add(sent);

            }
        }
    }



    public void getMms() {

        if (cursorMms.getPosition() < mmsCount) {

            int mmsType = cursorMms.getInt(8);

            if (mmsType == 1) {

                SmsMmsReceivedBean rec = new SmsMmsReceivedBean();
                mmsMessage = cursorMms.getString(6);
                rec.setSmsMessage(mmsMessage);
                textlist.add(rec);
            }
            if (mmsType == 2) {

                SmsMmsReceivedBean rec = new SmsMmsReceivedBean();
                mmsMessage = "Message : Click here to view image";
                rec.setSmsMessage(mmsMessage);
                textlist.add(rec);
            }
            if (mmsType == 3) {

                SmsMmsReceivedBean rec = new SmsMmsReceivedBean();
                mmsMessage = cursorMms.getString(6);
                rec.setSmsMessage(mmsMessage);
                textlist.add(rec);
            }
        }
    }


    public void UpdateData() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(SmsMmsStream.this);
        ContentValues valuesSms = new ContentValues();
        valuesSms = new ContentValues();
        SQLiteDatabase contactDb = dataBaseHelper.getWritableDatabase();
        valuesSms.put(DataBaseHelper.smsMessage, body);
        valuesSms.put(DataBaseHelper.smsLQID, 1);
        SmsMmsReceivedBean rec = new SmsMmsReceivedBean();
        rec.setSmsMessage(body);
        textlist.add(rec);
        adapter.notifyDataSetChanged();
        listviewTextMessages.post(new Runnable() {
            @Override
            public void run() {
        listviewTextMessages.setSelection(adapter.getCount() - 1);
            }

        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {
        finish();
        Intent intent = new Intent(SmsMmsStream.this, LiquidManager.class);
        intent.putExtra("savedInstanceState" , "true");
        intent.putExtra("saver", saved);
        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        this.startActivity(intent);
        unregisterReceiver(smsStream);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        messageToSend = (EditText) findViewById(R.id.messageEnter);
        String message = messageToSend.getText().toString();
        ContentValues values = new ContentValues();
        int maxChars = 160;

        try {
            if (!messageToSend.getText().toString().trim().isEmpty()) {
                SmsMmsSentBean sent = new SmsMmsSentBean();
                SmsManager sendText = SmsManager.getDefault();
                values.put("address", returnNumber);
                values.put("body", message);
                getContentResolver().insert(Uri.parse("content://sms/sent"), values);
                PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
                PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);

                if (message.length() > 160) {
                    ArrayList<String> messageList = sendText.divideMessage(message);
                    sendText.sendMultipartTextMessage(returnNumber, null, messageList, null, null);
                } else {
                    sendText.sendTextMessage(textSender, null, message, null, null);
                }

                messageToSend.setText("");
                sent.setSmsMessage(message);
                textlist.add(sent);
                adapter.notifyDataSetChanged();

                listviewTextMessages.post(new Runnable() {
                    @Override
                    public void run() {
                        listviewTextMessages.setSelection(adapter.getCount() - 1);
                    }

                });
                Toast.makeText(getApplicationContext(), "Pressing the button harder, will make me send the message faster.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "You forgot to enter a message, at least say hi.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "You forgot to enter a message, at least say hi.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
