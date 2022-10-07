package top.niunaijun.bcore.fake.service.libcore;

import android.os.Process;
import android.util.Log;

import androidx.core.util.ObjectsCompat;

import java.lang.reflect.Method;
import java.util.Objects;

import black.Reflector;
import black.libcore.io.Libcore;
import top.niunaijun.bcore.BlackBoxCore;
import top.niunaijun.bcore.app.BActivityThread;
import top.niunaijun.bcore.core.IOCore;
import top.niunaijun.bcore.fake.hook.ClassInvocationStub;
import top.niunaijun.bcore.fake.hook.MethodHook;
import top.niunaijun.bcore.fake.hook.ProxyMethod;

public class OsProxy extends ClassInvocationStub {
    public static final String TAG = "OsProxy";
    private final Object mBase;

    public OsProxy() {
        mBase = Libcore.os.get();
    }

    @Override
    protected Object getWho() {
        return mBase;
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        Libcore.os.set(proxyInvocation);
    }

    @Override
    public boolean isBadEnv() {
        return Libcore.os.get() != getProxyInvocation();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    continue;
                }

                if (args[i] instanceof String && ((String) args[i]).startsWith("/")) {
                    String orig = (String) args[i];
                    args[i] = IOCore.get().redirectPath(orig);

                    if (!ObjectsCompat.equals(orig, args[i])) {
                        Log.d(TAG, "redirectPath: " + orig + "  => " + args[i]);
                    }
                }
            }
        }
        return super.invoke(proxy, method, args);
    }

    @ProxyMethod("getuid")
    public static class GetUID extends MethodHook {

        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            int callUid = (int) method.invoke(who, args);
            return getFakeUid(callUid);
        }
    }

    @ProxyMethod("stat")
    public static class Stat extends MethodHook {

        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            Object invoke;
            try {
                invoke = method.invoke(who, args);
            } catch (Throwable e) {
                throw Objects.requireNonNull(e.getCause());
            }

            Reflector.on("android.system.StructStat")
                            .field("st_uid").set(invoke, getFakeUid(-1));
            return invoke;
        }
    }

    private static int getFakeUid(int callUid) {
        if (callUid > 0 && callUid <= Process.FIRST_APPLICATION_UID) {
            return callUid;
        }

        if (BActivityThread.isThreadInit() && BActivityThread.currentActivityThread().isInit()) {
            return BActivityThread.getBAppId();
        }
        return BlackBoxCore.getHostUid();
    }
}
