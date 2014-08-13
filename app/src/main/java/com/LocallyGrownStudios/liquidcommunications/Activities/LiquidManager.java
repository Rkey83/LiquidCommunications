package com.LocallyGrownStudios.liquidcommunications.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.LocallyGrownStudios.liquidcommunications.Adapters.CircularPagerAdapter;
import com.LocallyGrownStudios.liquidcommunications.R;
import com.LocallyGrownStudios.liquidcommunications.Services.SmsMmsService;
import static android.support.v4.view.ViewPager.*;

public class LiquidManager extends Activity {


    private int currentState;
    private ViewPager mainPager;
    CircularPagerAdapter testAdapter;





    @Override
    protected void onCreate(Bundle position) {
        super.onCreate(position);
        Context context = this;
        setContentView(R.layout.activity_liquid_manager);
        mainPager = (ViewPager) findViewById(R.id.pager);
        testAdapter = new CircularPagerAdapter(getFragmentManager());
        mainPager.setAdapter(testAdapter);
        mainPager.setCurrentItem(2);
        mainPager.setOnPageChangeListener(onPageChangeListener);
        mainPager.setOffscreenPageLimit(4);
        Intent serviceSmsMms = new Intent(context, SmsMmsService.class);
        context.startService(serviceSmsMms);
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

    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

}

