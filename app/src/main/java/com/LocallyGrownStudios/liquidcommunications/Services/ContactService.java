package com.LocallyGrownStudios.liquidcommunications.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.LocallyGrownStudios.liquidcommunications.Helpers.QuickConnectBean;

import java.util.ArrayList;
import java.util.List;

public class ContactService extends Service {

    private List<QuickConnectBean> mylist = new ArrayList<QuickConnectBean>();



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
