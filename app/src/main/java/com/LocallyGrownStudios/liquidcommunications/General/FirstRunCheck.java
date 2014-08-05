package com.LocallyGrownStudios.liquidcommunications.General;

import android.content.Context;
import android.content.SharedPreferences;

import com.LocallyGrownStudios.liquidcommunications.Activities.ContactImporter;

public class FirstRunCheck{

    SharedPreferences sprefContactImporter, sprefSmsMmsImporter, sprefSetDefault;
    Context context;

    public void appFirstRun(){
        context = ContactImporter.getCntxContactImporter();
        sprefContactImporter = context.getSharedPreferences("appPrefs", 0);
        sprefSmsMmsImporter = context.getSharedPreferences("appPrefs", 0);
        sprefSetDefault = context.getSharedPreferences("appPrefs", 0);
    }



    public boolean isContactFirstRun(){
        return sprefContactImporter.getBoolean("contactFirstRun", true);
    }

    public void setContactHasRun(){
        SharedPreferences.Editor editContact = sprefContactImporter.edit().putBoolean("contactFirstRun", false);
        editContact.apply();
    }

    public boolean isSmsMmsFirstRun(){
        return sprefSmsMmsImporter.getBoolean("textFirstRun", true);
    }

    public void setSmsMmsHasRun() {
        SharedPreferences.Editor editSmsMms = sprefSmsMmsImporter.edit().putBoolean("textFirstRun", false);
        editSmsMms.apply();
    }

    public boolean isDefaultAppFirstRun(){
        return sprefSetDefault.getBoolean("defaultFirstRun", true);
    }

    public void setDefaultAppHasRun() {
        SharedPreferences.Editor editDefault = sprefSetDefault.edit().putBoolean("defaultFirstRun", false);
        editDefault.apply();
    }

}
