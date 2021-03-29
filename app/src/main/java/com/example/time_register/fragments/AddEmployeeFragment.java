package com.example.time_register.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.time_register.R;
import com.example.time_register.data_providers.EmployeeDataProvider;
import com.example.time_register.models.Employee;
import com.google.firebase.auth.FirebaseAuth;

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
                addEmployee();
                break;
        }
    }

    private void addEmployee() {
        if(validateFields())
        {
            employee.Id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if(employeeDataProvider.Add(employee))
            {
                setFragment(R.id.account_fragment, new LoginFragment());
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