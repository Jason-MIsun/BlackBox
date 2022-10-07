package black.android.app;

import black.Reflector;

public class ApplicationPackageManager {
    public static final Reflector REF = Reflector.on("android.app.ApplicationPackageManager");

    public static Reflector.FieldWrapper mPM = REF.field("mPM");
    public static Reflector.FieldWrapper mPermissionManager = REF.field("mPermissionManager");
}
