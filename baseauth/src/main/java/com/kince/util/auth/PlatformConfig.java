package com.kince.util.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * 第三方平台配置信息存储
 * Created by Kince
 */
public class PlatformConfig {

    public static Map<PlatformType, Platform> configs = new HashMap();

    static {
        configs.put(PlatformType.WEIXIN, new PlatformConfig.Weixin(PlatformType.WEIXIN));
        configs.put(PlatformType.WEIXIN_CIRCLE, new PlatformConfig.Weixin(PlatformType.WEIXIN_CIRCLE));
        configs.put(PlatformType.QQ, new PlatformConfig.QQ(PlatformType.QQ));
        configs.put(PlatformType.QZONE, new PlatformConfig.QQ(PlatformType.QZONE));
        configs.put(PlatformType.SINA_WB, new PlatformConfig.SinaWB(PlatformType.SINA_WB));

        configs.put(PlatformType.FACEBOOK, new PlatformConfig.SinaWB(PlatformType.FACEBOOK));
        configs.put(PlatformType.TWITTER, new PlatformConfig.SinaWB(PlatformType.TWITTER));
        configs.put(PlatformType.INSTAGRAM, new PlatformConfig.SinaWB(PlatformType.INSTAGRAM));
        configs.put(PlatformType.LINE, new PlatformConfig.SinaWB(PlatformType.LINE));
    }

    public interface Platform {
        PlatformType getName();
    }

    public static Platform getPlatformConfig(PlatformType platformType) {
        return configs.get(platformType);
    }

    //微信
    public static class Weixin implements PlatformConfig.Platform {
        private final PlatformType platformType;
        private String weChatId;
        private String weChatSecret;

        public PlatformType getName() {
            return this.platformType;
        }

        public Weixin(PlatformType type) {
            this.platformType = type;
        }
    }

    /**
     * 设置微信配置信息
     *
     * @param appId
     */
    public static void setWeixin(String appId, String secret) {
        PlatformConfig.Weixin weixin = (PlatformConfig.Weixin) configs.get(PlatformType.WEIXIN);
        weixin.weChatId = appId;
        weixin.weChatSecret = secret;

        PlatformConfig.Weixin weixin_circle = (PlatformConfig.Weixin) configs.get(PlatformType.WEIXIN_CIRCLE);
        weixin_circle.weChatId = appId;
        weixin_circle.weChatSecret = secret;
    }

    // QQ
    public static class QQ implements PlatformConfig.Platform {
        private final PlatformType platformType;
        public String appId = null;

        public PlatformType getName() {
            return this.platformType;
        }

        public QQ(PlatformType type) {
            this.platformType = type;
        }
    }

    /**
     * 设置qq配置信息
     *
     * @param appId
     */
    public static void setQQ(String appId) {
        PlatformConfig.QQ qq = (PlatformConfig.QQ) configs.get(PlatformType.QQ);
        qq.appId = appId;

        PlatformConfig.QQ qzone = (PlatformConfig.QQ) configs.get(PlatformType.QZONE);
        qzone.appId = appId;
    }

    //qq
    public static class SinaWB implements PlatformConfig.Platform {
        private final PlatformType platformType;
        public String appKey = null;

        public PlatformType getName() {
            return this.platformType;
        }

        public SinaWB(PlatformType type) {
            this.platformType = type;
        }
    }

    /**
     * 设置新浪微博配置信息
     *
     * @param appKey
     */
    public static void setSinaWB(String appKey) {
        PlatformConfig.SinaWB sinaWB = (PlatformConfig.SinaWB) configs.get(PlatformType.SINA_WB);
        sinaWB.appKey = appKey;
    }

    // Facebook
    public static class Facebook implements PlatformConfig.Platform {
        private final PlatformType platformType;
        public String appKey = null;

        public PlatformType getName() {
            return this.platformType;
        }

        public Facebook(PlatformType type) {
            this.platformType = type;
        }
    }

    /**
     * 设置Facebook配置信息
     *
     * @param appKey
     */
    public static void setFacebook(String appKey) {
        PlatformConfig.Facebook facebook = (PlatformConfig.Facebook) configs.get(PlatformType.FACEBOOK);
        facebook.appKey = appKey;
    }

}
