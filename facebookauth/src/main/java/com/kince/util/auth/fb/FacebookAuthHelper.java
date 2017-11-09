package com.kince.util.auth.fb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kince.util.auth.AbsSocialOauth;
import com.kince.util.auth.PlatformType;
import com.kince.util.auth.listener.OnOauthListener;
import com.kince.util.auth.model.ProfileModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kince on 16-12-9.
 */
public class FacebookAuthHelper extends AbsSocialOauth {

    private CallbackManager mCallBackManager;
    private String mFieldString = "";

    public FacebookAuthHelper(@NonNull Activity context) {
        FacebookSdk.sdkInitialize(context.getApplicationContext());

        mCallBackManager = CallbackManager.Factory.create();

        //get access token
        FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //get the user profile
                getUserProfile(loginResult);
            }

            @Override
            public void onCancel() {
                mOnOauthListener.onCancel(PlatformType.FACEBOOK);
            }

            @Override
            public void onError(FacebookException e) {
                mOnOauthListener.onError(PlatformType.FACEBOOK, e.getMessage());
            }
        };
        LoginManager.getInstance().registerCallback(mCallBackManager, mCallBack);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn(Activity activity, OnOauthListener listener) {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void revoke() {

    }

    @Override
    public void release() {

    }

    @NonNull
    @CheckResult
    public CallbackManager getCallbackManager() {
        return mCallBackManager;
    }

    public void setFieldString(String fieldString) {
        if (fieldString == null) throw new IllegalArgumentException("field string cannot be null.");
        mFieldString = fieldString;
    }

    private void getUserProfile(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //mOnOauthListener.onComplete(parseResponse(object));
                        } catch (Exception e) {
                            //mOnOauthListener.onSignInFail(e.getMessage());
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", mFieldString);
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * Parse the response received into {@link ProfileModel} object.
     *
     * @param object response received.
     * @return {@link ProfileModel} with required fields.
     * @throws JSONException
     */
    private ProfileModel parseResponse(JSONObject object) throws JSONException {
        ProfileModel model = new ProfileModel();
        if (object.has("id")) model.setId(object.getString("id"));
        if (object.has("email")) model.setEmail(object.getString("email"));
        if (object.has("name")) model.setName(object.getString("name"));
        if (object.has("gender")) model.setGender(object.getString("gender"));
        if (object.has("about")) model.setAbout(object.getString("about"));
        if (object.has("picture"))
            model.setAvatar(object.getJSONObject("picture").getJSONObject("data").getString("url"));
        return model;
    }

}
