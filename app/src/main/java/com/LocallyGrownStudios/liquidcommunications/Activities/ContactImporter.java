package com.LocallyGrownStudios.liquidcommunications.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.LocallyGrownStudios.liquidcommunications.General.FirstRunCheck;
import com.LocallyGrownStudios.liquidcommunications.General.ImportOptions;
import com.LocallyGrownStudios.liquidcommunications.R;
import com.LocallyGrownStudios.liquidcommunications.Services.ImportContactService;

public class ContactImporter extends Activity implements View.OnClickListener{

    // Class objects

    /// Mark can you see this?????


    static Context context, cntxContactImporter;
    Button btnImport, btnCancel;
    FirstRunCheck appFirstRun = new FirstRunCheck();
    ImportOptions importOptions = new ImportOptions();
    Activity actContactImpoter = this;


    // get Context cntxContactImporter

    public static Context getCntxContactImporter()
    {
        // Return the value of cntxContactImporter
        return cntxContactImporter;
    }

    // onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create Activity and set context and cntxContextImporter;
        super.onCreate(savedInstanceState);
        cntxContactImporter = getApplicationContext();
        context = this;

        // Run Method checkFirstRun
        checkFirstRun();

    }

    private void checkFirstRun(){

        // Run method appFirstRun in class FirstRunCheck
        appFirstRun.appFirstRun();

        // If this is the first run of the this activity, run the activity
        if(appFirstRun.isContactFirstRun()) {
            setContentView(R.layout.activity_contact_importer);
            btnImport = (Button) findViewById(R.id.btnImportContacts);
            btnImport.setOnClickListener(this);
            appFirstRun.setContactHasRun();
        }
        // Move to next activity
        else{
            Intent startSmsMmsImport = new Intent(context, SmsMmsImporter.class);
            actContactImpoter.finish();
            context.startActivity(startSmsMmsImport);
        }

    }

    // On Creation of Options Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_importer, menu);
        return true;
    }

    // On Option Menu Item Selected

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // On Click Listener

    @Override
    public void onClick(View v) {

        importOptions.importContacts();
        importOptions.importContactsOn();
        Intent activitySmsMmsImpoter = new Intent(context, SmsMmsImporter.class);
        context.startActivity(activitySmsMmsImpoter);
        Intent serviceImportContacts = new Intent(context, ImportContactService.class);
        context.startService(serviceImportContacts);
        actContactImpoter.finish();

    }
}
