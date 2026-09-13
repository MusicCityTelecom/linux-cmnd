/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime;

import groovy.io.GroovyPrintWriter;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.DelegatingMetaClass;
import groovy.lang.EmptyRange;
import groovy.lang.ExpandoMetaClass;
import groovy.lang.GString;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovySystem;
import groovy.lang.IntRange;
import groovy.lang.ListWithDefault;
import groovy.lang.MapWithDefault;
import groovy.lang.MetaClass;
import groovy.lang.MetaClassImpl;
import groovy.lang.MetaClassRegistry;
import groovy.lang.MetaMethod;
import groovy.lang.MetaProperty;
import groovy.lang.MissingPropertyException;
import groovy.lang.ObjectRange;
import groovy.lang.PropertyValue;
import groovy.lang.Range;
import groovy.lang.SpreadMap;
import groovy.lang.Tuple2;
import groovy.lang.groovydoc.Groovydoc;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FirstParam;
import groovy.transform.stc.FromString;
import groovy.transform.stc.MapEntryOrKeyValue;
import groovy.transform.stc.SimpleType;
import groovy.util.BufferedIterator;
import groovy.util.ClosureComparator;
import groovy.util.GroovyCollections;
import groovy.util.MapEntry;
import groovy.util.OrderBy;
import groovy.util.PermutationGenerator;
import groovy.util.ProxyGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.constant.Constable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.text.MessageFormat;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import org.apache.groovy.io.StringBuilderWriter;
import org.apache.groovy.util.ReversedList;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.reflection.MixinInMetaClass;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.codehaus.groovy.reflection.ReflectionUtils;
import org.codehaus.groovy.reflection.stdclasses.CachedSAMClass;
import org.codehaus.groovy.runtime.ConvertedClosure;
import org.codehaus.groovy.runtime.ConvertedMap;
import org.codehaus.groovy.runtime.DefaultGroovyMethodsSupport;
import org.codehaus.groovy.runtime.EncodingGroovyMethods;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.GroovyCategorySupport;
import org.codehaus.groovy.runtime.HandleMetaClass;
import org.codehaus.groovy.runtime.IOGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.NullObject;
import org.codehaus.groovy.runtime.NumberAwareComparator;
import org.codehaus.groovy.runtime.ProcessGroovyMethods;
import org.codehaus.groovy.runtime.RangeInfo;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.codehaus.groovy.runtime.ReverseListIterator;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.SocketGroovyMethods;
import org.codehaus.groovy.runtime.StreamGroovyMethods;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.callsite.BooleanClosureWrapper;
import org.codehaus.groovy.runtime.callsite.BooleanReturningMethodInvoker;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberDiv;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberMinus;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberMultiply;
import org.codehaus.groovy.runtime.dgmimpl.NumberNumberPlus;
import org.codehaus.groovy.runtime.dgmimpl.arrays.BooleanArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.BooleanArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.ByteArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.ByteArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.CharacterArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.CharacterArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.DoubleArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.DoubleArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.FloatArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.FloatArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.LongArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.LongArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.ObjectArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.ObjectArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.ShortArrayGetAtMetaMethod;
import org.codehaus.groovy.runtime.dgmimpl.arrays.ShortArrayPutAtMetaMethod;
import org.codehaus.groovy.runtime.metaclass.MetaClassRegistryImpl;
import org.codehaus.groovy.runtime.metaclass.MissingPropertyExceptionNoStack;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.GroovyCastException;
import org.codehaus.groovy.runtime.typehandling.NumberMath;
import org.codehaus.groovy.tools.RootLoader;
import org.codehaus.groovy.transform.trait.Traits;
import org.codehaus.groovy.util.ArrayIterable;
import org.codehaus.groovy.util.ArrayIterator;
import org.codehaus.groovy.util.BooleanArrayIterator;
import org.codehaus.groovy.util.ByteArrayIterator;
import org.codehaus.groovy.util.CharArrayIterator;
import org.codehaus.groovy.util.DoubleArrayIterable;
import org.codehaus.groovy.util.DoubleArrayIterator;
import org.codehaus.groovy.util.FloatArrayIterator;
import org.codehaus.groovy.util.IntArrayIterable;
import org.codehaus.groovy.util.IntArrayIterator;
import org.codehaus.groovy.util.IteratorBufferedIterator;
import org.codehaus.groovy.util.ListBufferedIterator;
import org.codehaus.groovy.util.LongArrayIterable;
import org.codehaus.groovy.util.LongArrayIterator;
import org.codehaus.groovy.util.ShortArrayIterator;

