package com.kince.util.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kince.util.auth.listener.OnOauthListener;
import com.kince.util.auth.listener.OnShareListener;
import com.kince.util.auth.share.IShareMedia;

/**
 * Created by kince
 */
public abstract class AbsSocialOauth {

    protected OnOauthListener mOnOauthListener;
    protected OnShareListener mOnShareListener;

    /**
     * 用于必须在Application中初始化的SDK，如Twitter等
     */
    public void initializedSdk(Context context, PlatformConfig.Platform config){

    }

    /**
     * 初始化
     * @param config 配置信息
     */
    public void onCreate(Activity context, PlatformConfig.Platform config) {

    }

    /**
     *
     * @param activity
     * @param listener
     */
    public abstract void signIn(Activity activity,OnOauthListener listener);

    /**
     * 分享
     * @param shareMedia 分享内容
     * @param shareListener 分享回调
     */
    public void share(Activity activity, IShareMedia shareMedia, OnShareListener shareListener) {

    }

    /**
     * 重写onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * 是否安装
     *
     * @return
     */
    public boolean isInstall() {
        return true;
    }

    public void signOut() {

    }

    public void revoke() {

    }

    public void release() {

    }

    public void setOnAuthListener(OnOauthListener onOauthListener) {
        mOnOauthListener = onOauthListener;
    }

    public void setOnShareListener(OnShareListener onShareListener) {
        mOnShareListener = onShareListener;
    }

}
