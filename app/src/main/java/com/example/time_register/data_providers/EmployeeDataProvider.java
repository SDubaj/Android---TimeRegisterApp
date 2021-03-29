package com.example.time_register.data_providers;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.time_register.models.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeDataProvider {
    private DatabaseReference database;

    public EmployeeDataProvider() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public boolean Add(Employee employee) {
        final boolean[] result = {false};
        database.child("Employees").child(employee.Id).setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    result[0] = true;
                }
            }
        });

        return true;
    }
}
