package com.LocallyGrownStudios.liquidcommunications.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ImportSmsMmsService extends Service {
    public ImportSmsMmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
