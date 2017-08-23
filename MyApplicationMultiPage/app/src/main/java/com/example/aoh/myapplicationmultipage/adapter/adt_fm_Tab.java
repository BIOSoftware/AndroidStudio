package com.example.aoh.myapplicationmultipage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.aoh.myapplicationmultipage.fmCamera;
import com.example.aoh.myapplicationmultipage.fmGallery;
import com.example.aoh.myapplicationmultipage.fmSlideshow;

/**
 * Created by Aoh on 8/21/2017 AD.
 */

public class adt_fm_Tab extends FragmentPagerAdapter {
    public adt_fm_Tab(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new fmCamera();
            case 1 : return  new fmGallery();
            case 2 : return  new fmSlideshow();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : return "กล้อง";
            case 1 : return  "คลังรูป";
            case 2 : return  "Slide Show";

        }
        return null;
    }
}
