package com.example.time_register.fragments;

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

public class AdminTaskFragment extends BasicFragment implements View.OnClickListener {
    private ListView mListView;
    private ArrayList<String> mTasks = new ArrayList<>();
    private ArrayList<String> listOfKey = new ArrayList<>();
    private View view;
    private TextView title;
    private TableLayout tasksTable;
    private TaskDataProvider taskDataProvider = new TaskDataProvider();
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.admin_tasks_fragment, container, false);
//        tasksTable = view.findViewById(R.id.tasks_table);
        FloatingActionButton addBtn =  view.findViewById(R.id.admin_add_task_btn);
        addBtn.setOnClickListener(this);
        title = view.findViewById(R.id.title2);
        mListView = view.findViewById(R.id.listview2);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TaskFragment taskFragment = new TaskFragment();
                Bundle bundle = new Bundle();
//                Toast.makeText(getActivity(), listOfKey.get(1).toString(), Toast.LENGTH_LONG).show();
                bundle.putString("TaskId", listOfKey.get(i));
                taskFragment.setArguments(bundle);
                setFragment(R.id.admin_fragment, taskFragment);
            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.list_item, mTasks);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =    mFirebaseDatabase.getReference().child("Tasks");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot uniqueKeySnapshot : snapshot.getChildren()){
                    if(!uniqueKeySnapshot.child("Status").toString().contains("DONE")) {
                        String post = uniqueKeySnapshot.child("Name").getValue().toString() + " : "
                                + uniqueKeySnapshot.child("Describe").getValue().toString();


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



//        loadTasks();



        /*ArrayList<String> mMeetings = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, mMeetings);
        mListView.setAdapter(arrayAdapter);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =    mFirebaseDatabase.getReference().child("Tasks");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    mMeetings.add(childDataSnapshot.child("Name").getValue().toString());
                    Toast.makeText(getActivity(), childDataSnapshot.child("Name").getValue().toString(), Toast.LENGTH_LONG).show();

//                    Log.v(TAG,""+ childDataSnapshot.getKey()); //displays the key for the node
//                    Log.v(TAG,""+ childDataSnapshot.child("Name").getValue());   //gives the value for given keyname
                }
            arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
/*
        loadTasks();
*/
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
