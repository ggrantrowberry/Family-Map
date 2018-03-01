package com.example.grantrowberry.fmclient.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grantrowberry.fmclient.Models.Model.Person;
import com.example.grantrowberry.fmclient.Models.RequestResult.LoginRequest;
import com.example.grantrowberry.fmclient.R;

/**
 * Created by GrantRowberry on 3/20/17.
 */

public class LoginFragment extends android.support.v4.app.Fragment {
    private Button mLogin;
    private Button mRegister;
    private EditText mUsernameText;
    private EditText mPasswordText;
    private EditText mServerHost;
    private EditText mServerPort;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.login, container, false);



        mLogin = (Button)v.findViewById(R.id.login_button_login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                LoginRequest loginRequest = createLoginRequest();
                if(loginRequest != null) {
                    mLogin.setEnabled(false);
                    ((MainActivity) getActivity()).login(loginRequest);
                    mLogin.setEnabled(true);
                }
            }
        });

        mRegister = (Button) v.findViewById(R.id.register_button_login);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).createRegisterFragment();

            }
        });

        mUsernameText = (EditText) v.findViewById(R.id.username_text_login);
        mPasswordText = (EditText) v.findViewById(R.id.password_text_login);
        mServerHost = (EditText) v.findViewById(R.id.server_host_text_login);
        mServerPort = (EditText) v.findViewById(R.id.server_port_text_login);




        return v;
    }

    public LoginRequest createLoginRequest(){
        LoginRequest loginRequest = new LoginRequest();


        if(!mUsernameText.getText().toString().equals("")){
            loginRequest.setUsername(mUsernameText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a username", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mPasswordText.getText().toString().equals("")){
            loginRequest.setPassword(mPasswordText.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a password", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mServerPort.getText().toString().equals("")){
            loginRequest.setServerHost(mServerHost.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a server port", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(!mServerPort.getText().toString().equals("")){
            loginRequest.setServerPort(mServerPort.getText().toString());
        } else {
            Toast.makeText(getActivity(),"Please input a server host", Toast.LENGTH_SHORT).show();
            return null;
        }
        return loginRequest;

    }

}
