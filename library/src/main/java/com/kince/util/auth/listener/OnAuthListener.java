package com.kince.util.auth.listener;

import com.kince.util.auth.model.ProfileModel;

/**
 * Created by kince
 */
public interface OnAuthListener {

    void onSignInFail(String failMessage);

    void onSignInSuccess(ProfileModel profileModel);

    void onSignInCancle();

    void onSignOut();

    void onRevoke();

}
