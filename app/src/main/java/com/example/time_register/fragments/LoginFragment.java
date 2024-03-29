package com.example.time_register.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.time_register.R;
import com.example.time_register.activities.AdminActivity;
import com.example.time_register.activities.EmployeeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends BasicFragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference DataRef;
    private EditText editTextEmail , editTextPassword ;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        Button loginUser =  v.findViewById(R.id.login_button);
        Button registerUser = v.findViewById(R.id.register_button);
        loginUser.setOnClickListener(this);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText) v.findViewById(R.id.email_input_login);
        editTextPassword = (EditText) v.findViewById(R.id.password_input_login);
        mAuth = FirebaseAuth.getInstance();
        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_button:
                loginUser();
                break;
            case R.id.register_button:
                Fragment registerFragment = new RegisterFragment();
                setFragment(R.id.account_fragment, registerFragment);
                break;
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("E-mail is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid e-mail!");
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() <6 ){
            editTextPassword.setError("Minimum password lenght is 6 characters !");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //check role
                    String uid = mAuth.getCurrentUser().getUid();
                    DataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    checkUserAccessLevel(uid);


                    /*startActivity(new Intent(getActivity(), AdminActivity.class));
                    Toast.makeText(getActivity(),"Logged in",Toast.LENGTH_LONG).show();*/
                }else{
                    Toast.makeText(getActivity(),"Failed to log in",Toast.LENGTH_LONG).show();
                }
            }
        });





    }

    private void checkUserAccessLevel(String uid) {
        DataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        DataRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.child("role").getValue().toString();
                if (userType.equals("ADMINISTRATOR")) {
                    startActivity(new Intent(getActivity(), AdminActivity.class));
//                    Intent myIntent = new Intent(LoginFragment.this.getActivity(), Admin.class);
//                    startActivity(myIntent);
//                    Toast.makeText(getActivity(), "admin", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getActivity(), EmployeeActivity.class));
                    Toast.makeText(getActivity(), "user", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}