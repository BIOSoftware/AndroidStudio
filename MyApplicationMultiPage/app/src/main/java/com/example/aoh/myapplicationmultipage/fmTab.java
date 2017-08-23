package com.example.aoh.myapplicationmultipage;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.aoh.myapplicationmultipage.adapter.adt_fm_Tab;


/**
 * A simple {@link Fragment} subclass.
 */
public class fmTab extends Fragment {


    public fmTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_fm_tab,container,false);
        final TabLayout tabLayout = (TabLayout)v.findViewById(R.id.tabLayout);
        final ViewPager vpMain = (ViewPager)v.findViewById(R.id.vpMain);
        adt_fm_Tab adt = new adt_fm_Tab(getChildFragmentManager());
        vpMain.setAdapter(adt);
        tabLayout.setupWithViewPager(vpMain);

        return v;
    }

}
