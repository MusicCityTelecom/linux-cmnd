/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import java.lang.reflect.Array;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.wrappers.Wrapper;

public class ParameterTypes {
    protected volatile Class[] nativeParamTypes;
    protected volatile CachedClass[] parameterTypes;
    protected boolean isVargsMethod;

    public ParameterTypes() {
    }

    public ParameterTypes(Class[] pt) {
        this.nativeParamTypes = pt;
    }

    public ParameterTypes(String[] pt) {
        this.nativeParamTypes = new Class[pt.length];
        for (int i = 0; i != pt.length; ++i) {
            try {
                this.nativeParamTypes[i] = Class.forName(pt[i]);
                continue;
            }
            catch (ClassNotFoundException e) {
                NoClassDefFoundError err = new NoClassDefFoundError();
                err.initCause(e);
                throw err;
            }
        }
    }

    public ParameterTypes(CachedClass[] parameterTypes) {
        this.setParametersTypes(parameterTypes);
    }

    protected final void setParametersTypes(CachedClass[] pt) {
        this.parameterTypes = pt;
        this.isVargsMethod = pt.length > 0 && pt[pt.length - 1].isArray;
    }

    public CachedClass[] getParameterTypes() {
        if (this.parameterTypes == null) {
            this.getParametersTypes0();
        }
        return this.parameterTypes;
    }

    private synchronized void getParametersTypes0() {
        Class[] npt;
        if (this.parameterTypes != null) {
            return;
        }
        Class[] classArray = npt = this.nativeParamTypes == null ? this.getPT() : this.nativeParamTypes;
        if (npt.length == 0) {
            this.nativeParamTypes = MetaClassHelper.EMPTY_TYPE_ARRAY;
            this.setParametersTypes(CachedClass.EMPTY_ARRAY);
        } else {
            CachedClass[] pt = new CachedClass[npt.length];
            for (int i = 0; i != npt.length; ++i) {
                pt[i] = ReflectionCache.getCachedClass(npt[i]);
            }
            this.nativeParamTypes = npt;
            this.setParametersTypes(pt);
        }
    }

    public Class[] getNativeParameterTypes() {
        if (this.nativeParamTypes == null) {
            this.getNativeParameterTypes0();
        }
        return this.nativeParamTypes;
    }

    private synchronized void getNativeParameterTypes0() {
        Class[] npt;
        if (this.nativeParamTypes != null) {
            return;
        }
        if (this.parameterTypes != null) {
            npt = new Class[this.parameterTypes.length];
            for (int i = 0; i != this.parameterTypes.length; ++i) {
                npt[i] = this.parameterTypes[i].getTheClass();
            }
        } else {
            npt = this.getPT();
        }
        this.nativeParamTypes = npt;
    }

    protected Class[] getPT() {
        throw new UnsupportedOperationException(this.getClass().getName());
    }

    public boolean isVargsMethod() {
        return this.isVargsMethod;
    }

    public boolean isVargsMethod(Object[] arguments) {
        if (!this.isVargsMethod) {
            return false;
        }
        int lenMinus1 = this.parameterTypes.length - 1;
        if (lenMinus1 == arguments.length) {
            return true;
        }
        if (lenMinus1 > arguments.length) {
            return false;
        }
        if (arguments.length > this.parameterTypes.length) {
            return true;
        }
        Object last = arguments[arguments.length - 1];
        if (last == null) {
            return true;
        }
        Class<?> clazz = last.getClass();
        return !clazz.equals(this.parameterTypes[lenMinus1].getTheClass());
    }

    public final Object[] coerceArgumentsToClasses(Object[] argumentArray) {
        argumentArray = this.correctArguments(argumentArray);
        CachedClass[] pt = this.parameterTypes;
        int len = argumentArray.length;
        for (int i = 0; i < len; ++i) {
            Object argument = argumentArray[i];
            if (argument == null) continue;
            argumentArray[i] = pt[i].coerceArgument(argument);
        }
        return argumentArray;
    }

    public Object[] correctArguments(Object[] argumentArray) {
        if (argumentArray == null) {
            return MetaClassHelper.EMPTY_ARRAY;
        }
        CachedClass[] pt = this.getParameterTypes();
        if (pt.length == 1 && argumentArray.length == 0) {
            if (this.isVargsMethod) {
                return new Object[]{Array.newInstance(pt[0].getTheClass().getComponentType(), 0)};
            }
            return MetaClassHelper.ARRAY_WITH_NULL;
        }
        if (this.isVargsMethod && this.isVargsMethod(argumentArray)) {
            return ParameterTypes.fitToVargs(argumentArray, pt);
        }
        return argumentArray;
    }

    private static Object[] fitToVargs(Object[] argumentArrayOrig, CachedClass[] paramTypes) {
        Class<?> vargsClassOrig = paramTypes[paramTypes.length - 1].getTheClass().getComponentType();
        Class vargsClass = ReflectionCache.autoboxType(vargsClassOrig);
        Object[] argumentArray = (Object[])argumentArrayOrig.clone();
        MetaClassHelper.unwrap(argumentArray);
        if (argumentArray.length == paramTypes.length - 1) {
            Object vargs;
            Object[] newArgs = new Object[paramTypes.length];
            System.arraycopy(argumentArray, 0, newArgs, 0, argumentArray.length);
            newArgs[newArgs.length - 1] = vargs = Array.newInstance(vargsClass, 0);
            return newArgs;
        }
        if (argumentArray.length == paramTypes.length) {
            Object lastArgument = argumentArray[argumentArray.length - 1];
            if (lastArgument != null && !lastArgument.getClass().isArray()) {
                Object wrapped = ParameterTypes.makeCommonArray(argumentArray, paramTypes.length - 1, vargsClass);
                Object[] newArgs = new Object[paramTypes.length];
                System.arraycopy(argumentArray, 0, newArgs, 0, paramTypes.length - 1);
                newArgs[newArgs.length - 1] = wrapped;
                return newArgs;
            }
            return argumentArray;
        }
        if (argumentArray.length > paramTypes.length) {
            Object vargs;
            Object[] newArgs = new Object[paramTypes.length];
            System.arraycopy(argumentArray, 0, newArgs, 0, paramTypes.length - 1);
            newArgs[newArgs.length - 1] = vargs = ParameterTypes.makeCommonArray(argumentArray, paramTypes.length - 1, vargsClass);
            return newArgs;
        }
        throw new GroovyBugError("trying to call a vargs method without enough arguments");
    }

