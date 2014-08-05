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
        mainPager.setCurrentItem(1);
        Intent serviceSmsMms = new Intent(context, SmsMmsService.class);
        Cursor cursor = getContentResolver().query(ContactProvider.contactUri, null, null, null, null);

        while (cursor.moveToNext()) {
            String avb = cursor.getString(1);
            String tyb = cursor.getString(5);
            String kfd = cursor.getString(3);
        }

        context.startService(serviceSmsMms);


    }


          OnPageChangeListener  onPageChangeListener = new OnPageChangeListener() {

            @Override
            public void onPageSelected(int pageSelected) {
                Log.e("onPageSelected", "pageSelected:" + pageSelected);
            }

            @Override
            public void onPageScrolled(int pageSelected, float positionOffset,
                                       int positionOffsetPixel) {
                Log.e("onPageScrolled", "pageSelected" + pageSelected
                        + ",positionOffset:" + positionOffset
                        + ",positionOffsetPixel:" + positionOffsetPixel);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("onPageScrollStateChanged", "state:" + state);
                int currentPage = mainPager.getCurrentItem();
                if (currentPage == 3 || currentPage == 0) {
                    previousState = currentState;
                    currentState = state;
                    if (previousState == 1 && currentState == 0) {
                        mainPager.setCurrentItem(currentPage == 0 ? 3 : 0);
                    }
                }
            }
        };
}
