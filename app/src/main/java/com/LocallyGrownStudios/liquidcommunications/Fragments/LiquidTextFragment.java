package com.LocallyGrownStudios.liquidcommunications.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.animation.Animator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LocallyGrownStudios.liquidcommunications.Helpers.QuickConnectBean;
import com.LocallyGrownStudios.liquidcommunications.R;

import java.util.ArrayList;
import java.util.List;

public class LiquidTextFragment extends Fragment {

    Context context;
    private List<QuickConnectBean> mylist = new ArrayList<QuickConnectBean>();
    private ListView mylistView;

    public static LiquidTextFragment newInstance() {

        LiquidTextFragment fragLQT = new LiquidTextFragment();
        Bundle args = new Bundle();
        fragLQT.setArguments(args);
        return fragLQT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewLQT = inflater.inflate(R.layout.fragment_liquid_text, container, false);

        return viewLQT;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }
}
