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
import com.LocallyGrownStudios.liquidcommunications.R;

public class SetDefaultApp extends Activity implements View.OnClickListener {

    static Context context, cntxDefaultApp;
    Button btnSetDefault, btnCancel;
    FirstRunCheck appFirstRun = new FirstRunCheck();
    Activity actDefaultApp = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cntxDefaultApp = getApplicationContext();
        context = this;
        appFirstRun.appFirstRun();
        if (appFirstRun.isDefaultAppFirstRun()) {
            setContentView(R.layout.activity_set_default_app);
            btnSetDefault = (Button) findViewById(R.id.btnSetDefault);
            btnSetDefault.setOnClickListener(this);
            appFirstRun.setDefaultAppHasRun();
        } else {
            Intent startSetDefault = new Intent(context, LiquidManager.class);
            actDefaultApp.finish();
            context.startActivity(startSetDefault);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.set_default_app, menu);
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

        Intent startLiquidManager = new Intent(context, LiquidManager.class);
        actDefaultApp.finish();
        context.startActivity(startLiquidManager);

    }
}
