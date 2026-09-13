/*
 * Decompiled with CFR 0.152.
 */
package groovy.inspect;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.MetaMethod;
import groovy.lang.PropertyValue;
import groovy.lang.Tuple2;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.FormatHelper;
import org.codehaus.groovy.runtime.InvokerHelper;

public class Inspector {
    protected Object objectUnderInspection;
    public static final int CLASS_PACKAGE_IDX = 0;
    public static final int CLASS_CLASS_IDX = 1;
    public static final int CLASS_INTERFACE_IDX = 2;
    public static final int CLASS_SUPERCLASS_IDX = 3;
    public static final int CLASS_OTHER_IDX = 4;
    public static final int MEMBER_ORIGIN_IDX = 0;
    public static final int MEMBER_MODIFIER_IDX = 1;
    public static final int MEMBER_DECLARER_IDX = 2;
    public static final int MEMBER_TYPE_IDX = 3;
    public static final int MEMBER_NAME_IDX = 4;
    public static final int MEMBER_PARAMS_IDX = 5;
    public static final int MEMBER_VALUE_IDX = 5;
    public static final int MEMBER_EXCEPTIONS_IDX = 6;
    public static final String NOT_APPLICABLE = "n/a";
    public static final String GROOVY = "GROOVY";
    public static final String JAVA = "JAVA";

    public Inspector(Object objectUnderInspection) {
        if (null == objectUnderInspection) {
            throw new IllegalArgumentException("argument must not be null");
        }
        this.objectUnderInspection = objectUnderInspection;
    }

    public String[] getClassProps() {
        Class<?>[] interfaces;
        String[] result = new String[5];
        Package pack = this.getClassUnderInspection().getPackage();
        result[0] = "package " + (pack == null ? NOT_APPLICABLE : pack.getName());
        String modifiers = Modifier.toString(this.getClassUnderInspection().getModifiers());
        result[1] = modifiers + " class " + Inspector.shortName(this.getClassUnderInspection());
        result[2] = "implements ";
        for (Class<?> anInterface : interfaces = this.getClassUnderInspection().getInterfaces()) {
            result[2] = result[2] + Inspector.shortName(anInterface) + " ";
        }
        result[3] = "extends " + Inspector.shortName(this.getClassUnderInspection().getSuperclass());
        result[4] = "is Primitive: " + this.getClassUnderInspection().isPrimitive() + ", is Array: " + this.getClassUnderInspection().isArray() + ", is Groovy: " + this.isGroovy();
        return result;
    }

    public boolean isGroovy() {
        return GroovyObject.class.isAssignableFrom(this.getClassUnderInspection());
    }

    public Object getObject() {
        return this.objectUnderInspection;
    }

    public Object[] getMethods() {
        int resultIndex;
        Method[] methods = this.getClassUnderInspection().getMethods();
        Constructor<?>[] ctors = this.getClassUnderInspection().getConstructors();
        Object[] result = new Object[methods.length + ctors.length];
        for (resultIndex = 0; resultIndex < methods.length; ++resultIndex) {
            Method method = methods[resultIndex];
            result[resultIndex] = this.methodInfo(method);
        }
        int i = 0;
        while (i < ctors.length) {
            Constructor<?> ctor = ctors[i];
            result[resultIndex] = this.methodInfo(ctor);
            ++i;
            ++resultIndex;
        }
        return result;
    }

    public Tuple2[] getMethodsWithInfo() {
        int resultIndex;
        Method[] methods = this.getClassUnderInspection().getMethods();
        Constructor<?>[] ctors = this.getClassUnderInspection().getConstructors();
        Tuple2[] result = new Tuple2[methods.length + ctors.length];
        for (resultIndex = 0; resultIndex < methods.length; ++resultIndex) {
            Method method = methods[resultIndex];
            result[resultIndex] = Tuple2.tuple(method, this.methodInfo(method));
        }
        int i = 0;
        while (i < ctors.length) {
            Constructor<?> ctor = ctors[i];
            result[resultIndex] = Tuple2.tuple(ctor, this.methodInfo(ctor));
            ++i;
            ++resultIndex;
        }
        return result;
    }

