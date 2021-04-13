package com.example.time_register.fragments;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.time_register.R;
import com.example.time_register.data_providers.TaskDataProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeCompletedTasks extends BasicFragment implements View.OnClickListener {
    private ListView mListView;
    private ArrayList<String> mTasks = new ArrayList<>();
    private ArrayList<String> listOfKey = new ArrayList<>();
    private View view;
    private TextView title;
    private TableLayout tasksTable;
    private TaskDataProvider taskDataProvider = new TaskDataProvider();
    private FirebaseAuth mAuth;
    private List<Object> listObject;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =    mFirebaseDatabase.getReference().child("Users").child(userid);
        view = inflater.inflate(R.layout.employee_tasks_fragment, container, false);
        title = view.findViewById(R.id.title2);
        mListView = view.findViewById(R.id.listview3);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskEmployeeFragment taskFragment = new TaskEmployeeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TaskId", listOfKey.get(i));
                taskFragment.setArguments(bundle);
                setFragment(R.id.employee_fragment, taskFragment);
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, mTasks);


        DatabaseReference databaseTasks =    mFirebaseDatabase.getReference().child("Tasks");
        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot uniqueKeySnapshot : snapshot.getChildren()){
                    if(uniqueKeySnapshot.child("EmployeeId").toString().contains(userid)
                            && uniqueKeySnapshot.child("Status").toString().contains("DONE")
                    ){


                        String post = uniqueKeySnapshot.child("Name").getValue().toString() + " : "
                                + uniqueKeySnapshot.child("Describe").getValue().toString();

                        //push items to lists
                        listOfKey.add(uniqueKeySnapshot.getKey());
                        arrayAdapter.add(post);
                        mListView.setAdapter(arrayAdapter);
                    }
                }

                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });





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


}
