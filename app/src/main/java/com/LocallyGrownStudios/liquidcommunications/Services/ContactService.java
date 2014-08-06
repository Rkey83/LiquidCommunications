package com.LocallyGrownStudios.liquidcommunications.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ContactService extends Service {
    public ContactService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
