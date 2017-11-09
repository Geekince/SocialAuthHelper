package com.kince.util.auth.twitter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.kince.util.auth.AbsSocialOauth;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by kince
 */
public class TwitterAuthHelperAbs extends AbsSocialOauth {

    private TwitterAuthClient mAuthClient;
    @NonNull
    private final Activity mActivity;

    @NonNull private final String mTwitterApiKey;
    @NonNull private final String mTwitterSecreteKey;

    public TwitterAuthHelperAbs(@NonNull TwitterListener response, @NonNull Activity context,
                                @NonNull String twitterApiKey, @NonNull String twitterSecreteKey) {
        mActivity = context;
        mListener = response;
        mTwitterApiKey = twitterApiKey;
        mTwitterSecreteKey = twitterSecreteKey;
        mAuthClient = new TwitterAuthClient();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(mTwitterApiKey, mTwitterSecreteKey);
        Fabric.with(context, new Twitter(authConfig));
        mAuthClient.authorize(mActivity, mCallback);
    }

}
