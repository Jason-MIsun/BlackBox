package black.android.content;

import java.util.List;

import black.Reflector;

public class IntentFilter {
    public static final Reflector REF = Reflector.on("android.content.IntentFilter");

    public static Reflector.FieldWrapper<List<String>> mActions = REF.field("mActions");
}
