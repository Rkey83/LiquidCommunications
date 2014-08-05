package com.LocallyGrownStudios.liquidcommunications.Fragments;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.LocallyGrownStudios.liquidcommunications.R;


public class LiquidContactsFragment extends Fragment {

    private final static String argSectionNumber = "section_number";

    public static LiquidContactsFragment newInstance(int sectionNumber) {

        LiquidContactsFragment fragLQC = new LiquidContactsFragment();
        Bundle args = new Bundle();
        args.putInt(argSectionNumber, sectionNumber);
        fragLQC.setArguments(args);
        return fragLQC;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewLQC = inflater.inflate(R.layout.fragment_liquid_contacts, container, false);

        return viewLQC;
    }


}
