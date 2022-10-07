package black;

import android.util.Log;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

@SuppressWarnings({"WeakerAccess", "unchecked"})
public class Reflector {
    private static final String TAG = "Reflector";
    private static Class<?> mClazz;

    private Reflector(Class<?> clazz) {
        mClazz = clazz;
    }

    public static Reflector on(String name) {
        return new Reflector(findClass(name));
    }

    public static Reflector on(String name, ClassLoader loader) {
        return new Reflector(findClass(name, loader));
    }

    public static <T> Reflector on(Class<T> clazz) {
        return new Reflector(clazz);
    }

    public Class<?> getClazz() {
        return mClazz;
    }

    public static <T> MethodWrapper<T> wrap(Method method) {
        return new MethodWrapper<>(method);
    }

    public static <T> StaticMethodWrapper<T> wrapStatic(Method method) {
        return new StaticMethodWrapper<>(method);
    }

    public <T> MethodWrapper<T> method(String name, Class<?>... parameterTypes) {
        return method(mClazz, name, parameterTypes);
    }

    public <T> MethodWrapper<T> method(String className, String name, Class<?>... parameterTypes) {
        return method(findClass(className), name, parameterTypes);
    }

    public <T> MethodWrapper<T> method(Class<?> clazz, String name, Class<?>... parameterTypes) {
        Method method = getMethod(clazz, name, parameterTypes);
        if ((parameterTypes == null || parameterTypes.length == 0) && method == null) {
            method = findMethodNoChecks(clazz, name);
        }
        return wrap(method);
    }

    public <T> StaticMethodWrapper<T> staticMethod(String name, Class<?>... parameterTypes) {
        return staticMethod(mClazz, name, parameterTypes);
    }

    public <T> StaticMethodWrapper<T> staticMethod(String className, String name, Class<?>... parameterTypes) {
        return staticMethod(findClass(className), name, parameterTypes);
    }

    public <T> StaticMethodWrapper<T> staticMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        Method method = getMethod(clazz, name, parameterTypes);
        if ((parameterTypes == null || parameterTypes.length == 0) && method == null) {
            method = findMethodNoChecks(clazz, name);
        }
        return wrapStatic(method);
    }

    public static <T> FieldWrapper<T> wrap(Field field) {
        return new FieldWrapper<>(field);
    }

    public <T> FieldWrapper<T> field(String name) {
        return field(mClazz, name);
    }

    public <T> FieldWrapper<T> field(String className, String name) {
        return field(findClass(className), name);
    }

    public <T> FieldWrapper<T> field(Class<?> clazz, String name) {
        return wrap(getField(clazz, name));
    }

    public static <T> ConstructorWrapper<T> wrap(Constructor<T> constructor) {
        return new ConstructorWrapper<>(constructor);
    }

    public <T> ConstructorWrapper<T> constructor(Class<?>... parameterTypes) {
        return wrap(getConstructor(mClazz, parameterTypes));
    }

    public static Class<?> findClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static Class<?> findClass(String name, ClassLoader loader) {
        try {
            return Class.forName(name, true, loader);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        return findMethod(clazz, name, parameterTypes);
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        checkForFindMethod(parameterTypes);
        return findMethodNoChecks(clazz, name, parameterTypes);
    }

    public static Method findMethodNoChecks(Class<?> clazz, String name, Class<?>... parameterTypes) {
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                Log.e(TAG, e.getMessage());
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private static void checkForFindMethod(Class<?>... parameterTypes) {
        if (parameterTypes != null) {
            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i] == null) {
                    throw new NullPointerException("parameterTypes[" + i + "] == null");
                }
            }
        }
    }

    public static Field getField(Class<?> clazz, String name) {
        return findField(clazz, name);
    }

    public static Field findField(Class<?> clazz, String name) {
        return findFieldNoChecks(clazz, name);
    }

    public static Field findFieldNoChecks(Class<?> clazz, String name) {
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                Log.e(TAG, e.getMessage());
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static <T> Constructor<T> getConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        return findConstructor(clazz, parameterTypes);
    }

    public static <T> Constructor<T> findConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        checkForFindConstructor(parameterTypes);
        return findConstructorNoChecks(clazz, parameterTypes);
    }

    public static <T> Constructor<T> findConstructorNoChecks(Class<?> clazz, Class<?>... parameterTypes) {
        try {
            Constructor<T> constructor = (Constructor<T>) clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    private static void checkForFindConstructor(Class<?>... parameterTypes) {
        if (parameterTypes != null) {
            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i] == null) {
                    throw new NullPointerException("parameterTypes[" + i + "] == null");
                }
            }
        }
    }

    public static class MemberWrapper<M extends AccessibleObject & Member> {
        M member;

        MemberWrapper(M member) {
            if (member == null) {
                return;
            }

            member.setAccessible(true);
            this.member = member;
        }
    }

    public static class MethodWrapper<T> extends MemberWrapper<Method> {
        MethodWrapper(Method method) {
            super(method);
        }

        public T call(Object instance, Object... args) {
            try {
                return (T) member.invoke(instance, args);
            } catch (Throwable e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }
    }

    public static class StaticMethodWrapper<T> extends MemberWrapper<Method> {
        StaticMethodWrapper(Method method) {
            super(method);
        }

        public T call(Object... args) {
            try {
                return (T) member.invoke(null, args);
            } catch (Throwable e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }
    }

    public static class FieldWrapper<T> extends MemberWrapper<Field> {
        FieldWrapper(Field field) {
            super(field);
        }

        public T get(Object instance) {
            try {
                return (T) member.get(instance);
            } catch (Throwable e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        public T get() {
            return get(null);
        }

        public void set(Object instance, Object value) {
            try {
                member.set(instance, value);
            } catch (Throwable e) {
                Log.e(TAG, e.getMessage());
            }
        }

        public void set(Object value) {
            set(null, value);
        }

        public Class<?> getType() {
            return member.getType();
        }
    }

    public static class ConstructorWrapper<T> extends MemberWrapper<Constructor<T>> {
        ConstructorWrapper(Constructor<T> constructor) {
            super(constructor);
        }

        public T newInstance(Object... args) {
            try {
                return member.newInstance(args);
            } catch (Throwable e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }
    }
}