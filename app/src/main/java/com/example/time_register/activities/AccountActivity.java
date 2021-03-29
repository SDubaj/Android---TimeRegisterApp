package com.example.time_register.activities;

import android.os.Bundle;

import com.example.time_register.fragments.LoginFragment;
import com.example.time_register.R;
import com.example.time_register.fragments.RegisterFragment;

import androidx.fragment.app.Fragment;

public class AccountActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        Fragment loginFragment = new LoginFragment();
        Fragment createEmployee = new LoginFragment();

        setFragment(R.id.account_fragment, loginFragment);
    }
}