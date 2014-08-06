package com.LocallyGrownStudios.liquidcommunications.Services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.widget.ListView;

import com.LocallyGrownStudios.liquidcommunications.Activities.LiquidManager;
import com.LocallyGrownStudios.liquidcommunications.Adapters.QuickConnectAdapter;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.ContactProvider;
import com.LocallyGrownStudios.liquidcommunications.Fragments.QuickConnectFragment;
import com.LocallyGrownStudios.liquidcommunications.Helpers.ContactBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.ArrayList;
import java.util.List;

public class ContactService extends Service {

    private List<ContactBean> mylist = new ArrayList<ContactBean>();



    public ContactService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
