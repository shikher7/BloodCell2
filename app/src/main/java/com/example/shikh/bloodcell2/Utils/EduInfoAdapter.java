package com.example.shikh.bloodcell2.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.shikh.bloodcell2.Views.Fragments.FragmentLearn;
import com.example.shikh.bloodcell2.Views.Fragments.LearnFragmentFiles.FactsFragment;
import com.example.shikh.bloodcell2.Views.Fragments.LearnFragmentFiles.TypesFragment;
import com.example.shikh.bloodcell2.Views.Fragments.LearnFragmentFiles.WhatFragment;
import com.example.shikh.bloodcell2.Views.Fragments.LearnFragmentFiles.WhyFragment;


public class EduInfoAdapter extends FragmentStatePagerAdapter {

    public EduInfoAdapter(FragmentManager fm) {

        super(fm);

    }


    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            return new WhatFragment();

        } else if (position == 1) {

            return new WhyFragment();


        } else if (position == 2) {

            return new TypesFragment();


        } else if (position == 3) {


            return new FactsFragment();

        } else {

            return new FragmentLearn();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

}