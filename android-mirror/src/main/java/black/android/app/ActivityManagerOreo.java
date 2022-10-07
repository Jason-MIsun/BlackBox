package black.android.app;

import android.os.IInterface;

import black.Reflector;

public class ActivityManagerOreo {
    public static final Reflector REF = Reflector.on("android.app.ActivityManager");

    public static Reflector.FieldWrapper<Object> IActivityManagerSingleton = REF.field("IActivityManagerSingleton");

    public static Reflector.StaticMethodWrapper<IInterface> getService = REF.staticMethod("getService");
}