    private static Object makeCommonArray(Object[] arguments, int offset, Class baseClass) {
        Object[] result = (Object[])Array.newInstance(baseClass, arguments.length - offset);
        for (int i = offset; i < arguments.length; ++i) {
            Object v = arguments[i];
            result[i - offset] = v = DefaultTypeTransformation.castToType(v, baseClass);
        }
        return result;
    }

    public boolean isValidMethod(Class[] argumentTypes) {
        if (argumentTypes == null) {
            return true;
        }
        CachedClass[] pt = this.getParameterTypes();
        int nArguments = argumentTypes.length;
        int nParameters = pt.length;
        int nthParameter = nParameters - 1;
        if (this.isVargsMethod && nArguments >= nthParameter) {
            return ParameterTypes.isValidVargsMethod(argumentTypes, pt, nthParameter);
        }
        if (nArguments == nParameters) {
            return ParameterTypes.isValidExactMethod(argumentTypes, pt);
        }
        return nArguments == 0 && nParameters == 1 && !pt[0].isPrimitive;
    }

    private static boolean isValidExactMethod(Class[] arguments, CachedClass[] pt) {
        int size = pt.length;
        for (int i = 0; i < size; ++i) {
            if (pt[i].isAssignableFrom(arguments[i])) continue;
            return false;
        }
        return true;
    }

    public boolean isValidExactMethod(Object[] args) {
        this.getParametersTypes0();
        int size = args.length;
        if (size != this.parameterTypes.length) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (args[i] == null || this.parameterTypes[i].isAssignableFrom(args[i].getClass())) continue;
            return false;
        }
        return true;
    }

    public boolean isValidExactMethod(Class[] args) {
        this.getParametersTypes0();
        int size = args.length;
        if (size != this.parameterTypes.length) {
            return false;
        }
        for (int i = 0; i < size; ++i) {
            if (args[i] == null || this.parameterTypes[i].isAssignableFrom(args[i])) continue;
            return false;
        }
        return true;
    }

    private static boolean isValidVargsMethod(Class[] argumentTypes, CachedClass[] parameterTypes, int nthParameter) {
        Class argumentType;
        for (int i = 0; i < nthParameter; ++i) {
            if (parameterTypes[i].isAssignableFrom(argumentTypes[i])) continue;
            return false;
        }
        CachedClass arrayType = parameterTypes[nthParameter];
        CachedClass componentType = ReflectionCache.getCachedClass(arrayType.getTheClass().getComponentType());
        if (argumentTypes.length == parameterTypes.length && (arrayType.isAssignableFrom(argumentType = argumentTypes[nthParameter]) || argumentType.isArray() && componentType.isAssignableFrom(argumentType.getComponentType()))) {
            return true;
        }
        for (int i = nthParameter; i < argumentTypes.length; ++i) {
            if (componentType.isAssignableFrom(argumentTypes[i])) continue;
            return false;
        }
        return true;
    }

    public boolean isValidMethod(Object[] arguments) {
        if (arguments == null) {
            return true;
        }
        CachedClass[] parameterTypes = this.getParameterTypes();
        int nArguments = arguments.length;
        int nParameters = parameterTypes.length;
        int nthParameter = nParameters - 1;
        if (nParameters > 0 && parameterTypes[nthParameter].isArray && nArguments >= nthParameter) {
            Class argumentType;
            for (int i = 0; i < nthParameter; ++i) {
                if (parameterTypes[i].isAssignableFrom(ParameterTypes.getArgClass(arguments[i]))) continue;
                return false;
            }
            CachedClass arrayType = parameterTypes[nthParameter];
            CachedClass componentType = ReflectionCache.getCachedClass(arrayType.getTheClass().getComponentType());
            if (nArguments == parameterTypes.length && (arrayType.isAssignableFrom(argumentType = ParameterTypes.getArgClass(arguments[nthParameter])) || argumentType.isArray() && componentType.isAssignableFrom(argumentType.getComponentType()))) {
                return true;
            }
            for (int i = nthParameter; i < nArguments; ++i) {
                if (componentType.isAssignableFrom(ParameterTypes.getArgClass(arguments[i]))) continue;
                return false;
            }
            return true;
        }
        if (nArguments == nParameters) {
            for (int i = 0; i < nArguments; ++i) {
                if (parameterTypes[i].isAssignableFrom(ParameterTypes.getArgClass(arguments[i]))) continue;
                return false;
            }
            return true;
        }
        return nArguments == 0 && nParameters == 1 && !parameterTypes[0].isPrimitive;
    }

    private static Class getArgClass(Object arg) {
        Class cls = arg == null ? null : (arg instanceof Wrapper ? ((Wrapper)arg).getType() : arg.getClass());
        return cls;
    }
}