public class DefaultGroovyMethods
extends DefaultGroovyMethodsSupport {
    private static final Integer ONE = 1;
    private static final BigInteger BI_INT_MAX = BigInteger.valueOf(Integer.MAX_VALUE);
    private static final BigInteger BI_INT_MIN = BigInteger.valueOf(Integer.MIN_VALUE);
    private static final BigInteger BI_LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
    private static final BigInteger BI_LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);
    public static final Class[] ADDITIONAL_CLASSES = new Class[]{NumberNumberPlus.class, NumberNumberMultiply.class, NumberNumberMinus.class, NumberNumberDiv.class, ObjectArrayGetAtMetaMethod.class, ObjectArrayPutAtMetaMethod.class, BooleanArrayGetAtMetaMethod.class, BooleanArrayPutAtMetaMethod.class, ByteArrayGetAtMetaMethod.class, ByteArrayPutAtMetaMethod.class, CharacterArrayGetAtMetaMethod.class, CharacterArrayPutAtMetaMethod.class, ShortArrayGetAtMetaMethod.class, ShortArrayPutAtMetaMethod.class, IntegerArrayGetAtMetaMethod.class, IntegerArrayPutAtMetaMethod.class, LongArrayGetAtMetaMethod.class, LongArrayPutAtMetaMethod.class, FloatArrayGetAtMetaMethod.class, FloatArrayPutAtMetaMethod.class, DoubleArrayGetAtMetaMethod.class, DoubleArrayPutAtMetaMethod.class};
    public static final Class[] DGM_LIKE_CLASSES = new Class[]{DefaultGroovyMethods.class, EncodingGroovyMethods.class, IOGroovyMethods.class, ProcessGroovyMethods.class, ResourceGroovyMethods.class, SocketGroovyMethods.class, StreamGroovyMethods.class, StringGroovyMethods.class};
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final NumberAwareComparator<Comparable> COMPARABLE_NUMBER_AWARE_COMPARATOR = new NumberAwareComparator();
    private static Random r;

    public static boolean is(Object self, Object other) {
        return self == other;
    }

    public static <T, U> T identity(@DelegatesTo.Target(value="self") U self, @DelegatesTo(value=DelegatesTo.Target.class, target="self", strategy=1) @ClosureParams(value=FirstParam.class) Closure<T> closure) {
        return DefaultGroovyMethods.with(self, closure);
    }

    public static <T, U> T with(@DelegatesTo.Target(value="self") U self, @DelegatesTo(value=DelegatesTo.Target.class, target="self", strategy=1) @ClosureParams(value=FirstParam.class) Closure<T> closure) {
        return DefaultGroovyMethods.with(self, false, closure);
    }

    public static <T, U extends T, V extends T> T with(@DelegatesTo.Target(value="self") U self, boolean returning, @DelegatesTo(value=DelegatesTo.Target.class, target="self", strategy=1) @ClosureParams(value=FirstParam.class) Closure<T> closure) {
        Closure clonedClosure = (Closure)closure.clone();
        clonedClosure.setResolveStrategy(1);
        clonedClosure.setDelegate(self);
        Object result = clonedClosure.call((Object)self);
        return (T)(returning ? self : result);
    }

    public static <T, U> U tap(@DelegatesTo.Target(value="self") U self, @DelegatesTo(value=DelegatesTo.Target.class, target="self", strategy=1) @ClosureParams(value=FirstParam.class) Closure<T> closure) {
        return (U)DefaultGroovyMethods.with(self, true, closure);
    }

    public static Object getAt(Object self, String property) {
        return InvokerHelper.getProperty(self, property);
    }

    public static void putAt(Object self, String property, Object newValue) {
        InvokerHelper.setProperty(self, property, newValue);
    }

    public static String dump(Object self) {
        Class<?> klass;
        if (self == null) {
            return "null";
        }
        StringBuilder buffer = new StringBuilder("<");
        buffer.append(klass.getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(self.hashCode()));
        boolean groovyObject = self instanceof GroovyObject;
        for (klass = self.getClass(); klass != null; klass = klass.getSuperclass()) {
            for (Field field : klass.getDeclaredFields()) {
                if ((field.getModifiers() & 8) != 0 || groovyObject && field.getName().equals("metaClass")) continue;
                DefaultGroovyMethods.trySetAccessible(field);
                buffer.append(" ");
                buffer.append(field.getName());
                buffer.append("=");
                try {
                    buffer.append(FormatHelper.toString(field.get(self)));
                }
                catch (IllegalAccessException e) {
                    buffer.append("inaccessible");
                }
                catch (Exception e) {
                    buffer.append(e);
                }
            }
        }
        buffer.append(">");
        return buffer.toString();
    }

    private static void trySetAccessible(Field field) {
        AccessController.doPrivileged(() -> {
            ReflectionUtils.trySetAccessible(field);
            return null;
        });
    }

    public static List<PropertyValue> getMetaPropertyValues(Object self) {
        MetaClass metaClass = InvokerHelper.getMetaClass(self);
        List<MetaProperty> mps = metaClass.getProperties();
        ArrayList<PropertyValue> props = new ArrayList<PropertyValue>(mps.size());
        for (MetaProperty mp : mps) {
            props.add(new PropertyValue(self, mp));
        }
        return props;
    }

    public static Map getProperties(Object self) {
        List<PropertyValue> metaProps = DefaultGroovyMethods.getMetaPropertyValues(self);
        LinkedHashMap<String, Object> props = new LinkedHashMap<String, Object>(metaProps.size());
        for (PropertyValue mp : metaProps) {
            try {
                props.put(mp.getName(), mp.getValue());
            }
            catch (Exception e) {
                Logger.getLogger(DefaultGroovyMethods.class.getName()).throwing(self.getClass().getName(), "getProperty(" + mp.getName() + ")", e);
            }
        }
        return props;
    }

    public static <T> T use(Object self, Class categoryClass, Closure<T> closure) {
        return GroovyCategorySupport.use(categoryClass, closure);
    }

    public static void mixin(MetaClass self, List<Class> categoryClasses) {
        MixinInMetaClass.mixinClassesToMetaClass(self, categoryClasses);
    }

    public static void mixin(Class self, List<Class> categoryClasses) {
        DefaultGroovyMethods.mixin(DefaultGroovyMethods.getMetaClass(self), categoryClasses);
    }

    public static void mixin(Class self, Class categoryClass) {
        DefaultGroovyMethods.mixin(DefaultGroovyMethods.getMetaClass(self), Collections.singletonList(categoryClass));
    }

    public static void mixin(Class self, Class[] categoryClass) {
        DefaultGroovyMethods.mixin(DefaultGroovyMethods.getMetaClass(self), Arrays.asList(categoryClass));
    }

    public static void mixin(MetaClass self, Class categoryClass) {
        DefaultGroovyMethods.mixin(self, Collections.singletonList(categoryClass));
    }

    public static void mixin(MetaClass self, Class[] categoryClass) {
        DefaultGroovyMethods.mixin(self, Arrays.asList(categoryClass));
    }

    public static URL getLocation(Class self) {
        CodeSource codeSource = self.getProtectionDomain().getCodeSource();
        return null == codeSource ? null : codeSource.getLocation();
    }

    public static void addShutdownHook(Object self, Closure closure) {
        Runtime.getRuntime().addShutdownHook(new Thread(closure));
    }

    public static <T> T use(Object self, List<Class> categoryClassList, Closure<T> closure) {
        return GroovyCategorySupport.use(categoryClassList, closure);
    }

    public static Object use(Object self, Object[] array) {
        Closure closure;
        if (array.length < 2) {
            throw new IllegalArgumentException("Expecting at least 2 arguments, a category class and a Closure");
        }
        try {
            closure = (Closure)array[array.length - 1];
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException("Expecting a Closure to be the last argument");
        }
        ArrayList<Class> list = new ArrayList<Class>(array.length - 1);
        for (int i = 0; i < array.length - 1; ++i) {
            Class categoryClass;
            try {
                categoryClass = (Class)array[i];
            }
            catch (ClassCastException e) {
                throw new IllegalArgumentException("Expecting a Category Class for argument " + i);
            }
            list.add(categoryClass);
        }
        return GroovyCategorySupport.use(list, closure);
    }

    private static Object getClosureOwner(Closure<?> cls) {
        Object owner = cls.getOwner();
        while (owner instanceof GeneratedClosure) {
            owner = ((Closure)owner).getOwner();
        }
        return owner;
    }

    public static String inspect(Object self) {
        return FormatHelper.inspect(self);
    }

    public static Object invokeMethod(Object object, String method, Object arguments) {
        return InvokerHelper.invokeMethod(object, method, arguments);
    }

    public static void print(Object self, Object value) {
        if (self instanceof Writer) {
            try {
                ((Writer)self).write(FormatHelper.toString(value));
            }
            catch (IOException iOException) {}
        } else {
            System.out.print(FormatHelper.toString(value));
        }
    }

    public static void print(PrintWriter self, Object value) {
        self.print(FormatHelper.toString(value));
    }

    public static void print(PrintStream self, Object value) {
        self.print(FormatHelper.toString(value));
    }

    public static void print(Closure self, Object value) {
        Object owner = DefaultGroovyMethods.getClosureOwner(self);
        InvokerHelper.invokeMethod(owner, "print", new Object[]{value});
    }

    public static void print(Object self, PrintWriter out) {
        if (out == null) {
            out = new PrintWriter(System.out);
        }
        out.print(FormatHelper.toString(self));
    }

    public static void println(Object self) {
        if (self instanceof Writer) {
            GroovyPrintWriter pw = new GroovyPrintWriter((Writer)self);
            pw.println();
        } else {
            System.out.println();
        }
    }

    public static void println(Closure self) {
        Object owner = DefaultGroovyMethods.getClosureOwner(self);
        InvokerHelper.invokeMethod(owner, "println", EMPTY_OBJECT_ARRAY);
    }

    public static void println(Object self, Object value) {
        if (self instanceof Writer) {
            GroovyPrintWriter pw = new GroovyPrintWriter((Writer)self);
            ((PrintWriter)pw).println(value);
        } else {
            System.out.println(FormatHelper.toString(value));
        }
    }

    public static void println(PrintWriter self, Object value) {
        self.println(FormatHelper.toString(value));
    }

    public static void println(PrintStream self, Object value) {
        self.println(FormatHelper.toString(value));
    }

    public static void println(Closure self, Object value) {
        Object owner = DefaultGroovyMethods.getClosureOwner(self);
        InvokerHelper.invokeMethod(owner, "println", new Object[]{value});
    }

    public static void println(Object self, PrintWriter out) {
        if (out == null) {
            out = new PrintWriter(System.out);
        }
        out.println(FormatHelper.toString(self));
    }

    public static void printf(Object self, String format, Object[] values) {
        if (self instanceof PrintStream) {
            ((PrintStream)self).printf(format, values);
        } else {
            System.out.printf(format, values);
        }
    }

    public static void printf(Closure self, String format, Object[] values) {
        Object owner = DefaultGroovyMethods.getClosureOwner(self);
        Object[] newValues = new Object[values.length + 1];
        newValues[0] = format;
        System.arraycopy(values, 0, newValues, 1, values.length);
        InvokerHelper.invokeMethod(owner, "printf", newValues);
    }

    public static void printf(Closure self, String format, Object value) {
        Object owner = DefaultGroovyMethods.getClosureOwner(self);
        Object[] newValues = new Object[]{format, value};
        InvokerHelper.invokeMethod(owner, "printf", newValues);
    }

    public static void printf(Object self, String format, Object arg) {
        if (self instanceof PrintStream) {
            DefaultGroovyMethods.printf((PrintStream)self, format, arg);
        } else if (self instanceof Writer) {
            DefaultGroovyMethods.printf((Writer)self, format, arg);
        } else {
            DefaultGroovyMethods.printf(System.out, format, arg);
        }
    }

    private static void printf(PrintStream self, String format, Object arg) {
        self.print(DefaultGroovyMethods.sprintf((Object)self, format, arg));
    }

    private static void printf(Writer self, String format, Object arg) {
        try {
            self.write(DefaultGroovyMethods.sprintf((Object)self, format, arg));
        }
        catch (IOException e) {
            DefaultGroovyMethods.printf(System.out, format, arg);
        }
    }

    public static String sprintf(Object self, String format, Object[] values) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        out.printf(format, values);
        return outputStream.toString();
    }

    public static String sprintf(Object self, String format, Object arg) {
        Constable[] ans;
        String elemType;
        if (arg instanceof Object[]) {
            return DefaultGroovyMethods.sprintf(self, format, (Object[])arg);
        }
        if (arg instanceof List) {
            return DefaultGroovyMethods.sprintf(self, format, ((List)arg).toArray());
        }
        if (!arg.getClass().isArray()) {
            Object[] o = (Object[])Array.newInstance(arg.getClass(), 1);
            o[0] = arg;
            return DefaultGroovyMethods.sprintf(self, format, o);
        }
        switch (elemType = arg.getClass().getName()) {
            case "[I": {
                int[] ia = (int[])arg;
                ans = new Integer[ia.length];
                for (int i = 0; i < ia.length; ++i) {
                    ans[i] = Integer.valueOf(ia[i]);
                }
                break;
            }
            case "[C": {
                char[] ca = (char[])arg;
                ans = new Character[ca.length];
                for (int i = 0; i < ca.length; ++i) {
                    ans[i] = Character.valueOf(ca[i]);
                }
                break;
            }
            case "[Z": {
                boolean[] ba = (boolean[])arg;
                ans = new Boolean[ba.length];
                for (int i = 0; i < ba.length; ++i) {
                    ans[i] = Boolean.valueOf(ba[i]);
                }
                break;
            }
            case "[B": {
                byte[] ba = (byte[])arg;
                ans = new Byte[ba.length];
                for (int i = 0; i < ba.length; ++i) {
                    ans[i] = Byte.valueOf(ba[i]);
                }
                break;
            }
            case "[S": {
                short[] sa = (short[])arg;
                ans = new Short[sa.length];
                for (int i = 0; i < sa.length; ++i) {
                    ans[i] = Short.valueOf(sa[i]);
                }
                break;
            }
            case "[F": {
                float[] fa = (float[])arg;
                ans = new Float[fa.length];
                for (int i = 0; i < fa.length; ++i) {
                    ans[i] = Float.valueOf(fa[i]);
                }
                break;
            }
            case "[J": {
                long[] la = (long[])arg;
                ans = new Long[la.length];
                for (int i = 0; i < la.length; ++i) {
                    ans[i] = Long.valueOf(la[i]);
                }
                break;
            }
            case "[D": {
                double[] da = (double[])arg;
                ans = new Double[da.length];
                for (int i = 0; i < da.length; ++i) {
                    ans[i] = Double.valueOf(da[i]);
                }
                break;
            }
            default: {
                throw new RuntimeException("sprintf(String," + arg + ")");
            }
        }
        return DefaultGroovyMethods.sprintf(self, format, ans);
    }

    public static boolean isCase(Object caseValue, Object switchValue) {
        if (caseValue.getClass().isArray()) {
            return DefaultGroovyMethods.isCase(DefaultTypeTransformation.asCollection(caseValue), switchValue);
        }
        return caseValue.equals(switchValue);
    }

    public static boolean isCase(Class caseValue, Object switchValue) {
        if (switchValue instanceof Class) {
            Class val = (Class)switchValue;
            return caseValue.isAssignableFrom(val);
        }
        return caseValue.isInstance(switchValue);
    }

    public static boolean isCase(Collection caseValue, Object switchValue) {
        return caseValue.contains(switchValue);
    }

    public static boolean isCase(Map caseValue, Object switchValue) {
        return DefaultTypeTransformation.castToBoolean(caseValue.get(switchValue));
    }

    public static boolean isCase(Number caseValue, Number switchValue) {
        return NumberMath.compareTo(caseValue, switchValue) == 0;
    }

    public static boolean isNotCase(Number caseValue, Number switchValue) {
        return !DefaultGroovyMethods.isCase(caseValue, switchValue);
    }

    public static boolean isNotCase(Object caseValue, Object switchValue) {
        return !DefaultGroovyMethods.isCase(caseValue, switchValue);
    }

    public static boolean isNotCase(Class<?> caseValue, Object switchValue) {
        return !DefaultGroovyMethods.isCase(caseValue, switchValue);
    }

    public static boolean isNotCase(Closure<?> caseValue, Object switchValue) {
        return !caseValue.isCase(switchValue);
    }

    public static boolean isNotCase(Collection<?> caseValue, Object switchValue) {
        return !DefaultGroovyMethods.isCase(caseValue, switchValue);
    }

    public static boolean isNotCase(Map<?, ?> caseValue, Object switchValue) {
        return !DefaultGroovyMethods.isCase(caseValue, switchValue);
    }

    public static <T> Iterator<T> unique(Iterator<T> self) {
        return DefaultGroovyMethods.uniqueItems(new IteratorIterableAdapter(self)).listIterator();
    }

    public static <T> Collection<T> unique(Collection<T> self) {
        return DefaultGroovyMethods.unique(self, true);
    }

    public static <T> List<T> unique(List<T> self) {
        return (List)DefaultGroovyMethods.unique(self, true);
    }

    public static <T> Collection<T> unique(Collection<T> self, boolean mutate) {
        List<T> answer = DefaultGroovyMethods.uniqueItems(self);
        if (mutate) {
            self.clear();
            self.addAll(answer);
        }
        return mutate ? self : answer;
    }

    private static <T> List<T> uniqueItems(Iterable<T> self) {
        ArrayList<T> answer = new ArrayList<T>();
        for (T t : self) {
            boolean duplicated = false;
            for (Object t2 : answer) {
                if (!DefaultGroovyMethods.coercedEquals(t, t2)) continue;
                duplicated = true;
                break;
            }
            if (duplicated) continue;
            answer.add(t);
        }
        return answer;
    }

    public static <T> List<T> unique(List<T> self, boolean mutate) {
        return (List)DefaultGroovyMethods.unique(self, mutate);
    }

    public static int numberAwareCompareTo(Comparable self, Comparable other) {
        return COMPARABLE_NUMBER_AWARE_COMPARATOR.compare(self, other);
    }

    public static <T> Iterator<T> unique(Iterator<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition) {
        Serializable comparator = condition.getMaximumNumberOfParameters() == 1 ? new OrderBy(condition, true) : new ClosureComparator(condition);
        return DefaultGroovyMethods.unique(self, comparator);
    }

    public static <T> Collection<T> unique(Collection<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.unique(self, true, closure);
    }

    public static <T> List<T> unique(List<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return (List)DefaultGroovyMethods.unique(self, true, closure);
    }

    public static <T> Collection<T> unique(Collection<T> self, boolean mutate, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        int params = closure.getMaximumNumberOfParameters();
        self = params == 1 ? DefaultGroovyMethods.unique(self, mutate, new OrderBy(closure, true)) : DefaultGroovyMethods.unique(self, mutate, new ClosureComparator(closure));
        return self;
    }

    public static <T> List<T> unique(List<T> self, boolean mutate, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return (List)DefaultGroovyMethods.unique(self, mutate, closure);
    }

    public static <T> Iterator<T> unique(Iterator<T> self, Comparator<T> comparator) {
        return DefaultGroovyMethods.uniqueItems(new IteratorIterableAdapter(self), comparator).listIterator();
    }

    public static <T> Collection<T> unique(Collection<T> self, Comparator<T> comparator) {
        return DefaultGroovyMethods.unique(self, true, comparator);
    }

    public static <T> List<T> unique(List<T> self, Comparator<T> comparator) {
        return (List)DefaultGroovyMethods.unique(self, true, comparator);
    }

    public static <T> Collection<T> unique(Collection<T> self, boolean mutate, Comparator<T> comparator) {
        List<T> answer = DefaultGroovyMethods.uniqueItems(self, comparator);
        if (mutate) {
            self.clear();
            self.addAll(answer);
        }
        return mutate ? self : answer;
    }

    private static <T> List<T> uniqueItems(Iterable<T> self, Comparator<T> comparator) {
        ArrayList<T> answer = new ArrayList<T>();
        for (T t : self) {
            boolean duplicated = false;
            for (Object t2 : answer) {
                if (comparator.compare(t, t2) != 0) continue;
                duplicated = true;
                break;
            }
            if (duplicated) continue;
            answer.add(t);
        }
        return answer;
    }

    public static <T> List<T> unique(List<T> self, boolean mutate, Comparator<T> comparator) {
        return (List)DefaultGroovyMethods.unique(self, mutate, comparator);
    }

    public static <T> Iterator<T> toUnique(Iterator<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition) {
        return DefaultGroovyMethods.toUnique(self, condition.getMaximumNumberOfParameters() == 1 ? new OrderBy(condition, true) : new ClosureComparator(condition));
    }

    public static <T> Iterator<T> toUnique(Iterator<T> self, Comparator<T> comparator) {
        return new ToUniqueIterator(self, comparator);
    }

    public static <T> Iterator<T> toUnique(Iterator<T> self) {
        return DefaultGroovyMethods.toUnique(self, (Comparator)null);
    }

    public static <T> Collection<T> toUnique(Iterable<T> self, Comparator<T> comparator) {
        Collection<T> result = DefaultGroovyMethods.createSimilarCollection(self);
        DefaultGroovyMethods.addAll(result, DefaultGroovyMethods.toUnique(self.iterator(), comparator));
        return result;
    }

    public static <T> List<T> toUnique(List<T> self, Comparator<T> comparator) {
        return (List)DefaultGroovyMethods.toUnique(self, comparator);
    }

    public static <T> Collection<T> toUnique(Iterable<T> self) {
        return DefaultGroovyMethods.toUnique(self, (Comparator)null);
    }

    public static <T> List<T> toUnique(List<T> self) {
        return DefaultGroovyMethods.toUnique(self, (Comparator)null);
    }

    public static <T> Collection<T> toUnique(Iterable<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition) {
        Serializable comparator = condition.getMaximumNumberOfParameters() == 1 ? new OrderBy(condition, true) : new ClosureComparator(condition);
        return DefaultGroovyMethods.toUnique(self, comparator);
    }

    public static <T> List<T> toUnique(List<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition) {
        return (List)DefaultGroovyMethods.toUnique(self, condition);
    }

    public static <T> T[] toUnique(T[] self, Comparator<T> comparator) {
        Collection<T> items = DefaultGroovyMethods.toUnique(new ArrayIterable<T>(self), comparator);
        return items.toArray(DefaultGroovyMethods.createSimilarArray(self, items.size()));
    }

    public static <T> T[] toUnique(T[] self) {
        return DefaultGroovyMethods.toUnique(self, (Comparator)null);
    }

    public static <T> T[] toUnique(T[] self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition) {
        Serializable comparator = condition.getMaximumNumberOfParameters() == 1 ? new OrderBy(condition, true) : new ClosureComparator(condition);
        return DefaultGroovyMethods.toUnique(self, comparator);
    }

    public static <T> T[] each(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        for (T item : self) {
            closure.call((Object)item);
        }
        return self;
    }

    public static <T> T each(T self, Closure closure) {
        DefaultGroovyMethods.each(InvokerHelper.asIterator(self), closure);
        return self;
    }

    public static <T> T[] eachWithIndex(T[] self, @ClosureParams(value=FromString.class, options={"T,Integer"}) Closure closure) {
        Object[] args = new Object[2];
        int counter = 0;
        for (T item : self) {
            args[0] = item;
            args[1] = counter++;
            closure.call(args);
        }
        return self;
    }

    public static <T> T eachWithIndex(T self, Closure closure) {
        Object[] args = new Object[2];
        int counter = 0;
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        while (iter.hasNext()) {
            args[0] = iter.next();
            args[1] = counter++;
            closure.call(args);
        }
        return self;
    }

    public static <T> Iterable<T> eachWithIndex(Iterable<T> self, @ClosureParams(value=FromString.class, options={"T,java.lang.Integer"}) Closure closure) {
        DefaultGroovyMethods.eachWithIndex(self.iterator(), closure);
        return self;
    }

    public static <T> Iterator<T> eachWithIndex(Iterator<T> self, @ClosureParams(value=FromString.class, options={"T,java.lang.Integer"}) Closure closure) {
        Object[] args = new Object[2];
        int counter = 0;
        while (self.hasNext()) {
            args[0] = self.next();
            args[1] = counter++;
            closure.call(args);
        }
        return self;
    }

    public static <T> Collection<T> eachWithIndex(Collection<T> self, @ClosureParams(value=FromString.class, options={"T,java.lang.Integer"}) Closure closure) {
        return (Collection)DefaultGroovyMethods.eachWithIndex(self, closure);
    }

    public static <T> List<T> eachWithIndex(List<T> self, @ClosureParams(value=FromString.class, options={"T,java.lang.Integer"}) Closure closure) {
        return (List)DefaultGroovyMethods.eachWithIndex(self, closure);
    }

    public static <T> Set<T> eachWithIndex(Set<T> self, @ClosureParams(value=FromString.class, options={"T,java.lang.Integer"}) Closure closure) {
        return (Set)DefaultGroovyMethods.eachWithIndex(self, closure);
    }

    public static <T> SortedSet<T> eachWithIndex(SortedSet<T> self, @ClosureParams(value=FromString.class, options={"T,java.lang.Integer"}) Closure closure) {
        return (SortedSet)DefaultGroovyMethods.eachWithIndex(self, closure);
    }

    public static <T> Iterable<T> each(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        DefaultGroovyMethods.each(self.iterator(), closure);
        return self;
    }

    public static <T> Iterator<T> each(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        while (self.hasNext()) {
            T arg = self.next();
            closure.call((Object)arg);
        }
        return self;
    }

    public static <T> Collection<T> each(Collection<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return (Collection)DefaultGroovyMethods.each(self, closure);
    }

    public static <T> List<T> each(List<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return (List)DefaultGroovyMethods.each(self, closure);
    }

    public static <T> Set<T> each(Set<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return (Set)DefaultGroovyMethods.each(self, closure);
    }

    public static <T> SortedSet<T> each(SortedSet<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return (SortedSet)DefaultGroovyMethods.each(self, closure);
    }

    public static <K, V> Map<K, V> each(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<?> closure) {
        for (Map.Entry<K, V> entry : self.entrySet()) {
            DefaultGroovyMethods.callClosureForMapEntry(closure, entry);
        }
        return self;
    }

    public static <K, V> Map<K, V> reverseEach(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<?> closure) {
        Iterator<Map.Entry<K, V>> entries = DefaultGroovyMethods.reverse(self.entrySet().iterator());
        while (entries.hasNext()) {
            DefaultGroovyMethods.callClosureForMapEntry(closure, entries.next());
        }
        return self;
    }

    public static <K, V> Map<K, V> eachWithIndex(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class, options={"index=true"}) Closure<?> closure) {
        int counter = 0;
        for (Map.Entry<K, V> entry : self.entrySet()) {
            DefaultGroovyMethods.callClosureForMapEntryAndCounter(closure, entry, counter++);
        }
        return self;
    }

    public static <T> List<T> reverseEach(List<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        DefaultGroovyMethods.each(new ReverseListIterator<T>(self), closure);
        return self;
    }

    public static <T> T[] reverseEach(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        DefaultGroovyMethods.each(new ReverseListIterator<T>(Arrays.asList(self)), closure);
        return self;
    }

    public static boolean every(Object self, Closure predicate) {
        return DefaultGroovyMethods.every(InvokerHelper.asIterator(self), predicate);
    }

    public static <T> boolean every(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure predicate) {
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(predicate);
        while (self.hasNext()) {
            if (bcw.call(self.next())) continue;
            return false;
        }
        return true;
    }

    public static <T> boolean every(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure predicate) {
        return DefaultGroovyMethods.every(new ArrayIterator<T>(self), predicate);
    }

    public static <T> boolean every(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure predicate) {
        return DefaultGroovyMethods.every(self.iterator(), predicate);
    }

    public static <K, V> boolean every(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure predicate) {
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(predicate);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            if (bcw.callForMap(entry)) continue;
            return false;
        }
        return true;
    }

    public static boolean every(Object self) {
        BooleanReturningMethodInvoker bmi = new BooleanReturningMethodInvoker();
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        while (iter.hasNext()) {
            if (bmi.convertToBoolean(iter.next())) continue;
            return false;
        }
        return true;
    }

    public static boolean any(Object self, Closure predicate) {
        return DefaultGroovyMethods.any(InvokerHelper.asIterator(self), predicate);
    }

    public static <T> boolean any(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure predicate) {
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(predicate);
        while (self.hasNext()) {
            if (!bcw.call(self.next())) continue;
            return true;
        }
        return false;
    }

    public static <T> boolean any(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure predicate) {
        return DefaultGroovyMethods.any(self.iterator(), predicate);
    }

    public static <T> boolean any(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure predicate) {
        return DefaultGroovyMethods.any(new ArrayIterator<T>(self), predicate);
    }

    public static <K, V> boolean any(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<?> predicate) {
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(predicate);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            if (!bcw.callForMap(entry)) continue;
            return true;
        }
        return false;
    }

    public static boolean any(Object self) {
        BooleanReturningMethodInvoker bmi = new BooleanReturningMethodInvoker();
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        while (iter.hasNext()) {
            if (!bmi.convertToBoolean(iter.next())) continue;
            return true;
        }
        return false;
    }

    public static Collection grep(Object self, Object filter) {
        Collection answer = DefaultGroovyMethods.createSimilarOrDefaultCollection(self);
        BooleanReturningMethodInvoker bmi = new BooleanReturningMethodInvoker("isCase");
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        while (iter.hasNext()) {
            Object object = iter.next();
            if (!bmi.invoke(filter, object)) continue;
            answer.add(object);
        }
        return answer;
    }

    public static <T> Collection<T> grep(Collection<T> self, Object filter) {
        Collection<T> answer = DefaultGroovyMethods.createSimilarCollection(self);
        BooleanReturningMethodInvoker bmi = new BooleanReturningMethodInvoker("isCase");
        for (T element : self) {
            if (!bmi.invoke(filter, element)) continue;
            answer.add(element);
        }
        return answer;
    }

    public static <T> List<T> grep(List<T> self, Object filter) {
        return (List)DefaultGroovyMethods.grep(self, filter);
    }

    public static <T> Set<T> grep(Set<T> self, Object filter) {
        return (Set)DefaultGroovyMethods.grep(self, filter);
    }

    public static <T> Collection<T> grep(T[] self, Object filter) {
        ArrayList<T> answer = new ArrayList<T>();
        BooleanReturningMethodInvoker bmi = new BooleanReturningMethodInvoker("isCase");
        for (T element : self) {
            if (!bmi.invoke(filter, element)) continue;
            answer.add(element);
        }
        return answer;
    }

    public static Collection grep(Object self) {
        return DefaultGroovyMethods.grep(self, (Object)Closure.IDENTITY);
    }

    public static <T> Collection<T> grep(Collection<T> self) {
        return DefaultGroovyMethods.grep(self, (Object)Closure.IDENTITY);
    }

    public static <T> List<T> grep(List<T> self) {
        return DefaultGroovyMethods.grep(self, (Object)Closure.IDENTITY);
    }

    public static <T> Set<T> grep(Set<T> self) {
        return DefaultGroovyMethods.grep(self, (Object)Closure.IDENTITY);
    }

    public static <T> Collection<T> grep(T[] self) {
        return DefaultGroovyMethods.grep(self, (Object)Closure.IDENTITY);
    }

    public static Number count(Iterator self, Object value) {
        long answer = 0L;
        while (self.hasNext()) {
            if (!DefaultTypeTransformation.compareEqual(self.next(), value)) continue;
            ++answer;
        }
        if (answer <= Integer.MAX_VALUE) {
            return (int)answer;
        }
        return answer;
    }

    public static <T> Number count(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        long answer = 0L;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(closure);
        while (self.hasNext()) {
            if (!bcw.call(self.next())) continue;
            ++answer;
        }
        if (answer <= Integer.MAX_VALUE) {
            return (int)answer;
        }
        return answer;
    }

    public static Number count(Iterable self, Object value) {
        return DefaultGroovyMethods.count(self.iterator(), value);
    }

    public static <T> Number count(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return DefaultGroovyMethods.count(self.iterator(), closure);
    }

    public static <K, V> Number count(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<?> closure) {
        long answer = 0L;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(closure);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            if (!bcw.callForMap(entry)) continue;
            ++answer;
        }
        if (answer <= Integer.MAX_VALUE) {
            return (int)answer;
        }
        return answer;
    }

    public static Number count(Object[] self, Object value) {
        return DefaultGroovyMethods.count(Arrays.asList(self), value);
    }

    public static <T> Number count(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        return DefaultGroovyMethods.count(Arrays.asList(self), closure);
    }

    public static Number count(int[] self, Object value) {
        return DefaultGroovyMethods.count(InvokerHelper.asIterator(self), value);
    }

    public static Number count(long[] self, Object value) {
        return DefaultGroovyMethods.count(InvokerHelper.asIterator(self), value);
    }

    public static Number count(short[] self, Object value) {
        return DefaultGroovyMethods.count(InvokerHelper.asIterator(self), value);
    }

    public static Number count(char[] self, Object value) {
        return DefaultGroovyMethods.count(InvokerHelper.asIterator(self), value);
    }

    public static Number count(boolean[] self, Object value) {
        return DefaultGroovyMethods.count(InvokerHelper.asIterator(self), value);
    }

    public static Number count(double[] self, Object value) {
        return DefaultGroovyMethods.count(InvokerHelper.asIterator(self), value);
    }

    public static Number count(float[] self, Object value) {
        return DefaultGroovyMethods.count(InvokerHelper.asIterator(self), value);
    }

    public static Number count(byte[] self, Object value) {
        return DefaultGroovyMethods.count(InvokerHelper.asIterator(self), value);
    }

    public static <T> List<T> toList(Iterator<T> self) {
        ArrayList<T> answer = new ArrayList<T>();
        while (self.hasNext()) {
            answer.add(self.next());
        }
        return answer;
    }

    public static <T> List<T> toList(Iterable<T> self) {
        return DefaultGroovyMethods.toList(self.iterator());
    }

    public static <T> List<T> toList(Enumeration<T> self) {
        ArrayList<T> answer = new ArrayList<T>();
        while (self.hasMoreElements()) {
            answer.add(self.nextElement());
        }
        return answer;
    }

    public static <T> List<List<T>> collate(Iterable<T> self, int size) {
        return DefaultGroovyMethods.collate(self, size, true);
    }

    public static <T> List<List<T>> collate(T[] self, int size) {
        return DefaultGroovyMethods.collate(Arrays.asList(self), size, true);
    }

    public static <T> List<List<T>> collate(Iterable<T> self, int size, int step) {
        return DefaultGroovyMethods.collate(self, size, step, true);
    }

    public static <T> List<List<T>> collate(T[] self, int size, int step) {
        return DefaultGroovyMethods.collate(Arrays.asList(self), size, step, true);
    }

    public static <T> List<List<T>> collate(Iterable<T> self, int size, boolean keepRemainder) {
        return DefaultGroovyMethods.collate(self, size, size, keepRemainder);
    }

    public static <T> List<List<T>> collate(T[] self, int size, boolean keepRemainder) {
        return DefaultGroovyMethods.collate(Arrays.asList(self), size, size, keepRemainder);
    }

    public static <T> List<List<T>> collate(Iterable<T> self, int size, int step, boolean keepRemainder) {
        ArrayList<List<Object>> answer;
        List<T> selfList = DefaultGroovyMethods.asList(self);
        if (size <= 0) {
            answer = new ArrayList<List<T>>(1);
            answer.add(selfList);
        } else {
            if (step == 0) {
                throw new IllegalArgumentException("step cannot be zero");
            }
            int selfListSize = selfList.size();
            answer = new ArrayList(step < 0 ? 1 : selfListSize / step + 1);
            for (int pos = 0; pos < selfListSize && pos > -1 && (keepRemainder || pos <= selfListSize - size); pos += step) {
                ArrayList<T> element = new ArrayList<T>(size);
                for (int offs = pos; offs < pos + size && offs < selfListSize; ++offs) {
                    element.add(selfList.get(offs));
                }
                answer.add(element);
            }
        }
        return answer;
    }

    public static <T> List<List<T>> collate(T[] self, int size, int step, boolean keepRemainder) {
        return DefaultGroovyMethods.collate(Arrays.asList(self), size, step, keepRemainder);
    }

    public static Collection collect(Object self) {
        return DefaultGroovyMethods.collect(self, Closure.IDENTITY);
    }

    public static <T> List<T> collect(Object self, Closure<T> transform) {
        return DefaultGroovyMethods.collect(self, new ArrayList(), transform);
    }

    public static <T, C extends Collection<T>> C collect(Object self, C collector, Closure<? extends T> transform) {
        return DefaultGroovyMethods.collect(InvokerHelper.asIterator(self), collector, transform);
    }

    public static <E, T> List<T> collect(E[] self, @ClosureParams(value=FirstParam.Component.class) Closure<T> transform) {
        return DefaultGroovyMethods.collect(new ArrayIterator<E>(self), transform);
    }

    public static <E, T, C extends Collection<T>> C collect(E[] self, C collector, @ClosureParams(value=FirstParam.Component.class) Closure<? extends T> transform) {
        return DefaultGroovyMethods.collect(new ArrayIterator<E>(self), collector, transform);
    }

    public static <E, T> List<T> collect(Iterator<E> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<T> transform) {
        return DefaultGroovyMethods.collect(self, new ArrayList(), transform);
    }

    public static <E, T, C extends Collection<T>> C collect(Iterator<E> self, C collector, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<? extends T> transform) {
        while (self.hasNext()) {
            collector.add(transform.call((Object)self.next()));
        }
        return collector;
    }

    public static <T> List<T> collect(Iterable<T> self) {
        return DefaultGroovyMethods.collect(self, Closure.IDENTITY);
    }

    public static <E, T> List<T> collect(Iterable<E> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<T> transform) {
        return DefaultGroovyMethods.collect(self.iterator(), transform);
    }

    public static <E, T, C extends Collection<T>> C collect(Iterable<E> self, C collector, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<? extends T> transform) {
        for (E element : self) {
            collector.add(transform.call((Object)element));
            if (transform.getDirective() != 1) continue;
            break;
        }
        return collector;
    }

    public static List collectNested(Collection self, Closure transform) {
        return DefaultGroovyMethods.collectNested(self, new ArrayList(self.size()), transform);
    }

    public static List collectNested(Iterable self, Closure transform) {
        return DefaultGroovyMethods.collectNested(self, new ArrayList(), transform);
    }

    public static <C extends Collection> C collectNested(Iterable self, C collector, Closure transform) {
        for (Object element : self) {
            if (element instanceof Collection) {
                Collection c = (Collection)element;
                collector.add(DefaultGroovyMethods.collectNested(c, DefaultGroovyMethods.createSimilarCollection(collector, c.size()), transform));
            } else {
                collector.add(transform.call(element));
            }
            if (transform.getDirective() != 1) continue;
            break;
        }
        return collector;
    }

    public static <T, E> List<T> collectMany(Iterable<E> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<? extends Collection<? extends T>> projection) {
        return DefaultGroovyMethods.collectMany(self, new ArrayList(), projection);
    }

    public static <T, E, C extends Collection<T>> C collectMany(Iterable<E> self, C collector, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<? extends Collection<? extends T>> projection) {
        return DefaultGroovyMethods.collectMany(self.iterator(), collector, projection);
    }

    public static <T, K, V, C extends Collection<T>> C collectMany(Map<K, V> self, C collector, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<? extends Collection<? extends T>> projection) {
        for (Map.Entry<K, V> entry : self.entrySet()) {
            collector.addAll(DefaultGroovyMethods.callClosureForMapEntry(projection, entry));
        }
        return collector;
    }

    public static <T, K, V> List<T> collectMany(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<? extends Collection<? extends T>> projection) {
        return DefaultGroovyMethods.collectMany(self, new ArrayList(), projection);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ <T, K, V> Collection<T> collectMany(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<? extends Collection<? extends T>> projection) {
        return DefaultGroovyMethods.collectMany(self, projection);
    }

    public static <T, E> List<T> collectMany(E[] self, @ClosureParams(value=FirstParam.Component.class) Closure<? extends Collection<? extends T>> projection) {
        return DefaultGroovyMethods.collectMany(self, new ArrayList(), projection);
    }

    public static <T, E, C extends Collection<T>> C collectMany(E[] self, C collector, @ClosureParams(value=FirstParam.Component.class) Closure<? extends Collection<? extends T>> projection) {
        return DefaultGroovyMethods.collectMany(new ArrayIterable<E>(self), collector, projection);
    }

    public static <T, E, C extends Collection<T>> C collectMany(Iterator<E> self, C collector, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<? extends Collection<? extends T>> projection) {
        while (self.hasNext()) {
            E element = self.next();
            collector.addAll(projection.call((Object)element));
        }
        return collector;
    }

    public static <T, E> List<T> collectMany(Iterator<E> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<? extends Collection<? extends T>> projection) {
        return DefaultGroovyMethods.collectMany(self, new ArrayList(), projection);
    }

    public static <T, K, V, C extends Collection<T>> C collect(Map<K, V> self, C collector, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<? extends T> transform) {
        for (Map.Entry<K, V> entry : self.entrySet()) {
            collector.add(DefaultGroovyMethods.callClosureForMapEntry(transform, entry));
        }
        return collector;
    }

    public static <T, K, V> List<T> collect(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<T> transform) {
        return DefaultGroovyMethods.collect(self, new ArrayList(self.size()), transform);
    }

    public static <K, V, X, Y> Map<K, V> collectEntries(Map<X, Y> self, Map<K, V> collector, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<?> transform) {
        for (Map.Entry<X, Y> entry : self.entrySet()) {
            DefaultGroovyMethods.addEntry(collector, DefaultGroovyMethods.callClosureForMapEntry(transform, entry));
        }
        return collector;
    }

    public static <K, V, X, Y> Map<K, V> collectEntries(Map<X, Y> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<?> transform) {
        return DefaultGroovyMethods.collectEntries(self, DefaultGroovyMethods.createSimilarMap(self), transform);
    }

    public static <K, V, E> Map<K, V> collectEntries(Iterator<E> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<?> transform) {
        return DefaultGroovyMethods.collectEntries(self, new LinkedHashMap(), transform);
    }

    public static <K, V, E> Map<K, V> collectEntries(Iterable<E> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<?> transform) {
        return DefaultGroovyMethods.collectEntries(self.iterator(), transform);
    }

    public static <K, V> Map<K, V> collectEntries(Iterator<?> self) {
        return DefaultGroovyMethods.collectEntries(self, Closure.IDENTITY);
    }

    public static <K, V> Map<K, V> collectEntries(Iterable<?> self) {
        return DefaultGroovyMethods.collectEntries(self.iterator());
    }

    public static <K, V, E> Map<K, V> collectEntries(Iterator<E> self, Map<K, V> collector, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<?> transform) {
        while (self.hasNext()) {
            E element = self.next();
            DefaultGroovyMethods.addEntry(collector, transform.call((Object)element));
        }
        return collector;
    }

    public static <K, V, E> Map<K, V> collectEntries(Iterable<E> self, Map<K, V> collector, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<?> transform) {
        return DefaultGroovyMethods.collectEntries(self.iterator(), collector, transform);
    }

    public static <K, V> Map<K, V> collectEntries(Iterator<?> self, Map<K, V> collector) {
        return DefaultGroovyMethods.collectEntries(self, collector, Closure.IDENTITY);
    }

    public static <K, V> Map<K, V> collectEntries(Iterable<?> self, Map<K, V> collector) {
        return DefaultGroovyMethods.collectEntries(self.iterator(), collector);
    }

    public static <K, V, E> Map<K, V> collectEntries(E[] self, Map<K, V> collector, @ClosureParams(value=FirstParam.Component.class) Closure<?> transform) {
        return DefaultGroovyMethods.collectEntries(new ArrayIterator<E>(self), collector, transform);
    }

    public static <K, V, E> Map<K, V> collectEntries(E[] self, Map<K, V> collector) {
        return DefaultGroovyMethods.collectEntries(self, collector, Closure.IDENTITY);
    }

    public static <K, V, E> Map<K, V> collectEntries(E[] self, @ClosureParams(value=FirstParam.Component.class) Closure<?> transform) {
        return DefaultGroovyMethods.collectEntries(new ArrayIterator<E>(self), new LinkedHashMap(), transform);
    }

    public static <K, V, E> Map<K, V> collectEntries(E[] self) {
        return DefaultGroovyMethods.collectEntries(self, Closure.IDENTITY);
    }

    private static <K, V> void addEntry(Map<K, V> result, Object newEntry) {
        if (newEntry instanceof Map) {
            DefaultGroovyMethods.leftShift(result, (Map)newEntry);
        } else if (newEntry instanceof List) {
            List list = (List)newEntry;
            Object key = list.isEmpty() ? null : list.get(0);
            Object value = list.size() <= 1 ? null : list.get(1);
            DefaultGroovyMethods.leftShift(result, new MapEntry(key, value));
        } else if (newEntry.getClass().isArray()) {
            Object[] array = (Object[])newEntry;
            Object key = array.length == 0 ? null : array[0];
            Object value = array.length <= 1 ? null : array[1];
            DefaultGroovyMethods.leftShift(result, new MapEntry(key, value));
        } else {
            DefaultGroovyMethods.leftShift(result, DefaultGroovyMethods.asType(newEntry, Map.Entry.class));
        }
    }

    public static Object find(Object self, Closure closure) {
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(closure);
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        while (iter.hasNext()) {
            Object value = iter.next();
            if (!bcw.call(value)) continue;
            return value;
        }
        return null;
    }

    public static Object find(Object self) {
        return DefaultGroovyMethods.find(self, Closure.IDENTITY);
    }

    public static <T> T find(Collection<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(closure);
        for (T value : self) {
            if (!bcw.call(value)) continue;
            return value;
        }
        return null;
    }

    public static <T> T find(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        for (T element : self) {
            if (!bcw.call(element)) continue;
            return element;
        }
        return null;
    }

    public static <T> T find(Collection<T> self) {
        return DefaultGroovyMethods.find(self, Closure.IDENTITY);
    }

    public static Object findResult(Object self, Closure condition) {
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        while (iter.hasNext()) {
            Object value = iter.next();
            Object result = condition.call(value);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    public static Object findResult(Object self, Object defaultResult, Closure condition) {
        Object result = DefaultGroovyMethods.findResult(self, condition);
        if (result == null) {
            return defaultResult;
        }
        return result;
    }

    public static <S, T, U extends T, V extends T> T findResult(Iterator<S> self, U defaultResult, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<V> condition) {
        V result = DefaultGroovyMethods.findResult(self, condition);
        if (result == null) {
            return defaultResult;
        }
        return (T)result;
    }

    public static <T, U> T findResult(Iterator<U> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<T> condition) {
        while (self.hasNext()) {
            U next = self.next();
            T result = condition.call((Object)next);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    public static <S, T, U extends T, V extends T> T findResult(Iterable<S> self, U defaultResult, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<V> condition) {
        V result = DefaultGroovyMethods.findResult(self, condition);
        if (result == null) {
            return defaultResult;
        }
        return (T)result;
    }

    public static <T, U> T findResult(Iterable<U> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<T> condition) {
        return DefaultGroovyMethods.findResult(self.iterator(), condition);
    }

    public static <S, T, U extends T, V extends T> T findResult(S[] self, U defaultResult, @ClosureParams(value=FirstParam.Component.class) Closure<V> condition) {
        return DefaultGroovyMethods.findResult(new ArrayIterator<S>(self), defaultResult, condition);
    }

    public static <S, T> T findResult(S[] self, @ClosureParams(value=FirstParam.Component.class) Closure<T> condition) {
        return DefaultGroovyMethods.findResult(new ArrayIterator<S>(self), condition);
    }

    public static <T, K, V> T findResult(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<T> condition) {
        for (Map.Entry<K, V> entry : self.entrySet()) {
            T result = DefaultGroovyMethods.callClosureForMapEntry(condition, entry);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    public static <T, U extends T, V extends T, A, B> T findResult(Map<A, B> self, U defaultResult, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<V> condition) {
        V result = DefaultGroovyMethods.findResult(self, condition);
        if (result == null) {
            return defaultResult;
        }
        return (T)result;
    }

    public static <T, U> Collection<T> findResults(Iterable<U> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<T> filteringTransform) {
        return DefaultGroovyMethods.findResults(self.iterator(), filteringTransform);
    }

    public static <T, U> Collection<T> findResults(Iterator<U> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<T> filteringTransform) {
        ArrayList<T> result = new ArrayList<T>();
        while (self.hasNext()) {
            U value = self.next();
            T transformed = filteringTransform.call((Object)value);
            if (transformed == null) continue;
            result.add(transformed);
        }
        return result;
    }

    public static <T, U> Collection<T> findResults(U[] self, @ClosureParams(value=FirstParam.Component.class) Closure<T> filteringTransform) {
        return DefaultGroovyMethods.findResults(new ArrayIterator<U>(self), filteringTransform);
    }

    public static <T, K, V> Collection<T> findResults(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<T> filteringTransform) {
        ArrayList<T> result = new ArrayList<T>();
        for (Map.Entry<K, V> entry : self.entrySet()) {
            T transformed = DefaultGroovyMethods.callClosureForMapEntry(filteringTransform, entry);
            if (transformed == null) continue;
            result.add(transformed);
        }
        return result;
    }

    public static <K, V> Map.Entry<K, V> find(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<?> closure) {
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(closure);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            if (!bcw.callForMap(entry)) continue;
            return entry;
        }
        return null;
    }

    public static <K, V> Map<K, V> findAll(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure closure) {
        Map<K, V> answer = DefaultGroovyMethods.createSimilarMap(self);
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(closure);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            if (!bcw.callForMap(entry)) continue;
            answer.put(entry.getKey(), entry.getValue());
        }
        return answer;
    }

    public static <T> Set<T> findAll(Set<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return (Set)DefaultGroovyMethods.findAll(self, closure);
    }

    public static <T> List<T> findAll(List<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return (List)DefaultGroovyMethods.findAll(self, closure);
    }

    public static <T> Collection<T> findAll(Collection<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return DefaultGroovyMethods.findMany(DefaultGroovyMethods.createSimilarCollection(self), self.iterator(), closure);
    }

    public static <T> List<T> findAll(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        return DefaultGroovyMethods.findMany(new ArrayList(), new ArrayIterator<T>(self), condition);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ <T> Collection<T> findAll(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        return DefaultGroovyMethods.findAll(self, condition);
    }

    public static <T> Set<T> findAll(Set<T> self) {
        return DefaultGroovyMethods.findAll(self, Closure.IDENTITY);
    }

    public static <T> List<T> findAll(List<T> self) {
        return DefaultGroovyMethods.findAll(self, Closure.IDENTITY);
    }

    public static <T> Collection<T> findAll(Collection<T> self) {
        return DefaultGroovyMethods.findAll(self, Closure.IDENTITY);
    }

    public static <T> List<T> findAll(T[] self) {
        return DefaultGroovyMethods.findAll(self, Closure.IDENTITY);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ <T> Collection<T> findAll(T[] self) {
        return DefaultGroovyMethods.findAll(self);
    }

    public static List findAll(Object self, Closure closure) {
        return DefaultGroovyMethods.findMany(new ArrayList(), InvokerHelper.asIterator(self), closure);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ Collection findAll(Object self, Closure closure) {
        return DefaultGroovyMethods.findAll(self, closure);
    }

    public static List findAll(Object self) {
        return DefaultGroovyMethods.findAll(self, Closure.IDENTITY);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ Collection findAll(Object self) {
        return DefaultGroovyMethods.findAll(self);
    }

    private static <T, C extends Collection<T>> C findMany(C collector, Iterator<? extends T> iter, Closure<?> closure) {
        BooleanClosureWrapper test = new BooleanClosureWrapper(closure);
        while (iter.hasNext()) {
            T value = iter.next();
            if (!test.call(value)) continue;
            collector.add(value);
        }
        return collector;
    }

    public static boolean contains(Iterable self, Object item) {
        for (Object e : self) {
            if (!Objects.equals(item, e)) continue;
            return true;
        }
        return false;
    }

    public static boolean containsAll(Iterable<?> self, Object[] items) {
        return DefaultGroovyMethods.asCollection(self).containsAll(Arrays.asList(items));
    }

    public static boolean removeAll(Collection self, Object[] items) {
        TreeSet pickFrom = new TreeSet(new NumberAwareComparator());
        pickFrom.addAll(Arrays.asList(items));
        return self.removeAll(pickFrom);
    }

    public static boolean retainAll(Collection self, Object[] items) {
        TreeSet pickFrom = new TreeSet(new NumberAwareComparator());
        pickFrom.addAll(Arrays.asList(items));
        return self.retainAll(pickFrom);
    }

    public static <T> boolean retainAll(Collection<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        boolean result = false;
        while (iter.hasNext()) {
            Object value = iter.next();
            if (bcw.call(value)) continue;
            iter.remove();
            result = true;
        }
        return result;
    }

    public static <K, V> boolean retainAll(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure condition) {
        Iterator<Map.Entry<K, V>> iter = self.entrySet().iterator();
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        boolean result = false;
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            if (bcw.callForMap(entry)) continue;
            iter.remove();
            result = true;
        }
        return result;
    }

    public static <T> boolean removeAll(Collection<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        boolean result = false;
        while (iter.hasNext()) {
            Object value = iter.next();
            if (!bcw.call(value)) continue;
            iter.remove();
            result = true;
        }
        return result;
    }

    public static <K, V> boolean removeAll(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure condition) {
        Iterator<Map.Entry<K, V>> iter = self.entrySet().iterator();
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        boolean result = false;
        while (iter.hasNext()) {
            Map.Entry<K, V> entry = iter.next();
            if (!bcw.callForMap(entry)) continue;
            iter.remove();
            result = true;
        }
        return result;
    }

    public static <T> boolean addAll(Collection<T> self, T[] items) {
        return self.addAll(Arrays.asList(items));
    }

    public static <T> boolean addAll(List<T> self, int index, T[] items) {
        return self.addAll(index, Arrays.asList(items));
    }

    public static Collection split(Object self, Closure closure) {
        ArrayList accept = new ArrayList();
        ArrayList reject = new ArrayList();
        return DefaultGroovyMethods.split(closure, accept, reject, InvokerHelper.asIterator(self));
    }

    public static <T> Collection<Collection<T>> split(Collection<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        Collection<T> accept = DefaultGroovyMethods.createSimilarCollection(self);
        Collection<T> reject = DefaultGroovyMethods.createSimilarCollection(self);
        Iterator<T> iter = self.iterator();
        return DefaultGroovyMethods.split(closure, accept, reject, iter);
    }

    public static <T> Collection<Collection<T>> split(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        ArrayList accept = new ArrayList();
        ArrayList reject = new ArrayList();
        ArrayIterator<T> iter = new ArrayIterator<T>(self);
        return DefaultGroovyMethods.split(closure, accept, reject, iter);
    }

    private static <T> Collection<Collection<T>> split(Closure closure, Collection<T> accept, Collection<T> reject, Iterator<T> iter) {
        ArrayList<Collection<T>> answer = new ArrayList<Collection<T>>();
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(closure);
        while (iter.hasNext()) {
            T value = iter.next();
            if (bcw.call(value)) {
                accept.add(value);
                continue;
            }
            reject.add(value);
        }
        answer.add(accept);
        answer.add(reject);
        return answer;
    }

    public static <T> List<List<T>> split(List<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return (List)DefaultGroovyMethods.split(self, closure);
    }

    public static <T> List<Set<T>> split(Set<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return (List)DefaultGroovyMethods.split(self, closure);
    }

    public static List combinations(Iterable self) {
        return GroovyCollections.combinations(self);
    }

    public static List combinations(Iterable self, Closure<?> function) {
        return DefaultGroovyMethods.collect(GroovyCollections.combinations(self), function);
    }

    public static void eachCombination(Iterable self, Closure<?> function) {
        DefaultGroovyMethods.each(GroovyCollections.combinations(self), function);
    }

    public static <T> Set<List<T>> subsequences(List<T> self) {
        return GroovyCollections.subsequences(self);
    }

    public static <T> Set<List<T>> permutations(Iterable<T> self) {
        HashSet<List<T>> ans = new HashSet<List<T>>();
        PermutationGenerator<T> generator = new PermutationGenerator<T>(self);
        while (generator.hasNext()) {
            ans.add((List<T>)generator.next());
        }
        return ans;
    }

    public static <T, V> List<V> permutations(Iterable<T> self, Closure<V> function) {
        return DefaultGroovyMethods.collect(DefaultGroovyMethods.permutations(self), function);
    }

    public static <T> Iterator<List<T>> eachPermutation(Iterable<T> self, Closure closure) {
        PermutationGenerator<T> generator = new PermutationGenerator<T>(self);
        while (generator.hasNext()) {
            closure.call(generator.next());
        }
        return generator;
    }

    public static List transpose(List self) {
        return GroovyCollections.transpose(self);
    }

    public static int[][] transpose(int[][] self) {
        int[][] result = new int[self[0].length][self.length];
        for (int i = 0; i < self.length; ++i) {
            for (int j = 0; j < self[i].length; ++j) {
                result[j][i] = self[i][j];
            }
        }
        return result;
    }

    public static long[][] transpose(long[][] self) {
        long[][] result = new long[self[0].length][self.length];
        for (int i = 0; i < self.length; ++i) {
            for (int j = 0; j < self[i].length; ++j) {
                result[j][i] = self[i][j];
            }
        }
        return result;
    }

    public static double[][] transpose(double[][] self) {
        double[][] result = new double[self[0].length][self.length];
        for (int i = 0; i < self.length; ++i) {
            for (int j = 0; j < self[i].length; ++j) {
                result[j][i] = self[i][j];
            }
        }
        return result;
    }

    public static <K, T> Map<K, List<T>> groupBy(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<K> closure) {
        LinkedHashMap answer = new LinkedHashMap();
        for (T element : self) {
            K value = closure.call((Object)element);
            DefaultGroovyMethods.groupAnswer(answer, element, value);
        }
        return answer;
    }

    public static <K, T> Map<K, List<T>> groupBy(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure<K> closure) {
        return DefaultGroovyMethods.groupBy(Arrays.asList(self), closure);
    }

    public static Map groupBy(Iterable self, Object ... closures) {
        Closure head = closures.length == 0 ? Closure.IDENTITY : (Closure)closures[0];
        Map first = DefaultGroovyMethods.groupBy(self, head);
        if (closures.length < 2) {
            return first;
        }
        Object[] tail = new Object[closures.length - 1];
        System.arraycopy(closures, 1, tail, 0, closures.length - 1);
        LinkedHashMap acc = new LinkedHashMap();
        for (Map.Entry item : first.entrySet()) {
            acc.put(item.getKey(), DefaultGroovyMethods.groupBy((Iterable)item.getValue(), tail));
        }
        return acc;
    }

    public static Map groupBy(Object[] self, Object ... closures) {
        return DefaultGroovyMethods.groupBy(new ArrayIterable<Object>(self), closures);
    }

    public static Map groupBy(Iterable self, List<Closure> closures) {
        return DefaultGroovyMethods.groupBy(self, closures.toArray());
    }

    public static Map groupBy(Object[] self, List<Closure> closures) {
        return DefaultGroovyMethods.groupBy(new ArrayIterable<Object>(self), closures);
    }

    public static <K, E> Map<K, Integer> countBy(Iterable<E> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<K> closure) {
        return DefaultGroovyMethods.countBy(self.iterator(), closure);
    }

    public static <K, E> Map<K, Integer> countBy(E[] self, @ClosureParams(value=FirstParam.Component.class) Closure<K> closure) {
        return DefaultGroovyMethods.countBy(new ArrayIterator<E>(self), closure);
    }

    public static <K, E> Map<K, Integer> countBy(Iterator<E> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<K> closure) {
        LinkedHashMap answer = new LinkedHashMap();
        while (self.hasNext()) {
            K value = closure.call((Object)self.next());
            DefaultGroovyMethods.countAnswer(answer, value);
        }
        return answer;
    }

    public static <G, K, V> Map<G, List<Map.Entry<K, V>>> groupEntriesBy(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<G> closure) {
        LinkedHashMap answer = new LinkedHashMap();
        for (Map.Entry<K, V> entry : self.entrySet()) {
            G value = DefaultGroovyMethods.callClosureForMapEntry(closure, entry);
            DefaultGroovyMethods.groupAnswer(answer, entry, value);
        }
        return answer;
    }

    public static <G, K, V> Map<G, Map<K, V>> groupBy(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<G> closure) {
        Map<G, List<Map.Entry<K, V>>> initial = DefaultGroovyMethods.groupEntriesBy(self, closure);
        LinkedHashMap<G, Map<K, V>> answer = new LinkedHashMap<G, Map<K, V>>();
        for (Map.Entry<G, List<Map.Entry<K, V>>> outer : initial.entrySet()) {
            G key = outer.getKey();
            List<Map.Entry<K, V>> entries = outer.getValue();
            Map<K, V> target = DefaultGroovyMethods.createSimilarMap(self);
            DefaultGroovyMethods.putAll(target, entries);
            answer.put(key, target);
        }
        return answer;
    }

    public static Map<Object, Map> groupBy(Map self, Object ... closures) {
        Closure head = closures.length == 0 ? Closure.IDENTITY : (Closure)closures[0];
        Map<Object, Map> first = DefaultGroovyMethods.groupBy(self, head);
        if (closures.length < 2) {
            return first;
        }
        Object[] tail = new Object[closures.length - 1];
        System.arraycopy(closures, 1, tail, 0, closures.length - 1);
        LinkedHashMap<Object, Map> acc = new LinkedHashMap<Object, Map>();
        for (Map.Entry<Object, Map> item : first.entrySet()) {
            acc.put(item.getKey(), DefaultGroovyMethods.groupBy(item.getValue(), tail));
        }
        return acc;
    }

    public static Map<Object, Map> groupBy(Map self, List<Closure> closures) {
        return DefaultGroovyMethods.groupBy(self, closures.toArray());
    }

    public static <K, U, V> Map<K, Integer> countBy(Map<U, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure<K> closure) {
        LinkedHashMap answer = new LinkedHashMap();
        for (Map.Entry<U, V> entry : self.entrySet()) {
            DefaultGroovyMethods.countAnswer(answer, DefaultGroovyMethods.callClosureForMapEntry(closure, entry));
        }
        return answer;
    }

    protected static <K, T> void groupAnswer(Map<K, List<T>> answer, T element, K value) {
        List groupedElements = answer.computeIfAbsent(value, k -> new ArrayList());
        groupedElements.add(element);
    }

    private static <T> void countAnswer(Map<T, Integer> answer, T mappedKey) {
        Integer current = answer.get(mappedKey);
        if (null == current) {
            current = 0;
        }
        answer.put(mappedKey, current + 1);
    }

    protected static <T, K, V> T callClosureForMapEntry(@ClosureParams(value=FromString.class, options={"K,V", "Map.Entry<K,V>"}) Closure<T> closure, Map.Entry<K, V> entry) {
        if (closure.getMaximumNumberOfParameters() == 2) {
            return closure.call(entry.getKey(), entry.getValue());
        }
        return closure.call((Object)entry);
    }

    protected static <T> T callClosureForLine(@ClosureParams(value=FromString.class, options={"String", "String,Integer"}) Closure<T> closure, String line, int counter) {
        if (closure.getMaximumNumberOfParameters() == 2) {
            return closure.call(line, counter);
        }
        return closure.call((Object)line);
    }

    protected static <T, K, V> T callClosureForMapEntryAndCounter(@ClosureParams(value=FromString.class, options={"K,V,Integer", "K,V", "Map.Entry<K,V>"}) Closure<T> closure, Map.Entry<K, V> entry, int counter) {
        if (closure.getMaximumNumberOfParameters() == 3) {
            return closure.call(entry.getKey(), entry.getValue(), counter);
        }
        if (closure.getMaximumNumberOfParameters() == 2) {
            return closure.call(entry, counter);
        }
        return closure.call((Object)entry);
    }

    public static <T, V extends T> T inject(Collection<T> self, @ClosureParams(value=FromString.class, options={"V,T"}) Closure<V> closure) {
        if (self.isEmpty()) {
            throw new NoSuchElementException("Cannot call inject() on an empty collection without passing an initial value.");
        }
        Iterator<T> iter = self.iterator();
        T head = iter.next();
        Collection<T> tail = DefaultGroovyMethods.tail(self);
        if (!tail.iterator().hasNext()) {
            return head;
        }
        return DefaultGroovyMethods.inject(tail, head, closure);
    }

    public static <E, T, U extends T, V extends T> T inject(Collection<E> self, U initialValue, @ClosureParams(value=FromString.class, options={"U,E"}) Closure<V> closure) {
        return DefaultGroovyMethods.inject(self.iterator(), initialValue, closure);
    }

    public static <K, V, T, U extends T, W extends T> T inject(Map<K, V> self, U initialValue, @ClosureParams(value=FromString.class, options={"U,Map.Entry<K,V>", "U,K,V"}) Closure<W> closure) {
        Object value = initialValue;
        for (Map.Entry<K, V> entry : self.entrySet()) {
            if (closure.getMaximumNumberOfParameters() == 3) {
                value = closure.call(value, entry.getKey(), entry.getValue());
                continue;
            }
            value = closure.call(value, entry);
        }
        return value;
    }

    public static <E, T, U extends T, V extends T> T inject(Iterator<E> self, U initialValue, @ClosureParams(value=FromString.class, options={"U,E"}) Closure<V> closure) {
        Object value = initialValue;
        Object[] params = new Object[2];
        while (self.hasNext()) {
            E item = self.next();
            params[0] = value;
            params[1] = item;
            value = closure.call(params);
        }
        return value;
    }

    public static <T, V extends T> T inject(Object self, Closure<V> closure) {
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        if (!iter.hasNext()) {
            throw new NoSuchElementException("Cannot call inject() over an empty iterable without passing an initial value.");
        }
        Object initialValue = iter.next();
        return DefaultGroovyMethods.inject(iter, initialValue, closure);
    }

    public static <T, U extends T, V extends T> T inject(Object self, U initialValue, Closure<V> closure) {
        Iterator<Object> iter = InvokerHelper.asIterator(self);
        return DefaultGroovyMethods.inject(iter, initialValue, closure);
    }

    public static <E, T, V extends T> T inject(E[] self, @ClosureParams(value=FromString.class, options={"E,E"}) Closure<V> closure) {
        return DefaultGroovyMethods.inject(self, closure);
    }

    public static <E, T, U extends T, V extends T> T inject(E[] self, U initialValue, @ClosureParams(value=FromString.class, options={"U,E"}) Closure<V> closure) {
        Object[] params = new Object[2];
        Object value = initialValue;
        for (E next : self) {
            params[0] = value;
            params[1] = next;
            value = closure.call(params);
        }
        return value;
    }

    public static Object sum(Iterable<?> self) {
        return DefaultGroovyMethods.sum(self.iterator(), null, true);
    }

    public static Object sum(Object[] self) {
        return DefaultGroovyMethods.sum(new ArrayIterator<Object>(self), null, true);
    }

    public static Object sum(Iterator<Object> self) {
        return DefaultGroovyMethods.sum(self, null, true);
    }

    public static byte sum(byte[] self) {
        return DefaultGroovyMethods.sum(self, (byte)0);
    }

    public static short sum(short[] self) {
        return DefaultGroovyMethods.sum(self, (short)0);
    }

    public static int sum(int[] self) {
        return DefaultGroovyMethods.sum(self, 0);
    }

    public static long sum(long[] self) {
        return DefaultGroovyMethods.sum(self, 0L);
    }

    public static char sum(char[] self) {
        return DefaultGroovyMethods.sum(self, '\u0000');
    }

    public static float sum(float[] self) {
        return DefaultGroovyMethods.sum(self, 0.0f);
    }

    public static double sum(double[] self) {
        return DefaultGroovyMethods.sum(self, 0.0);
    }

    public static Object sum(Iterable<?> self, Object initialValue) {
        return DefaultGroovyMethods.sum(self.iterator(), initialValue, false);
    }

    public static Object sum(Object[] self, Object initialValue) {
        return DefaultGroovyMethods.sum(new ArrayIterator<Object>(self), initialValue, false);
    }

    public static Object sum(Iterator<?> self, Object initialValue) {
        return DefaultGroovyMethods.sum(self, initialValue, false);
    }

    private static Object sum(Iterator<?> self, Object initialValue, boolean first) {
        Object result = initialValue;
        Object[] param = new Object[1];
        while (self.hasNext()) {
            Object next = self.next();
            param[0] = next;
            if (first) {
                result = param[0];
                first = false;
                continue;
            }
            MetaClass metaClass = InvokerHelper.getMetaClass(result);
            result = metaClass.invokeMethod(result, "plus", param);
        }
        return result;
    }

    public static byte sum(byte[] self, byte initialValue) {
        byte s = initialValue;
        for (byte v : self) {
            s = (byte)(s + v);
        }
        return s;
    }

    public static short sum(short[] self, short initialValue) {
        short s = initialValue;
        for (short v : self) {
            s = (short)(s + v);
        }
        return s;
    }

    public static int sum(int[] self, int initialValue) {
        int s = initialValue;
        for (int v : self) {
            s += v;
        }
        return s;
    }

    public static long sum(long[] self, long initialValue) {
        long s = initialValue;
        for (long v : self) {
            s += v;
        }
        return s;
    }

    public static char sum(char[] self, char initialValue) {
        char s = initialValue;
        for (char v : self) {
            s = (char)(s + v);
        }
        return s;
    }

    public static float sum(float[] self, float initialValue) {
        float s = initialValue;
        for (float v : self) {
            s += v;
        }
        return s;
    }

    public static double sum(double[] self, double initialValue) {
        double s = initialValue;
        for (double v : self) {
            s += v;
        }
        return s;
    }

    public static <T> Object sum(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return DefaultGroovyMethods.sum(self.iterator(), null, closure, true);
    }

    public static <T> Object sum(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        return DefaultGroovyMethods.sum(new ArrayIterator<T>(self), null, closure, true);
    }

    public static <T> Object sum(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return DefaultGroovyMethods.sum(self, null, closure, true);
    }

    public static <T> Object sum(Iterable<T> self, Object initialValue, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return DefaultGroovyMethods.sum(self.iterator(), initialValue, closure, false);
    }

    public static <T> Object sum(T[] self, Object initialValue, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        return DefaultGroovyMethods.sum(new ArrayIterator<T>(self), initialValue, closure, false);
    }

    public static <T> Object sum(Iterator<T> self, Object initialValue, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return DefaultGroovyMethods.sum(self, initialValue, closure, false);
    }

    private static Object sum(Iterator<?> self, Object initialValue, Closure closure, boolean first) {
        Object result = initialValue;
        Object[] closureParam = new Object[1];
        Object[] plusParam = new Object[1];
        while (self.hasNext()) {
            closureParam[0] = self.next();
            plusParam[0] = closure.call(closureParam);
            if (first) {
                result = plusParam[0];
                first = false;
                continue;
            }
            MetaClass metaClass = InvokerHelper.getMetaClass(result);
            result = metaClass.invokeMethod(result, "plus", plusParam);
        }
        return result;
    }

    public static Object average(Iterable<?> self) {
        return DefaultGroovyMethods.average(self.iterator());
    }

    public static Object average(Object[] self) {
        Object result = DefaultGroovyMethods.sum(self);
        MetaClass metaClass = InvokerHelper.getMetaClass(result);
        result = metaClass.invokeMethod(result, "div", self.length);
        return result;
    }

    public static Object average(Iterator<?> self) {
        MetaClass metaClass;
        Object result = null;
        long count = 0L;
        Object[] param = new Object[1];
        while (self.hasNext()) {
            param[0] = self.next();
            if (count == 0L) {
                result = param[0];
            } else {
                metaClass = InvokerHelper.getMetaClass(result);
                result = metaClass.invokeMethod(result, "plus", param);
            }
            ++count;
        }
        metaClass = InvokerHelper.getMetaClass(result);
        result = metaClass.invokeMethod(result, "div", count);
        return result;
    }

    public static BigDecimal average(byte[] self) {
        long s = 0L;
        int count = 0;
        for (byte v : self) {
            s += (long)v;
            ++count;
        }
        return BigDecimal.valueOf(s).divide(BigDecimal.valueOf(count));
    }

    public static BigDecimal average(short[] self) {
        long s = 0L;
        int count = 0;
        for (short v : self) {
            s += (long)v;
            ++count;
        }
        return BigDecimal.valueOf(s).divide(BigDecimal.valueOf(count));
    }

    public static BigDecimal average(int[] self) {
        long s = 0L;
        int count = 0;
        for (int v : self) {
            s += (long)v;
            ++count;
        }
        return BigDecimal.valueOf(s).divide(BigDecimal.valueOf(count));
    }

    public static BigDecimal average(long[] self) {
        long s = 0L;
        int count = 0;
        for (long v : self) {
            s += v;
            ++count;
        }
        return BigDecimal.valueOf(s).divide(BigDecimal.valueOf(count));
    }

    public static double average(float[] self) {
        double s = 0.0;
        int count = 0;
        for (float v : self) {
            s += (double)v;
            ++count;
        }
        return s / (double)count;
    }

    public static double average(double[] self) {
        double s = 0.0;
        int count = 0;
        for (double v : self) {
            s += v;
            ++count;
        }
        return s / (double)count;
    }

    public static <T> Object average(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        return DefaultGroovyMethods.average(self.iterator(), closure);
    }

    public static <T> Object average(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        return DefaultGroovyMethods.average(new ArrayIterator<T>(self), closure);
    }

    public static <T> Object average(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure closure) {
        MetaClass metaClass;
        Object result = null;
        long count = 0L;
        Object[] closureParam = new Object[1];
        Object[] plusParam = new Object[1];
        while (self.hasNext()) {
            closureParam[0] = self.next();
            plusParam[0] = closure.call(closureParam);
            if (count == 0L) {
                result = plusParam[0];
            } else {
                metaClass = InvokerHelper.getMetaClass(result);
                result = metaClass.invokeMethod(result, "plus", plusParam);
            }
            ++count;
        }
        metaClass = InvokerHelper.getMetaClass(result);
        result = metaClass.invokeMethod(result, "div", count);
        return result;
    }

    public static String join(Iterator<?> self, String separator) {
        if (separator == null) {
            separator = "";
        }
        StringBuilder buffer = new StringBuilder();
        boolean first = true;
        while (self.hasNext()) {
            Object value = self.next();
            if (first) {
                first = false;
            } else {
                buffer.append(separator);
            }
            buffer.append(FormatHelper.toString(value));
        }
        return buffer.toString();
    }

    public static String join(Iterable<?> self, String separator) {
        return DefaultGroovyMethods.join(self.iterator(), separator);
    }

    public static <T> String join(T[] self, String separator) {
        return DefaultGroovyMethods.join(new ArrayIterator<T>(self), separator);
    }

    public static String join(boolean[] self, String separator) {
        return DefaultGroovyMethods.join(new BooleanArrayIterator(self), separator);
    }

    public static String join(byte[] self, String separator) {
        return DefaultGroovyMethods.join(new ByteArrayIterator(self), separator);
    }

    public static String join(char[] self, String separator) {
        return DefaultGroovyMethods.join(new CharArrayIterator(self), separator);
    }

    public static String join(double[] self, String separator) {
        return DefaultGroovyMethods.join(new DoubleArrayIterator(self), separator);
    }

    public static String join(float[] self, String separator) {
        return DefaultGroovyMethods.join(new FloatArrayIterator(self), separator);
    }

    public static String join(int[] self, String separator) {
        return DefaultGroovyMethods.join(new IntArrayIterator(self), separator);
    }

    public static String join(long[] self, String separator) {
        return DefaultGroovyMethods.join(new LongArrayIterator(self), separator);
    }

    public static String join(short[] self, String separator) {
        return DefaultGroovyMethods.join(new ShortArrayIterator(self), separator);
    }

    public static <T> T min(Iterable<T> self) {
        return DefaultGroovyMethods.min(self.iterator());
    }

    public static <T> T min(Iterator<T> self) {
        T answer = null;
        while (self.hasNext()) {
            T value = self.next();
            if (value == null || answer != null && !ScriptBytecodeAdapter.compareLessThan(value, answer)) continue;
            answer = value;
        }
        return answer;
    }

    public static <T> T min(T[] self) {
        return DefaultGroovyMethods.min(new ArrayIterator<T>(self));
    }

    public static int min(int[] self) {
        Objects.requireNonNull(self);
        if (self.length == 0) {
            throw new UnsupportedOperationException("Can't call min() on empty int[]");
        }
        int answer = self[0];
        for (int i = 1; i < self.length; ++i) {
            int value = self[i];
            if (value >= answer) continue;
            answer = value;
        }
        return answer;
    }

    public static long min(long[] self) {
        Objects.requireNonNull(self);
        if (self.length == 0) {
            throw new UnsupportedOperationException("Can't call min() on empty long[]");
        }
        long answer = self[0];
        for (int i = 1; i < self.length; ++i) {
            long value = self[i];
            if (value >= answer) continue;
            answer = value;
        }
        return answer;
    }

    public static double min(double[] self) {
        Objects.requireNonNull(self);
        if (self.length == 0) {
            throw new UnsupportedOperationException("Can't call min() on empty double[]");
        }
        double answer = self[0];
        for (int i = 1; i < self.length; ++i) {
            double value = self[i];
            if (!(value < answer)) continue;
            answer = value;
        }
        return answer;
    }

    public static <T> T min(Iterable<T> self, Comparator<T> comparator) {
        return DefaultGroovyMethods.min(self.iterator(), comparator);
    }

    public static <T> T min(Iterator<T> self, Comparator<T> comparator) {
        Object answer = null;
        boolean first = true;
        while (self.hasNext()) {
            T value = self.next();
            if (first) {
                first = false;
                answer = value;
                continue;
            }
            if (comparator.compare(value, answer) >= 0) continue;
            answer = value;
        }
        return answer;
    }

    public static <T> T min(T[] self, Comparator<T> comparator) {
        return DefaultGroovyMethods.min(new ArrayIterator<T>(self), comparator);
    }

    public static <T> T min(Iterable<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.min(self.iterator(), closure);
    }

    public static <K, V> Map.Entry<K, V> min(Map<K, V> self, @ClosureParams(value=FromString.class, options={"Map.Entry<K,V>", "Map.Entry<K,V>,Map.Entry<K,V>"}) Closure closure) {
        return DefaultGroovyMethods.min(self.entrySet(), closure);
    }

    public static <K, V> Map.Entry<K, V> max(Map<K, V> self, @ClosureParams(value=FromString.class, options={"Map.Entry<K,V>", "Map.Entry<K,V>,Map.Entry<K,V>"}) Closure closure) {
        return DefaultGroovyMethods.max(self.entrySet(), closure);
    }

    public static <T> T min(Iterator<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        int params = closure.getMaximumNumberOfParameters();
        if (params != 1) {
            return DefaultGroovyMethods.min(self, new ClosureComparator(closure));
        }
        boolean first = true;
        T answer = null;
        Object answerValue = null;
        while (self.hasNext()) {
            T item = self.next();
            Object value = closure.call((Object)item);
            if (first) {
                first = false;
                answer = item;
                answerValue = value;
                continue;
            }
            if (!ScriptBytecodeAdapter.compareLessThan(value, answerValue)) continue;
            answer = item;
            answerValue = value;
        }
        return answer;
    }

    public static <T> T min(T[] self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.min(new ArrayIterator<T>(self), closure);
    }

    public static <T> T max(Iterable<T> self) {
        return DefaultGroovyMethods.max(self.iterator());
    }

    public static <T> T max(Iterator<T> self) {
        T answer = null;
        while (self.hasNext()) {
            T value = self.next();
            if (value == null || answer != null && !ScriptBytecodeAdapter.compareGreaterThan(value, answer)) continue;
            answer = value;
        }
        return answer;
    }

    public static <T> T max(T[] self) {
        return DefaultGroovyMethods.max(new ArrayIterator<T>(self));
    }

    public static int max(int[] self) {
        Objects.requireNonNull(self);
        if (self.length == 0) {
            throw new UnsupportedOperationException("Can't call max() on empty int[]");
        }
        int answer = self[0];
        for (int i = 1; i < self.length; ++i) {
            int value = self[i];
            if (value <= answer) continue;
            answer = value;
        }
        return answer;
    }

    public static long max(long[] self) {
        Objects.requireNonNull(self);
        if (self.length == 0) {
            throw new UnsupportedOperationException("Can't call max() on empty long[]");
        }
        long answer = self[0];
        for (int i = 1; i < self.length; ++i) {
            long value = self[i];
            if (value <= answer) continue;
            answer = value;
        }
        return answer;
    }

    public static double max(double[] self) {
        Objects.requireNonNull(self);
        if (self.length == 0) {
            throw new UnsupportedOperationException("Can't call max() on empty double[]");
        }
        double answer = self[0];
        for (int i = 1; i < self.length; ++i) {
            double value = self[i];
            if (!(value > answer)) continue;
            answer = value;
        }
        return answer;
    }

    public static <T> T max(Iterable<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.max(self.iterator(), closure);
    }

    public static <T> T max(Iterator<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        int params = closure.getMaximumNumberOfParameters();
        if (params != 1) {
            return DefaultGroovyMethods.max(self, new ClosureComparator(closure));
        }
        boolean first = true;
        T answer = null;
        Object answerValue = null;
        while (self.hasNext()) {
            T item = self.next();
            Object value = closure.call((Object)item);
            if (first) {
                first = false;
                answer = item;
                answerValue = value;
                continue;
            }
            if (!ScriptBytecodeAdapter.compareLessThan(answerValue, value)) continue;
            answer = item;
            answerValue = value;
        }
        return answer;
    }

    public static <T> T max(T[] self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.max(new ArrayIterator<T>(self), closure);
    }

    public static <T> T max(Iterable<T> self, Comparator<T> comparator) {
        return DefaultGroovyMethods.max(self.iterator(), comparator);
    }

    public static <T> T max(Iterator<T> self, Comparator<T> comparator) {
        Object answer = null;
        while (self.hasNext()) {
            T value = self.next();
            if (value == null || answer != null && comparator.compare(value, answer) <= 0) continue;
            answer = value;
        }
        return answer;
    }

    public static <T> T max(T[] self, Comparator<T> comparator) {
        return DefaultGroovyMethods.max(new ArrayIterator<T>(self), comparator);
    }

    public static IntRange getIndices(Collection self) {
        return new IntRange(false, 0, self.size());
    }

    public static <T> IntRange getIndices(T[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static IntRange getIndices(boolean[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static IntRange getIndices(byte[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static IntRange getIndices(char[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static IntRange getIndices(double[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static IntRange getIndices(float[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static IntRange getIndices(int[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static IntRange getIndices(long[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static IntRange getIndices(short[] self) {
        return new IntRange(false, 0, self.length);
    }

    public static int size(Iterator self) {
        int count = 0;
        while (self.hasNext()) {
            self.next();
            ++count;
        }
        return count;
    }

    public static int size(Iterable self) {
        return DefaultGroovyMethods.size(self.iterator());
    }

    public static int size(Object[] self) {
        return self.length;
    }

    public static boolean isEmpty(Iterable self) {
        return !self.iterator().hasNext();
    }

    public static <T> List<T> getAt(List<T> self, Range range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(self.size(), range);
        List<T> subList = self.subList(info.from, info.to);
        if (info.reverse) {
            subList = DefaultGroovyMethods.reverse(subList);
        }
        List<T> answer = DefaultGroovyMethods.createSimilarList(self, subList.size());
        answer.addAll(subList);
        return answer;
    }

    public static <T> List<T> getAt(ListWithDefault<T> self, Collection indices) {
        ListWithDefault<T> answer = ListWithDefault.newInstance(new ArrayList(indices.size()), self.isLazyDefaultValues(), self.getInitClosure());
        for (Object value : indices) {
            if (value instanceof Collection) {
                answer.addAll((List)InvokerHelper.invokeMethod(self, "getAt", value));
                continue;
            }
            int idx = DefaultGroovyMethods.normaliseIndex(DefaultTypeTransformation.intUnbox(value), self.size());
            answer.add(self.getAt(idx));
        }
        return answer;
    }

    public static <T> List<T> getAt(ListWithDefault<T> self, Range range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(self.size(), range);
        if (self.size() < info.to) {
            self.get(info.to - 1);
        }
        ListWithDefault<Object> answer = self.subList(info.from, info.to);
        answer = info.reverse ? ListWithDefault.newInstance(DefaultGroovyMethods.reverse(answer), self.isLazyDefaultValues(), self.getInitClosure()) : ListWithDefault.newInstance(new ArrayList(answer), self.isLazyDefaultValues(), self.getInitClosure());
        return answer;
    }

    public static <T> List<T> getAt(ListWithDefault<T> self, EmptyRange range) {
        return ListWithDefault.newInstance(new ArrayList(), self.isLazyDefaultValues(), self.getInitClosure());
    }

    public static <T> List<T> getAt(List<T> self, EmptyRange range) {
        return DefaultGroovyMethods.createSimilarList(self, 0);
    }

    public static <T> List<T> getAt(List<T> self, Collection indices) {
        ArrayList<T> answer = new ArrayList<T>(indices.size());
        for (Object value : indices) {
            if (value instanceof Collection) {
                answer.addAll((List)InvokerHelper.invokeMethod(self, "getAt", value));
                continue;
            }
            int idx = DefaultTypeTransformation.intUnbox(value);
            answer.add(DefaultGroovyMethods.getAt(self, idx));
        }
        return answer;
    }

    public static <T> List<T> getAt(T[] self, Collection indices) {
        ArrayList<T> answer = new ArrayList<T>(indices.size());
        for (Object value : indices) {
            if (value instanceof Range) {
                answer.addAll(DefaultGroovyMethods.getAt(self, (Range)value));
                continue;
            }
            if (value instanceof Collection) {
                answer.addAll(DefaultGroovyMethods.getAt(self, (Collection)value));
                continue;
            }
            int idx = DefaultTypeTransformation.intUnbox(value);
            answer.add(DefaultGroovyMethods.getAtImpl(self, idx));
        }
        return answer;
    }

    public static <K, V> Map<K, V> subMap(Map<K, V> map, Collection<K> keys) {
        LinkedHashMap<K, V> answer = new LinkedHashMap<K, V>(keys.size());
        for (K key : keys) {
            if (!map.containsKey(key)) continue;
            answer.put(key, map.get(key));
        }
        return answer;
    }

    public static <K, V> Map<K, V> subMap(Map<K, V> map, K[] keys) {
        LinkedHashMap<K, V> answer = new LinkedHashMap<K, V>(keys.length);
        for (K key : keys) {
            if (!map.containsKey(key)) continue;
            answer.put(key, map.get(key));
        }
        return answer;
    }

    public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, defaultValue);
        }
        return map.get(key);
    }

    public static <T> List<T> getAt(T[] array, Range range) {
        List<T> list = Arrays.asList(array);
        return DefaultGroovyMethods.getAt(list, range);
    }

    public static <T> List<T> getAt(T[] array, IntRange range) {
        List<T> list = Arrays.asList(array);
        return DefaultGroovyMethods.getAt(list, (Range)range);
    }

    public static <T> List<T> getAt(T[] array, EmptyRange range) {
        return new ArrayList();
    }

    public static <T> List<T> getAt(T[] array, ObjectRange range) {
        List<T> list = Arrays.asList(array);
        return DefaultGroovyMethods.getAt(list, (Range)range);
    }

    private static <T> T getAtImpl(T[] array, int idx) {
        return array[DefaultGroovyMethods.normaliseIndex(idx, array.length)];
    }

    public static <T> List<T> toList(T[] array) {
        return new ArrayList<T>(Arrays.asList(array));
    }

    public static <T> T getAt(List<T> self, int idx) {
        int size = self.size();
        int i = DefaultGroovyMethods.normaliseIndex(idx, size);
        if (i < size) {
            return self.get(i);
        }
        return null;
    }

    public static <T> T getAt(List<T> self, Number idx) {
        return DefaultGroovyMethods.getAt(self, idx.intValue());
    }

    public static <T> T getAt(Iterator<T> self, int idx) {
        if (idx < 0) {
            List<T> list = DefaultGroovyMethods.toList(self);
            int adjustedIndex = idx + list.size();
            if (adjustedIndex < 0 || adjustedIndex >= list.size()) {
                return null;
            }
            return list.get(adjustedIndex);
        }
        int count = 0;
        while (self.hasNext()) {
            if (count == idx) {
                return self.next();
            }
            ++count;
            self.next();
        }
        return null;
    }

    public static <T> T getAt(Iterable<T> self, int idx) {
        return DefaultGroovyMethods.getAt(self.iterator(), idx);
    }

    public static <T> void putAt(List<T> self, int idx, T value) {
        int size = self.size();
        if ((idx = DefaultGroovyMethods.normaliseIndex(idx, size)) < size) {
            self.set(idx, value);
        } else {
            while (size < idx) {
                self.add(size++, null);
            }
            self.add(idx, value);
        }
    }

    public static <T> void putAt(List<T> self, Number idx, T value) {
        DefaultGroovyMethods.putAt(self, idx.intValue(), value);
    }

    public static void putAt(List self, EmptyRange range, Object value) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(self.size(), range);
        List<Object> sublist = self.subList(info.from, info.to);
        sublist.clear();
        if (value instanceof Collection) {
            Collection col = (Collection)value;
            if (col.isEmpty()) {
                return;
            }
            sublist.addAll(col);
        } else {
            sublist.add(value);
        }
    }

    public static void putAt(List self, EmptyRange range, Collection value) {
        DefaultGroovyMethods.putAt(self, range, (Object)value);
    }

    private static <T> List<T> resizeListWithRangeAndGetSublist(List<T> self, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(self.size(), range);
        int size = self.size();
        if (info.to >= size) {
            while (size < info.to) {
                self.add(size++, null);
            }
        }
        List<T> sublist = self.subList(info.from, info.to);
        sublist.clear();
        return sublist;
    }

    public static void putAt(List self, IntRange range, Collection col) {
        List sublist = DefaultGroovyMethods.resizeListWithRangeAndGetSublist(self, range);
        if (col.isEmpty()) {
            return;
        }
        sublist.addAll(col);
    }

    public static void putAt(List self, IntRange range, Object value) {
        List sublist = DefaultGroovyMethods.resizeListWithRangeAndGetSublist(self, range);
        sublist.add(value);
    }

    public static void putAt(List self, List splice, List values) {
        if (splice.isEmpty()) {
            if (!values.isEmpty()) {
                throw new IllegalArgumentException("Trying to replace 0 elements with " + values.size() + " elements");
            }
            return;
        }
        Object first = splice.iterator().next();
        if (first instanceof Integer) {
            if (values.size() != splice.size()) {
                throw new IllegalArgumentException("Trying to replace " + splice.size() + " elements with " + values.size() + " elements");
            }
            Iterator valuesIter = values.iterator();
            for (Object index : splice) {
                DefaultGroovyMethods.putAt(self, (Integer)index, valuesIter.next());
            }
        } else {
            throw new IllegalArgumentException("Can only index a List with another List of Integers, not a List of " + first.getClass().getName());
        }
    }

    public static void putAt(List self, List splice, Object value) {
        if (splice.isEmpty()) {
            return;
        }
        Object first = splice.get(0);
        if (first instanceof Integer) {
            for (Object index : splice) {
                self.set((Integer)index, value);
            }
        } else {
            throw new IllegalArgumentException("Can only index a List with another List of Integers, not a List of " + first.getClass().getName());
        }
    }

    @Deprecated
    protected static List getSubList(List self, List splice) {
        int left;
        int right = 0;
        boolean emptyRange = false;
        if (splice.size() == 2) {
            left = DefaultTypeTransformation.intUnbox(splice.get(0));
            right = DefaultTypeTransformation.intUnbox(splice.get(1));
        } else if (splice instanceof IntRange) {
            IntRange range = (IntRange)splice;
            left = range.getFrom();
            right = range.getTo();
        } else if (splice instanceof EmptyRange) {
            RangeInfo info = DefaultGroovyMethods.subListBorders(self.size(), (EmptyRange)splice);
            left = info.from;
            emptyRange = true;
        } else {
            throw new IllegalArgumentException("You must specify a list of 2 indexes to create a sub-list");
        }
        int size = self.size();
        left = DefaultGroovyMethods.normaliseIndex(left, size);
        right = DefaultGroovyMethods.normaliseIndex(right, size);
        List sublist = !emptyRange ? self.subList(left, right + 1) : self.subList(left, left);
        return sublist;
    }

    public static <K, V> V getAt(Map<K, V> self, Object key) {
        return self.get(key);
    }

    public static <K, V> Map<K, V> plus(Map<K, V> left, Map<K, V> right) {
        Map<K, V> map = DefaultGroovyMethods.cloneSimilarMap(left);
        map.putAll(right);
        return map;
    }

    public static <K, V> V putAt(Map<K, V> self, K key, V value) {
        self.put(key, value);
        return value;
    }

    public static List getAt(Collection coll, String property) {
        ArrayList<Object> answer = new ArrayList<Object>(coll.size());
        return DefaultGroovyMethods.getAtIterable(coll, property, answer);
    }

    private static List getAtIterable(Iterable coll, String property, List<Object> answer) {
        for (Object item : coll) {
            Object value;
            if (item == null) continue;
            try {
                value = InvokerHelper.getProperty(item, property);
            }
            catch (MissingPropertyExceptionNoStack mpe) {
                String causeString = new MissingPropertyException(mpe.getProperty(), mpe.getType()).toString();
                throw new MissingPropertyException("Exception evaluating property '" + property + "' for " + coll.getClass().getName() + ", Reason: " + causeString);
            }
            answer.add(value);
        }
        return answer;
    }

    public static <K, V> Map<K, V> asImmutable(Map<K, V> self) {
        return DefaultGroovyMethods.asUnmodifiable(new LinkedHashMap<K, V>(self));
    }

    public static <K, V> SortedMap<K, V> asImmutable(SortedMap<K, V> self) {
        return DefaultGroovyMethods.asUnmodifiable(new TreeMap<K, V>(self));
    }

    public static <T> List<T> asImmutable(List<T> self) {
        return DefaultGroovyMethods.asUnmodifiable(new ArrayList<T>(self));
    }

    public static <T> Set<T> asImmutable(Set<T> self) {
        return DefaultGroovyMethods.asUnmodifiable(new LinkedHashSet<T>(self));
    }

    public static <T> SortedSet<T> asImmutable(SortedSet<T> self) {
        return DefaultGroovyMethods.asUnmodifiable(new TreeSet<T>(self));
    }

    public static <T> Collection<T> asImmutable(Collection<T> self) {
        return DefaultGroovyMethods.asUnmodifiable(new ArrayList<T>(self));
    }

    public static <K, V> Map<K, V> asUnmodifiable(Map<K, V> self) {
        return Collections.unmodifiableMap(self);
    }

    public static <K, V> SortedMap<K, V> asUnmodifiable(SortedMap<K, V> self) {
        return Collections.unmodifiableSortedMap(self);
    }

    public static <T> List<T> asUnmodifiable(List<T> self) {
        return Collections.unmodifiableList(self);
    }

    public static <T> Set<T> asUnmodifiable(Set<T> self) {
        return Collections.unmodifiableSet(self);
    }

    public static <T> SortedSet<T> asUnmodifiable(SortedSet<T> self) {
        return Collections.unmodifiableSortedSet(self);
    }

    public static <T> Collection<T> asUnmodifiable(Collection<T> self) {
        return Collections.unmodifiableCollection(self);
    }

    public static <K, V> Map<K, V> asSynchronized(Map<K, V> self) {
        return Collections.synchronizedMap(self);
    }

    public static <K, V> SortedMap<K, V> asSynchronized(SortedMap<K, V> self) {
        return Collections.synchronizedSortedMap(self);
    }

    public static <T> Collection<T> asSynchronized(Collection<T> self) {
        return Collections.synchronizedCollection(self);
    }

    public static <T> List<T> asSynchronized(List<T> self) {
        return Collections.synchronizedList(self);
    }

    public static <T> Set<T> asSynchronized(Set<T> self) {
        return Collections.synchronizedSet(self);
    }

    public static <T> SortedSet<T> asSynchronized(SortedSet<T> self) {
        return Collections.synchronizedSortedSet(self);
    }

    public static SpreadMap spread(Map self) {
        return DefaultGroovyMethods.toSpreadMap(self);
    }

    public static SpreadMap toSpreadMap(Map self) {
        if (self == null) {
            throw new GroovyRuntimeException("Fail to convert Map to SpreadMap, because it is null.");
        }
        return new SpreadMap(self);
    }

    public static SpreadMap toSpreadMap(Object[] self) {
        if (self == null) {
            throw new GroovyRuntimeException("Fail to convert Object[] to SpreadMap, because it is null.");
        }
        if (self.length % 2 != 0) {
            throw new GroovyRuntimeException("Fail to convert Object[] to SpreadMap, because its size is not even.");
        }
        return new SpreadMap(self);
    }

    public static SpreadMap toSpreadMap(List self) {
        if (self == null) {
            throw new GroovyRuntimeException("Fail to convert List to SpreadMap, because it is null.");
        }
        if (self.size() % 2 != 0) {
            throw new GroovyRuntimeException("Fail to convert List to SpreadMap, because its size is not even.");
        }
        return new SpreadMap(self);
    }

    public static SpreadMap toSpreadMap(Iterable self) {
        if (self == null) {
            throw new GroovyRuntimeException("Fail to convert Iterable to SpreadMap, because it is null.");
        }
        return DefaultGroovyMethods.toSpreadMap(DefaultGroovyMethods.asList(self));
    }

    public static <K, V> Map<K, V> withDefault(Map<K, V> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<V> init) {
        return MapWithDefault.newInstance(self, true, false, init);
    }

    public static <K, V> Map<K, V> withDefault(Map<K, V> self, boolean autoGrow, boolean autoShrink, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<V> init) {
        return MapWithDefault.newInstance(self, autoGrow, autoShrink, init);
    }

    public static <T> ListWithDefault<T> withDefault(List<T> self, @ClosureParams(value=SimpleType.class, options={"int"}) Closure<T> init) {
        return DefaultGroovyMethods.withLazyDefault(self, init);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ <T> List<T> withDefault(List<T> self, @ClosureParams(value=SimpleType.class, options={"int"}) Closure<T> init) {
        return DefaultGroovyMethods.withDefault(self, init);
    }

    public static <T> ListWithDefault<T> withLazyDefault(List<T> self, @ClosureParams(value=SimpleType.class, options={"int"}) Closure<T> init) {
        return ListWithDefault.newInstance(self, true, init);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ <T> List<T> withLazyDefault(List<T> self, @ClosureParams(value=SimpleType.class, options={"int"}) Closure<T> init) {
        return ListWithDefault.newInstance(self, true, init);
    }

    public static <T> ListWithDefault<T> withEagerDefault(List<T> self, @ClosureParams(value=SimpleType.class, options={"int"}) Closure<T> init) {
        return ListWithDefault.newInstance(self, false, init);
    }

    @Deprecated
    public static /* bridge */ /* synthetic */ <T> List<T> withEagerDefault(List<T> self, @ClosureParams(value=SimpleType.class, options={"int"}) Closure<T> init) {
        return ListWithDefault.newInstance(self, false, init);
    }

    public static <E> List<Tuple2<E, Integer>> withIndex(Iterable<E> self) {
        return DefaultGroovyMethods.withIndex(self, 0);
    }

    public static <E> Map<Integer, E> indexed(Iterable<E> self) {
        return DefaultGroovyMethods.indexed(self, 0);
    }

    public static <E> List<Tuple2<E, Integer>> withIndex(Iterable<E> self, int offset) {
        return DefaultGroovyMethods.toList(DefaultGroovyMethods.withIndex(self.iterator(), offset));
    }

    public static <E> Map<Integer, E> indexed(Iterable<E> self, int offset) {
        LinkedHashMap<Integer, E> result = new LinkedHashMap<Integer, E>();
        Iterator<Tuple2<Integer, E>> indexed = DefaultGroovyMethods.indexed(self.iterator(), offset);
        while (indexed.hasNext()) {
            Tuple2<Integer, E> next = indexed.next();
            result.put(next.getV1(), next.getV2());
        }
        return result;
    }

    public static Map<Integer, Integer> indexed(int[] self) {
        return DefaultGroovyMethods.indexed(self, 0);
    }

    public static Map<Integer, Integer> indexed(int[] self, int offset) {
        return DefaultGroovyMethods.indexed(new IntArrayIterable(self), offset);
    }

    public static Map<Integer, Long> indexed(long[] self) {
        return DefaultGroovyMethods.indexed(self, 0);
    }

    public static Map<Integer, Long> indexed(long[] self, int offset) {
        return DefaultGroovyMethods.indexed(new LongArrayIterable(self), offset);
    }

    public static Map<Integer, Double> indexed(double[] self) {
        return DefaultGroovyMethods.indexed(self, 0);
    }

    public static Map<Integer, Double> indexed(double[] self, int offset) {
        return DefaultGroovyMethods.indexed(new DoubleArrayIterable(self), offset);
    }

    public static <E> Iterator<Tuple2<E, Integer>> withIndex(Iterator<E> self) {
        return DefaultGroovyMethods.withIndex(self, 0);
    }

    public static <E> Iterator<Tuple2<Integer, E>> indexed(Iterator<E> self) {
        return DefaultGroovyMethods.indexed(self, 0);
    }

    public static <E> Iterator<Tuple2<E, Integer>> withIndex(Iterator<E> self, int offset) {
        return new ZipPostIterator(self, offset);
    }

    public static <E> Iterator<Tuple2<Integer, E>> indexed(Iterator<E> self, int offset) {
        return new ZipPreIterator(self, offset);
    }

    public static <T> List<T> sort(Iterable<T> self) {
        return DefaultGroovyMethods.sort(self, true);
    }

    public static <T> List<T> sort(Iterable<T> self, boolean mutate) {
        List<T> answer = mutate ? DefaultGroovyMethods.asList(self) : DefaultGroovyMethods.toList(self);
        answer.sort(new NumberAwareComparator());
        return answer;
    }

    public static <K, V> Map<K, V> sort(Map<K, V> self, @ClosureParams(value=FromString.class, options={"Map.Entry<K,V>", "Map.Entry<K,V>,Map.Entry<K,V>"}) Closure closure) {
        LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();
        List<Map.Entry<K, V>> entries = DefaultGroovyMethods.asList(self.entrySet());
        DefaultGroovyMethods.sort(entries, closure);
        for (Map.Entry<K, V> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static <K, V> Map<K, V> sort(Map<K, V> self, Comparator<? super K> comparator) {
        TreeMap<K, V> result = new TreeMap<K, V>(comparator);
        result.putAll(self);
        return result;
    }

    public static <K, V> Map<K, V> sort(Map<K, V> self) {
        return new TreeMap<K, V>(self);
    }

    public static <T> T[] sort(T[] self) {
        Arrays.sort(self, new NumberAwareComparator());
        return self;
    }

    public static <T> T[] sort(T[] self, boolean mutate) {
        T[] answer = mutate ? self : (Object[])self.clone();
        Arrays.sort(answer, new NumberAwareComparator());
        return answer;
    }

    public static <T> Iterator<T> sort(Iterator<T> self) {
        return DefaultGroovyMethods.sort(DefaultGroovyMethods.toList(self)).listIterator();
    }

    public static <T> Iterator<T> sort(Iterator<T> self, Comparator<? super T> comparator) {
        return DefaultGroovyMethods.sort(DefaultGroovyMethods.toList(self), true, comparator).listIterator();
    }

    public static <T> List<T> sort(Iterable<T> self, boolean mutate, Comparator<? super T> comparator) {
        List<T> list = mutate ? DefaultGroovyMethods.asList(self) : DefaultGroovyMethods.toList(self);
        list.sort(comparator);
        return list;
    }

    public static <T> T[] sort(T[] self, Comparator<? super T> comparator) {
        return DefaultGroovyMethods.sort(self, true, comparator);
    }

    public static <T> T[] sort(T[] self, boolean mutate, Comparator<? super T> comparator) {
        T[] answer = mutate ? self : (Object[])self.clone();
        Arrays.sort(answer, comparator);
        return answer;
    }

    public static <T> Iterator<T> sort(Iterator<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.sort(DefaultGroovyMethods.toList(self), closure).listIterator();
    }

    public static <T> T[] sort(T[] self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.sort(self, false, closure);
    }

    public static <T> T[] sort(T[] self, boolean mutate, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        if (!mutate) {
            self = (Object[])self.clone();
        }
        Serializable c = closure.getMaximumNumberOfParameters() == 1 ? new OrderBy(closure) : new ClosureComparator(closure);
        Arrays.sort(self, c);
        return self;
    }

    public static <T> List<T> sort(Iterable<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.sort(self, true, closure);
    }

    public static <T> List<T> sort(Iterable<T> self, boolean mutate, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        List<T> list = mutate ? DefaultGroovyMethods.asList(self) : DefaultGroovyMethods.toList(self);
        int params = closure.getMaximumNumberOfParameters();
        if (params == 1) {
            list.sort(new OrderBy(closure));
        } else {
            list.sort(new ClosureComparator(closure));
        }
        return list;
    }

    public static <T> SortedSet<T> sort(SortedSet<T> self) {
        return self;
    }

    public static <K, V> SortedMap<K, V> sort(SortedMap<K, V> self) {
        return self;
    }

    public static <T> List<T> toSorted(Iterable<T> self) {
        return DefaultGroovyMethods.toSorted(self, new NumberAwareComparator());
    }

    public static <T> List<T> toSorted(Iterable<T> self, Comparator<T> comparator) {
        List<T> list = DefaultGroovyMethods.toList(self);
        list.sort(comparator);
        return list;
    }

    public static <T> List<T> toSorted(Iterable<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        Serializable comparator = closure.getMaximumNumberOfParameters() == 1 ? new OrderBy(closure) : new ClosureComparator(closure);
        return DefaultGroovyMethods.toSorted(self, comparator);
    }

    public static <T> Iterator<T> toSorted(Iterator<T> self) {
        return DefaultGroovyMethods.toSorted(self, new NumberAwareComparator());
    }

    public static <T> Iterator<T> toSorted(Iterator<T> self, Comparator<T> comparator) {
        return DefaultGroovyMethods.toSorted(DefaultGroovyMethods.toList(self), comparator).listIterator();
    }

    public static <T> Iterator<T> toSorted(Iterator<T> self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        Serializable comparator = closure.getMaximumNumberOfParameters() == 1 ? new OrderBy(closure) : new ClosureComparator(closure);
        return DefaultGroovyMethods.toSorted(self, comparator);
    }

    public static <T> T[] toSorted(T[] self) {
        return DefaultGroovyMethods.toSorted(self, new NumberAwareComparator());
    }

    public static <T> T[] toSorted(T[] self, Comparator<T> comparator) {
        Object[] answer = (Object[])self.clone();
        Arrays.sort(answer, comparator);
        return answer;
    }

    public static <T> T[] toSorted(T[] self, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure closure) {
        return DefaultGroovyMethods.sort(self, false, closure);
    }

    public static <K, V> Map<K, V> toSorted(Map<K, V> self) {
        return DefaultGroovyMethods.toSorted(self, new NumberAwareValueComparator());
    }

    public static <K, V> Map<K, V> toSorted(Map<K, V> self, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> sortedEntries = DefaultGroovyMethods.toSorted(self.entrySet(), comparator);
        LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : sortedEntries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static <K, V> Map<K, V> toSorted(Map<K, V> self, @ClosureParams(value=FromString.class, options={"Map.Entry<K,V>", "Map.Entry<K,V>,Map.Entry<K,V>"}) Closure condition) {
        OrderBy<Map.Entry<K, V>> comparator = condition.getMaximumNumberOfParameters() == 1 ? new OrderBy(condition) : new ClosureComparator(condition);
        return DefaultGroovyMethods.toSorted(self, comparator);
    }

    public static <T> Set<T> toSorted(SortedSet<T> self) {
        return new LinkedHashSet<T>(self);
    }

    public static <K, V> Map<K, V> toSorted(SortedMap<K, V> self) {
        return new LinkedHashMap<K, V>(self);
    }

    public static <T> T pop(List<T> self) {
        if (self.isEmpty()) {
            throw new NoSuchElementException("Cannot pop() an empty List");
        }
        return self.remove(0);
    }

    public static <T> T removeLast(List<T> self) {
        if (self.isEmpty()) {
            throw new NoSuchElementException("Cannot removeLast() an empty List");
        }
        return self.remove(self.size() - 1);
    }

    public static <K, V> Map<K, V> putAll(Map<K, V> self, Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
        for (Map.Entry<K, V> entry : entries) {
            self.put(entry.getKey(), entry.getValue());
        }
        return self;
    }

    public static <K, V> Map<K, V> plus(Map<K, V> self, Collection<? extends Map.Entry<? extends K, ? extends V>> entries) {
        Map<K, V> map = DefaultGroovyMethods.cloneSimilarMap(self);
        DefaultGroovyMethods.putAll(map, entries);
        return map;
    }

    public static <T> boolean push(List<T> self, T value) {
        self.add(0, value);
        return true;
    }

    public static <T> T last(Deque<T> self) {
        if (self.isEmpty()) {
            throw new NoSuchElementException("Cannot access last() element from an empty Deque");
        }
        return self.getLast();
    }

    public static <T> T last(List<T> self) {
        if (self.isEmpty()) {
            throw new NoSuchElementException("Cannot access last() element from an empty List");
        }
        return self.get(self.size() - 1);
    }

    public static <T> T last(Iterable<T> self) {
        Iterator<T> iterator = self.iterator();
        if (!iterator.hasNext()) {
            throw new NoSuchElementException("Cannot access last() element from an empty Iterable");
        }
        T result = null;
        while (iterator.hasNext()) {
            result = iterator.next();
        }
        return result;
    }

    public static <T> T last(T[] self) {
        if (self.length == 0) {
            throw new NoSuchElementException("Cannot access last() element from an empty Array");
        }
        return self[self.length - 1];
    }

    public static <T> T first(List<T> self) {
        if (self.isEmpty()) {
            throw new NoSuchElementException("Cannot access first() element from an empty List");
        }
        return self.get(0);
    }

    public static <T> T first(Iterable<T> self) {
        Iterator<T> iterator = self.iterator();
        if (!iterator.hasNext()) {
            throw new NoSuchElementException("Cannot access first() element from an empty Iterable");
        }
        return iterator.next();
    }

    public static <T> T first(T[] self) {
        if (self.length == 0) {
            throw new NoSuchElementException("Cannot access first() element from an empty array");
        }
        return self[0];
    }

    public static <T> T head(Iterable<T> self) {
        return DefaultGroovyMethods.first(self);
    }

    public static <T> T head(List<T> self) {
        return DefaultGroovyMethods.first(self);
    }

    public static <T> T head(T[] self) {
        return DefaultGroovyMethods.first(self);
    }

    public static <T> List<T> tail(List<T> self) {
        return (List)DefaultGroovyMethods.tail(self);
    }

    public static <T> SortedSet<T> tail(SortedSet<T> self) {
        return (SortedSet)DefaultGroovyMethods.tail(self);
    }

    public static <T> List<List<T>> tails(Iterable<T> self) {
        return GroovyCollections.tails(self);
    }

    public static <T> Collection<T> tail(Iterable<T> self) {
        if (!self.iterator().hasNext()) {
            throw new NoSuchElementException("Cannot access tail() for an empty iterable");
        }
        Collection<T> result = DefaultGroovyMethods.createSimilarCollection(self);
        DefaultGroovyMethods.addAll(result, DefaultGroovyMethods.tail(self.iterator()));
        return result;
    }

    public static <T> T[] tail(T[] self) {
        if (self.length == 0) {
            throw new NoSuchElementException("Cannot access tail() for an empty array");
        }
        return Arrays.copyOfRange(self, 1, self.length);
    }

    public static <T> Iterator<T> tail(Iterator<T> self) {
        if (!self.hasNext()) {
            throw new NoSuchElementException("Cannot access tail() for an empty Iterator");
        }
        self.next();
        return self;
    }

    public static <T> List<List<T>> inits(Iterable<T> self) {
        return GroovyCollections.inits(self);
    }

    public static <T> Collection<T> init(Iterable<T> self) {
        Collection<Object> result;
        if (!self.iterator().hasNext()) {
            throw new NoSuchElementException("Cannot access init() for an empty Iterable");
        }
        if (self instanceof Collection) {
            Collection selfCol = (Collection)self;
            result = DefaultGroovyMethods.createSimilarCollection(selfCol, selfCol.size() - 1);
        } else {
            result = new ArrayList();
        }
        DefaultGroovyMethods.addAll(result, DefaultGroovyMethods.init(self.iterator()));
        return result;
    }

    public static <T> List<T> init(List<T> self) {
        return (List)DefaultGroovyMethods.init(self);
    }

    public static <T> SortedSet<T> init(SortedSet<T> self) {
        return (SortedSet)DefaultGroovyMethods.init(self);
    }

    public static <T> Iterator<T> init(Iterator<T> self) {
        if (!self.hasNext()) {
            throw new NoSuchElementException("Cannot access init() for an empty Iterator");
        }
        return new InitIterator(self);
    }

    public static <T> T[] init(T[] self) {
        if (self.length == 0) {
            throw new NoSuchElementException("Cannot access init() for an empty Object array");
        }
        return Arrays.copyOfRange(self, 0, self.length - 1);
    }

    public static <T> List<T> take(List<T> self, int num) {
        return (List)DefaultGroovyMethods.take(self, num);
    }

    public static <T> SortedSet<T> take(SortedSet<T> self, int num) {
        return (SortedSet)DefaultGroovyMethods.take(self, num);
    }

    public static <T> T[] take(T[] self, int num) {
        if (self.length == 0 || num <= 0) {
            return DefaultGroovyMethods.createSimilarArray(self, 0);
        }
        if (self.length <= num) {
            T[] ret = DefaultGroovyMethods.createSimilarArray(self, self.length);
            System.arraycopy(self, 0, ret, 0, self.length);
            return ret;
        }
        T[] ret = DefaultGroovyMethods.createSimilarArray(self, num);
        System.arraycopy(self, 0, ret, 0, num);
        return ret;
    }

    public static <T> Collection<T> take(Iterable<T> self, int num) {
        ArrayList result = self instanceof Collection ? DefaultGroovyMethods.createSimilarCollection((Collection)self, Math.max(num, 0)) : new ArrayList();
        DefaultGroovyMethods.addAll(result, DefaultGroovyMethods.take(self.iterator(), num));
        return result;
    }

    public static <T> boolean addAll(Collection<T> self, Iterator<? extends T> items) {
        boolean changed = false;
        while (items.hasNext()) {
            T next = items.next();
            if (!self.add(next)) continue;
            changed = true;
        }
        return changed;
    }

    public static <T> boolean addAll(Collection<T> self, Iterable<? extends T> items) {
        boolean changed = false;
        for (T next : items) {
            if (!self.add(next)) continue;
            changed = true;
        }
        return changed;
    }

    public static <K, V> Map<K, V> take(Map<K, V> self, int num) {
        if (self.isEmpty() || num <= 0) {
            return DefaultGroovyMethods.createSimilarMap(self);
        }
        Map<K, V> ret = DefaultGroovyMethods.createSimilarMap(self);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            ret.put(key, value);
            if (--num > 0) continue;
            break;
        }
        return ret;
    }

    public static <T> Iterator<T> take(Iterator<T> self, int num) {
        return new TakeIterator(self, num);
    }

    public static <T> T[] takeRight(T[] self, int num) {
        if (self.length == 0 || num <= 0) {
            return DefaultGroovyMethods.createSimilarArray(self, 0);
        }
        if (self.length <= num) {
            T[] ret = DefaultGroovyMethods.createSimilarArray(self, self.length);
            System.arraycopy(self, 0, ret, 0, self.length);
            return ret;
        }
        T[] ret = DefaultGroovyMethods.createSimilarArray(self, num);
        System.arraycopy(self, self.length - num, ret, 0, num);
        return ret;
    }

    public static <T> Collection<T> takeRight(Iterable<T> self, int num) {
        List<T> selfCol;
        if (num <= 0 || !self.iterator().hasNext()) {
            return self instanceof Collection ? DefaultGroovyMethods.createSimilarCollection((Collection)self, 0) : new ArrayList();
        }
        List<T> list = selfCol = self instanceof Collection ? (List<T>)self : DefaultGroovyMethods.toList(self);
        if (selfCol.size() <= num) {
            Collection<T> ret = DefaultGroovyMethods.createSimilarCollection(selfCol, selfCol.size());
            ret.addAll(selfCol);
            return ret;
        }
        Collection<T> ret = DefaultGroovyMethods.createSimilarCollection(selfCol, num);
        ret.addAll(DefaultGroovyMethods.asList(selfCol).subList(selfCol.size() - num, selfCol.size()));
        return ret;
    }

    public static <T> List<T> takeRight(List<T> self, int num) {
        return (List)DefaultGroovyMethods.takeRight(self, num);
    }

    public static <T> SortedSet<T> takeRight(SortedSet<T> self, int num) {
        return (SortedSet)DefaultGroovyMethods.takeRight(self, num);
    }

    public static <T> SortedSet<T> drop(SortedSet<T> self, int num) {
        return (SortedSet)DefaultGroovyMethods.drop(self, num);
    }

    public static <T> List<T> drop(List<T> self, int num) {
        return (List)DefaultGroovyMethods.drop(self, num);
    }

    public static <T> Collection<T> drop(Iterable<T> self, int num) {
        Collection<T> result = DefaultGroovyMethods.createSimilarCollection(self);
        DefaultGroovyMethods.addAll(result, DefaultGroovyMethods.drop(self.iterator(), num));
        return result;
    }

    public static <T> T[] drop(T[] self, int num) {
        if (self.length <= num) {
            return DefaultGroovyMethods.createSimilarArray(self, 0);
        }
        if (num <= 0) {
            T[] ret = DefaultGroovyMethods.createSimilarArray(self, self.length);
            System.arraycopy(self, 0, ret, 0, self.length);
            return ret;
        }
        T[] ret = DefaultGroovyMethods.createSimilarArray(self, self.length - num);
        System.arraycopy(self, num, ret, 0, self.length - num);
        return ret;
    }

    public static <K, V> Map<K, V> drop(Map<K, V> self, int num) {
        if (self.size() <= num) {
            return DefaultGroovyMethods.createSimilarMap(self);
        }
        if (num == 0) {
            return DefaultGroovyMethods.cloneSimilarMap(self);
        }
        Map<K, V> ret = DefaultGroovyMethods.createSimilarMap(self);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (num-- > 0) continue;
            ret.put(key, value);
        }
        return ret;
    }

    public static <T> Iterator<T> drop(Iterator<T> self, int num) {
        while (num-- > 0 && self.hasNext()) {
            self.next();
        }
        return self;
    }

    public static <T> SortedSet<T> dropRight(SortedSet<T> self, int num) {
        return (SortedSet)DefaultGroovyMethods.dropRight(self, num);
    }

    public static <T> List<T> dropRight(List<T> self, int num) {
        return (List)DefaultGroovyMethods.dropRight(self, num);
    }

    public static <T> Collection<T> dropRight(Iterable<T> self, int num) {
        List<T> selfCol;
        List<T> list = selfCol = self instanceof Collection ? (List<T>)self : DefaultGroovyMethods.toList(self);
        if (selfCol.size() <= num) {
            return DefaultGroovyMethods.createSimilarCollection(selfCol, 0);
        }
        if (num <= 0) {
            Collection<T> ret = DefaultGroovyMethods.createSimilarCollection(selfCol, selfCol.size());
            ret.addAll(selfCol);
            return ret;
        }
        Collection<T> ret = DefaultGroovyMethods.createSimilarCollection(selfCol, selfCol.size() - num);
        ret.addAll(DefaultGroovyMethods.asList(selfCol).subList(0, selfCol.size() - num));
        return ret;
    }

    public static <T> Iterator<T> dropRight(Iterator<T> self, int num) {
        if (num <= 0) {
            return self;
        }
        return new DropRightIterator(self, num);
    }

    public static <T> T[] dropRight(T[] self, int num) {
        if (self.length <= num) {
            return DefaultGroovyMethods.createSimilarArray(self, 0);
        }
        if (num <= 0) {
            T[] ret = DefaultGroovyMethods.createSimilarArray(self, self.length);
            System.arraycopy(self, 0, ret, 0, self.length);
            return ret;
        }
        T[] ret = DefaultGroovyMethods.createSimilarArray(self, self.length - num);
        System.arraycopy(self, 0, ret, 0, self.length - num);
        return ret;
    }

    public static <T> List<T> takeWhile(List<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        int num = 0;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        for (T value : self) {
            if (!bcw.call(value)) break;
            ++num;
        }
        return DefaultGroovyMethods.take(self, num);
    }

    public static <T> Collection<T> takeWhile(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        Collection<T> result = DefaultGroovyMethods.createSimilarCollection(self);
        DefaultGroovyMethods.addAll(result, DefaultGroovyMethods.takeWhile(self.iterator(), condition));
        return result;
    }

    public static <T> SortedSet<T> takeWhile(SortedSet<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return (SortedSet)DefaultGroovyMethods.takeWhile(self, condition);
    }

    public static <K, V> Map<K, V> takeWhile(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure condition) {
        if (self.isEmpty()) {
            return DefaultGroovyMethods.createSimilarMap(self);
        }
        Map<K, V> ret = DefaultGroovyMethods.createSimilarMap(self);
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            if (!bcw.callForMap(entry)) break;
            ret.put(entry.getKey(), entry.getValue());
        }
        return ret;
    }

    public static <T> T[] takeWhile(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        int num;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        for (num = 0; num < self.length; ++num) {
            T value = self[num];
            if (!bcw.call(value)) break;
        }
        return DefaultGroovyMethods.take(self, num);
    }

    public static <T> Iterator<T> takeWhile(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return new TakeWhileIterator(self, condition);
    }

    public static <T> SortedSet<T> dropWhile(SortedSet<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return (SortedSet)DefaultGroovyMethods.dropWhile(self, condition);
    }

    public static <T> List<T> dropWhile(List<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        int num = 0;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        for (T value : self) {
            if (!bcw.call(value)) break;
            ++num;
        }
        return DefaultGroovyMethods.drop(self, num);
    }

    public static <T> Collection<T> dropWhile(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        List<T> selfCol = self instanceof Collection ? (List<T>)self : DefaultGroovyMethods.toList(self);
        Collection<T> result = DefaultGroovyMethods.createSimilarCollection(selfCol);
        DefaultGroovyMethods.addAll(result, DefaultGroovyMethods.dropWhile(self.iterator(), condition));
        return result;
    }

    public static <K, V> Map<K, V> dropWhile(Map<K, V> self, @ClosureParams(value=MapEntryOrKeyValue.class) Closure condition) {
        if (self.isEmpty()) {
            return DefaultGroovyMethods.createSimilarMap(self);
        }
        Map<K, V> ret = DefaultGroovyMethods.createSimilarMap(self);
        boolean dropping = true;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        for (Map.Entry<K, V> entry : self.entrySet()) {
            if (dropping && !bcw.callForMap(entry)) {
                dropping = false;
            }
            if (dropping) continue;
            ret.put(entry.getKey(), entry.getValue());
        }
        return ret;
    }

    public static <T> T[] dropWhile(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure<?> condition) {
        int num;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        for (num = 0; num < self.length && bcw.call(self[num]); ++num) {
        }
        return DefaultGroovyMethods.drop(self, num);
    }

    public static <T> Iterator<T> dropWhile(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure<?> condition) {
        return new DropWhileIterator(self, condition);
    }

    public static <T> Collection<T> asCollection(Iterable<T> self) {
        if (self instanceof Collection) {
            return (Collection)self;
        }
        return DefaultGroovyMethods.toList(self);
    }

    public static <T> List<T> asList(Iterable<T> self) {
        if (self instanceof List) {
            return (List)self;
        }
        return DefaultGroovyMethods.toList(self);
    }

    public static boolean asBoolean(Object object) {
        return object != null;
    }

    public static boolean asBoolean(Boolean bool) {
        if (null == bool) {
            return false;
        }
        return bool;
    }

    public static boolean asBoolean(Collection collection) {
        if (null == collection) {
            return false;
        }
        return !collection.isEmpty();
    }

    public static boolean asBoolean(Map map) {
        if (null == map) {
            return false;
        }
        return !map.isEmpty();
    }

    public static boolean asBoolean(Iterator iterator) {
        if (null == iterator) {
            return false;
        }
        return iterator.hasNext();
    }

    public static boolean asBoolean(Enumeration enumeration) {
        if (null == enumeration) {
            return false;
        }
        return enumeration.hasMoreElements();
    }

    public static boolean asBoolean(Object[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(byte[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(short[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(int[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(long[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(float[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(double[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(boolean[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(char[] array) {
        if (null == array) {
            return false;
        }
        return array.length > 0;
    }

    public static boolean asBoolean(Character character) {
        if (null == character) {
            return false;
        }
        return character.charValue() != '\u0000';
    }

    public static boolean asBoolean(Float object) {
        float f = object.floatValue();
        return f != 0.0f && !Float.isNaN(f);
    }

    public static boolean asBoolean(Double object) {
        double d = object;
        return d != 0.0 && !Double.isNaN(d);
    }

    public static boolean asBoolean(Number number) {
        if (null == number) {
            return false;
        }
        return number.doubleValue() != 0.0;
    }

    public static <T> T asType(Iterable iterable, Class<T> clazz) {
        if (Collection.class.isAssignableFrom(clazz)) {
            return DefaultGroovyMethods.asType(DefaultGroovyMethods.toList(iterable), clazz);
        }
        return DefaultGroovyMethods.asType((Object)iterable, clazz);
    }

    public static <T> T asType(Collection col, Class<T> clazz) {
        if (col.getClass() == clazz) {
            return (T)col;
        }
        if (clazz == List.class) {
            return (T)DefaultGroovyMethods.asList(col);
        }
        if (clazz == Set.class) {
            if (col instanceof Set) {
                return (T)col;
            }
            return (T)new LinkedHashSet(col);
        }
        if (clazz == SortedSet.class) {
            if (col instanceof SortedSet) {
                return (T)col;
            }
            return (T)new TreeSet(col);
        }
        if (clazz == Queue.class) {
            if (col instanceof Queue) {
                return (T)col;
            }
            return (T)new LinkedList(col);
        }
        if (clazz == Stack.class) {
            if (col instanceof Stack) {
                return (T)col;
            }
            Stack stack = new Stack();
            stack.addAll(col);
            return (T)stack;
        }
        if (clazz != String[].class && ReflectionCache.isArray(clazz)) {
            try {
                return (T)DefaultGroovyMethods.asArrayType(col, clazz);
            }
            catch (GroovyCastException stack) {
                // empty catch block
            }
        }
        Object[] args = new Object[]{col};
        try {
            return (T)InvokerHelper.invokeConstructorOf(clazz, (Object)args);
        }
        catch (Exception exception) {
            if (Collection.class.isAssignableFrom(clazz)) {
                try {
                    Collection result = (Collection)InvokerHelper.invokeConstructorOf(clazz, null);
                    result.addAll(col);
                    return (T)result;
                }
                catch (Exception exception2) {
                    // empty catch block
                }
            }
            return DefaultGroovyMethods.asType((Object)col, clazz);
        }
    }

    public static <T> T asType(Object[] ary, Class<T> clazz) {
        if (clazz == List.class) {
            return (T)new ArrayList<Object>(Arrays.asList(ary));
        }
        if (clazz == Set.class) {
            return (T)new HashSet<Object>(Arrays.asList(ary));
        }
        if (clazz == SortedSet.class) {
            return (T)new TreeSet<Object>(Arrays.asList(ary));
        }
        return DefaultGroovyMethods.asType((Object)ary, clazz);
    }

    public static <T> T asType(Closure impl, Class<T> type) {
        if (type.isInterface() && !type.isInstance(impl)) {
            Method sam;
            if ((Traits.isTrait(type) || ClassHelper.make(type).getAllInterfaces().stream().anyMatch(Traits::isTrait)) && (sam = CachedSAMClass.getSAMMethod(type)) != null) {
                return (T)ProxyGenerator.INSTANCE.instantiateAggregate(Collections.singletonMap(sam.getName(), impl), Collections.singletonList(type));
            }
            return (T)Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, (InvocationHandler)new ConvertedClosure(impl));
        }
        try {
            return DefaultGroovyMethods.asType((Object)impl, type);
        }
        catch (GroovyCastException gce) {
            try {
                return (T)ProxyGenerator.INSTANCE.instantiateAggregateFromBaseClass(impl, type);
            }
            catch (GroovyRuntimeException gre) {
                throw new GroovyCastException("Error casting closure to " + type.getName() + ", Reason: " + gre.getMessage());
            }
        }
    }

    public static <T> T asType(Map map, Class<T> clazz) {
        if (!clazz.isInstance(map) && clazz.isInterface() && !Traits.isTrait(clazz)) {
            return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (InvocationHandler)new ConvertedMap(map));
        }
        try {
            return DefaultGroovyMethods.asType((Object)map, clazz);
        }
        catch (GroovyCastException ce) {
            try {
                return (T)ProxyGenerator.INSTANCE.instantiateAggregateFromBaseClass(map, clazz);
            }
            catch (GroovyRuntimeException cause) {
                throw new GroovyCastException("Error casting map to " + clazz.getName() + ", Reason: " + cause.getMessage());
            }
        }
    }

    public static void shuffle(List<?> self) {
        Collections.shuffle(self);
    }

    public static void shuffle(List<?> self, Random rnd) {
        Collections.shuffle(self, rnd);
    }

    public static <T> List<T> shuffled(List<T> self) {
        ArrayList<T> copy = new ArrayList<T>(self);
        Collections.shuffle(copy);
        return copy;
    }

    public static <T> List<T> shuffled(List<T> self, Random rnd) {
        ArrayList<T> copy = new ArrayList<T>(self);
        Collections.shuffle(copy, rnd);
        return copy;
    }

    public static <T> void shuffle(T[] self) {
        Random rnd = r;
        if (rnd == null) {
            r = rnd = new Random();
        }
        DefaultGroovyMethods.shuffle(self, rnd);
    }

    public static <T> void shuffle(T[] self, Random rnd) {
        for (int i = 0; i < self.length - 1; ++i) {
            int nextIndex = rnd.nextInt(self.length);
            T tmp = self[i];
            self[i] = self[nextIndex];
            self[nextIndex] = tmp;
        }
    }

    public static <T> T[] shuffled(T[] self) {
        Random rnd = r;
        if (rnd == null) {
            r = rnd = new Random();
        }
        return DefaultGroovyMethods.shuffled(self, rnd);
    }

    public static <T> T[] shuffled(T[] self, Random rnd) {
        Object[] result = (Object[])self.clone();
        List<T> items = Arrays.asList(self);
        Collections.shuffle(items, rnd);
        System.arraycopy(items.toArray(), 0, result, 0, items.size());
        return result;
    }

    public static <T> List<T> asReversed(List<T> self) {
        return new ReversedList<T>(self);
    }

    public static <T> List<T> reverse(List<T> self) {
        return DefaultGroovyMethods.reverse(self, false);
    }

    public static <T> List<T> reverse(List<T> self, boolean mutate) {
        if (mutate) {
            Collections.reverse(self);
            return self;
        }
        int size = self.size();
        ArrayList<T> answer = new ArrayList<T>(size);
        ListIterator<T> iter = self.listIterator(size);
        while (iter.hasPrevious()) {
            answer.add(iter.previous());
        }
        return answer;
    }

    public static <T> T[] reverse(T[] self) {
        return DefaultGroovyMethods.reverse(self, false);
    }

    public static <T> T[] reverse(T[] self, boolean mutate) {
        if (!mutate) {
            self = (Object[])self.clone();
        }
        Collections.reverse(Arrays.asList(self));
        return self;
    }

    public static <T> Iterator<T> reverse(Iterator<T> self) {
        return new ReverseListIterator<T>(DefaultGroovyMethods.toList(self));
    }

    public static <T> T[] plus(T[] left, Object[] right) {
        T[] result = Arrays.copyOf(left, left.length + right.length);
        Object[] temp = (Object[])DefaultTypeTransformation.castToType(right, left.getClass());
        System.arraycopy(temp, 0, result, left.length, temp.length);
        return result;
    }

    public static <T> T[] plus(T[] left, Object right) {
        T[] result = Arrays.copyOf(left, left.length + 1);
        result[left.length] = DefaultTypeTransformation.castToType(right, left.getClass().getComponentType());
        return result;
    }

    public static <T> T[] plus(T[] left, Collection<?> right) {
        T[] result = Arrays.copyOf(left, left.length + right.size());
        int i = left.length;
        Class<?> leftType = left.getClass().getComponentType();
        for (Object t : right) {
            result[i] = DefaultTypeTransformation.castToType(t, leftType);
            ++i;
        }
        return result;
    }

    public static <T> T[] plus(T[] left, Iterable<?> right) {
        return DefaultGroovyMethods.plus(left, DefaultGroovyMethods.toList(right));
    }

    public static Object[] union(Object[] left, Object[] right) {
        Object[] result = new Object[left.length + right.length];
        System.arraycopy(left, 0, result, 0, left.length);
        System.arraycopy(right, 0, result, left.length, right.length);
        return result;
    }

    public static Object[] union(Object[] left, Object right) {
        Object[] result = new Object[left.length + 1];
        System.arraycopy(left, 0, result, 0, left.length);
        result[left.length] = right;
        return result;
    }

    public static Object[] union(Object[] left, Collection<?> right) {
        Object[] result = new Object[left.length + right.size()];
        System.arraycopy(left, 0, result, 0, left.length);
        int i = left.length;
        for (Object t : right) {
            result[i] = t;
            ++i;
        }
        return result;
    }

    public static Object[] union(Object[] left, Iterable<?> right) {
        return DefaultGroovyMethods.union(left, DefaultGroovyMethods.toList(right));
    }

    public static <T> Collection<T> plus(Collection<T> left, Collection<T> right) {
        Collection<T> answer = DefaultGroovyMethods.cloneSimilarCollection(left, left.size() + right.size());
        answer.addAll(right);
        return answer;
    }

    public static <T> Collection<T> plus(Iterable<T> left, Iterable<T> right) {
        return DefaultGroovyMethods.plus(DefaultGroovyMethods.asCollection(left), DefaultGroovyMethods.asCollection(right));
    }

    public static <T> Collection<T> plus(Collection<T> left, Iterable<T> right) {
        return DefaultGroovyMethods.plus(left, DefaultGroovyMethods.asCollection(right));
    }

    public static <T> List<T> plus(List<T> left, Iterable<T> right) {
        return (List)DefaultGroovyMethods.plus(left, DefaultGroovyMethods.asCollection(right));
    }

    public static <T> List<T> plus(List<T> left, Collection<T> right) {
        return (List)DefaultGroovyMethods.plus(left, right);
    }

    public static <T> Set<T> plus(Set<T> left, Iterable<T> right) {
        return (Set)DefaultGroovyMethods.plus(left, DefaultGroovyMethods.asCollection(right));
    }

    public static <T> Set<T> plus(Set<T> left, Collection<T> right) {
        return (Set)DefaultGroovyMethods.plus(left, right);
    }

    public static <T> SortedSet<T> plus(SortedSet<T> left, Iterable<T> right) {
        return (SortedSet)DefaultGroovyMethods.plus(left, DefaultGroovyMethods.asCollection(right));
    }

    public static <T> SortedSet<T> plus(SortedSet<T> left, Collection<T> right) {
        return (SortedSet)DefaultGroovyMethods.plus(left, right);
    }

    public static <T> List<T> plus(List<T> self, int index, T[] items) {
        return DefaultGroovyMethods.plus(self, index, Arrays.asList(items));
    }

    public static <T> List<T> plus(List<T> self, int index, List<T> additions) {
        ArrayList<T> answer = new ArrayList<T>(self);
        answer.addAll(index, additions);
        return answer;
    }

    public static <T> List<T> plus(List<T> self, int index, Iterable<T> additions) {
        return DefaultGroovyMethods.plus(self, index, DefaultGroovyMethods.toList(additions));
    }

    public static <T> Collection<T> plus(Collection<T> left, T right) {
        Collection<T> answer = DefaultGroovyMethods.cloneSimilarCollection(left, left.size() + 1);
        answer.add(right);
        return answer;
    }

    public static <T> Collection<T> plus(Iterable<T> left, T right) {
        return DefaultGroovyMethods.plus(DefaultGroovyMethods.asCollection(left), right);
    }

    public static <T> List<T> plus(List<T> left, T right) {
        return (List)DefaultGroovyMethods.plus(left, right);
    }

    public static <T> Set<T> plus(Set<T> left, T right) {
        return (Set)DefaultGroovyMethods.plus(left, right);
    }

    public static <T> SortedSet<T> plus(SortedSet<T> left, T right) {
        return (SortedSet)DefaultGroovyMethods.plus(left, right);
    }

    public static <T> Collection<T> multiply(Iterable<T> self, Number factor) {
        Collection<T> selfCol = DefaultGroovyMethods.asCollection(self);
        int size = factor.intValue();
        Collection<T> answer = DefaultGroovyMethods.createSimilarCollection(selfCol, selfCol.size() * size);
        for (int i = 0; i < size; ++i) {
            answer.addAll(selfCol);
        }
        return answer;
    }

    public static <T> List<T> multiply(List<T> self, Number factor) {
        return (List)DefaultGroovyMethods.multiply(self, factor);
    }

    public static <T> Collection<T> intersect(Collection<T> left, Collection<T> right) {
        return DefaultGroovyMethods.intersect(left, right, new NumberAwareComparator());
    }

    public static <T> Collection<T> intersect(Collection<T> left, Collection<T> right, Comparator<T> comparator) {
        if (left.isEmpty() || right.isEmpty()) {
            return DefaultGroovyMethods.createSimilarCollection(left, 0);
        }
        Collection<T> result = DefaultGroovyMethods.createSimilarCollection(left, Math.min(left.size(), right.size()));
        TreeSet<T> compareWith = new TreeSet<T>(comparator);
        compareWith.addAll(right);
        for (T t : left) {
            if (!compareWith.contains(t)) continue;
            result.add(t);
        }
        return result;
    }

    public static <T> Collection<T> intersect(Iterable<T> left, Iterable<T> right) {
        return DefaultGroovyMethods.intersect(DefaultGroovyMethods.asCollection(left), DefaultGroovyMethods.asCollection(right));
    }

    public static <T> Collection<T> intersect(Iterable<T> left, Iterable<T> right, Comparator<T> comparator) {
        return DefaultGroovyMethods.intersect(DefaultGroovyMethods.asCollection(left), DefaultGroovyMethods.asCollection(right), comparator);
    }

    public static <T> Collection<T> intersect(Iterable<T> left, Iterable<T> right, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition) {
        Serializable comparator = condition.getMaximumNumberOfParameters() == 1 ? new OrderBy(condition, true) : new ClosureComparator(condition);
        return DefaultGroovyMethods.intersect(left, right, comparator);
    }

    public static <T> List<T> intersect(List<T> left, Iterable<T> right) {
        return (List)DefaultGroovyMethods.intersect(left, DefaultGroovyMethods.asCollection(right));
    }

    public static <T> List<T> intersect(List<T> left, Iterable<T> right, Comparator<T> comparator) {
        return (List)DefaultGroovyMethods.intersect(left, DefaultGroovyMethods.asCollection(right), comparator);
    }

    public static <T> Set<T> intersect(Set<T> left, Iterable<T> right) {
        return (Set)DefaultGroovyMethods.intersect(left, DefaultGroovyMethods.asCollection(right));
    }

    public static <T> Set<T> intersect(Set<T> left, Iterable<T> right, Comparator<T> comparator) {
        return (Set)DefaultGroovyMethods.intersect(left, DefaultGroovyMethods.asCollection(right), comparator);
    }

    public static <T> SortedSet<T> intersect(SortedSet<T> left, Iterable<T> right) {
        return (SortedSet)DefaultGroovyMethods.intersect(left, DefaultGroovyMethods.asCollection(right));
    }

    public static <T> SortedSet<T> intersect(SortedSet<T> left, Iterable<T> right, Comparator<T> comparator) {
        return (SortedSet)DefaultGroovyMethods.intersect(left, DefaultGroovyMethods.asCollection(right), comparator);
    }

    public static <K, V> Map<K, V> intersect(Map<K, V> left, Map<K, V> right) {
        Map<K, V> ansMap = DefaultGroovyMethods.createSimilarMap(left);
        if (right != null && !right.isEmpty()) {
            for (Map.Entry<K, V> e1 : left.entrySet()) {
                for (Map.Entry<K, V> e2 : right.entrySet()) {
                    if (!DefaultTypeTransformation.compareEqual(e1, e2)) continue;
                    ansMap.put(e1.getKey(), e1.getValue());
                }
            }
        }
        return ansMap;
    }

    public static boolean disjoint(Iterable left, Iterable right) {
        Collection leftCol = DefaultGroovyMethods.asCollection(left);
        Collection rightCol = DefaultGroovyMethods.asCollection(right);
        if (leftCol.isEmpty() || rightCol.isEmpty()) {
            return true;
        }
        TreeSet pickFrom = new TreeSet(new NumberAwareComparator());
        pickFrom.addAll(rightCol);
        for (Object o : leftCol) {
            if (!pickFrom.contains(o)) continue;
            return false;
        }
        return true;
    }

    public static <T> List<List<T>> chop(T[] self, int ... chopSizes) {
        return DefaultGroovyMethods.chop(Arrays.asList(self), chopSizes);
    }

    public static <T> List<List<T>> chop(Iterable<T> self, int ... chopSizes) {
        return DefaultGroovyMethods.chop(self.iterator(), chopSizes);
    }

    public static <T> List<List<T>> chop(Iterator<T> self, int ... chopSizes) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        int[] nArray = chopSizes;
        int n = nArray.length;
        for (int i = 0; i < n; ++i) {
            int nextSize;
            int size = nextSize = nArray[i];
            ArrayList<T> next = new ArrayList<T>();
            while (size-- != 0 && self.hasNext()) {
                next.add(self.next());
            }
            result.add(next);
        }
        return result;
    }

    public static boolean equals(int[] left, int[] right) {
        if (left == null) {
            return right == null;
        }
        if (right == null) {
            return false;
        }
        if (left == right) {
            return true;
        }
        if (left.length != right.length) {
            return false;
        }
        for (int i = 0; i < left.length; ++i) {
            if (left[i] == right[i]) continue;
            return false;
        }
        return true;
    }

    public static boolean equals(Object[] left, List right) {
        return DefaultGroovyMethods.coercedEquals(left, right);
    }

    public static boolean equals(List left, Object[] right) {
        return DefaultGroovyMethods.coercedEquals(right, left);
    }

    private static boolean coercedEquals(Object[] left, List right) {
        if (left == null) {
            return right == null;
        }
        if (right == null) {
            return false;
        }
        if (left.length != right.size()) {
            return false;
        }
        for (int i = left.length - 1; i >= 0; --i) {
            Object o1 = left[i];
            Object o2 = right.get(i);
            if (!(o1 == null ? o2 != null : !DefaultGroovyMethods.coercedEquals(o1, o2))) continue;
            return false;
        }
        return true;
    }

    private static boolean coercedEquals(Object o1, Object o2) {
        if (o1 instanceof Comparable && (!(o2 instanceof Comparable) || DefaultGroovyMethods.numberAwareCompareTo((Comparable)o1, (Comparable)o2) != 0)) {
            return false;
        }
        return DefaultTypeTransformation.compareEqual(o1, o2);
    }

    public static boolean equals(List left, List right) {
        if (left == null) {
            return right == null;
        }
        if (right == null) {
            return false;
        }
        if (left == right) {
            return true;
        }
        if (left.size() != right.size()) {
            return false;
        }
        Iterator it1 = left.iterator();
        Iterator it2 = right.iterator();
        while (it1.hasNext()) {
            Object o1 = it1.next();
            Object o2 = it2.next();
            if (!(o1 == null ? o2 != null : !DefaultGroovyMethods.coercedEquals(o1, o2))) continue;
            return false;
        }
        return true;
    }

    public static <T> boolean equals(Set<T> self, Set<T> other) {
        if (self == null) {
            return other == null;
        }
        if (other == null) {
            return false;
        }
        if (self == other) {
            return true;
        }
        if (self.size() != other.size()) {
            return false;
        }
        Iterator<T> it1 = self.iterator();
        HashSet<T> otherItems = new HashSet<T>(other);
        while (it1.hasNext()) {
            T o1 = it1.next();
            Iterator it2 = otherItems.iterator();
            Object foundItem = null;
            boolean found = false;
            while (it2.hasNext() && foundItem == null) {
                Object o2 = it2.next();
                if (!DefaultGroovyMethods.coercedEquals(o1, o2)) continue;
                foundItem = o2;
                found = true;
            }
            if (!found) {
                return false;
            }
            otherItems.remove(foundItem);
        }
        return otherItems.isEmpty();
    }

    public static boolean equals(Map self, Map other) {
        if (self == null) {
            return other == null;
        }
        if (other == null) {
            return false;
        }
        if (self == other) {
            return true;
        }
        if (self.size() != other.size()) {
            return false;
        }
        if (!self.keySet().equals(other.keySet())) {
            return false;
        }
        Iterator iterator = self.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry o;
            Map.Entry entry = o = iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (DefaultGroovyMethods.coercedEquals(value, other.get(key))) continue;
            return false;
        }
        return true;
    }

    public static <T> Set<T> minus(Set<T> self, Collection<?> removeMe) {
        Comparator comparator = self instanceof SortedSet ? ((SortedSet)self).comparator() : null;
        Set<T> ansSet = DefaultGroovyMethods.createSimilarSet(self);
        ansSet.addAll(self);
        if (removeMe != null) {
            for (T o1 : self) {
                for (Object o2 : removeMe) {
                    boolean bl = comparator != null ? comparator.compare(o1, o2) == 0 : DefaultGroovyMethods.coercedEquals(o1, o2);
                    boolean areEqual = bl;
                    if (!areEqual) continue;
                    ansSet.remove(o1);
                }
            }
        }
        return ansSet;
    }

    public static <T> Set<T> minus(Set<T> self, Iterable<?> removeMe) {
        return DefaultGroovyMethods.minus(self, DefaultGroovyMethods.asCollection(removeMe));
    }

    public static <T> Set<T> minus(Set<T> self, Object removeMe) {
        Comparator comparator = self instanceof SortedSet ? ((SortedSet)self).comparator() : null;
        Set<T> ansSet = DefaultGroovyMethods.createSimilarSet(self);
        for (T t : self) {
            boolean areEqual = comparator != null ? comparator.compare(t, removeMe) == 0 : DefaultGroovyMethods.coercedEquals(t, removeMe);
            if (areEqual) continue;
            ansSet.add(t);
        }
        return ansSet;
    }

    public static <T> SortedSet<T> minus(SortedSet<T> self, Collection<?> removeMe) {
        return (SortedSet)DefaultGroovyMethods.minus(self, removeMe);
    }

    public static <T> SortedSet<T> minus(SortedSet<T> self, Iterable<?> removeMe) {
        return (SortedSet)DefaultGroovyMethods.minus(self, removeMe);
    }

    public static <T> SortedSet<T> minus(SortedSet<T> self, Object removeMe) {
        return (SortedSet)DefaultGroovyMethods.minus(self, removeMe);
    }

    public static <T> T[] minus(T[] self, Iterable removeMe) {
        Collection<T> temp = DefaultGroovyMethods.minus(DefaultGroovyMethods.toList(self), removeMe);
        return temp.toArray(DefaultGroovyMethods.createSimilarArray(self, temp.size()));
    }

    public static <T> T[] minus(T[] self, Object[] removeMe) {
        switch (removeMe.length) {
            case 0: {
                return (Object[])self.clone();
            }
            case 1: {
                return DefaultGroovyMethods.minus(self, removeMe[0]);
            }
        }
        Collection<T> temp = DefaultGroovyMethods.minus(DefaultGroovyMethods.toList(self), Arrays.asList(removeMe));
        return temp.toArray(DefaultGroovyMethods.createSimilarArray(self, temp.size()));
    }

    public static <T> List<T> minus(List<T> self, Collection<?> removeMe) {
        return (List)DefaultGroovyMethods.minus(self, removeMe);
    }

    public static <T> Collection<T> minus(Collection<T> self, Collection<?> removeMe) {
        return DefaultGroovyMethods.minus(self, removeMe, new NumberAwareComparator());
    }

    public static <T> List<T> minus(List<T> self, Iterable<?> removeMe) {
        return (List)DefaultGroovyMethods.minus(self, removeMe);
    }

    public static <T> Collection<T> minus(Iterable<T> self, Iterable<?> removeMe) {
        return DefaultGroovyMethods.minus(DefaultGroovyMethods.asCollection(self), DefaultGroovyMethods.asCollection(removeMe));
    }

    public static <T> Collection<T> minus(Iterable<T> self, Iterable<?> removeMe, @ClosureParams(value=FromString.class, options={"T", "T,T"}) Closure condition) {
        Serializable comparator = condition.getMaximumNumberOfParameters() == 1 ? new OrderBy(condition, true) : new ClosureComparator(condition);
        return DefaultGroovyMethods.minus(self, removeMe, comparator);
    }

    public static <T> Collection<T> minus(Iterable<T> self, Iterable<?> removeMe, Comparator<T> comparator) {
        Collection<T> self1 = DefaultGroovyMethods.asCollection(self);
        Collection<?> removeMe1 = DefaultGroovyMethods.asCollection(removeMe);
        Collection<T> ansCollection = DefaultGroovyMethods.createSimilarCollection(self1);
        if (self1.isEmpty()) {
            return ansCollection;
        }
        T head = self1.iterator().next();
        boolean nlgnSort = DefaultGroovyMethods.sameType(new Collection[]{self1, removeMe1});
        if (nlgnSort && head instanceof Comparable) {
            TreeSet<T> answer;
            if (head instanceof Number) {
                answer = new TreeSet<T>(comparator);
                answer.addAll(self1);
                for (T t : self1) {
                    if (t instanceof Number) {
                        for (Object t2 : removeMe1) {
                            if (!(t2 instanceof Number) || comparator.compare(t, t2) != 0) continue;
                            answer.remove(t);
                        }
                        continue;
                    }
                    if (!removeMe1.contains(t)) continue;
                    answer.remove(t);
                }
            } else {
                answer = new TreeSet<T>(comparator);
                answer.addAll(self1);
                answer.removeAll(removeMe1);
            }
            for (T o : self1) {
                if (!answer.contains(o)) continue;
                ansCollection.add(o);
            }
        } else {
            LinkedList<T> tmpAnswer = new LinkedList<T>(self1);
            Iterator iter = tmpAnswer.iterator();
            while (iter.hasNext()) {
                Object element = iter.next();
                boolean elementRemoved = false;
                Iterator<?> iterator = removeMe1.iterator();
                while (iterator.hasNext() && !elementRemoved) {
                    Object elt = iterator.next();
                    if (!DefaultTypeTransformation.compareEqual(element, elt)) continue;
                    iter.remove();
                    elementRemoved = true;
                }
            }
            ansCollection.addAll(tmpAnswer);
        }
        return ansCollection;
    }

    public static <T> List<T> minus(List<T> self, Object removeMe) {
        return (List)DefaultGroovyMethods.minus(self, removeMe);
    }

    public static <T> Collection<T> minus(Iterable<T> self, Object removeMe) {
        Collection<T> ansList = DefaultGroovyMethods.createSimilarCollection(self);
        for (T t : self) {
            if (DefaultGroovyMethods.coercedEquals(t, removeMe)) continue;
            ansList.add(t);
        }
        return ansList;
    }

    public static <T> T[] minus(T[] self, Object removeMe) {
        int i = 0;
        int n = self.length;
        T[] result = DefaultGroovyMethods.createSimilarArray(self, n);
        for (T t : self) {
            if (DefaultGroovyMethods.coercedEquals(t, removeMe)) continue;
            result[i] = t;
            ++i;
        }
        if (i != n) {
            result = Arrays.copyOfRange(result, 0, i);
        }
        return result;
    }

    public static <K, V> Map<K, V> minus(Map<K, V> self, Map removeMe) {
        Map<K, V> ansMap = DefaultGroovyMethods.createSimilarMap(self);
        ansMap.putAll(self);
        if (removeMe != null && !removeMe.isEmpty()) {
            for (Map.Entry<K, V> e1 : self.entrySet()) {
                for (Map.Entry e2 : removeMe.entrySet()) {
                    if (!DefaultTypeTransformation.compareEqual(e1, e2)) continue;
                    ansMap.remove(e1.getKey());
                }
            }
        }
        return ansMap;
    }

    public static Collection<?> flatten(Collection<?> self) {
        return DefaultGroovyMethods.flatten(self, DefaultGroovyMethods.createSimilarCollection(self));
    }

    public static Collection<?> flatten(Iterable<?> self) {
        return DefaultGroovyMethods.flatten(self, DefaultGroovyMethods.createSimilarCollection(self));
    }

    public static List<?> flatten(List<?> self) {
        return (List)DefaultGroovyMethods.flatten(self);
    }

    public static Set<?> flatten(Set<?> self) {
        return (Set)DefaultGroovyMethods.flatten(self);
    }

    public static SortedSet<?> flatten(SortedSet<?> self) {
        return (SortedSet)DefaultGroovyMethods.flatten(self);
    }

    public static Collection flatten(Object[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    public static Collection flatten(boolean[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    public static Collection flatten(byte[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    public static Collection flatten(char[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    public static Collection flatten(short[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    public static Collection flatten(int[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    public static Collection flatten(long[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    public static Collection flatten(float[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    public static Collection flatten(double[] self) {
        return DefaultGroovyMethods.flatten(DefaultGroovyMethods.toList(self), new ArrayList());
    }

    private static Collection flatten(Iterable elements, Collection addTo) {
        for (Object element : elements) {
            if (element instanceof Collection) {
                DefaultGroovyMethods.flatten((Iterable)((Collection)element), addTo);
                continue;
            }
            if (element != null && element.getClass().isArray()) {
                DefaultGroovyMethods.flatten((Iterable)DefaultTypeTransformation.arrayAsCollection(element), addTo);
                continue;
            }
            addTo.add(element);
        }
        return addTo;
    }

    public static <T> Collection<T> flatten(Iterable<T> self, Closure<? extends T> flattenUsing) {
        return DefaultGroovyMethods.flatten(self, DefaultGroovyMethods.createSimilarCollection(self), flattenUsing);
    }

    private static <T> Collection<T> flatten(Iterable elements, Collection<T> addTo, Closure<? extends T> flattenUsing) {
        for (Object element : elements) {
            List<T> list;
            boolean returnedSelf;
            if (element instanceof Collection) {
                DefaultGroovyMethods.flatten((Collection)element, addTo, flattenUsing);
                continue;
            }
            if (element != null && element.getClass().isArray()) {
                DefaultGroovyMethods.flatten(DefaultTypeTransformation.arrayAsCollection(element), addTo, flattenUsing);
                continue;
            }
            T flattened = flattenUsing.call(new Object[]{element});
            boolean bl = returnedSelf = flattened == element;
            if (!returnedSelf && flattened instanceof Collection && (list = DefaultGroovyMethods.toList((Iterable)flattened)).size() == 1 && list.get(0) == element) {
                returnedSelf = true;
            }
            if (flattened instanceof Collection && !returnedSelf) {
                DefaultGroovyMethods.flatten((Collection)flattened, addTo, flattenUsing);
                continue;
            }
            addTo.add(flattened);
        }
        return addTo;
    }

    public static <T> Collection<T> leftShift(Collection<T> self, T value) {
        self.add(value);
        return self;
    }

    public static <T> List<T> leftShift(List<T> self, T value) {
        return (List)DefaultGroovyMethods.leftShift(self, value);
    }

    public static <T> Set<T> leftShift(Set<T> self, T value) {
        return (Set)DefaultGroovyMethods.leftShift(self, value);
    }

    public static <T> SortedSet<T> leftShift(SortedSet<T> self, T value) {
        return (SortedSet)DefaultGroovyMethods.leftShift(self, value);
    }

    public static <T> BlockingQueue<T> leftShift(BlockingQueue<T> self, T value) throws InterruptedException {
        self.put(value);
        return self;
    }

    public static <K, V> Map<K, V> leftShift(Map<K, V> self, Map.Entry<K, V> entry) {
        self.put(entry.getKey(), entry.getValue());
        return self;
    }

    public static <K, V> Map<K, V> leftShift(Map<K, V> self, Map<K, V> other) {
        self.putAll(other);
        return self;
    }

    public static Number leftShift(Number self, Number operand) {
        return NumberMath.leftShift(self, operand);
    }

    public static Number rightShift(Number self, Number operand) {
        return NumberMath.rightShift(self, operand);
    }

    public static Number rightShiftUnsigned(Number self, Number operand) {
        return NumberMath.rightShiftUnsigned(self, operand);
    }

    public static List<Byte> getAt(byte[] array, Range range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Character> getAt(char[] array, Range range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Short> getAt(short[] array, Range range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Integer> getAt(int[] array, Range range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Long> getAt(long[] array, Range range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Float> getAt(float[] array, Range range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Double> getAt(double[] array, Range range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Boolean> getAt(boolean[] array, Range range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Byte> getAt(byte[] array, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(array.length, range);
        List<Byte> answer = DefaultGroovyMethods.primitiveArrayGet((Object)array, DefaultGroovyMethods.subListRange(info, range));
        return info.reverse ? DefaultGroovyMethods.reverse(answer) : answer;
    }

    public static List<Character> getAt(char[] array, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(array.length, range);
        List<Character> answer = DefaultGroovyMethods.primitiveArrayGet((Object)array, DefaultGroovyMethods.subListRange(info, range));
        return info.reverse ? DefaultGroovyMethods.reverse(answer) : answer;
    }

    public static List<Short> getAt(short[] array, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(array.length, range);
        List<Short> answer = DefaultGroovyMethods.primitiveArrayGet((Object)array, DefaultGroovyMethods.subListRange(info, range));
        return info.reverse ? DefaultGroovyMethods.reverse(answer) : answer;
    }

    public static List<Integer> getAt(int[] array, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(array.length, range);
        List<Integer> answer = DefaultGroovyMethods.primitiveArrayGet((Object)array, DefaultGroovyMethods.subListRange(info, range));
        return info.reverse ? DefaultGroovyMethods.reverse(answer) : answer;
    }

    public static List<Long> getAt(long[] array, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(array.length, range);
        List<Long> answer = DefaultGroovyMethods.primitiveArrayGet((Object)array, DefaultGroovyMethods.subListRange(info, range));
        return info.reverse ? DefaultGroovyMethods.reverse(answer) : answer;
    }

    public static List<Float> getAt(float[] array, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(array.length, range);
        List<Float> answer = DefaultGroovyMethods.primitiveArrayGet((Object)array, DefaultGroovyMethods.subListRange(info, range));
        return info.reverse ? DefaultGroovyMethods.reverse(answer) : answer;
    }

    public static List<Double> getAt(double[] array, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(array.length, range);
        List<Double> answer = DefaultGroovyMethods.primitiveArrayGet((Object)array, DefaultGroovyMethods.subListRange(info, range));
        return info.reverse ? DefaultGroovyMethods.reverse(answer) : answer;
    }

    public static List<Boolean> getAt(boolean[] array, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(array.length, range);
        List<Boolean> answer = DefaultGroovyMethods.primitiveArrayGet((Object)array, DefaultGroovyMethods.subListRange(info, range));
        return info.reverse ? DefaultGroovyMethods.reverse(answer) : answer;
    }

    public static List<Byte> getAt(byte[] array, ObjectRange range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Character> getAt(char[] array, ObjectRange range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Short> getAt(short[] array, ObjectRange range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Integer> getAt(int[] array, ObjectRange range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Long> getAt(long[] array, ObjectRange range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Float> getAt(float[] array, ObjectRange range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Double> getAt(double[] array, ObjectRange range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Boolean> getAt(boolean[] array, ObjectRange range) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, range);
    }

    public static List<Byte> getAt(byte[] array, Collection indices) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, indices);
    }

    public static List<Character> getAt(char[] array, Collection indices) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, indices);
    }

    public static List<Short> getAt(short[] array, Collection indices) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, indices);
    }

    public static List<Integer> getAt(int[] array, Collection indices) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, indices);
    }

    public static List<Long> getAt(long[] array, Collection indices) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, indices);
    }

    public static List<Float> getAt(float[] array, Collection indices) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, indices);
    }

    public static List<Double> getAt(double[] array, Collection indices) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, indices);
    }

    public static List<Boolean> getAt(boolean[] array, Collection indices) {
        return DefaultGroovyMethods.primitiveArrayGet((Object)array, indices);
    }

    public static boolean getAt(BitSet self, int index) {
        int i = DefaultGroovyMethods.normaliseIndex(index, self.length());
        return self.get(i);
    }

    public static BitSet getAt(BitSet self, IntRange range) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(self.length(), range);
        BitSet result = new BitSet();
        int numberOfBits = info.to - info.from;
        int adjuster = 1;
        int offset = info.from;
        if (info.reverse) {
            adjuster = -1;
            offset = info.to - 1;
        }
        for (int i = 0; i < numberOfBits; ++i) {
            result.set(i, self.get(offset + adjuster * i));
        }
        return result;
    }

    public static void putAt(BitSet self, IntRange range, boolean value) {
        RangeInfo info = DefaultGroovyMethods.subListBorders(self.length(), range);
        self.set(info.from, info.to, value);
    }

    public static void putAt(BitSet self, int index, boolean value) {
        self.set(index, value);
    }

    public static int size(boolean[] array) {
        return Array.getLength(array);
    }

    public static int size(byte[] array) {
        return Array.getLength(array);
    }

    public static int size(char[] array) {
        return Array.getLength(array);
    }

    public static int size(short[] array) {
        return Array.getLength(array);
    }

    public static int size(int[] array) {
        return Array.getLength(array);
    }

    public static int size(long[] array) {
        return Array.getLength(array);
    }

    public static int size(float[] array) {
        return Array.getLength(array);
    }

    public static int size(double[] array) {
        return Array.getLength(array);
    }

    public static List<Byte> toList(byte[] array) {
        return DefaultTypeTransformation.primitiveArrayToList(array);
    }

    public static List<Boolean> toList(boolean[] array) {
        return DefaultTypeTransformation.primitiveArrayToList(array);
    }

    public static List<Character> toList(char[] array) {
        return DefaultTypeTransformation.primitiveArrayToList(array);
    }

    public static List<Short> toList(short[] array) {
        return DefaultTypeTransformation.primitiveArrayToList(array);
    }

    public static List<Integer> toList(int[] array) {
        return DefaultTypeTransformation.primitiveArrayToList(array);
    }

    public static List<Long> toList(long[] array) {
        return DefaultTypeTransformation.primitiveArrayToList(array);
    }

    public static List<Float> toList(float[] array) {
        return DefaultTypeTransformation.primitiveArrayToList(array);
    }

    public static List<Double> toList(double[] array) {
        return DefaultTypeTransformation.primitiveArrayToList(array);
    }

    public static Set<Byte> toSet(byte[] array) {
        return DefaultGroovyMethods.toSet(DefaultTypeTransformation.primitiveArrayToList(array));
    }

    public static Set<Boolean> toSet(boolean[] array) {
        return DefaultGroovyMethods.toSet(DefaultTypeTransformation.primitiveArrayToList(array));
    }

    public static Set<Character> toSet(char[] array) {
        return DefaultGroovyMethods.toSet(DefaultTypeTransformation.primitiveArrayToList(array));
    }

    public static Set<Short> toSet(short[] array) {
        return DefaultGroovyMethods.toSet(DefaultTypeTransformation.primitiveArrayToList(array));
    }

    public static Set<Integer> toSet(int[] array) {
        return DefaultGroovyMethods.toSet(DefaultTypeTransformation.primitiveArrayToList(array));
    }

    public static Set<Long> toSet(long[] array) {
        return DefaultGroovyMethods.toSet(DefaultTypeTransformation.primitiveArrayToList(array));
    }

    public static Set<Float> toSet(float[] array) {
        return DefaultGroovyMethods.toSet(DefaultTypeTransformation.primitiveArrayToList(array));
    }

    public static Set<Double> toSet(double[] array) {
        return DefaultGroovyMethods.toSet(DefaultTypeTransformation.primitiveArrayToList(array));
    }

    public static <T> Set<T> toSet(Collection<T> self) {
        HashSet<T> answer = new HashSet<T>(self.size());
        answer.addAll(self);
        return answer;
    }

    public static <T> Set<T> toSet(Iterable<T> self) {
        return DefaultGroovyMethods.toSet(self.iterator());
    }

    public static <T> Set<T> toSet(Iterator<T> self) {
        HashSet<T> answer = new HashSet<T>();
        while (self.hasNext()) {
            answer.add(self.next());
        }
        return answer;
    }

    public static <T> Set<T> toSet(Enumeration<T> self) {
        HashSet<T> answer = new HashSet<T>();
        while (self.hasMoreElements()) {
            answer.add(self.nextElement());
        }
        return answer;
    }

    protected static Object primitiveArrayGet(Object self, int idx) {
        return Array.get(self, DefaultGroovyMethods.normaliseIndex(idx, Array.getLength(self)));
    }

    protected static List primitiveArrayGet(Object self, Range range) {
        ArrayList<Object> answer = new ArrayList<Object>();
        for (Object next : range) {
            int idx = DefaultTypeTransformation.intUnbox(next);
            answer.add(DefaultGroovyMethods.primitiveArrayGet(self, idx));
        }
        return answer;
    }

    protected static List primitiveArrayGet(Object self, Collection indices) {
        ArrayList<Object> answer = new ArrayList<Object>();
        for (Object value : indices) {
            if (value instanceof Range) {
                answer.addAll(DefaultGroovyMethods.primitiveArrayGet(self, (Range)value));
                continue;
            }
            if (value instanceof List) {
                answer.addAll(DefaultGroovyMethods.primitiveArrayGet(self, (List)value));
                continue;
            }
            int idx = DefaultTypeTransformation.intUnbox(value);
            answer.add(DefaultGroovyMethods.primitiveArrayGet(self, idx));
        }
        return answer;
    }

    protected static Object primitiveArrayPut(Object self, int idx, Object newValue) {
        Array.set(self, DefaultGroovyMethods.normaliseIndex(idx, Array.getLength(self)), newValue);
        return newValue;
    }

    public static Boolean toBoolean(Boolean self) {
        return self;
    }

    public static boolean contains(int[] self, Object value) {
        for (int next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, next)) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(long[] self, Object value) {
        for (long next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, next)) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(short[] self, Object value) {
        for (short next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, next)) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(char[] self, Object value) {
        for (char next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, Character.valueOf(next))) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(boolean[] self, Object value) {
        for (boolean next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, next)) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(double[] self, Object value) {
        for (double next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, next)) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(float[] self, Object value) {
        for (float next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, Float.valueOf(next))) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(byte[] self, Object value) {
        for (byte next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, next)) continue;
            return true;
        }
        return false;
    }

    public static boolean contains(Object[] self, Object value) {
        for (Object next : self) {
            if (!DefaultTypeTransformation.compareEqual(value, next)) continue;
            return true;
        }
        return false;
    }

    public static String toString(boolean[] self) {
        return FormatHelper.toString(self);
    }

    public static String toString(byte[] self) {
        return FormatHelper.toString(self);
    }

    public static String toString(char[] self) {
        return FormatHelper.toString(self);
    }

    public static String toString(short[] self) {
        return FormatHelper.toString(self);
    }

    public static String toString(int[] self) {
        return FormatHelper.toString(self);
    }

    public static String toString(long[] self) {
        return FormatHelper.toString(self);
    }

    public static String toString(float[] self) {
        return FormatHelper.toString(self);
    }

    public static String toString(double[] self) {
        return FormatHelper.toString(self);
    }

    public static String toString(AbstractMap self) {
        return DefaultGroovyMethods.toMapString(self);
    }

    public static String toMapString(Map self) {
        return DefaultGroovyMethods.toMapString(self, -1);
    }

    public static String toMapString(Map self, int maxSize) {
        return self == null ? "null" : FormatHelper.toMapString(self, maxSize);
    }

    public static String toString(AbstractCollection self) {
        return DefaultGroovyMethods.toListString(self);
    }

    public static String toListString(Collection self) {
        return DefaultGroovyMethods.toListString(self, -1);
    }

    public static String toListString(Collection self, int maxSize) {
        return self == null ? "null" : FormatHelper.toListString(self, maxSize);
    }

    public static String toString(Object[] self) {
        return DefaultGroovyMethods.toArrayString(self);
    }

    public static String toArrayString(Object[] self) {
        return self == null ? "null" : FormatHelper.toArrayString(self);
    }

    public static String toString(Object value) {
        return FormatHelper.toString(value);
    }

    public static Character next(Character self) {
        return Character.valueOf((char)(self.charValue() + '\u0001'));
    }

    public static Number next(Number self) {
        return NumberNumberPlus.plus(self, ONE);
    }

    public static Character previous(Character self) {
        return Character.valueOf((char)(self.charValue() - '\u0001'));
    }

    public static Number previous(Number self) {
        return NumberNumberMinus.minus(self, ONE);
    }

    public static Number plus(Character left, Number right) {
        return NumberNumberPlus.plus((int)left.charValue(), right);
    }

    public static Number plus(Number left, Character right) {
        return NumberNumberPlus.plus(left, (int)right.charValue());
    }

    public static Number plus(Character left, Character right) {
        return DefaultGroovyMethods.plus((int)left.charValue(), right);
    }

    public static String plus(Map left, String right) {
        return DefaultGroovyMethods.toString(left) + right;
    }

    public static String plus(Map left, GString right) {
        return DefaultGroovyMethods.toString(left) + right;
    }

    public static int compareTo(Character left, Number right) {
        return DefaultGroovyMethods.compareTo((Number)left.charValue(), right);
    }

    public static int compareTo(Number left, Character right) {
        return DefaultGroovyMethods.compareTo(left, (Number)right.charValue());
    }

    public static int compareTo(Character left, Character right) {
        return DefaultGroovyMethods.compareTo((Number)left.charValue(), right);
    }

    public static int compareTo(Number left, Number right) {
        return NumberMath.compareTo(left, right);
    }

    public static Number minus(Character left, Number right) {
        return NumberNumberMinus.minus((int)left.charValue(), right);
    }

    public static Number minus(Number left, Character right) {
        return NumberNumberMinus.minus(left, (int)right.charValue());
    }

    public static Number minus(Character left, Character right) {
        return DefaultGroovyMethods.minus((int)left.charValue(), right);
    }

    public static Number multiply(Character left, Number right) {
        return NumberNumberMultiply.multiply((int)left.charValue(), right);
    }

    public static Number multiply(Number left, Character right) {
        return NumberNumberMultiply.multiply((int)right.charValue(), left);
    }

    public static Number multiply(Character left, Character right) {
        return DefaultGroovyMethods.multiply((int)left.charValue(), right);
    }

    public static Number multiply(BigDecimal left, Double right) {
        return NumberMath.multiply(left, right);
    }

    public static Number multiply(BigDecimal left, BigInteger right) {
        return NumberMath.multiply(left, right);
    }

    public static Boolean isAtLeast(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) >= 0;
    }

    public static Boolean isAtLeast(BigDecimal left, String right) {
        return DefaultGroovyMethods.isAtLeast(left, new BigDecimal(right));
    }

    public static Number power(Number self, Number exponent) {
        double exp;
        double base = self.doubleValue();
        double answer = Math.pow(base, exp = exponent.doubleValue());
        if ((double)((int)answer) == answer) {
            return (int)answer;
        }
        if ((double)((long)answer) == answer) {
            return (long)answer;
        }
        return answer;
    }

    public static Number power(BigDecimal self, Integer exponent) {
        if (exponent >= 0) {
            return self.pow(exponent);
        }
        return DefaultGroovyMethods.power((Number)self, (Number)exponent);
    }

    public static Number power(BigInteger self, Integer exponent) {
        if (exponent >= 0) {
            return self.pow(exponent);
        }
        return DefaultGroovyMethods.power((Number)self, (Number)exponent);
    }

    public static Number power(Integer self, Integer exponent) {
        if (exponent >= 0) {
            BigInteger answer = BigInteger.valueOf(self.intValue()).pow(exponent);
            if (answer.compareTo(BI_INT_MIN) >= 0 && answer.compareTo(BI_INT_MAX) <= 0) {
                return answer.intValue();
            }
            return answer;
        }
        return DefaultGroovyMethods.power((Number)self, (Number)exponent);
    }

    public static Number power(Long self, Integer exponent) {
        if (exponent >= 0) {
            BigInteger answer = BigInteger.valueOf(self).pow(exponent);
            if (answer.compareTo(BI_LONG_MIN) >= 0 && answer.compareTo(BI_LONG_MAX) <= 0) {
                return answer.longValue();
            }
            return answer;
        }
        return DefaultGroovyMethods.power((Number)self, (Number)exponent);
    }

    public static BigInteger power(BigInteger self, BigInteger exponent) {
        if (exponent.signum() >= 0 && exponent.compareTo(BI_INT_MAX) <= 0) {
            return self.pow(exponent.intValue());
        }
        return BigDecimal.valueOf(Math.pow(self.doubleValue(), exponent.doubleValue())).toBigInteger();
    }

    public static Number div(Character left, Number right) {
        return NumberNumberDiv.div((int)left.charValue(), right);
    }

    public static Number div(Number left, Character right) {
        return NumberNumberDiv.div(left, (int)right.charValue());
    }

    public static Number div(Character left, Character right) {
        return DefaultGroovyMethods.div((int)left.charValue(), right);
    }

    public static Number intdiv(Character left, Number right) {
        return DefaultGroovyMethods.intdiv((Number)left.charValue(), right);
    }

    public static Number intdiv(Number left, Character right) {
        return DefaultGroovyMethods.intdiv(left, (Number)right.charValue());
    }

    public static Number intdiv(Character left, Character right) {
        return DefaultGroovyMethods.intdiv((Number)left.charValue(), right);
    }

    public static Number intdiv(Number left, Number right) {
        return NumberMath.intdiv(left, right);
    }

    public static Number or(Number left, Number right) {
        return NumberMath.or(left, right);
    }

    public static Number and(Number left, Number right) {
        return NumberMath.and(left, right);
    }

    public static BitSet and(BitSet left, BitSet right) {
        BitSet result = (BitSet)left.clone();
        result.and(right);
        return result;
    }

    public static BitSet xor(BitSet left, BitSet right) {
        BitSet result = (BitSet)left.clone();
        result.xor(right);
        return result;
    }

    public static BitSet bitwiseNegate(BitSet self) {
        BitSet result = (BitSet)self.clone();
        result.flip(0, result.size() - 1);
        return result;
    }

    public static Number bitwiseNegate(Number left) {
        return NumberMath.bitwiseNegate(left);
    }

    public static BitSet or(BitSet left, BitSet right) {
        BitSet result = (BitSet)left.clone();
        result.or(right);
        return result;
    }

    public static Number xor(Number left, Number right) {
        return NumberMath.xor(left, right);
    }

    public static Number mod(Number left, Number right) {
        return NumberMath.mod(left, right);
    }

    public static Number unaryMinus(Number left) {
        return NumberMath.unaryMinus(left);
    }

    public static Number unaryPlus(Number left) {
        return NumberMath.unaryPlus(left);
    }

    public static void times(Number self, @ClosureParams(value=SimpleType.class, options={"int"}) Closure closure) {
        int size = self.intValue();
        for (int i = 0; i < size; ++i) {
            closure.call((Object)i);
            if (closure.getDirective() == 1) break;
        }
    }

    public static void upto(Number self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        int to1;
        int self1 = self.intValue();
        if (self1 <= (to1 = to.intValue())) {
            for (int i = self1; i <= to1; ++i) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
        }
    }

    public static void upto(long self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        long to1 = to.longValue();
        if (self <= to1) {
            for (long i = self; i <= to1; ++i) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
        }
    }

    public static void upto(Long self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        long to1 = to.longValue();
        if (self <= to1) {
            for (long i = self.longValue(); i <= to1; ++i) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
        }
    }

    public static void upto(float self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        float to1 = to.floatValue();
        if (self <= to1) {
            for (float i = self; i <= to1; i += 1.0f) {
                closure.call((Object)Float.valueOf(i));
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
        }
    }

    public static void upto(Float self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        float to1 = to.floatValue();
        if (self.floatValue() <= to1) {
            for (float i = self.floatValue(); i <= to1; i += 1.0f) {
                closure.call((Object)Float.valueOf(i));
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
        }
    }

    public static void upto(double self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        double to1 = to.doubleValue();
        if (self <= to1) {
            for (double i = self; i <= to1; i += 1.0) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
        }
    }

    public static void upto(Double self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        double to1 = to.doubleValue();
        if (self <= to1) {
            for (double i = self.doubleValue(); i <= to1; i += 1.0) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void upto(BigInteger self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        if (to instanceof BigDecimal) {
            BigDecimal one = BigDecimal.valueOf(10L, 1);
            BigDecimal self1 = new BigDecimal(self);
            BigDecimal to1 = (BigDecimal)to;
            if (self1.compareTo(to1) > 0) throw new GroovyRuntimeException(MessageFormat.format("The argument ({0}) to upto() cannot be less than the value ({1}) it''s called on.", to, self));
            BigDecimal i = self1;
            while (i.compareTo(to1) <= 0) {
                closure.call((Object)i);
                i = i.add(one);
            }
            return;
        } else if (to instanceof BigInteger) {
            BigInteger one = BigInteger.valueOf(1L);
            BigInteger to1 = (BigInteger)to;
            if (self.compareTo(to1) > 0) throw new GroovyRuntimeException(MessageFormat.format("The argument ({0}) to upto() cannot be less than the value ({1}) it''s called on.", to, self));
            BigInteger i = self;
            while (i.compareTo(to1) <= 0) {
                closure.call((Object)i);
                i = i.add(one);
            }
            return;
        } else {
            BigInteger one = BigInteger.valueOf(1L);
            BigInteger to1 = new BigInteger(to.toString());
            if (self.compareTo(to1) > 0) throw new GroovyRuntimeException(MessageFormat.format("The argument ({0}) to upto() cannot be less than the value ({1}) it''s called on.", to, self));
            BigInteger i = self;
            while (i.compareTo(to1) <= 0) {
                closure.call((Object)i);
                i = i.add(one);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void upto(BigDecimal self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        BigDecimal one = BigDecimal.valueOf(10L, 1);
        if (to instanceof BigDecimal) {
            BigDecimal to1 = (BigDecimal)to;
            if (self.compareTo(to1) > 0) throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
            BigDecimal i = self;
            while (i.compareTo(to1) <= 0) {
                closure.call((Object)i);
                i = i.add(one);
            }
            return;
        } else if (to instanceof BigInteger) {
            BigDecimal to1 = new BigDecimal((BigInteger)to);
            if (self.compareTo(to1) > 0) throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
            BigDecimal i = self;
            while (i.compareTo(to1) <= 0) {
                closure.call((Object)i);
                i = i.add(one);
            }
            return;
        } else {
            BigDecimal to1 = NumberMath.toBigDecimal(to);
            if (self.compareTo(to1) > 0) throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be less than the value (" + self + ") it's called on.");
            BigDecimal i = self;
            while (i.compareTo(to1) <= 0) {
                closure.call((Object)i);
                i = i.add(one);
            }
        }
    }

    public static void downto(Number self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        int to1;
        int self1 = self.intValue();
        if (self1 >= (to1 = to.intValue())) {
            for (int i = self1; i >= to1; --i) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
        }
    }

    public static void downto(long self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        long to1 = to.longValue();
        if (self >= to1) {
            for (long i = self; i >= to1; --i) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
        }
    }

    public static void downto(Long self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        long to1 = to.longValue();
        if (self >= to1) {
            for (long i = self.longValue(); i >= to1; --i) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
        }
    }

    public static void downto(float self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        float to1 = to.floatValue();
        if (self >= to1) {
            for (float i = self; i >= to1; i -= 1.0f) {
                closure.call((Object)Float.valueOf(i));
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
        }
    }

    public static void downto(Float self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        float to1 = to.floatValue();
        if (self.floatValue() >= to1) {
            for (float i = self.floatValue(); i >= to1; i -= 1.0f) {
                closure.call((Object)Float.valueOf(i));
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
        }
    }

    public static void downto(double self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        double to1 = to.doubleValue();
        if (self >= to1) {
            for (double i = self; i >= to1; i -= 1.0) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
        }
    }

    public static void downto(Double self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        double to1 = to.doubleValue();
        if (self >= to1) {
            for (double i = self.doubleValue(); i >= to1; i -= 1.0) {
                closure.call((Object)i);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void downto(BigInteger self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        if (to instanceof BigDecimal) {
            BigDecimal one = BigDecimal.valueOf(10L, 1);
            BigDecimal selfD = new BigDecimal(self);
            BigDecimal to1 = (BigDecimal)to;
            if (selfD.compareTo(to1) < 0) throw new GroovyRuntimeException(MessageFormat.format("The argument ({0}) to downto() cannot be greater than the value ({1}) it''s called on.", to, self));
            BigDecimal i = selfD;
            while (i.compareTo(to1) >= 0) {
                closure.call((Object)i.toBigInteger());
                i = i.subtract(one);
            }
            return;
        } else if (to instanceof BigInteger) {
            BigInteger one = BigInteger.valueOf(1L);
            BigInteger to1 = (BigInteger)to;
            if (self.compareTo(to1) < 0) throw new GroovyRuntimeException(MessageFormat.format("The argument ({0}) to downto() cannot be greater than the value ({1}) it''s called on.", to, self));
            BigInteger i = self;
            while (i.compareTo(to1) >= 0) {
                closure.call((Object)i);
                i = i.subtract(one);
            }
            return;
        } else {
            BigInteger one = BigInteger.valueOf(1L);
            BigInteger to1 = new BigInteger(to.toString());
            if (self.compareTo(to1) < 0) throw new GroovyRuntimeException(MessageFormat.format("The argument ({0}) to downto() cannot be greater than the value ({1}) it''s called on.", to, self));
            BigInteger i = self;
            while (i.compareTo(to1) >= 0) {
                closure.call((Object)i);
                i = i.subtract(one);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void downto(BigDecimal self, Number to, @ClosureParams(value=FirstParam.class) Closure closure) {
        BigDecimal one = BigDecimal.valueOf(10L, 1);
        if (to instanceof BigDecimal) {
            BigDecimal to1 = (BigDecimal)to;
            if (self.compareTo(to1) < 0) throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
            BigDecimal i = self;
            while (i.compareTo(to1) >= 0) {
                closure.call((Object)i);
                i = i.subtract(one);
            }
            return;
        } else if (to instanceof BigInteger) {
            BigDecimal to1 = new BigDecimal((BigInteger)to);
            if (self.compareTo(to1) < 0) throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
            BigDecimal i = self;
            while (i.compareTo(to1) >= 0) {
                closure.call((Object)i);
                i = i.subtract(one);
            }
            return;
        } else {
            BigDecimal to1 = NumberMath.toBigDecimal(to);
            if (self.compareTo(to1) < 0) throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be greater than the value (" + self + ") it's called on.");
            BigDecimal i = self;
            while (i.compareTo(to1) >= 0) {
                closure.call((Object)i);
                i = i.subtract(one);
            }
        }
    }

    public static void step(Number self, Number to, Number stepNumber, Closure closure) {
        if (self instanceof BigDecimal || to instanceof BigDecimal || stepNumber instanceof BigDecimal) {
            BigDecimal zero = BigDecimal.valueOf(0L, 1);
            BigDecimal self1 = NumberMath.toBigDecimal(self);
            BigDecimal to1 = NumberMath.toBigDecimal(to);
            BigDecimal stepNumber1 = NumberMath.toBigDecimal(stepNumber);
            if (stepNumber1.compareTo(zero) > 0 && to1.compareTo(self1) > 0) {
                BigDecimal i = self1;
                while (i.compareTo(to1) < 0) {
                    closure.call((Object)i);
                    i = i.add(stepNumber1);
                }
            } else if (stepNumber1.compareTo(zero) < 0 && to1.compareTo(self1) < 0) {
                BigDecimal i = self1;
                while (i.compareTo(to1) > 0) {
                    closure.call((Object)i);
                    i = i.add(stepNumber1);
                }
            } else if (self1.compareTo(to1) != 0) {
                throw new GroovyRuntimeException("Infinite loop in " + self1 + ".step(" + to1 + ", " + stepNumber1 + ")");
            }
        } else if (self instanceof BigInteger || to instanceof BigInteger || stepNumber instanceof BigInteger) {
            BigInteger stepNumber1;
            BigInteger zero = BigInteger.valueOf(0L);
            BigInteger self1 = self instanceof BigInteger ? (BigInteger)self : new BigInteger(self.toString());
            BigInteger to1 = to instanceof BigInteger ? (BigInteger)to : new BigInteger(to.toString());
            BigInteger bigInteger = stepNumber1 = stepNumber instanceof BigInteger ? (BigInteger)stepNumber : new BigInteger(stepNumber.toString());
            if (stepNumber1.compareTo(zero) > 0 && to1.compareTo(self1) > 0) {
                BigInteger i = self1;
                while (i.compareTo(to1) < 0) {
                    closure.call((Object)i);
                    i = i.add(stepNumber1);
                }
            } else if (stepNumber1.compareTo(zero) < 0 && to1.compareTo(self1) < 0) {
                BigInteger i = self1;
                while (i.compareTo(to1) > 0) {
                    closure.call((Object)i);
                    i = i.add(stepNumber1);
                }
            } else if (self1.compareTo(to1) != 0) {
                throw new GroovyRuntimeException("Infinite loop in " + self1 + ".step(" + to1 + ", " + stepNumber1 + ")");
            }
        } else {
            int self1 = self.intValue();
            int to1 = to.intValue();
            int stepNumber1 = stepNumber.intValue();
            if (stepNumber1 > 0 && to1 > self1) {
                for (int i = self1; i < to1; i += stepNumber1) {
                    closure.call((Object)i);
                }
            } else if (stepNumber1 < 0 && to1 < self1) {
                for (int i = self1; i > to1; i += stepNumber1) {
                    closure.call((Object)i);
                }
            } else if (self1 != to1) {
                throw new GroovyRuntimeException("Infinite loop in " + self1 + ".step(" + to1 + ", " + stepNumber1 + ")");
            }
        }
    }

    public static int abs(Number number) {
        return Math.abs(number.intValue());
    }

    public static long abs(Long number) {
        return Math.abs(number);
    }

    public static float abs(Float number) {
        return Math.abs(number.floatValue());
    }

    public static double abs(Double number) {
        return Math.abs(number);
    }

    public static boolean equalsIgnoreZeroSign(Float number, Object other) {
        if (other instanceof Float && 0.0f == number.floatValue() && 0.0f == ((Float)other).floatValue()) {
            return true;
        }
        return number.equals(other);
    }

    public static boolean equalsIgnoreZeroSign(Double number, Object other) {
        if (other instanceof Double && 0.0 == number && 0.0 == (Double)other) {
            return true;
        }
        return number.equals(other);
    }

    public static int round(Float number) {
        return Math.round(number.floatValue());
    }

    public static float round(Float number, int precision) {
        return (float)(Math.floor(number.doubleValue() * Math.pow(10.0, precision) + 0.5) / Math.pow(10.0, precision));
    }

    public static float trunc(Float number, int precision) {
        double p = Math.pow(10.0, precision);
        double n = number.doubleValue() * p;
        if (number.floatValue() < 0.0f) {
            return (float)(Math.ceil(n) / p);
        }
        return (float)(Math.floor(n) / p);
    }

    public static float trunc(Float number) {
        if (number.floatValue() < 0.0f) {
            return (float)Math.ceil(number.doubleValue());
        }
        return (float)Math.floor(number.doubleValue());
    }

    public static long round(Double number) {
        return Math.round(number);
    }

    public static double round(Double number, int precision) {
        return Math.floor(number * Math.pow(10.0, precision) + 0.5) / Math.pow(10.0, precision);
    }

    public static double trunc(Double number) {
        if (number < 0.0) {
            return Math.ceil(number);
        }
        return Math.floor(number);
    }

    public static double trunc(Double number, int precision) {
        if (number < 0.0) {
            return Math.ceil(number * Math.pow(10.0, precision)) / Math.pow(10.0, precision);
        }
        return Math.floor(number * Math.pow(10.0, precision)) / Math.pow(10.0, precision);
    }

    public static BigDecimal round(BigDecimal number) {
        return DefaultGroovyMethods.round(number, 0);
    }

    public static BigDecimal round(BigDecimal number, int precision) {
        return number.setScale(precision, RoundingMode.HALF_UP);
    }

    public static BigDecimal trunc(BigDecimal number) {
        return DefaultGroovyMethods.trunc(number, 0);
    }

    public static BigDecimal trunc(BigDecimal number, int precision) {
        return number.setScale(precision, RoundingMode.DOWN);
    }

    public static boolean isUpperCase(Character self) {
        return Character.isUpperCase(self.charValue());
    }

    public static boolean isLowerCase(Character self) {
        return Character.isLowerCase(self.charValue());
    }

    public static boolean isLetter(Character self) {
        return Character.isLetter(self.charValue());
    }

    public static boolean isDigit(Character self) {
        return Character.isDigit(self.charValue());
    }

    public static boolean isLetterOrDigit(Character self) {
        return Character.isLetterOrDigit(self.charValue());
    }

    public static boolean isWhitespace(Character self) {
        return Character.isWhitespace(self.charValue());
    }

    public static char toUpperCase(Character self) {
        return Character.toUpperCase(self.charValue());
    }

    public static char toLowerCase(Character self) {
        return Character.toLowerCase(self.charValue());
    }

    public static Integer toInteger(Number self) {
        return self.intValue();
    }

    public static Long toLong(Number self) {
        return self.longValue();
    }

    public static Float toFloat(Number self) {
        return Float.valueOf(self.floatValue());
    }

    public static Double toDouble(Number self) {
        if (self instanceof Double || self instanceof Long || self instanceof Integer || self instanceof Short || self instanceof Byte) {
            return self.doubleValue();
        }
        return Double.valueOf(self.toString());
    }

    public static BigDecimal toBigDecimal(Number self) {
        return NumberMath.toBigDecimal(self);
    }

    public static <T> T asType(Number self, Class<T> c) {
        if (c == BigDecimal.class) {
            return (T)DefaultGroovyMethods.toBigDecimal(self);
        }
        if (c == BigInteger.class) {
            return (T)DefaultGroovyMethods.toBigInteger(self);
        }
        if (c == Double.class) {
            return (T)DefaultGroovyMethods.toDouble(self);
        }
        if (c == Float.class) {
            return (T)DefaultGroovyMethods.toFloat(self);
        }
        return DefaultGroovyMethods.asType((Object)self, c);
    }

    public static BigInteger toBigInteger(Number self) {
        return NumberMath.toBigInteger(self);
    }

    public static Boolean and(Boolean left, Boolean right) {
        return left != false && Boolean.TRUE.equals(right);
    }

    public static Boolean or(Boolean left, Boolean right) {
        return left != false || Boolean.TRUE.equals(right);
    }

    public static Boolean implies(Boolean left, Boolean right) {
        return left == false || Boolean.TRUE.equals(right);
    }

    public static Boolean xor(Boolean left, Boolean right) {
        return left ^ Boolean.TRUE.equals(right);
    }

    public static TimerTask runAfter(Timer timer, int delay, final Closure closure) {
        TimerTask timerTask = new TimerTask(){

            @Override
            public void run() {
                closure.call();
            }
        };
        timer.schedule(timerTask, delay);
        return timerTask;
    }

    public static void eachByte(Byte[] self, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        DefaultGroovyMethods.each(self, closure);
    }

    public static void eachByte(byte[] self, @ClosureParams(value=FirstParam.Component.class) Closure closure) {
        DefaultGroovyMethods.each(self, closure);
    }

    public static int findIndexOf(Object self, Closure condition) {
        return DefaultGroovyMethods.findIndexOf(self, 0, condition);
    }

    public static int findIndexOf(Object self, int startIndex, Closure condition) {
        return DefaultGroovyMethods.findIndexOf(InvokerHelper.asIterator(self), startIndex, condition);
    }

    public static <T> int findIndexOf(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findIndexOf(self, 0, condition);
    }

    public static <T> int findIndexOf(Iterator<T> self, int startIndex, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        int result = -1;
        int i = 0;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        while (self.hasNext()) {
            T value = self.next();
            if (i++ < startIndex) continue;
            if (!bcw.call(value)) continue;
            result = i - 1;
            break;
        }
        return result;
    }

    public static <T> int findIndexOf(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findIndexOf(self, 0, condition);
    }

    public static <T> int findIndexOf(Iterable<T> self, int startIndex, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findIndexOf(self.iterator(), startIndex, condition);
    }

    public static <T> int findIndexOf(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        return DefaultGroovyMethods.findIndexOf(self, 0, condition);
    }

    public static <T> int findIndexOf(T[] self, int startIndex, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        return DefaultGroovyMethods.findIndexOf(new ArrayIterator<T>(self), startIndex, condition);
    }

    public static int findLastIndexOf(Object self, Closure condition) {
        return DefaultGroovyMethods.findLastIndexOf(self, 0, condition);
    }

    public static int findLastIndexOf(Object self, int startIndex, Closure condition) {
        return DefaultGroovyMethods.findLastIndexOf(InvokerHelper.asIterator(self), startIndex, condition);
    }

    public static <T> int findLastIndexOf(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findLastIndexOf(self, 0, condition);
    }

    public static <T> int findLastIndexOf(Iterator<T> self, int startIndex, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        int result = -1;
        int i = 0;
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        while (self.hasNext()) {
            T value = self.next();
            if (i++ < startIndex) continue;
            if (!bcw.call(value)) continue;
            result = i - 1;
        }
        return result;
    }

    public static <T> int findLastIndexOf(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findLastIndexOf(self.iterator(), 0, condition);
    }

    public static <T> int findLastIndexOf(Iterable<T> self, int startIndex, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findLastIndexOf(self.iterator(), startIndex, condition);
    }

    public static <T> int findLastIndexOf(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        return DefaultGroovyMethods.findLastIndexOf(new ArrayIterator<T>(self), 0, condition);
    }

    public static <T> int findLastIndexOf(T[] self, int startIndex, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        return DefaultGroovyMethods.findLastIndexOf(new ArrayIterator<T>(self), startIndex, condition);
    }

    public static List<Number> findIndexValues(Object self, Closure condition) {
        return DefaultGroovyMethods.findIndexValues(self, (Number)0, condition);
    }

    public static List<Number> findIndexValues(Object self, Number startIndex, Closure condition) {
        return DefaultGroovyMethods.findIndexValues(InvokerHelper.asIterator(self), startIndex, condition);
    }

    public static <T> List<Number> findIndexValues(Iterator<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findIndexValues(self, (Number)0, condition);
    }

    public static <T> List<Number> findIndexValues(Iterator<T> self, Number startIndex, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        ArrayList<Number> result = new ArrayList<Number>();
        long count = 0L;
        long startCount = startIndex.longValue();
        BooleanClosureWrapper bcw = new BooleanClosureWrapper(condition);
        while (self.hasNext()) {
            T value = self.next();
            if (count++ < startCount) continue;
            if (!bcw.call(value)) continue;
            result.add(count - 1L);
        }
        return result;
    }

    public static <T> List<Number> findIndexValues(Iterable<T> self, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findIndexValues(self, (Number)0, condition);
    }

    public static <T> List<Number> findIndexValues(Iterable<T> self, Number startIndex, @ClosureParams(value=FirstParam.FirstGenericType.class) Closure condition) {
        return DefaultGroovyMethods.findIndexValues(self.iterator(), startIndex, condition);
    }

    public static <T> List<Number> findIndexValues(T[] self, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        return DefaultGroovyMethods.findIndexValues(self, (Number)0, condition);
    }

    public static <T> List<Number> findIndexValues(T[] self, Number startIndex, @ClosureParams(value=FirstParam.Component.class) Closure condition) {
        return DefaultGroovyMethods.findIndexValues(new ArrayIterator<T>(self), startIndex, condition);
    }

    public static ClassLoader getRootLoader(ClassLoader self) {
        while (self != null) {
            if (DefaultGroovyMethods.isRootLoaderClassOrSubClass(self)) {
                return self;
            }
            self = self.getParent();
        }
        return null;
    }

    private static boolean isRootLoaderClassOrSubClass(ClassLoader self) {
        Class<?> current = self.getClass();
        while (!current.getName().equals(Object.class.getName())) {
            if (current.getName().equals(RootLoader.class.getName())) {
                return true;
            }
            current = current.getSuperclass();
        }
        return false;
    }

    public static <T> T asType(Object obj, Class<T> type) {
        if (String.class == type) {
            return (T)FormatHelper.toString(obj);
        }
        try {
            return (T)DefaultTypeTransformation.castToType(obj, type);
        }
        catch (GroovyCastException e) {
            ExpandoMetaClass emc;
            Object mixedIn;
            MetaClass mc = InvokerHelper.getMetaClass(obj);
            if (mc instanceof ExpandoMetaClass && (mixedIn = (emc = (ExpandoMetaClass)mc).castToMixedType(obj, type)) != null) {
                return (T)mixedIn;
            }
            if (type.isInterface()) {
                try {
                    ArrayList<Class> interfaces = new ArrayList<Class>();
                    interfaces.add(type);
                    return (T)ProxyGenerator.INSTANCE.instantiateDelegate(interfaces, obj);
                }
                catch (GroovyRuntimeException groovyRuntimeException) {
                    // empty catch block
                }
            }
            throw e;
        }
    }

    private static Object asArrayType(Object object, Class type) {
        if (type.isAssignableFrom(object.getClass())) {
            return object;
        }
        Collection list = DefaultTypeTransformation.asCollection(object);
        int size = list.size();
        Class<?> elementType = type.getComponentType();
        Object array = Array.newInstance(elementType, size);
        int idx = 0;
        if (Boolean.TYPE.equals(elementType)) {
            for (Object element : list) {
                Array.setBoolean(array, idx, (Boolean)DefaultGroovyMethods.invokeAsType(element, Boolean.TYPE));
                ++idx;
            }
        } else if (Byte.TYPE.equals(elementType)) {
            for (Object element : list) {
                Array.setByte(array, idx, (Byte)DefaultGroovyMethods.invokeAsType(element, Byte.TYPE));
                ++idx;
            }
        } else if (Character.TYPE.equals(elementType)) {
            for (Object element : list) {
                Array.setChar(array, idx, ((Character)DefaultGroovyMethods.invokeAsType(element, Character.TYPE)).charValue());
                ++idx;
            }
        } else if (Double.TYPE.equals(elementType)) {
            for (Object element : list) {
                Array.setDouble(array, idx, (Double)DefaultGroovyMethods.invokeAsType(element, Double.TYPE));
                ++idx;
            }
        } else if (Float.TYPE.equals(elementType)) {
            for (Object element : list) {
                Array.setFloat(array, idx, ((Float)DefaultGroovyMethods.invokeAsType(element, Float.TYPE)).floatValue());
                ++idx;
            }
        } else if (Integer.TYPE.equals(elementType)) {
            for (Object element : list) {
                Array.setInt(array, idx, (Integer)DefaultGroovyMethods.invokeAsType(element, Integer.TYPE));
                ++idx;
            }
        } else if (Long.TYPE.equals(elementType)) {
            for (Object element : list) {
                Array.setLong(array, idx, (Long)DefaultGroovyMethods.invokeAsType(element, Long.TYPE));
                ++idx;
            }
        } else if (Short.TYPE.equals(elementType)) {
            for (Object element : list) {
                Array.setShort(array, idx, (Short)DefaultGroovyMethods.invokeAsType(element, Short.TYPE));
                ++idx;
            }
        } else {
            for (Object element : list) {
                Array.set(array, idx, DefaultGroovyMethods.invokeAsType(element, elementType));
                ++idx;
            }
        }
        return array;
    }

    private static Object invokeAsType(Object element, Class<?> elementType) {
        Class target = element instanceof CharSequence ? StringGroovyMethods.class : DefaultGroovyMethods.class;
        return InvokerHelper.invokeStaticMethod(target, "asType", (Object)new Object[]{element, elementType});
    }

    public static <T> T newInstance(Class<T> c) {
        return (T)InvokerHelper.invokeConstructorOf(c, null);
    }

    public static <T> T newInstance(Class<T> c, Object[] args) {
        if (args == null) {
            args = new Object[]{null};
        }
        return (T)InvokerHelper.invokeConstructorOf(c, (Object)args);
    }

    public static MetaClass getMetaClass(Class c) {
        MetaClassRegistry metaClassRegistry = GroovySystem.getMetaClassRegistry();
        MetaClass mc = metaClassRegistry.getMetaClass(c);
        if (mc instanceof ExpandoMetaClass || mc instanceof DelegatingMetaClass && ((DelegatingMetaClass)mc).getAdaptee() instanceof ExpandoMetaClass) {
            return mc;
        }
        return new HandleMetaClass(mc);
    }

    public static MetaClass getMetaClass(Object obj) {
        MetaClass mc = InvokerHelper.getMetaClass(obj);
        return new HandleMetaClass(mc, obj);
    }

    public static MetaClass getMetaClass(GroovyObject obj) {
        return DefaultGroovyMethods.getMetaClass((Object)obj);
    }

    public static void setMetaClass(Class self, MetaClass metaClass) {
        MetaClassRegistry metaClassRegistry = GroovySystem.getMetaClassRegistry();
        if (metaClass == null) {
            metaClassRegistry.removeMetaClass(self);
        } else {
            if (metaClass instanceof HandleMetaClass) {
                metaClassRegistry.setMetaClass(self, ((HandleMetaClass)metaClass).getAdaptee());
            } else {
                metaClassRegistry.setMetaClass(self, metaClass);
            }
            if (self == NullObject.class) {
                NullObject.getNullObject().setMetaClass(metaClass);
            }
        }
    }

    public static void setMetaClass(Object self, MetaClass metaClass) {
        if (metaClass instanceof HandleMetaClass) {
            metaClass = ((HandleMetaClass)metaClass).getAdaptee();
        }
        if (self instanceof Class) {
            GroovySystem.getMetaClassRegistry().setMetaClass((Class)self, metaClass);
        } else {
            ((MetaClassRegistryImpl)GroovySystem.getMetaClassRegistry()).setMetaClass(self, metaClass);
        }
    }

    public static void setMetaClass(GroovyObject self, MetaClass metaClass) {
        if (metaClass instanceof HandleMetaClass) {
            metaClass = ((HandleMetaClass)metaClass).getAdaptee();
        }
        self.setMetaClass(metaClass);
        DefaultGroovyMethods.disablePrimitiveOptimization(self);
    }

    private static void disablePrimitiveOptimization(Object self) {
        Class<?> c = self.getClass();
        try {
            Field sdyn = c.getDeclaredField("__$stMC");
            sdyn.setBoolean(null, true);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static MetaClass metaClass(Class self, @ClosureParams(value=SimpleType.class, options={"java.lang.Object"}) @DelegatesTo(type="groovy.lang.ExpandoMetaClass.DefiningClosure", strategy=3) Closure closure) {
        MetaClassRegistry metaClassRegistry = GroovySystem.getMetaClassRegistry();
        MetaClass mc = metaClassRegistry.getMetaClass(self);
        if (mc instanceof ExpandoMetaClass) {
            ((ExpandoMetaClass)mc).define(closure);
            return mc;
        }
        if (mc instanceof DelegatingMetaClass && ((DelegatingMetaClass)mc).getAdaptee() instanceof ExpandoMetaClass) {
            ((ExpandoMetaClass)((DelegatingMetaClass)mc).getAdaptee()).define(closure);
            return mc;
        }
        if (mc instanceof DelegatingMetaClass && ((DelegatingMetaClass)mc).getAdaptee().getClass() == MetaClassImpl.class) {
            ExpandoMetaClass emc = new ExpandoMetaClass(self, false, true);
            emc.initialize();
            emc.define(closure);
            ((DelegatingMetaClass)mc).setAdaptee(emc);
            return mc;
        }
        if (mc.getClass() == MetaClassImpl.class) {
            mc = new ExpandoMetaClass(self, false, true);
            mc.initialize();
            ((ExpandoMetaClass)mc).define(closure);
            metaClassRegistry.setMetaClass(self, mc);
            return mc;
        }
        throw new GroovyRuntimeException("Can't add methods to custom meta class " + mc);
    }

    public static MetaClass metaClass(Object self, @ClosureParams(value=SimpleType.class, options={"java.lang.Object"}) @DelegatesTo(type="groovy.lang.ExpandoMetaClass.DefiningClosure", strategy=3) Closure closure) {
        MetaClass emc = DefaultGroovyMethods.hasPerInstanceMetaClass(self);
        if (emc == null) {
            ExpandoMetaClass metaClass = new ExpandoMetaClass(self.getClass(), false, true);
            metaClass.initialize();
            metaClass.define(closure);
            if (self instanceof GroovyObject) {
                DefaultGroovyMethods.setMetaClass((GroovyObject)self, (MetaClass)metaClass);
            } else {
                DefaultGroovyMethods.setMetaClass(self, (MetaClass)metaClass);
            }
            return metaClass;
        }
        if (emc instanceof ExpandoMetaClass) {
            ((ExpandoMetaClass)emc).define(closure);
            return emc;
        }
        if (emc instanceof DelegatingMetaClass && ((DelegatingMetaClass)emc).getAdaptee() instanceof ExpandoMetaClass) {
            ((ExpandoMetaClass)((DelegatingMetaClass)emc).getAdaptee()).define(closure);
            return emc;
        }
        throw new RuntimeException("Can't add methods to non-ExpandoMetaClass " + emc);
    }

    private static MetaClass hasPerInstanceMetaClass(Object object) {
        if (object instanceof GroovyObject) {
            MetaClass mc = ((GroovyObject)object).getMetaClass();
            if (mc == GroovySystem.getMetaClassRegistry().getMetaClass(object.getClass()) || mc.getClass() == MetaClassImpl.class) {
                return null;
            }
            return mc;
        }
        ClassInfo info = ClassInfo.getClassInfo(object.getClass());
        info.lock();
        try {
            MetaClass metaClass = info.getPerInstanceMetaClass(object);
            return metaClass;
        }
        finally {
            info.unlock();
        }
    }

    public static <T> Iterator<T> iterator(T[] a) {
        return DefaultTypeTransformation.asCollection(a).iterator();
    }

    public static Iterator iterator(Object o) {
        return DefaultTypeTransformation.asCollection(o).iterator();
    }

    public static <T> Iterator<T> iterator(final Enumeration<T> enumeration) {
        return new Iterator<T>(){

            @Override
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            @Override
            public T next() {
                return enumeration.nextElement();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove() from an Enumeration");
            }
        };
    }

    public static <T> Iterator<T> iterator(Iterator<T> self) {
        return self;
    }

    public static <T> BufferedIterator<T> buffered(Iterator<T> self) {
        if (self instanceof BufferedIterator) {
            return (BufferedIterator)self;
        }
        return new IteratorBufferedIterator<T>(self);
    }

    public static <T> BufferedIterator<T> bufferedIterator(Iterable<T> self) {
        return new IteratorBufferedIterator<T>(self.iterator());
    }

    public static <T> BufferedIterator<T> bufferedIterator(List<T> self) {
        return new ListBufferedIterator<T>(self);
    }

    public static List<MetaMethod> respondsTo(Object self, String name, Object[] argTypes) {
        return InvokerHelper.getMetaClass(self).respondsTo(self, name, argTypes);
    }

    public static List<MetaMethod> respondsTo(Object self, String name) {
        return InvokerHelper.getMetaClass(self).respondsTo(self, name);
    }

    public static MetaProperty hasProperty(Object self, String name) {
        return InvokerHelper.getMetaClass(self).hasProperty(self, name);
    }

    public static Object withTraits(Object self, Class<?> ... traits) {
        ArrayList<Class> interfaces = new ArrayList<Class>();
        Collections.addAll(interfaces, traits);
        return ProxyGenerator.INSTANCE.instantiateDelegate(interfaces, self);
    }

    public static <T> List<T> swap(List<T> self, int i, int j) {
        Collections.swap(self, i, j);
        return self;
    }

    public static <T> T[] swap(T[] self, int i, int j) {
        T tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static boolean[] swap(boolean[] self, int i, int j) {
        boolean tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static byte[] swap(byte[] self, int i, int j) {
        byte tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static char[] swap(char[] self, int i, int j) {
        char tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static double[] swap(double[] self, int i, int j) {
        double tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static float[] swap(float[] self, int i, int j) {
        float tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static int[] swap(int[] self, int i, int j) {
        int tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static long[] swap(long[] self, int i, int j) {
        long tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static short[] swap(short[] self, int i, int j) {
        short tmp = self[i];
        self[i] = self[j];
        self[j] = tmp;
        return self;
    }

    public static <E> E removeAt(List<E> self, int index) {
        return self.remove(index);
    }

    public static <E> boolean removeElement(Collection<E> self, Object o) {
        return self.remove(o);
    }

    public static Groovydoc getGroovydoc(AnnotatedElement holder) {
        groovy.lang.Groovydoc groovydocAnnotation = holder.getAnnotation(groovy.lang.Groovydoc.class);
        return null == groovydocAnnotation ? Groovydoc.EMPTY_GROOVYDOC : new Groovydoc(groovydocAnnotation.value(), holder);
    }

    public static String asString(Throwable self) {
        StringBuilderWriter sw = new StringBuilderWriter();
        try (PrintWriter pw = new PrintWriter(sw);){
            self.printStackTrace(pw);
        }
        return sw.toString();
    }

    private static final class IteratorIterableAdapter<T>
    implements Iterable<T> {
        private final Iterator<T> self;

        private IteratorIterableAdapter(Iterator<T> self) {
            this.self = self;
        }

        @Override
        public Iterator<T> iterator() {
            return this.self;
        }
    }

    private static final class ToUniqueIterator<E>
    implements Iterator<E> {
        private final Iterator<E> delegate;
        private final Set<E> seen;
        private boolean exhausted;
        private E next;

        private ToUniqueIterator(Iterator<E> delegate, Comparator<E> comparator) {
            this.delegate = delegate;
            this.seen = new TreeSet<E>(comparator);
            this.advance();
        }

        @Override
        public boolean hasNext() {
            return !this.exhausted;
        }

        @Override
        public E next() {
            if (this.exhausted) {
                throw new NoSuchElementException();
            }
            E result = this.next;
            this.advance();
            return result;
        }

        @Override
        public void remove() {
            if (this.exhausted) {
                throw new NoSuchElementException();
            }
            this.delegate.remove();
        }

        private void advance() {
            boolean foundNext = false;
            while (!foundNext && !this.exhausted) {
                boolean bl = this.exhausted = !this.delegate.hasNext();
                if (this.exhausted) continue;
                this.next = this.delegate.next();
                foundNext = this.seen.add(this.next);
            }
        }
    }

    private static final class ZipPostIterator<E>
    implements Iterator<Tuple2<E, Integer>> {
        private final Iterator<E> delegate;
        private int index;

        private ZipPostIterator(Iterator<E> delegate, int offset) {
            this.delegate = delegate;
            this.index = offset;
        }

        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @Override
        public Tuple2<E, Integer> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return new Tuple2<E, Integer>(this.delegate.next(), this.index++);
        }

        @Override
        public void remove() {
            this.delegate.remove();
        }
    }

    private static final class ZipPreIterator<E>
    implements Iterator<Tuple2<Integer, E>> {
        private final Iterator<E> delegate;
        private int index;

        private ZipPreIterator(Iterator<E> delegate, int offset) {
            this.delegate = delegate;
            this.index = offset;
        }

        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @Override
        public Tuple2<Integer, E> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return new Tuple2<Integer, E>(this.index++, this.delegate.next());
        }

        @Override
        public void remove() {
            this.delegate.remove();
        }
    }

    private static class NumberAwareValueComparator<K, V>
    implements Comparator<Map.Entry<K, V>> {
        private final Comparator<V> delegate = new NumberAwareComparator<V>();

        private NumberAwareValueComparator() {
        }

        @Override
        public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
            return this.delegate.compare(e1.getValue(), e2.getValue());
        }
    }

    private static final class InitIterator<E>
    implements Iterator<E> {
        private final Iterator<E> delegate;
        private boolean exhausted;
        private E next;

        private InitIterator(Iterator<E> delegate) {
            this.delegate = delegate;
            this.advance();
        }

        @Override
        public boolean hasNext() {
            return !this.exhausted;
        }

        @Override
        public E next() {
            if (this.exhausted) {
                throw new NoSuchElementException();
            }
            E result = this.next;
            this.advance();
            return result;
        }

        @Override
        public void remove() {
            if (this.exhausted) {
                throw new NoSuchElementException();
            }
            this.advance();
        }

        private void advance() {
            this.next = this.delegate.next();
            this.exhausted = !this.delegate.hasNext();
        }
    }

    private static final class TakeIterator<E>
    implements Iterator<E> {
        private final Iterator<E> delegate;
        private Integer num;

        private TakeIterator(Iterator<E> delegate, Integer num) {
            this.delegate = delegate;
            this.num = num;
        }

        @Override
        public boolean hasNext() {
            return this.num > 0 && this.delegate.hasNext();
        }

        @Override
        public E next() {
            if (this.num <= 0) {
                throw new NoSuchElementException();
            }
            Integer n = this.num;
            this.num = this.num - 1;
            return this.delegate.next();
        }

        @Override
        public void remove() {
            this.delegate.remove();
        }
    }

    private static final class DropRightIterator<E>
    implements Iterator<E> {
        private final Iterator<E> delegate;
        private final LinkedList<E> discards;
        private boolean exhausted;
        private int num;

        private DropRightIterator(Iterator<E> delegate, int num) {
            this.delegate = delegate;
            this.num = num;
            this.discards = new LinkedList();
            this.advance();
        }

        @Override
        public boolean hasNext() {
            return !this.exhausted;
        }

        @Override
        public E next() {
            if (this.exhausted) {
                throw new NoSuchElementException();
            }
            E result = this.discards.removeFirst();
            this.advance();
            return result;
        }

        @Override
        public void remove() {
            if (this.exhausted) {
                throw new NoSuchElementException();
            }
            this.delegate.remove();
        }

        private void advance() {
            while (this.discards.size() <= this.num && !this.exhausted) {
                boolean bl = this.exhausted = !this.delegate.hasNext();
                if (this.exhausted) continue;
                this.discards.add(this.delegate.next());
            }
        }
    }

    private static final class TakeWhileIterator<E>
    implements Iterator<E> {
        private final Iterator<E> delegate;
        private final BooleanClosureWrapper condition;
        private boolean exhausted;
        private E next;

        private TakeWhileIterator(Iterator<E> delegate, Closure condition) {
            this.delegate = delegate;
            this.condition = new BooleanClosureWrapper(condition);
            this.advance();
        }

        @Override
        public boolean hasNext() {
            return !this.exhausted;
        }

        @Override
        public E next() {
            if (this.exhausted) {
                throw new NoSuchElementException();
            }
            E result = this.next;
            this.advance();
            return result;
        }

        @Override
        public void remove() {
            if (this.exhausted) {
                throw new NoSuchElementException();
            }
            this.delegate.remove();
        }

        private void advance() {
            boolean bl = this.exhausted = !this.delegate.hasNext();
            if (!this.exhausted) {
                this.next = this.delegate.next();
                if (!this.condition.call(this.next)) {
                    this.exhausted = true;
                    this.next = null;
                }
            }
        }
    }

    private static final class DropWhileIterator<E>
    implements Iterator<E> {
        private final Iterator<E> delegate;
        private final Closure condition;
        private boolean buffering = false;
        private E buffer = null;

        private DropWhileIterator(Iterator<E> delegate, Closure condition) {
            this.delegate = delegate;
            this.condition = condition;
            this.prepare();
        }

        @Override
        public boolean hasNext() {
            return this.buffering || this.delegate.hasNext();
        }

        @Override
        public E next() {
            if (this.buffering) {
                E result = this.buffer;
                this.buffering = false;
                this.buffer = null;
                return result;
            }
            return this.delegate.next();
        }

        @Override
        public void remove() {
            if (this.buffering) {
                this.buffering = false;
                this.buffer = null;
            } else {
                this.delegate.remove();
            }
        }

        private void prepare() {
            BooleanClosureWrapper bcw = new BooleanClosureWrapper(this.condition);
            while (this.delegate.hasNext()) {
                E next = this.delegate.next();
                if (bcw.call(next)) continue;
                this.buffer = next;
                this.buffering = true;
                break;
            }
        }
    }
}

