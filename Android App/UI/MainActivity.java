package com.example.grantrowberry.fmclient.UI;

import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.example.grantrowberry.fmclient.Models.Model.AuthToken;
import com.example.grantrowberry.fmclient.Models.Model.Util;
import com.example.grantrowberry.fmclient.Models.ModelContainer;
import com.example.grantrowberry.fmclient.Models.RequestResult.LoginRequest;
import com.example.grantrowberry.fmclient.Models.RequestResult.RegisterRequest;
import com.example.grantrowberry.fmclient.Net.Context;
import com.example.grantrowberry.fmclient.Net.LoginTask;
import com.example.grantrowberry.fmclient.Net.RegisterTask;
import com.example.grantrowberry.fmclient.R;

import java.util.List;


public class MainActivity extends FragmentActivity implements Context {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    private MapFragment mapFragment;
    Util util = new Util();


    public void onSuccess(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onError(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
         loginFragment = (LoginFragment) fm.findFragmentById(R.id.login_fragment);

        if(loginFragment == null){
            loginFragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.login_fragment, loginFragment)
                    .commit();
        }
    }

    public void createRegisterFragment(){
        FragmentManager fm = this.getSupportFragmentManager();

        //registerFragment = (RegisterFragment) fm.findFragmentById(R.id.register_fragment);
        fm.beginTransaction()
                .remove(loginFragment)
                .commit();

        registerFragment = new RegisterFragment();
        fm.beginTransaction()
                .add(R.id.register_fragment,registerFragment)
                .commit();

    }

    public void backToLoginFragment(){
        FragmentManager fm = this.getSupportFragmentManager();

        //loginFragment = (LoginFragment) fm.findFragmentById(R.id.login_fragment);
        fm.beginTransaction()
                .remove(registerFragment)
                .commit();

        loginFragment = new LoginFragment();
        fm.beginTransaction()
                .add(R.id.login_fragment,loginFragment)
                .commit();
    }

    public void goToMap(){
        util.fillColors(this);

        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();

        if (fragments != null) {
            for (android.support.v4.app.Fragment fragment : fragments) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
        }

        FragmentManager fm = this.getSupportFragmentManager();


        mapFragment = new MapFragment();


        fm.beginTransaction()
                .add(R.id.map_fragment,mapFragment)
                .commit();

    }

    public void login(LoginRequest loginRequest){

        LoginTask loginTask = new LoginTask();
        loginTask.setC(this);
        loginTask.execute(loginRequest);


    }

    public void register(RegisterRequest registerRequest){
        RegisterTask registerTask = new RegisterTask();
        registerTask.setC(this);
        registerTask.execute(registerRequest);
    }
}
