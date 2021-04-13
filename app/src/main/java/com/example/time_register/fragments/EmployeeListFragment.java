package com.example.time_register.fragments;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.time_register.R;
import com.example.time_register.data_providers.TaskDataProvider;
import com.example.time_register.models.Employee;
import com.example.time_register.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListFragment extends BasicFragment {
    private ListView mListView;
    private View view;
    private TextView title;
    private TableLayout tasksTable;
    private ArrayList<String> listOfKey = new ArrayList<>();
    private TaskDataProvider taskDataProvider = new TaskDataProvider();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.employee_list_fragment, container, false);
        mListView = view.findViewById(R.id.employeesList);
        ArrayList<String> employeesName = new ArrayList<String>();
        ArrayAdapter employeeAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, employeesName);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference database = mFirebaseDatabase.getReference();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EmployeeDetailsFragment taskFragment = new EmployeeDetailsFragment();
                Bundle bundle = new Bundle();
//                Toast.makeText(getActivity(), listOfKey.get(1).toString(), Toast.LENGTH_LONG).show();
                bundle.putString("TaskId", listOfKey.get(i));
                taskFragment.setArguments(bundle);
                setFragment(R.id.admin_fragment, taskFragment);
            }
        });



        database.child("Employees").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DataSnapshot> task) {
                for (DataSnapshot child:
                        task.getResult().getChildren()) {

                    employeesName.add(child.getValue(Employee.class).Name + " " + child.getValue(Employee.class).Surname);
                    listOfKey.add(child.getKey());
                }
                employeeAdapter.notifyDataSetChanged();
            }
        });
        mListView.setAdapter(employeeAdapter);

        return view;
    }
}
