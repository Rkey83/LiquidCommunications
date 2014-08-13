package com.LocallyGrownStudios.liquidcommunications.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.LocallyGrownStudios.liquidcommunications.Adapters.LiquidContactsAdapter;
import com.LocallyGrownStudios.liquidcommunications.Adapters.QuickConnectAdapter;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.ContactProvider;
import com.LocallyGrownStudios.liquidcommunications.Helpers.LiquidContactsBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.ArrayList;
import java.util.List;

public class LiquidContactsFragment extends Fragment {

    Context context;
    private List<LiquidContactsBean> listLiquidContacts = new ArrayList<LiquidContactsBean>();
    public static ListView lstvwLiquidContacts;
    String strContactsNumber;

    public static LiquidContactsFragment newInstance() {

        LiquidContactsFragment fragLQC = new LiquidContactsFragment();
        Bundle args = new Bundle();
        fragLQC.setArguments(args);
        return fragLQC;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

        // Get Contacts to fill QuickConnect

        GetContacts();

        // Send the values to the list adapter

        LiquidContactsAdapter quickConnectAdapter = new LiquidContactsAdapter(this.getActivity(), R.layout.list_item_liquid_contacts, listLiquidContacts);
        lstvwLiquidContacts.setAdapter(quickConnectAdapter);

    }

    public void GetContacts() {

        Cursor cursor = context.getContentResolver().query(ContactProvider.contactUri, null, "LQ_HasContacted", null, "LQ_Contacted" + " DESC");

    }

}
