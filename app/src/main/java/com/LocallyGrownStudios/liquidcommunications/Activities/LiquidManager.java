package com.LocallyGrownStudios.liquidcommunications.Activities;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;


import com.LocallyGrownStudios.liquidcommunications.Adapters.CircularPagerAdapter;
import com.LocallyGrownStudios.liquidcommunications.ContentProviders.ContactProvider;
import com.LocallyGrownStudios.liquidcommunications.R;
import com.LocallyGrownStudios.liquidcommunications.Services.SmsMmsService;

import static android.support.v4.view.ViewPager.*;


public class LiquidManager extends Activity {

    private int previousState, currentState;
    private ViewPager mainPager;
    CircularPagerAdapter testAdapter;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle position) {
        super.onCreate(position);
        Context context = this;
        setContentView(R.layout.activity_liquid_manager);
        mainPager = (ViewPager) findViewById(R.id.pager);
        testAdapter = new CircularPagerAdapter(getFragmentManager());
        mainPager.setAdapter(testAdapter);
        mainPager.setCurrentItem(2);
        mainPager.setOffscreenPageLimit(4);
        mainPager.setOnPageChangeListener(onPageChangeListener);
        Intent serviceSmsMms = new Intent(context, SmsMmsService.class);
        Cursor cursor = getContentResolver().query(ContactProvider.contactUri, null, null, null, null);


    }


          OnPageChangeListener  onPageChangeListener = new OnPageChangeListener() {

            @Override
            public void onPageSelected(int pageSelected) {
                Log.e("onPageSelected", "pageSelected:" + pageSelected);
            }

            @Override
            public void onPageScrolled(int pageSelected, float positionOffset, int positionOffsetPixel) {

                Log.e("onPageScrolled", "pageSelected" + pageSelected + ",positionOffset:" + positionOffset + ",positionOffsetPixel:" + positionOffsetPixel);
                currentState = mainPager.getCurrentItem();

               if (currentState == 0 && positionOffset < .1){
                   mainPager.setCurrentItem(3, false);
               }

               if (currentState == 4 && positionOffset > .9){
                   mainPager.setCurrentItem(1, false);
               }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("onPageScrollStateChanged", "state:" + state);


            }
        };
}

