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
    private int pos;


    public CircularPagerAdapter (FragmentManager fm) {



        super(fm);

    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ViewPager viewPager = (ViewPager) container.findViewById(R.id.pager);
        int i = viewPager.getCurrentItem();

        if ( i < 1 ){
            viewPager.setCurrentItem(3);
        }
        if ( i > 3){
            viewPager.setCurrentItem(1);
        }

        return super.instantiateItem(container, position);


    }



    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:

                currentFragment = LiquidTextFragment.newInstance(1);
                return currentFragment;

            case 1:

                currentFragment = LiquidContactsFragment.newInstance(2);
                return currentFragment;

            case 2:

                currentFragment = QuickConnectFragment.newInstance(3);
                return currentFragment;

            case 3:

                currentFragment = LiquidTextFragment.newInstance(4);
                return currentFragment;

            case 4:

                currentFragment = LiquidContactsFragment.newInstance(5);
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