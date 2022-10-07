package black.android.content.pm;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.util.DisplayMetrics;

import java.io.File;
import java.util.List;

import black.Reflector;

public class PackageParser {
    public static final Reflector REF = Reflector.on("android.content.pm.PackageParser");

    public static Reflector.ConstructorWrapper<android.content.pm.PackageParser> _new = REF.constructor(String.class);
    public static Reflector.MethodWrapper<Void> collectCertificates = REF.method("collectCertificates", android.content.pm.PackageParser.Package.class, int.class);
    public static Reflector.MethodWrapper<android.content.pm.PackageParser.Package> parsePackage = REF.method("parsePackage", File.class, String.class, DisplayMetrics.class, int.class);

    public static class SigningDetails {
        public static final Reflector REF = Reflector.on("android.content.pm.PackageParser$SigningDetails");

        public static Reflector.FieldWrapper<Signature[]> signatures = REF.field("signatures");
    }

    public static class Component {
        public static final Reflector REF = Reflector.on("android.content.pm.PackageParser$Component");

        public static Reflector.FieldWrapper<String> className = REF.field("className");
        public static Reflector.FieldWrapper<ComponentName> componentName = REF.field("componentName");
        public static Reflector.FieldWrapper<List<android.content.IntentFilter>> intents = REF.field("intents");
    }

    public static class Permission {
        public static final Reflector REF = Reflector.on("android.content.pm.PackageParser$Permission");

        public static Reflector.FieldWrapper<PermissionInfo> info = REF.field("info");
    }

    public static class Service {
        public static final Reflector REF = Reflector.on("android.content.pm.PackageParser$Service");

        public static Reflector.FieldWrapper<ServiceInfo> info = REF.field("info");
    }

    public static class Activity {
        public static final Reflector REF = Reflector.on("android.content.pm.PackageParser$Activity");

        public static Reflector.FieldWrapper<ActivityInfo> info = REF.field("info");
    }

    public static class Package {
        public static final Reflector REF = Reflector.on("android.content.pm.PackageParser$Package");

        public static Reflector.FieldWrapper<List<?>> activities = REF.field("activities");
        public static Reflector.FieldWrapper<String> packageName = REF.field("packageName");
        public static Reflector.FieldWrapper<List<?>> permissions = REF.field("permissions");
        public static Reflector.FieldWrapper<List<?>> providers = REF.field("providers");
        public static Reflector.FieldWrapper<List<?>> services = REF.field("services");
    }
}
