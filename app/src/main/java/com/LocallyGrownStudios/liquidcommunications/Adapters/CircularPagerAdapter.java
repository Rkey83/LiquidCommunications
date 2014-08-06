package com.LocallyGrownStudios.liquidcommunications.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.Toast;

import com.LocallyGrownStudios.liquidcommunications.Fragments.LiquidContactsFragment;
import com.LocallyGrownStudios.liquidcommunications.Fragments.LiquidTextFragment;
import com.LocallyGrownStudios.liquidcommunications.Fragments.QuickConnectFragment;
import com.LocallyGrownStudios.liquidcommunications.R;

public class CircularPagerAdapter extends FragmentPagerAdapter {

    Fragment currentFragment;
    private int pos = 0;


    public CircularPagerAdapter (FragmentManager fm) {



        super(fm);

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return super.instantiateItem(container, position);


    }



    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:

                currentFragment = LiquidTextFragment.newInstance(0);
                return currentFragment;

            case 1:

                currentFragment = LiquidContactsFragment.newInstance(1);
                return currentFragment;

            case 2:

                currentFragment = QuickConnectFragment.newInstance(2);
                return currentFragment;

            case 3:

                currentFragment = LiquidTextFragment.newInstance(3);
                return currentFragment;

            case 4:

                currentFragment = LiquidContactsFragment.newInstance(4);
                return currentFragment;

        }

        return LiquidContactsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


}