package com.example.time_register.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.time_register.R;
import com.example.time_register.data_providers.TaskDataProvider;
import com.example.time_register.enums.TaskStatuses;
import com.example.time_register.models.Task;

public class AddTaskFragment extends BasicFragment implements View.OnClickListener {

    private View view;
    private Task task;
    private TaskDataProvider taskDataProvider = new TaskDataProvider();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.add_task_fragment, container, false);

        task = new Task();
        task.Status = TaskStatuses.NEW;

        Button saveBtn = view.findViewById(R.id.task_save_button);
        saveBtn.setOnClickListener(this);
        Button backBtn = view.findViewById(R.id.task_back_button);
        backBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.task_save_button:
                if(validate()) {
                    taskDataProvider.Add(task);
                    setFragment(R.id.admin_fragment, new AdminTaskFragment());
                }
                break;
            case R.id.task_back_button:
                setFragment(R.id.admin_fragment, new AdminTaskFragment());
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
        task.Name = tempField.getText().toString().trim();

        tempField = (EditText) view.findViewById(R.id.task_describe_field);
        if(tempField.getText().toString().trim().isEmpty()) {
            tempField.setError(tempField.getHint() + " is required!");
            tempField.requestFocus();
            return false;
        }
        task.Describe = tempField.getText().toString().trim();
        return true;
    }
}