package com.kince.util.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kince.util.auth.listener.OnOauthListener;
import com.kince.util.auth.listener.OnShareListener;
import com.kince.util.auth.share.IShareMedia;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kince
 */
public class SocialOauthManager {

    private static volatile SocialOauthManager instance;
    private final Map<PlatformType, AbsSocialOauth> mMapSSOHandler = new HashMap();

    private SocialOauthManager() {
    }

    public SocialOauthManager getInstance() {
        if (instance == null) {
            synchronized (SocialOauthManager.class) {
                if (instance == null) {
                    instance = new SocialOauthManager();
                }
            }
        }
        return instance;
    }

    public AbsSocialOauth makeSocialAuth(PlatformType platformType) throws IllegalAccessException, InstantiationException {
        if (mMapSSOHandler.get(platformType) == null) {
            switch (platformType) {
                case WEIXIN:
                    AbsSocialOauth wxAuth = (AbsSocialOauth) getTargetClass("com.kince.util.auth.wx.WeixinAuthHelper").newInstance();
                    mMapSSOHandler.put(platformType, wxAuth);
                    break;
                case QQ:
                    AbsSocialOauth qqAuth = (AbsSocialOauth) getTargetClass("com.kince.util.auth.wx.WeixinAuthHelper").newInstance();
                    mMapSSOHandler.put(platformType, qqAuth);
                    break;

                case QZONE:
                    AbsSocialOauth qzoneAuth = (AbsSocialOauth) getTargetClass("com.kince.util.auth.wx.WeixinAuthHelper").newInstance();
                    mMapSSOHandler.put(platformType, qzoneAuth);
                    break;

                case SINA_WB:
                    AbsSocialOauth sinaAuth = (AbsSocialOauth) getTargetClass("com.kince.util.auth.wx.WeixinAuthHelper").newInstance();
                    mMapSSOHandler.put(platformType, sinaAuth);
                    break;
                case FACEBOOK:
                    AbsSocialOauth fbAuth = (AbsSocialOauth) getTargetClass("com.kince.util.auth.wx.WeixinAuthHelper").newInstance();
                    mMapSSOHandler.put(platformType, fbAuth);
                    break;
                case TWITTER:
                    AbsSocialOauth twitterAuth = (AbsSocialOauth) getTargetClass("com.kince.util.auth.wx.WeixinAuthHelper").newInstance();
                    mMapSSOHandler.put(platformType, twitterAuth);
                    break;
                case LINE:
                    AbsSocialOauth lineAuth = (AbsSocialOauth) getTargetClass("com.kince.util.auth.wx.WeixinAuthHelper").newInstance();
                    mMapSSOHandler.put(platformType, lineAuth);
                    break;
                case INSTAGRAM:
                    AbsSocialOauth insAuth = (AbsSocialOauth) getTargetClass("com.kince.util.auth.wx.WeixinAuthHelper").newInstance();
                    mMapSSOHandler.put(platformType, insAuth);
                    break;
                default:
                    break;
            }
        }
        return mMapSSOHandler.get(platformType);
    }

    /**
     * 第三方登录授权
     *
     * @param activity
     * @param platformType 第三方平台
     * @param authListener 授权回调
     */
    public void doOauthVerify(Activity activity, PlatformType platformType, OnOauthListener authListener) throws InstantiationException, IllegalAccessException {
        AbsSocialOauth absSocialOauth = makeSocialAuth(platformType);
        absSocialOauth.onCreate(activity, PlatformConfig.getPlatformConfig(platformType));
        absSocialOauth.signIn(activity, authListener);
    }

    /**
     * 分享
     * 调用客户端分享还是授权分享
     * @param platformType
     * @param shareMedia
     * @param shareListener
     */
    public void doShare(Activity activity, PlatformType platformType, IShareMedia shareMedia, OnShareListener shareListener) throws InstantiationException, IllegalAccessException {
        AbsSocialOauth ssoHandler = makeSocialAuth(platformType);
        ssoHandler.onCreate(activity, PlatformConfig.getPlatformConfig(platformType));
        ssoHandler.share(activity, shareMedia, shareListener);
    }

    /**
     * activity result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Map.Entry<PlatformType, AbsSocialOauth> entry : mMapSSOHandler.entrySet()) {
            entry.getValue().onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 用于必须在Application中初始化的SDK，如Twitter等
     */
    public SocialOauthManager initEssentialSdk(Context context, PlatformType platformType) throws InstantiationException, IllegalAccessException {
        AbsSocialOauth absSocialOauth = makeSocialAuth(platformType);
        absSocialOauth.initializedSdk(context, PlatformConfig.getPlatformConfig(platformType));
        return this;
    }

    private Class<?> getTargetClass(String className) {
        Class<?> target = null;
        try {
            target = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return target;
    }

}
