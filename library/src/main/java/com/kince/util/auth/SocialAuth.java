package com.kince.util.auth;

import android.content.Intent;
import android.net.Uri;

import com.kince.util.auth.listener.OnAuthListener;
import com.kince.util.auth.listener.OnShareListener;

/**
 * Created by kince
 *
 */
public abstract class SocialAuth {

    protected OnAuthListener mOnAuthListener;
    protected OnShareListener mOnShareListener;

    public abstract void resultAuthLogin(int requestCode, int resultCode, Intent data);
    public abstract void registerCallbackListener();
    public abstract void signIn();
    public abstract void signOut();
    public abstract void revoke();
    public abstract void share(String content, Uri imageOrVideo);
    public abstract void release();

    public void setOnAuthListener(OnAuthListener onAuthListener) {
        mOnAuthListener = onAuthListener;
    }

    public void setOnShareListener(OnShareListener onShareListener) {
        mOnShareListener = onShareListener;
    }

}
