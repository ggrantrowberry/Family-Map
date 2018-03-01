package com.example.grantrowberry.fmclient.Net;

import android.support.v4.app.Fragment;

import com.example.grantrowberry.fmclient.Models.Model.AuthToken;
import com.example.grantrowberry.fmclient.Models.ModelContainer;

/**
 * Created by GrantRowberry on 3/23/17.
 */

public interface Context {
     void onSuccess(String message);
     void onError(String message);
     void goToMap();
}
