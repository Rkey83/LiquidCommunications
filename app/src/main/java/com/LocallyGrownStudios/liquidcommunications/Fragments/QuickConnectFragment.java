package com.LocallyGrownStudios.liquidcommunications.Fragments;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.LocallyGrownStudios.liquidcommunications.Adapters.QuickConnectAdapter;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.ContactProvider;
import com.LocallyGrownStudios.liquidcommunications.General.Converters;
import com.LocallyGrownStudios.liquidcommunications.Helpers.ContactBean;
import com.LocallyGrownStudios.liquidcommunications.R;
import com.LocallyGrownStudios.liquidcommunications.Services.ContactService;

import java.util.ArrayList;
import java.util.List;

public class QuickConnectFragment extends Fragment {

    Context context;
    private List<ContactBean> mylist = new ArrayList<ContactBean>();
    public static ListView mylistView;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getActivity();
        mylistView = (ListView) view.findViewById(R.id.contactListView);
        Intent serviceImportContacts = new Intent(context, ContactService.class);
        context.startService(serviceImportContacts);
        GetContacts();
        QuickConnectAdapter quickConnectAdapter = new QuickConnectAdapter(this.getActivity(), R.layout.list_item_quick_connect, mylist);
        mylistView.setAdapter(quickConnectAdapter);

    }

    public static QuickConnectFragment newInstance(int position) {

        QuickConnectFragment fragQC = new QuickConnectFragment();
        Bundle args = new Bundle();
        fragQC.setArguments(args);
        return fragQC;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        context = this.getActivity();
        View viewQC = inflater.inflate(R.layout.fragment_quick_connect, container, false);


        return viewQC;
    }

    public void GetContacts() {

        Cursor cursor = context.getContentResolver().query(ContactProvider.contactUri, null, "LQ_HasContacted", null, "LQ_Contacted");

        while (cursor.moveToNext()) {

            ContactBean objContact = new ContactBean();

            String avb = cursor.getString(1);
            String tyb = cursor.getString(4);

            if (tyb.contains(",")){
                tyb = Converters.formatMultiPhoneNumbersQC(tyb,2);
            }
            else {
                tyb = tyb.split("_")[0];
            }

            String kfd = cursor.getString(5);

            if (kfd == null){
                kfd = "No Email";
            }

            objContact.Nameset(avb);
            objContact.PhoneNoset(tyb);
            objContact.LastTextSet(kfd);

            mylist.add(objContact);
        }
        cursor.close();
    }


    private void initState() {
        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalArgumentException("The arguments should be valid!");
        }
    }

}
