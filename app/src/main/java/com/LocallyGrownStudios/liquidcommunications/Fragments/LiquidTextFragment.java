package com.LocallyGrownStudios.liquidcommunications.Fragments;



import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.LocallyGrownStudios.liquidcommunications.Helpers.ContactBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.ArrayList;
import java.util.List;

public class LiquidTextFragment extends Fragment {

    Context context;
    private List<ContactBean> mylist = new ArrayList<ContactBean>();
    private ListView mylistView;

    private final static String argSectionNumber = "section_number";

    public static LiquidTextFragment newInstance(int sectionNumber) {

        LiquidTextFragment fragLQT = new LiquidTextFragment();
        Bundle args = new Bundle();
        args.putInt(argSectionNumber, sectionNumber);
        fragLQT.setArguments(args);
        return fragLQT;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewLQT = inflater.inflate(R.layout.fragment_liquid_text, container, false);

        return viewLQT;

    }


}