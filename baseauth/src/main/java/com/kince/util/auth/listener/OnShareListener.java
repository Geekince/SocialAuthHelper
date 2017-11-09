package com.kince.util.auth.listener;

import com.kince.util.auth.PlatformType;

/**
 * Created by kince
 *
 */
public interface OnShareListener {

    void onComplete(PlatformType platform_type);

    void onError(PlatformType platform_type, String err_msg);

    void onCancel(PlatformType platform_type);

}
