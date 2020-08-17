package com.absinthe.libchecker.constant.librarymap

import com.absinthe.libchecker.R
import com.absinthe.libchecker.constant.LibChip
import java.util.regex.Pattern

object ActivityLibMap : BaseMap() {
    private val MAP: HashMap<String, LibChip> = hashMapOf(
        Pair(
            "com.google.android.gms.common.api.GoogleApiActivity",
            LibChip(R.drawable.ic_lib_play_store, "Google Play Service")
        ),
        Pair(
            "com.android.billingclient.api.ProxyBillingActivity",
            LibChip(R.drawable.ic_lib_play_store, "Google Play Billing")
        ),
        Pair(
            "com.google.android.gms.auth.api.signin.internal.SignInHubActivity",
            LibChip(R.drawable.ic_lib_google, "Google Sign-In")
        ),
        Pair(
            "com.google.android.gms.ads.AdActivity",
            LibChip(R.drawable.ic_lib_google, "Google AdMob")
        ),
        Pair(
            "androidx.slice.compat.SlicePermissionActivity",
            LibChip(R.drawable.ic_lib_jetpack, "Jetpack Slice")
        ),
        Pair(
            "com.tencent.tauth.AuthActivity",
            LibChip(R.drawable.ic_lib_tencent, "腾讯开放平台")
        ),
        Pair(
            "com.tencent.connect.common.AssistActivity",
            LibChip(R.drawable.ic_lib_tencent, "腾讯开放平台")
        ),
        Pair(
            "com.tencent.smtt.sdk.VideoActivity",
            LibChip(R.drawable.ic_lib_tencent, "TBS")
        ),
        Pair(
            "com.tencent.captchasdk.TCaptchaPopupActivity",
            LibChip(R.drawable.ic_lib_tencent, "防水墙")
        ),
        Pair(
            "com.qq.e.ads.LandscapeADActivity",
            LibChip(R.drawable.ic_lib_tencent_ad, "腾讯广告 SDK")
        ),
        Pair(
            "com.qq.e.ads.PortraitADActivity",
            LibChip(R.drawable.ic_lib_tencent_ad, "腾讯广告 SDK")
        ),
        Pair(
            "com.qq.e.ads.ADActivity",
            LibChip(R.drawable.ic_lib_tencent_ad, "腾讯广告 SDK")
        ),
        Pair(
            "com.qq.e.ads.RewardvideoPortraitADActivity",
            LibChip(R.drawable.ic_lib_tencent_ad, "腾讯广告 SDK")
        ), 
        Pair(
            "com.qq.e.ads.RewardvideoLandscapeADActivity",
            LibChip(R.drawable.ic_lib_tencent_ad, "腾讯广告 SDK")
        ),
        Pair(
            "com.tencent.bugly.beta.ui.BetaActivity",
            LibChip(R.drawable.ic_lib_bugly, "Bugly")
        ),
        Pair(
            "com.huawei.updatesdk.service.otaupdate.AppUpdateActivity",
            LibChip(R.drawable.ic_lib_huawei, "HMS Update")
        ),
        Pair(
            "com.huawei.updatesdk.support.pm.PackageInstallerActivity",
            LibChip(R.drawable.ic_lib_huawei, "HMS Update")
        ),
        Pair(
            "com.huawei.hms.activity.BridgeActivity",
            LibChip(R.drawable.ic_lib_huawei, "HMS SDK")
        ),
        Pair(
            "com.huawei.android.hms.agent.common.HMSAgentActivity",
            LibChip(R.drawable.ic_lib_huawei, "HMS SDK")
        ),
        Pair(
            "com.huawei.android.hms.agent.pay.HMSPayAgentActivity",
            LibChip(R.drawable.ic_lib_huawei, "HMS SDK")
        ),
        Pair(
            "com.huawei.android.hms.agent.hwid.HMSSignInAgentActivity",
            LibChip(R.drawable.ic_lib_huawei, "HMS SDK")
        ),
        Pair(
            "com.alipay.sdk.app.H5PayActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.alipay.sdk.app.H5AuthActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.alipay.sdk.auth.AuthActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.alipay.sdk.app.H5OpenAuthActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.alipay.sdk.app.PayResultActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.alipay.sdk.app.AlipayResultActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.ali.auth.third.ui.LoginWebViewActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.ali.auth.third.ui.webview.BaseWebViewActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.ali.auth.third.ui.LoginActivity",
            LibChip(R.drawable.ic_lib_alipay, "支付宝 SDK")
        ),
        Pair(
            "com.alibaba.wireless.security.open.middletier.fc.ui.ContainerActivity",
            LibChip(R.drawable.ic_lib_ali_security, "阿里聚安全")
        ),
        Pair(
            "com.sina.weibo.sdk.share.WbShareTransActivity",
            LibChip(R.drawable.ic_lib_weibo, "微博 SDK")
        ),
        Pair(
            "com.sina.weibo.sdk.share.WbShareToStoryActivity",
            LibChip(R.drawable.ic_lib_weibo, "微博 SDK")
        ),
        Pair(
            "com.sina.weibo.sdk.share.WbShareResultActivity",
            LibChip(R.drawable.ic_lib_weibo, "微博 SDK")
        ),
        Pair(
            "com.sina.weibo.sdk.web.WeiboSdkWebActivity",
            LibChip(R.drawable.ic_lib_weibo, "微博 SDK")
        ),
        Pair(
            "com.sina.weibo.sdk.component.WeiboSdkBrowser",
            LibChip(R.drawable.ic_lib_weibo, "微博 SDK")
        ),
        Pair(
            "com.vivo.push.sdk.LinkProxyClientActivity",
            LibChip(R.drawable.ic_lib_vivo, "vivo Push")
        ),
        Pair(
            "com.facebook.react.devsupport.DevSettingsActivity",
            LibChip(R.drawable.ic_lib_react, "React Native")
        ),
        Pair(
            "com.facebook.FacebookActivity",
            LibChip(R.drawable.ic_lib_facebook, "Facebook SDK")
        ),
        Pair(
            "com.facebook.CustomTabActivity",
            LibChip(R.drawable.ic_lib_facebook, "Facebook SDK")
        ),
        Pair(
            "com.facebook.CustomTabMainActivity",
            LibChip(R.drawable.ic_lib_facebook, "Facebook SDK")
        ),
        Pair(
            "com.yalantis.ucrop.UCropActivity",
            LibChip(R.drawable.ic_question, "uCrop")
        ),
        Pair(
            "com.yalantis.ucrop.PictureMultiCuttingActivity",
            LibChip(R.drawable.ic_question, "uCrop")
        ),
        Pair(
            "com.zhihu.matisse.internal.ui.AlbumPreviewActivity",
            LibChip(R.drawable.ic_lib_zhihu, "Matisse")
        ),
        Pair(
            "com.zhihu.matisse.internal.ui.SelectedPreviewActivity",
            LibChip(R.drawable.ic_lib_zhihu, "Matisse")
        ),
        Pair(
            "com.zhihu.matisse.ui.MatisseActivity",
            LibChip(R.drawable.ic_lib_zhihu, "Matisse")
        ),
        Pair(
            "cn.jpush.android.service.JNotifyActivity",
            LibChip(R.drawable.ic_lib_jpush, "极光推送")
        ),
        Pair(
            "cn.jpush.android.ui.PushActivity",
            LibChip(R.drawable.ic_lib_jpush, "极光推送")
        ),
        Pair(
            "cn.jpush.android.ui.PopWinActivity",
            LibChip(R.drawable.ic_lib_jpush, "极光推送")
        ),
        Pair(
            "com.igexin.sdk.PushActivity",
            LibChip(R.drawable.ic_question, "个推")
        ),
        Pair(
            "com.igexin.sdk.GActivity",
            LibChip(R.drawable.ic_question, "个推")
        ),
        Pair(
            "com.xiaomi.account.openauth.AuthorizeActivity",
            LibChip(R.drawable.ic_lib_xiaomi, "小米账号开放平台")
        ),
        Pair(
            "com.blankj.utilcode.util.UtilsTransActivity",
            LibChip(R.drawable.ic_question, "AndroidUtilCode")
        ),
        Pair(
            "com.blankj.utilcode.util.UtilsTransActivity4MainProcess",
            LibChip(R.drawable.ic_question, "AndroidUtilCode")
        ),
        Pair(
            "com.alibaba.sdk.android.push.keeplive.PushExtActivity",
            LibChip(R.drawable.ic_lib_aliyun, "阿里移动推送")
        ),
        Pair(
            "com.taobao.weex.WXGlobalEventReceiver",
            LibChip(R.drawable.ic_lib_alibaba, "Weex")
        ),
        Pair(
            "cmb.pb.ui.PBKeyboardActivity",
            LibChip(R.drawable.ic_lib_cmb, "招商银行 SDK")
        ),
        Pair(
            "org.hapjs.features.channel.transparentactivity.TransparentActivity",
            LibChip(R.drawable.ic_lib_hapjs, "快应用")
        ),
        Pair(
            "com.unionpay.uppay.PayActivity",
            LibChip(R.drawable.ic_lib_unionpay, "银联 SDK")
        ),
        Pair(
            "com.unionpay.UPPayWapActivity",
            LibChip(R.drawable.ic_lib_unionpay, "银联 SDK")
        ),
        Pair(
            "net.openid.appauth.RedirectUriReceiverActivity",
            LibChip(R.drawable.ic_lib_appauth, "AppAuth")
        ),
        Pair(
            "net.openid.appauth.AuthorizationManagementActivity",
            LibChip(R.drawable.ic_lib_appauth, "AppAuth")
        ),
        Pair(
            "com.pingplusplus.android.PaymentActivity",
            LibChip(R.drawable.ic_question, "Ping++")
        ),
        Pair(
            "com.unity3d.player.UnityPlayerActivity",
            LibChip(R.drawable.ic_lib_unity, "Unity")
        ),
        Pair(
            "com.soundcloud.android.crop.CropImageActivity",
            LibChip(R.drawable.ic_question, "android-crop")
        ),
        Pair(
            "com.yanzhenjie.permission.bridge.BridgeActivity",
            LibChip(R.drawable.ic_question, "AndPermission")
        ),
        Pair(
            "com.yanzhenjie.permission.PermissionActivity",
            LibChip(R.drawable.ic_question, "AndPermission")
        ),
        Pair(
            "com.google.ar.core.InstallActivity",
            LibChip(R.drawable.ic_lib_google_arcore, "ARCore")
        ),
        Pair(
            "com.cmic.sso.sdk.activity.LoginAuthActivity",
            LibChip(R.drawable.ic_lib_jverification, "极光认证")
        ),
        Pair(
            "pub.devrel.easypermissions.AppSettingsDialogHolderActivity",
            LibChip(R.drawable.ic_question, "EasyPermissions")
        ),
        Pair(
            "com.bestpay.app.H5PayActivity",
            LibChip(R.drawable.ic_question, "翼支付")
        )
    )

    override fun getMap(): HashMap<String, LibChip> {
        return MAP
    }

    override fun findRegex(name: String): LibChip? {
        return when {
            Pattern.matches("com.tencent.tinker.loader.hotplug.ActivityStubs(.*)", name) -> LibChip(R.drawable.ic_lib_tencent, "Tinker", "regex_tinker")
            Pattern.matches("com.bytedance.sdk.openadsdk.activity.(.*)", name) -> LibChip(R.drawable.ic_lib_toutiao, "头条广告 SDK", "regex_toutiao_ad")
            Pattern.matches("com.ss.android.socialbase.appdownloader.(.*)", name) -> LibChip(R.drawable.ic_lib_toutiao, "头条广告 SDK", "regex_toutiao_ad")
            Pattern.matches("com.ss.android.downloadlib.(.*)", name) -> LibChip(R.drawable.ic_lib_toutiao, "头条广告 SDK", "regex_toutiao_ad")
            else -> null
        }
    }
}