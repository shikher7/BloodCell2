package com.example.shikh.bloodcell2.Views.Fragments.LearnFragmentFiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shikh.bloodcell2.R;


public class FactsFragment extends Fragment {

    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.viewpager_facts, container, false);

        return root;

    }

}
