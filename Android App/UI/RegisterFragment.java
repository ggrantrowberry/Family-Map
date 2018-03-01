package com.example.grantrowberry.fmclient.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.grantrowberry.fmclient.Models.RequestResult.LoginRequest;
import com.example.grantrowberry.fmclient.Models.RequestResult.RegisterRequest;
import com.example.grantrowberry.fmclient.R;

/**
 * Created by GrantRowberry on 3/20/17.
 */

public class RegisterFragment extends android.support.v4.app.Fragment {
    private Button mRegister;
    private Button mBack;
    private EditText mUsernameText;
    private EditText mPasswordText;
    private EditText mEmailText;
    private EditText mFirstNameText;
    private EditText mLastNameText;
    private EditText mServerHostText;
    private EditText mServerPortText;
    private RadioGroup mGenders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.register, container, false);

        mUsernameText = (EditText) v.findViewById(R.id.username_text_register);
        mPasswordText = (EditText) v.findViewById(R.id.password_text_register);
        mServerHostText = (EditText) v.findViewById(R.id.server_host_text_register);
        mServerPortText = (EditText) v.findViewById(R.id.server_port_text_register);
        mEmailText = (EditText) v.findViewById(R.id.email_text_register);
        mFirstNameText = (EditText) v.findViewById(R.id.first_name_text_register);
        mLastNameText = (EditText) v.findViewById(R.id.last_name_text_register);
        //TODO: fix gender radio buttons
        mGenders = (RadioGroup) v.findViewById(R.id.gender_radio_group_register);

        mRegister = (Button)v.findViewById(R.id.register_button_register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterRequest registerRequest = createRegisterRequest();
                if (registerRequest != null) {
                    mRegister.setEnabled(false);
                    ((MainActivity) getActivity()).register(registerRequest);
                    mRegister.setEnabled(true);
                }
            }
        });

        mBack = (Button)v.findViewById(R.id.back_button_register);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).backToLoginFragment();
            }
        });






        return v;
    }

    public RegisterRequest createRegisterRequest(){
        RegisterRequest registerRequest = new RegisterRequest();


        if(!mUsernameText.getText().toString().equals("")){
            registerRequest.setUsername(mUsernameText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a username", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mPasswordText.getText().toString().equals("")){
            registerRequest.setPassword(mPasswordText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a password", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mServerPortText.getText().toString().equals("")){
            registerRequest.setServerPort(mServerPortText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a server port", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mServerHostText.getText().toString().equals("")){
            registerRequest.setServerHost(mServerHostText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a server host", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mEmailText.getText().toString().equals("")){
            registerRequest.setEmail(mEmailText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input an email address", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mFirstNameText.getText().toString().equals("")){
            registerRequest.setFirstName(mFirstNameText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a server host", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mLastNameText.getText().toString().equals("")){
            registerRequest.setLastName(mLastNameText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a server host", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(mGenders.getCheckedRadioButtonId() != -1){
            RadioButton radioButton = (RadioButton) getActivity().findViewById(mGenders.getCheckedRadioButtonId());

            if(radioButton.getText().equals("Male")){
                registerRequest.setGender("m");
            } else {
                registerRequest.setGender("f");
            }
        } else {
            Toast.makeText(getActivity(),"Please select a gender", Toast.LENGTH_SHORT).show();
            return null;
        }

        return registerRequest;



    }

}
