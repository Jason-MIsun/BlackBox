package top.niunaijun.bcore.utils.compat;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class StartActivityCompat {
    private static int index = 0;
    private static final int intentIndex;
    private static final int resolvedTypeIndex;
    private static final int resultToIndex;
    private static final int resultWhoIndex;
    private static final int requestCodeIndex;
    private static final int flagsIndex;
    private static final int optionsIndex;

    static {
        index++;
        intentIndex = index++;
        resolvedTypeIndex = index++;
        resultToIndex = index++;
        resultWhoIndex = index++;
        requestCodeIndex = index++;
        flagsIndex = index++;
        optionsIndex = index++;
    }

    public static Intent getIntent(Object[] args) {
        if (args == null || args.length < intentIndex) {
            return null;
        }
        return (Intent) args[intentIndex];
    }

    public static String getResolvedType(Object[] args) {
        if (args == null || args.length < resolvedTypeIndex) {
            return null;
        }
        return (String) args[resolvedTypeIndex];
    }

    public static IBinder getResultTo(Object[] args) {
        if (args == null || args.length < resultToIndex) {
            return null;
        }
        return (IBinder) args[resultToIndex];
    }

    public static String getResultWho(Object[] args) {
        if (args == null || args.length < resultWhoIndex) {
            return null;
        }
        return (String) args[resultWhoIndex];
    }

    public static int getRequestCode(Object[] args) {
        if (args == null || args.length < requestCodeIndex) {
            return -1;
        }
        return (int) args[requestCodeIndex];
    }

    public static int getFlags(Object[] args) {
        if (args == null || args.length < flagsIndex) {
            return -1;
        }
        return (int) args[flagsIndex];
    }

    public static Bundle getOptions(Object[] args) {
        if (args == null || args.length < optionsIndex) {
            return null;
        }
        return (Bundle) args[optionsIndex];
    }
}
