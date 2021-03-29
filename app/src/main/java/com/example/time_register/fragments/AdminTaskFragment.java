package com.example.time_register.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.time_register.R;
import com.example.time_register.data_providers.TaskDataProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminTaskFragment extends BasicFragment implements View.OnClickListener {

    private View view;
    private TextView title;
    private TableLayout tasksTable;
    private TaskDataProvider taskDataProvider = new TaskDataProvider();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.admin_tasks_fragment, container, false);
        tasksTable = view.findViewById(R.id.tasks_table);
        FloatingActionButton addBtn =  view.findViewById(R.id.admin_add_task_btn);
        addBtn.setOnClickListener(this);
        title = view.findViewById(R.id.title);

        loadTasks();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.admin_add_task_btn:
                setFragment(R.id.admin_fragment, new AddTaskFragment());
                break;
        }
    }

    private void loadTasks(){
        taskDataProvider.GetTasks();
    }
}
