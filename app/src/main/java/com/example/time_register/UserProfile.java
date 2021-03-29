package com.example.time_register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfile extends Fragment implements View.OnClickListener{
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
        Button signOut =  view.findViewById(R.id.signOutBtn);
        signOut.setOnClickListener(this);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signOutBtn:

                signOut();
                break;
        }
    }

    private void signOut(){
        mAuth.signOut();
//        NavHostFragment.findNavController(UserProfile.this)
//                .navigate(R.id.action_userProfile_to_LoginFragment);
//        Toast.makeText(getActivity(),"Logged out",Toast.LENGTH_LONG).show();
    }
}