    public Object[] getMetaMethods() {
        MetaClass metaClass = InvokerHelper.getMetaClass(this.objectUnderInspection);
        List<MetaMethod> metaMethods = metaClass.getMetaMethods();
        Object[] result = new Object[metaMethods.size()];
        int i = 0;
        for (MetaMethod metaMethod : metaMethods) {
            result[i] = this.methodInfo(metaMethod);
            ++i;
        }
        return result;
    }

    public Tuple2[] getMetaMethodsWithInfo() {
        MetaClass metaClass = InvokerHelper.getMetaClass(this.objectUnderInspection);
        List<MetaMethod> metaMethods = metaClass.getMetaMethods();
        Tuple2[] result = new Tuple2[metaMethods.size()];
        int i = 0;
        for (MetaMethod metaMethod : metaMethods) {
            result[i] = Tuple2.tuple(metaMethod, this.methodInfo(metaMethod));
            ++i;
        }
        return result;
    }

    public Object[] getPublicFields() {
        Field[] fields = this.getClassUnderInspection().getFields();
        Object[] result = new Object[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            result[i] = this.fieldInfo(field);
        }
        return result;
    }

    public Object[] getPropertyInfo() {
        List<PropertyValue> props = DefaultGroovyMethods.getMetaPropertyValues(this.objectUnderInspection);
        Object[] result = new Object[props.size()];
        int i = 0;
        for (PropertyValue pv : props) {
            result[i] = this.fieldInfo(pv);
            ++i;
        }
        return result;
    }

    protected String[] fieldInfo(Field field) {
        String[] result = new String[6];
        result[0] = JAVA;
        result[1] = Modifier.toString(field.getModifiers());
        result[2] = Inspector.shortName(field.getDeclaringClass());
        result[3] = Inspector.shortName(field.getType());
        result[4] = field.getName();
        try {
            result[5] = FormatHelper.inspect(field.get(this.objectUnderInspection));
        }
        catch (IllegalAccessException e) {
            result[5] = NOT_APPLICABLE;
        }
        return this.withoutNulls(result);
    }

    protected String[] fieldInfo(PropertyValue pv) {
        return this.fieldWithInfo(pv).getV2();
    }

    public Object[] getPropertiesWithInfo() {
        List<PropertyValue> props = DefaultGroovyMethods.getMetaPropertyValues(this.objectUnderInspection);
        Object[] result = new Object[props.size()];
        int i = 0;
        Iterator<PropertyValue> iter = props.iterator();
        while (iter.hasNext()) {
            result[i] = this.fieldWithInfo(iter.next());
            ++i;
        }
        return result;
    }

    protected Tuple2<Object, String[]> fieldWithInfo(PropertyValue pv) {
        String[] info = new String[6];
        info[0] = GROOVY;
        info[1] = "public";
        info[2] = NOT_APPLICABLE;
        info[3] = Inspector.shortName(pv.getType());
        info[4] = pv.getName();
        Object field = null;
        try {
            field = pv.getValue();
            info[5] = FormatHelper.inspect(field);
        }
        catch (Exception e) {
            info[5] = NOT_APPLICABLE;
        }
        info = this.withoutNulls(info);
        return new Tuple2<Object, String[]>(field, info);
    }

    protected Class getClassUnderInspection() {
        return this.objectUnderInspection.getClass();
    }

    public static String shortName(Class clazz) {
        if (null == clazz) {
            return NOT_APPLICABLE;
        }
        String className = clazz.getName();
        if (null == clazz.getPackage()) {
            return className;
        }
        String packageName = clazz.getPackage().getName();
        int offset = packageName.length();
        if (offset > 0) {
            ++offset;
        }
        className = className.substring(offset);
        return className;
    }

    private static String makeTypesInfo(Class[] types) {
        StringBuilder sb = new StringBuilder(32);
        for (int k = 0; k < types.length; ++k) {
            sb.append(Inspector.shortName(types[k]));
            if (k >= types.length - 1) continue;
            sb.append(", ");
        }
        return sb.toString();
    }

