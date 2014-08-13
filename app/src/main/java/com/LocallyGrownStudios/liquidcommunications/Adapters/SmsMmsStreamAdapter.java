package com.LocallyGrownStudios.liquidcommunications.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.LocallyGrownStudios.liquidcommunications.Helpers.SmsMmsHolder;
import com.LocallyGrownStudios.liquidcommunications.Helpers.SmsMmsReceivedBean;
import com.LocallyGrownStudios.liquidcommunications.Helpers.SmsMmsSentBean;
import com.LocallyGrownStudios.liquidcommunications.R;
import java.util.ArrayList;


public class SmsMmsStreamAdapter extends ArrayAdapter<Object> {


    // Set Class Variables

    private final int txtSent = 0;
    private ArrayList<Object> textList = new ArrayList<Object>();
    private LayoutInflater textInflater;

    // Set Adapter Data

    public SmsMmsStreamAdapter(Context context, int resource, ArrayList<Object> objects) {

        super(context, resource, objects);
        textList = objects;
        textInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    // Get Data and Set it to View

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        SmsMmsHolder.TextReceivedHolder messageRec;
        SmsMmsHolder.TextSentHolder messageSent;
        int type = getItemViewType(position);

        if (type == txtSent) {
            if (convertView == null) {
                convertView = textInflater.inflate(R.layout.sms_mms_sent, parent, false);
                messageSent = new SmsMmsHolder().new TextSentHolder();
                messageSent.txtSent = (TextView) convertView.findViewById(R.id.txtMessages);
                convertView.setTag(messageSent);
            } else {
                messageSent = (SmsMmsHolder.TextSentHolder) convertView.getTag();
            }
            messageSent.txtSent.setText(((SmsMmsSentBean) textList.get(position)).getSmsMessage());
        } else {
            if (convertView == null) {
                convertView = textInflater.inflate(R.layout.sms_mms_received, parent, false);
                messageRec = new SmsMmsHolder().new TextReceivedHolder();
                messageRec.txtRec = (TextView) convertView.findViewById(R.id.txtMessages);
                convertView.setTag(messageRec);
            } else {
                messageRec = (SmsMmsHolder.TextReceivedHolder) convertView.getTag();
            }
            messageRec.txtRec.setText(((SmsMmsReceivedBean) textList.get(position)).getSmsMessage());
        }
        notifyDataSetChanged();
        return convertView;
    }


    @Override
    public int getViewTypeCount()
    {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if(textList.get(position) instanceof SmsMmsSentBean){
            return txtSent;
        }
        else return 1;
    }
}



