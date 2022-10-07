package black.android.content;

import android.content.pm.ProviderInfo;
import android.os.IInterface;

import black.Reflector;

public class ContentProviderHolderOreo {
    public static final Reflector REF = Reflector.on("android.app.ContentProviderHolder");

    public static Reflector.FieldWrapper<ProviderInfo> info = REF.field("info");
    public static Reflector.FieldWrapper<IInterface> provider = REF.field("provider");
}
