package com.LocallyGrownStudios.liquidcommunications.General;

import android.content.Context;
import android.content.SharedPreferences;
import com.LocallyGrownStudios.liquidcommunications.Activities.ContactImporter;
import com.LocallyGrownStudios.liquidcommunications.Activities.SmsMmsImporter;


public class ImportOptions {


    SharedPreferences sprefImportContacts, sprefImportSmsMms, sprefSetDefault;
    Context context;

    public void importContacts() {
        context = ContactImporter.getCntxContactImporter();
        sprefImportContacts = context.getSharedPreferences("appPrefs", 0);
    }

    public void sendImportContacts() {
        context = SmsMmsImporter.getCntxSmsMmsImporter();
        sprefImportContacts = context.getSharedPreferences("appPrefs", 0);
    }

    public void importContactsOn() {

        SharedPreferences.Editor editContacts = sprefImportContacts.edit().putBoolean("importContacts", true);
        editContacts.apply();

    }

    public void importContactsOff() {

        SharedPreferences.Editor editContacts = sprefImportContacts.edit().putBoolean("importContacts", false);
        editContacts.apply();

    }

    public boolean importedContacts(){

        return sprefImportContacts.getBoolean("importContacts", true);

    }

    public boolean noContacts () {

        return sprefImportContacts.getBoolean("importContacts", false);

    }


}
