package com.kince.util.oauth.qq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.kince.util.auth.AbsSocialOauth;
import com.kince.util.auth.PlatformConfig;
import com.kince.util.auth.PlatformType;
import com.kince.util.auth.listener.OnOauthListener;
import com.kince.util.auth.listener.OnShareListener;
import com.kince.util.auth.share.IShareMedia;
import com.kince.util.auth.share.ShareImageMedia;
import com.kince.util.auth.share.ShareMusicMedia;
import com.kince.util.auth.share.ShareWebMedia;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kince
 */
public class QQOauthHelper extends AbsSocialOauth{

    private Activity mActivity;

    private PlatformConfig.QQ mConfig;
    private OnOauthListener mAuthListener;
    private OnShareListener mShareListener;

    public QQOauthHelper(){

    }

    @Override
    public void onCreate(Activity activity, PlatformConfig.Platform config) {
        this.mActivity = activity;
        this.mConfig = (PlatformConfig.QQ) config;

        this.mTencent = Tencent.createInstance(mConfig.appId, mActivity);
    }

    @Override
    public void signIn(Activity activity, OnOauthListener listener) {
        this.mActivity = activity;
        this.mAuthListener = authListener;

        this.mTencent.login(this.mActivity, "all", new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if (null == o || ((JSONObject)o) == null) {
                    mAuthListener.onError(mConfig.getName(), "onComplete response=null");
                    return;
                }

                JSONObject response = (JSONObject) o;

                initOpenidAndToken(response);

                mAuthListener.onComplete(mConfig.getName(), Utils.jsonToMap(response));

                mTencent.logout(mActivity);
            }

            @Override
            public void onError(UiError uiError) {
                String errmsg = "errcode=" + uiError.errorCode + " errmsg=" + uiError.errorMessage + " errdetail=" + uiError.errorDetail;
                mAuthListener.onError(mConfig.getName(), errmsg);
            }

            @Override
            public void onCancel() {
                mAuthListener.onCancel(mConfig.getName());
            }
        });
    }

    @Override
    public void share(Activity activity, IShareMedia shareMedia, OnShareListener shareListener) {
        this.mActivity = activity;
        this.mShareListener = shareListener;

        String path = Environment.getExternalStorageDirectory().toString() + "/socail_qq_img_tmp.png";
        File file = new File(path);
        if(file.exists()) {
            file.delete();
        }

        Bundle params = new Bundle();

        if(this.mConfig.getName() == PlatformType.QZONE) {      //qq空间
            if(shareMedia instanceof ShareWebMedia) {       //网页分享
                ShareWebMedia shareWebMedia = (ShareWebMedia) shareMedia;

                //图片保存本地
                BitmapUtils.saveBitmapFile(shareWebMedia.getThumb(), path);

                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareWebMedia.getTitle());
                params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareWebMedia.getDescription());
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareWebMedia.getWebPageUrl());

                ArrayList<String> path_arr = new ArrayList<>();
                path_arr.add(path);
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, path_arr);  //!这里是大坑 不能用SHARE_TO_QQ_IMAGE_LOCAL_URL
            } else {
                if(this.mShareListener != null) {
                    this.mShareListener.onError(this.mConfig.getName(), "QZone is not support this shareMedia");
                }
                return ;
            }

            //qq zone分享
            this.mTencent.shareToQzone(this.mActivity, params, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    mShareListener.onComplete(mConfig.getName());
                }

                @Override
                public void onError(UiError uiError) {
                    String errmsg = "errcode=" + uiError.errorCode + " errmsg=" + uiError.errorMessage + " errdetail=" + uiError.errorDetail;
                    mShareListener.onError(mConfig.getName(), errmsg);
                }

                @Override
                public void onCancel() {
                    mShareListener.onCancel(mConfig.getName());
                }
            });
        } else {        //分享到qq
            if(shareMedia instanceof ShareWebMedia) {       //网页分享
                ShareWebMedia shareWebMedia = (ShareWebMedia) shareMedia;

                //图片保存本地
                BitmapUtils.saveBitmapFile(shareWebMedia.getThumb(), path);

                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareWebMedia.getTitle());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareWebMedia.getDescription());
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareWebMedia.getWebPageUrl());
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
            } else if(shareMedia instanceof ShareImageMedia) {  //图片分享
                ShareImageMedia shareImageMedia = (ShareImageMedia) shareMedia;

                //图片保存本地
                BitmapUtils.saveBitmapFile(shareImageMedia.getImage(), path);

                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,path);
            } else if (shareMedia instanceof ShareMusicMedia) {  //音乐分享
                ShareMusicMedia shareMusicMedia = (ShareMusicMedia) shareMedia;

                //图片保存本地
                BitmapUtils.saveBitmapFile(shareMusicMedia.getThumb(), path);

                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareMusicMedia.getTitle());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  shareMusicMedia.getDescription());
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  shareMusicMedia.getMusicUrl());
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
                params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareMusicMedia.getMusicUrl());
            } else {
                if(this.mShareListener != null) {
                    this.mShareListener.onError(this.mConfig.getName(), "QQ is not support this shareMedia");
                }
                return ;
            }

            //qq分享
            mTencent.shareToQQ(mActivity, params, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    mShareListener.onComplete(mConfig.getName());
                }

                @Override
                public void onError(UiError uiError) {
                    String errmsg = "errcode=" + uiError.errorCode + " errmsg=" + uiError.errorMessage + " errdetail=" + uiError.errorDetail;
                    mShareListener.onError(mConfig.getName(), errmsg);
                }

                @Override
                public void onCancel() {
                    mShareListener.onCancel(mConfig.getName());
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, null);
    }

    //要初始化open_id和token
    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);

            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openId);
        } catch(Exception e) {
        }
    }

}
