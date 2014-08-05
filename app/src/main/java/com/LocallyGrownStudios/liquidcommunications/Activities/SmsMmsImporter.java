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

public class SmsMmsImporter extends Activity implements View.OnClickListener{

    Activity actSmsMmsImporter = this;
    static Context context, cntxSmsMmsImporter;
    FirstRunCheck appFirstRun = new FirstRunCheck();
    ImportOptions importOptions = new ImportOptions();
    Button btnImport, btnCancel;

    public static Context getCntxSmsMmsImporter(){
        return cntxSmsMmsImporter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cntxSmsMmsImporter = getApplicationContext();
        context = this;
        appFirstRun.appFirstRun();
        if (appFirstRun.isSmsMmsFirstRun()) {
            setContentView(R.layout.activity_sms_mms_importer);
            btnImport = (Button) findViewById(R.id.btnImportSmsMms);
            btnImport.setOnClickListener(this);
            appFirstRun.setSmsMmsHasRun();
        } else {
            Intent startSetDefault = new Intent(context, SetDefaultApp.class);
            actSmsMmsImporter.finish();
            context.startActivity(startSetDefault);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sms_mms_importer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Intent startSetDefault = new Intent(context, SetDefaultApp.class);
        actSmsMmsImporter.finish();
        context.startActivity(startSetDefault);

    }
}
