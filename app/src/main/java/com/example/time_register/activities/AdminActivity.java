package com.example.time_register.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.time_register.R;
import com.example.time_register.fragments.AdminCompletedTasks;
import com.example.time_register.fragments.AdminTaskFragment;
import com.example.time_register.fragments.EmployeeListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth mAuth;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        BottomNavigationView navView = findViewById(R.id.admin_bottom_nav);
        navView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        getSupportActionBar().hide();
        title = findViewById(R.id.title2);
        title.setText("Tasks");
        mAuth = FirebaseAuth.getInstance();

        setFragment(R.id.admin_fragment, new AdminTaskFragment());
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_tasks:
                title.setText("Tasks");
                setFragment(R.id.admin_fragment, new AdminTaskFragment());
                break;
            case R.id.navigation_employees:
                title.setText("Employees");
                setFragment(R.id.admin_fragment, new EmployeeListFragment());
                break;
            case R.id.navigation_completed_tasks:
                title.setText("Completed Tasks");
                setFragment(R.id.admin_fragment, new AdminCompletedTasks());
                break;

            case R.id.navigation_sign_out:
                title.setText("Please wait...");
                mAuth.signOut();
                startActivity( new Intent(AdminActivity.this, AccountActivity.class));
                break;
            default:
                return false;
        }

        return true;
    }


}