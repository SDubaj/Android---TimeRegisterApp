package com.example.time_register.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BasicFragment extends Fragment {
    protected void setFragment(int fragmentViewId, Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentViewId, fragment);
        fragmentTransaction.commit();
    }
}