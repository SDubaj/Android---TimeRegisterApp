package com.example.time_register.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.time_register.R;
import com.example.time_register.activities.AdminActivity;
import com.example.time_register.activities.EmployeeActivity;
import com.example.time_register.data_providers.EmployeeDataProvider;
import com.example.time_register.models.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddEmployeeFragment extends BasicFragment implements View.OnClickListener {

    private Employee employee;
    private EmployeeDataProvider employeeDataProvider;
    private View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.add_employee_fragment, container, false);
        employee = new Employee();
        employeeDataProvider = new EmployeeDataProvider();

        Button saveBtn = view.findViewById(R.id.employee_save_button);
        saveBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.employee_save_button:
//                startActivity(new Intent(getActivity(), EmployeeActivity.class));

//                Toast.makeText(getActivity(),"Employee created",Toast.LENGTH_LONG).show();
                addEmployee();
                break;
        }
    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userid = user.getUid();
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference =    mFirebaseDatabase.getReference().child("Users").child(userid);

    private void addEmployee() {
        if(validateFields())
        {
            /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().child("isNew").setValue("false");
                    Toast.makeText(getActivity(),"dziala !",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userid = user.getUid();
            /*FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference =    mFirebaseDatabase.getReference().child("Users").child(userid);*/



            employee.Id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if(employeeDataProvider.Add(employee) && employeeDataProvider.Update(userid))
            {

                setFragment(R.id.employee_fragment, new EmployeeFragment());
//                startActivity(new Intent(getActivity(), EmployeeActivity.class));
                Toast.makeText(getActivity(),"Employee created",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validateFields() {
        EditText tempField = (EditText) view.findViewById(R.id.employee_name);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        employee.Name = tempField.getText().toString().trim();

        tempField = (EditText) view.findViewById(R.id.employee_surname);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        employee.Surname = tempField.getText().toString().trim();

        tempField = (EditText) view.findViewById(R.id.employee_hourly_rate);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        employee.HourlyRate = tempField.getText().toString().trim();

        tempField = (EditText) view.findViewById(R.id.employee_PESEL);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        employee.PESEL = tempField.getText().toString().trim();

        return true;
    }
}