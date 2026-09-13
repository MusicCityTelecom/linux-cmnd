/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util.ajax;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class JSONPojoConvertor
implements JSON.Convertor {
    private static final Logger LOG = Log.getLogger(JSONPojoConvertor.class);
    public static final Object[] GETTER_ARG = new Object[0];
    public static final Object[] NULL_ARG = new Object[]{null};
    private static final Map<Class<?>, NumberType> __numberTypes = new HashMap();
    protected boolean _fromJSON;
    protected Class<?> _pojoClass;
    protected Map<String, Method> _getters = new HashMap<String, Method>();
    protected Map<String, Setter> _setters = new HashMap<String, Setter>();
    protected Set<String> _excluded;
    public static final NumberType SHORT = new NumberType(){

        @Override
        public Object getActualValue(Number number) {
            return new Short(number.shortValue());
        }
    };
    public static final NumberType INTEGER = new NumberType(){

        @Override
        public Object getActualValue(Number number) {
            return new Integer(number.intValue());
        }
    };
    public static final NumberType FLOAT = new NumberType(){

        @Override
        public Object getActualValue(Number number) {
            return new Float(number.floatValue());
        }
    };
    public static final NumberType LONG = new NumberType(){

        @Override
        public Object getActualValue(Number number) {
            return number instanceof Long ? (Number)number : (Number)new Long(number.longValue());
        }
    };
    public static final NumberType DOUBLE = new NumberType(){

        @Override
        public Object getActualValue(Number number) {
            return number instanceof Double ? (Number)number : (Number)new Double(number.doubleValue());
        }
    };

    public static NumberType getNumberType(Class<?> clazz) {
        return __numberTypes.get(clazz);
    }

    public JSONPojoConvertor(Class<?> pojoClass) {
        this(pojoClass, null, true);
    }

    public JSONPojoConvertor(Class<?> pojoClass, String[] excluded) {
        this(pojoClass, new HashSet<String>(Arrays.asList(excluded)), true);
    }

    public JSONPojoConvertor(Class<?> pojoClass, Set<String> excluded) {
        this(pojoClass, excluded, true);
    }

    public JSONPojoConvertor(Class<?> pojoClass, Set<String> excluded, boolean fromJSON) {
        this._pojoClass = pojoClass;
        this._excluded = excluded;
        this._fromJSON = fromJSON;
        this.init();
    }

    public JSONPojoConvertor(Class<?> pojoClass, boolean fromJSON) {
        this(pojoClass, null, fromJSON);
    }

    protected void init() {
        Method[] methods = this._pojoClass.getMethods();
        block4: for (int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            if (Modifier.isStatic(m.getModifiers()) || m.getDeclaringClass() == Object.class) continue;
            String name = m.getName();
            switch (m.getParameterCount()) {
                case 0: {
                    if (m.getReturnType() == null) continue block4;
                    if (name.startsWith("is") && name.length() > 2) {
                        name = name.substring(2, 3).toLowerCase(Locale.ENGLISH) + name.substring(3);
                    } else {
                        if (!name.startsWith("get") || name.length() <= 3) continue block4;
                        name = name.substring(3, 4).toLowerCase(Locale.ENGLISH) + name.substring(4);
                    }
                    if (!this.includeField(name, m)) continue block4;
                    this.addGetter(name, m);
                    continue block4;
                }
                case 1: {
                    if (!name.startsWith("set") || name.length() <= 3 || !this.includeField(name = name.substring(3, 4).toLowerCase(Locale.ENGLISH) + name.substring(4), m)) continue block4;
                    this.addSetter(name, m);
                }
            }
        }
    }

    protected void addGetter(String name, Method method) {
        this._getters.put(name, method);
    }

    protected void addSetter(String name, Method method) {
        this._setters.put(name, new Setter(name, method));
    }

    protected Setter getSetter(String name) {
        return this._setters.get(name);
    }

    protected boolean includeField(String name, Method m) {
        return this._excluded == null || !this._excluded.contains(name);
    }

    protected int getExcludedCount() {
        return this._excluded == null ? 0 : this._excluded.size();
    }

    @Override
    public Object fromJSON(Map object) {
        Object obj = null;
        try {
            obj = this._pojoClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.setProps(obj, object);
        return obj;
    }

    public int setProps(Object obj, Map<?, ?> props) {
        int count = 0;
        for (Map.Entry<?, ?> entry : props.entrySet()) {
            Setter setter = this.getSetter((String)entry.getKey());
            if (setter == null) continue;
            try {
                setter.invoke(obj, entry.getValue());
                ++count;
            }
            catch (Exception e) {
                LOG.warn(this._pojoClass.getName() + "#" + setter.getPropertyName() + " not set from " + entry.getValue().getClass().getName() + "=" + entry.getValue().toString(), new Object[0]);
                this.log(e);
            }
        }
        return count;
    }

    @Override
    public void toJSON(Object obj, JSON.Output out) {
        if (this._fromJSON) {
            out.addClass(this._pojoClass);
        }
        for (Map.Entry<String, Method> entry : this._getters.entrySet()) {
            try {
                out.add(entry.getKey(), entry.getValue().invoke(obj, GETTER_ARG));
            }
            catch (Exception e) {
                LOG.warn("{} property '{}' excluded. (errors)", this._pojoClass.getName(), entry.getKey());
                this.log(e);
            }
        }
    }

    protected void log(Throwable t) {
        LOG.ignore(t);
    }

    static {
        __numberTypes.put(Short.class, SHORT);
        __numberTypes.put(Short.TYPE, SHORT);
        __numberTypes.put(Integer.class, INTEGER);
        __numberTypes.put(Integer.TYPE, INTEGER);
        __numberTypes.put(Long.class, LONG);
        __numberTypes.put(Long.TYPE, LONG);
        __numberTypes.put(Float.class, FLOAT);
        __numberTypes.put(Float.TYPE, FLOAT);
        __numberTypes.put(Double.class, DOUBLE);
        __numberTypes.put(Double.TYPE, DOUBLE);
    }

    public static interface NumberType {
        public Object getActualValue(Number var1);
    }

    public static class Setter {
        protected String _propertyName;
        protected Method _setter;
        protected NumberType _numberType;
        protected Class<?> _type;
        protected Class<?> _componentType;

        public Setter(String propertyName, Method method) {
            this._propertyName = propertyName;
            this._setter = method;
            this._type = method.getParameterTypes()[0];
            this._numberType = (NumberType)__numberTypes.get(this._type);
            if (this._numberType == null && this._type.isArray()) {
                this._componentType = this._type.getComponentType();
                this._numberType = (NumberType)__numberTypes.get(this._componentType);
            }
        }

        public String getPropertyName() {
            return this._propertyName;
        }

        public Method getMethod() {
            return this._setter;
        }

        public NumberType getNumberType() {
            return this._numberType;
        }

        public Class<?> getType() {
            return this._type;
        }

        public Class<?> getComponentType() {
            return this._componentType;
        }

        public boolean isPropertyNumber() {
            return this._numberType != null;
        }

        public void invoke(Object obj, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
            if (value == null) {
                this._setter.invoke(obj, NULL_ARG);
            } else {
                this.invokeObject(obj, value);
            }
        }

        protected void invokeObject(Object obj, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
            if (this._type.isEnum()) {
                if (value instanceof Enum) {
                    this._setter.invoke(obj, value);
                } else {
                    this._setter.invoke(obj, Enum.valueOf(this._type, value.toString()));
                }
            } else if (this._numberType != null && value instanceof Number) {
                this._setter.invoke(obj, this._numberType.getActualValue((Number)value));
            } else if (Character.TYPE.equals(this._type) || Character.class.equals(this._type)) {
                this._setter.invoke(obj, Character.valueOf(String.valueOf(value).charAt(0)));
            } else if (this._componentType != null && value.getClass().isArray()) {
                if (this._numberType == null) {
                    int len = Array.getLength(value);
                    Object array = Array.newInstance(this._componentType, len);
                    try {
                        System.arraycopy(value, 0, array, 0, len);
                    }
                    catch (Exception e) {
                        LOG.ignore(e);
                        this._setter.invoke(obj, value);
                        return;
                    }
                    this._setter.invoke(obj, array);
                } else {
                    Object[] old = (Object[])value;
                    Object array = Array.newInstance(this._componentType, old.length);
                    try {
                        for (int i = 0; i < old.length; ++i) {
                            Array.set(array, i, this._numberType.getActualValue((Number)old[i]));
                        }
                    }
                    catch (Exception e) {
                        LOG.ignore(e);
                        this._setter.invoke(obj, value);
                        return;
                    }
                    this._setter.invoke(obj, array);
                }
            } else {
                this._setter.invoke(obj, value);
            }
        }
    }
}

