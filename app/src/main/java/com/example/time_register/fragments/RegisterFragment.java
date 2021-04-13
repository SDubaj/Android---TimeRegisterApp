package com.example.time_register.fragments;

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
import com.example.time_register.enums.UserRoles;
import com.example.time_register.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends BasicFragment implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextEmail , editTextPassword ;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);

        Button loginUser =  v.findViewById(R.id.login_button);
        Button registerUser = v.findViewById(R.id.register_button);
        loginUser.setOnClickListener(this);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText) v.findViewById(R.id.email_input);
        editTextPassword = (EditText) v.findViewById(R.id.password_input_singup);

        mAuth = FirebaseAuth.getInstance();

        return v;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register_button:
                registerUser();
                break;
            case R.id.login_button:
                Fragment loginFragment = new LoginFragment();
                setFragment(R.id.account_fragment, loginFragment);
                break;
        }
    }

    private void registerUser() {
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

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(email, email.contains("admin") ? UserRoles.ADMINISTRATOR : UserRoles.USER,true);
                            Toast.makeText(getActivity(),"User has been registered",Toast.LENGTH_LONG).show();
                            Fragment addEmployee = new LoginFragment();
                            setFragment(R.id.account_fragment, addEmployee);

                            boolean isNew = true;
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            /*FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(isNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });*/
                        }else{
                            Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();
                        }
                    }

                });

    }
}



