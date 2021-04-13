package com.example.time_register.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.time_register.R;
import com.example.time_register.fragments.EmployeeCompletedTasks;
import com.example.time_register.fragments.EmployeeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth mAuth;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_activity);
        BottomNavigationView navView = findViewById(R.id.employee_bottom_nav);
        navView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        getSupportActionBar().hide();
        title = findViewById(R.id.title2);
        title.setText("Employee Tasks");
        mAuth = FirebaseAuth.getInstance();

        setFragment(R.id.employee_fragment, new EmployeeFragment());
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_tasks:
                title.setText("My tasks");
                setFragment(R.id.employee_fragment, new EmployeeFragment());
                break;
            case R.id.navigation_completed:
                title.setText("Completed tasks");
                setFragment(R.id.employee_fragment, new EmployeeCompletedTasks());
                // process action
                break;

            case R.id.navigation_sign_out:
                title.setText("Please wait...");
                mAuth.signOut();
                startActivity( new Intent(EmployeeActivity.this, AccountActivity.class));
                break;
            default:
                return false;
        }

        return true;
    }


}