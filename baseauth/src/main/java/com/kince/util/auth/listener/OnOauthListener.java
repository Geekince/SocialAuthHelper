package com.kince.util.auth.listener;

import com.kince.util.auth.PlatformType;

import java.util.Map;

/**
 * Created by kince
 */
public interface OnOauthListener {

    void onComplete(PlatformType platform_type, Map<String, String> map);

    void onError(PlatformType platform_type, String err_msg);

    void onCancel(PlatformType platform_type);

}
