package com.example.time_register.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.time_register.R;
import com.example.time_register.data_providers.TaskDataProvider;
import com.example.time_register.models.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class EmployeeDetailsFragment extends BasicFragment implements View.OnClickListener {

    private View view;
    private Employee currentEmployee;
    private Spinner taskStatuses;
    private Spinner employees;
    private TaskDataProvider taskDataProvider = new TaskDataProvider();
    private DatabaseReference database;
    private ArrayList<Employee> employeesFromDb;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        database = FirebaseDatabase.getInstance().getReference();
        view = inflater.inflate(R.layout.employee_details_fragment, container, false);
        employeesFromDb = new ArrayList<Employee>();
        ArrayList<String> employeesFromDbNames = new ArrayList<String>();

        String taskId = getArguments().getString("TaskId");

        database.child("Employees").child(taskId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DataSnapshot> task) {
                currentEmployee = task.getResult().getValue(Employee.class);
//                ((TextView)view.findViewById(R.id.taskId)).setText(currentTask.Id);
                ((TextView)view.findViewById(R.id.task_name_field)).setText(currentEmployee.Name);
                ((TextView)view.findViewById(R.id.task_describe_field)).setText(currentEmployee.Surname);
                ((TextView)view.findViewById(R.id.task_describe_field2)).setText(currentEmployee.HourlyRate);
                ((TextView)view.findViewById(R.id.task_describe_field3)).setText(currentEmployee.PESEL);

            }
        });

        Button backBtn = view.findViewById(R.id.task_back_button2);
        backBtn.setOnClickListener(this);
        Button deleteBtn = view.findViewById(R.id.task_delete_button2);
        deleteBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
          /*  case R.id.task_save_button:
                if(validate()) {
                    taskDataProvider.Update(currentEmployee);
                    setFragment(R.id.admin_fragment, new AdminTaskFragment());
                }
                break;*/
            case R.id.task_back_button2:
                setFragment(R.id.admin_fragment, new AdminTaskFragment());
                break;
            case R.id.task_delete_button2:
                deleteTask();
                Toast.makeText(getActivity(), "Task deleted", Toast.LENGTH_LONG).show();
                setFragment(R.id.admin_fragment, new AdminTaskFragment());
                break;
        }
    }

   /* private boolean validate() {
        EditText tempField = (EditText) view.findViewById(R.id.task_name_field);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        currentEmployee.Name = tempField.getText().toString().trim();

        tempField = (EditText) view.findViewById(R.id.task_describe_field);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        currentEmployee.Describe = tempField.getText().toString().trim();
        currentEmployee.EmployeeId = employeesFromDb.get(employees.getSelectedItemPosition()).Id;
        currentEmployee.Status = TaskStatuses.values()[taskStatuses.getSelectedItemPosition()];
        return true;
    }*/

    private int getStatusPosition(String position) {
        return Arrays.asList(getResources().getStringArray(R.array.taskStatuses)).indexOf(position);
    }
    public void deleteTask(){
        database.child("Employees").child(currentEmployee.Id).removeValue();
    }

}