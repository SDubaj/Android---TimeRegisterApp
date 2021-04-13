package com.example.time_register.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.time_register.R;
import com.example.time_register.data_providers.TaskDataProvider;
import com.example.time_register.enums.TaskStatuses;
import com.example.time_register.models.Employee;
import com.example.time_register.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskEmployeeFragment extends BasicFragment implements View.OnClickListener {

    private View view;
    private Task currentTask;
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
        view = inflater.inflate(R.layout.task_employee_fragment, container, false);
        taskStatuses = (Spinner) view.findViewById(R.id.statuses);
//        employees = (Spinner) view.findViewById(R.id.employees);
//        employeesFromDb = new ArrayList<Employee>();
        ArrayList<String> employeesFromDbNames = new ArrayList<String>();
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(
            this.getContext(), R.layout.spinner_item, getResources().getStringArray(R.array.taskStatusesEmployee)
        );
        statusAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        taskStatuses.setAdapter(statusAdapter);

        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<String>(
                this.getContext(), R.layout.spinner_item, employeesFromDbNames
        );
//        employeeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        employees.setAdapter(employeeAdapter);

        String taskId = getArguments().getString("TaskId");
        database.child("Tasks").child(taskId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DataSnapshot> task) {
                currentTask = task.getResult().getValue(Task.class);
//                ((TextView)view.findViewById(R.id.taskId)).setText(currentTask.Id);
                ((TextView)view.findViewById(R.id.task_name_field)).setText(currentTask.Name);
                ((TextView)view.findViewById(R.id.task_describe_field)).setText(currentTask.Describe);
                taskStatuses.setSelection(currentTask.Status.ordinal());
            }
        });

//        database.child("Employees").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DataSnapshot> task) {
//                for (DataSnapshot child:
//                     task.getResult().getChildren()) {
//                    employeesFromDb.add(child.getValue(Employee.class));
//                    employeesFromDbNames.add(child.getValue(Employee.class).Name + " " + child.getValue(Employee.class).Surname);
//                }
//                employeeAdapter.notifyDataSetChanged();
//            }
//        });
        Button saveBtn = view.findViewById(R.id.task_save_button);
        saveBtn.setOnClickListener(this);
        Button backBtn = view.findViewById(R.id.task_back_button);
        backBtn.setOnClickListener(this);
//        Toast.makeText(getActivity(), currentTask.EmployeeId.toString(), Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.task_save_button:
                if(validate()) {
                    taskDataProvider.Update(currentTask);
                    setFragment(R.id.employee_fragment, new EmployeeFragment());
                }
                break;
            case R.id.task_back_button:
                setFragment(R.id.employee_fragment, new EmployeeFragment());
                break;
        }
    }

    private boolean validate() {
        EditText tempField = (EditText) view.findViewById(R.id.task_name_field);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        currentTask.Name = tempField.getText().toString().trim();

        tempField = (EditText) view.findViewById(R.id.task_describe_field);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        currentTask.Describe = tempField.getText().toString().trim();
//        currentTask.EmployeeId = employeesFromDb.get(employees.getSelectedItemPosition()).Id;
        currentTask.Status = TaskStatuses.values()[taskStatuses.getSelectedItemPosition()];
        return true;
    }

    private int getStatusPosition(String position) {
        return Arrays.asList(getResources().getStringArray(R.array.taskStatuses)).indexOf(position);
    }

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
//    DatabaseReference fromPath = mFirebaseDatabase.getReference().child("Users").child(currentTask.Id);
//    DatabaseReference toPath = mFirebaseDatabase.getReference().child("Users").child(currentTask.Id);

    //move item to another node in database(tasks -> employee
   /* public void moveFirebaseRecord()
    {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fromPath.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                toPath.setValue(dataSnapshot.getValue(), new Firebase.CompletionListener()
                {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase)
                    {
                        if (firebaseError != null)
                        {
                            System.out.println("Copy failed");
                        }
                        else
                        {
                            System.out.println("Success");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                System.out.println("Copy failed");
            }
        });
    }*/
}