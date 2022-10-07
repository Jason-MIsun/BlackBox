package top.niunaijun.bcore.fake.service;

import java.lang.reflect.Method;

import black.android.telephony.TelephonyManager;
import top.niunaijun.bcore.BlackBoxCore;
import top.niunaijun.bcore.fake.hook.ClassInvocationStub;
import top.niunaijun.bcore.fake.hook.MethodHook;
import top.niunaijun.bcore.fake.hook.ProxyMethod;
import top.niunaijun.bcore.utils.Md5Utils;
import top.niunaijun.bcore.utils.MethodParameterUtils;

public class IPhoneSubInfoProxy extends ClassInvocationStub {
    public static final String TAG = "IPhoneSubInfoProxy";

    public IPhoneSubInfoProxy() {
        if (TelephonyManager.sServiceHandleCacheEnabled != null) {
            TelephonyManager.sServiceHandleCacheEnabled.set(true);
        }

        if (TelephonyManager.getSubscriberInfoService != null) {
            TelephonyManager.getSubscriberInfoService.call();
        }
    }

    @Override
    protected Object getWho() {
        return TelephonyManager.sIPhoneSubInfo.get();
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        TelephonyManager.sIPhoneSubInfo.set(proxyInvocation);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodParameterUtils.replaceLastAppPkg(args);
        return super.invoke(proxy, method, args);
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }

    @ProxyMethod("getLine1NumberForSubscriber")
    public static class GetLine1NumberForSubscriber extends MethodHook {

        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            return null;
        }
    }

    @ProxyMethod("getSubscriberIdForSubscriber")
    public static class GetSubscriberIdForSubscriber extends MethodHook {

        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            return Md5Utils.md5(BlackBoxCore.getHostPkg());
        }
    }

    @ProxyMethod("getIccSerialNumber")
    public static class GetIccSerialNumber extends MethodHook {

        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            return "89860221919704198154";
        }
    }

    @ProxyMethod("getIccSerialNumberForSubscriber")
    public static class GetIccSerialNumberForSubscriber extends GetIccSerialNumber { }
}