    private static String makeParamsInfo(Class[] params) {
        return Inspector.makeTypesInfo(params);
    }

    private static String makeExceptionInfo(Class[] exceptions) {
        return Inspector.makeTypesInfo(exceptions);
    }

    protected String[] methodInfo(Method method) {
        String[] result = new String[7];
        result[0] = JAVA;
        result[2] = Inspector.shortName(method.getDeclaringClass());
        result[1] = Modifier.toString(method.getModifiers());
        result[4] = method.getName();
        result[3] = Inspector.shortName(method.getReturnType());
        result[5] = Inspector.makeParamsInfo(method.getParameterTypes());
        result[6] = Inspector.makeExceptionInfo(method.getExceptionTypes());
        return this.withoutNulls(result);
    }

    protected String[] methodInfo(Constructor ctor) {
        String[] result = new String[]{JAVA, Modifier.toString(ctor.getModifiers()), Inspector.shortName(ctor.getDeclaringClass()), Inspector.shortName(ctor.getDeclaringClass()), ctor.getName(), Inspector.makeParamsInfo(ctor.getParameterTypes()), Inspector.makeExceptionInfo(ctor.getExceptionTypes())};
        return this.withoutNulls(result);
    }

    protected String[] methodInfo(MetaMethod method) {
        String[] result = new String[7];
        int mod = method.getModifiers();
        result[0] = GROOVY;
        result[1] = Modifier.toString(mod);
        result[2] = Inspector.shortName(method.getDeclaringClass().getTheClass());
        result[3] = Inspector.shortName(method.getReturnType());
        result[4] = method.getName();
        result[5] = Inspector.makeParamsInfo(method.getNativeParameterTypes());
        result[6] = NOT_APPLICABLE;
        return this.withoutNulls(result);
    }

    protected String[] withoutNulls(String[] toNormalize) {
        for (int i = 0; i < toNormalize.length; ++i) {
            String s = toNormalize[i];
            if (null != s) continue;
            toNormalize[i] = NOT_APPLICABLE;
        }
        return toNormalize;
    }

    public static void print(Object[] memberInfo) {
        Inspector.print(System.out, memberInfo);
    }

    static void print(PrintStream out, Object[] memberInfo) {
        for (int i = 0; i < memberInfo.length; ++i) {
            String[] metaMethod = (String[])memberInfo[i];
            out.print(i + ":\t");
            for (String s : metaMethod) {
                out.print(s + " ");
            }
            out.println();
        }
    }

    public static Collection sort(List<Object> memberInfo) {
        return Inspector.sort(memberInfo, new MemberComparator());
    }

    public static Collection sort(List<Object> memberInfo, Comparator<Object> comparator) {
        memberInfo.sort(comparator);
        return memberInfo;
    }

    public static class MemberComparator
    implements Comparator<Object>,
    Serializable {
        private static final long serialVersionUID = -7691851726606749541L;

        @Override
        public int compare(Object a, Object b) {
            String[] aStr = (String[])a;
            String[] bStr = (String[])b;
            int result = aStr[4].compareTo(bStr[4]);
            if (0 != result) {
                return result;
            }
            result = aStr[3].compareTo(bStr[3]);
            if (0 != result) {
                return result;
            }
            result = aStr[5].compareTo(bStr[5]);
            if (0 != result) {
                return result;
            }
            result = aStr[2].compareTo(bStr[2]);
            if (0 != result) {
                return result;
            }
            result = aStr[1].compareTo(bStr[1]);
            if (0 != result) {
                return result;
            }
            result = aStr[0].compareTo(bStr[0]);
            return result;
        }
    }

    public static class MemberComparatorWithValue
    implements Comparator<Object>,
    Serializable {
        private static final long serialVersionUID = 294298614093394525L;
        private static final MemberComparator delegate = new MemberComparator();

        @Override
        public int compare(Object a, Object b) {
            Tuple2 aTuple = (Tuple2)a;
            Tuple2 bTuple = (Tuple2)b;
            return delegate.compare(aTuple.getV2(), bTuple.getV2());
        }
    }
}

