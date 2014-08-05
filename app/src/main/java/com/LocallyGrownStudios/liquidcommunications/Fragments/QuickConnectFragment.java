package com.LocallyGrownStudios.liquidcommunications.Fragments;



import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.LocallyGrownStudios.liquidcommunications.Adapters.QuickConnectAdapter;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.ContactProvider;
import com.LocallyGrownStudios.liquidcommunications.Helpers.ContactBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.ArrayList;
import java.util.List;

public class QuickConnectFragment extends Fragment {

    Context context;
    private List<ContactBean> mylist = new ArrayList<ContactBean>();
    private ListView mylistView;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getActivity();
        mylistView = (ListView) view.findViewById(R.id.contactListView);
        getContactInfo();
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
        //mylistView = (ListView) container.findViewById(R.id.contactListView);
       // getContactInfo();
       // QuickConnectAdapter quickConnectAdapter = new QuickConnectAdapter(this.getActivity(), R.layout.list_item_quick_connect, mylist);
       // mylistView.setAdapter(quickConnectAdapter);


        return viewQC;
    }


    private void getContactInfo(){


        Cursor cursor = context.getContentResolver().query(ContactProvider.contactUri,null,null,null,null);

        while (cursor.moveToNext()){

            ContactBean objContact = new ContactBean();

            String avb = cursor.getString(1);
            String tyb = cursor.getString(5);
            String kfd = cursor.getString(3);
            objContact.Nameset(avb);
            objContact.PhoneNoset(tyb);
            objContact.LastTextSet(kfd);
            mylist.add(objContact);


        }
    }

    private void initState() {
        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalArgumentException("The arguments should be valid!");
        }
    }

}
