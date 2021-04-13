package com.example.time_register.data_providers;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.time_register.models.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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
    public boolean Update(String id) {
        final boolean[] result = {false};
        database.child("Users").child(id).child("isNew").setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    result[0] = true;
                }
            }
        });

        return true;
    }

    public List<Employee> GetEmployees(ArrayAdapter<Employee> array) {
        if(database == null)
            database = FirebaseDatabase.getInstance().getReference();
        ArrayList<Employee> employeesList = new ArrayList<Employee>();
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Employee tempEmployee = new Employee();
                    tempEmployee.PESEL = child.child("PESEL").getValue().toString();
                    tempEmployee.HourlyRate = child.child("HourlyRate").getValue().toString();
                    tempEmployee.Surname = child.child("Surname").getValue().toString();
                    tempEmployee.Name = child.child("Name").getValue().toString();
                    tempEmployee.Id = child.child("Id").getValue().toString();
                    employeesList.add(tempEmployee);
                    array.add(tempEmployee);
                }
                array.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        database.child("Employees").addChildEventListener(listener);
        return employeesList;
    }
}
