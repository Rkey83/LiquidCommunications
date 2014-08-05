package com.LocallyGrownStudios.liquidcommunications.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.LocallyGrownStudios.liquidcommunications.Fragments.LiquidContactsFragment;
import com.LocallyGrownStudios.liquidcommunications.Fragments.LiquidTextFragment;
import com.LocallyGrownStudios.liquidcommunications.Fragments.QuickConnectFragment;

public class CircularPagerAdapter extends FragmentPagerAdapter {

    Fragment currentFragment;


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

                currentFragment = LiquidContactsFragment.newInstance(position + 1);
                return currentFragment;

            case 1:

                currentFragment = QuickConnectFragment.newInstance(position + 1);
                return currentFragment;

            case 2:

                currentFragment = LiquidTextFragment.newInstance(position + 1);
                return currentFragment;

        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


}