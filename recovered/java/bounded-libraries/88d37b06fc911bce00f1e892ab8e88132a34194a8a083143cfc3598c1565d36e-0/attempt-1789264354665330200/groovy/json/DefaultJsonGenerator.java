/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.MetaBeanProperty
 *  groovy.lang.MetaProperty
 *  groovy.util.Expando
 *  org.codehaus.groovy.runtime.InvokerHelper
 */
package groovy.json;

import groovy.json.JsonDelegate;
import groovy.json.JsonException;
import groovy.json.JsonGenerator;
import groovy.json.JsonOutput;
import groovy.lang.Closure;
import groovy.lang.MetaBeanProperty;
import groovy.lang.MetaProperty;
import groovy.util.Expando;
import java.io.File;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import org.apache.groovy.json.internal.CharBuf;
import org.apache.groovy.json.internal.Chr;
import org.codehaus.groovy.runtime.InvokerHelper;

public class DefaultJsonGenerator
implements JsonGenerator {
    protected final boolean excludeNulls;
    protected final boolean disableUnicodeEscaping;
    protected final String dateFormat;
    protected final Locale dateLocale;
    protected final TimeZone timezone;
    protected final Set<JsonGenerator.Converter> converters = new LinkedHashSet<JsonGenerator.Converter>();
    protected final Set<String> excludedFieldNames = new HashSet<String>();
    protected final Set<Class<?>> excludedFieldTypes = new HashSet();

    protected DefaultJsonGenerator(JsonGenerator.Options options) {
        this.excludeNulls = options.excludeNulls;
        this.disableUnicodeEscaping = options.disableUnicodeEscaping;
        this.dateFormat = options.dateFormat;
        this.dateLocale = options.dateLocale;
        this.timezone = options.timezone;
        if (!options.converters.isEmpty()) {
            this.converters.addAll(options.converters);
        }
        if (!options.excludedFieldNames.isEmpty()) {
            this.excludedFieldNames.addAll(options.excludedFieldNames);
        }
        if (!options.excludedFieldTypes.isEmpty()) {
            this.excludedFieldTypes.addAll(options.excludedFieldTypes);
        }
    }

    @Override
    public String toJson(Object object) {
        CharBuf buffer = CharBuf.create(255);
        this.writeObject(object, buffer);
        return buffer.toString();
    }

    @Override
    public boolean isExcludingFieldsNamed(String name) {
        return this.excludedFieldNames.contains(name);
    }

    @Override
    public boolean isExcludingValues(Object value) {
        if (value == null) {
            return this.excludeNulls;
        }
        return this.shouldExcludeType(value.getClass());
    }

    protected void writeNumber(Class<?> numberClass, Number value, CharBuf buffer) {
        if (numberClass == Integer.class) {
            buffer.addInt((Integer)value);
        } else if (numberClass == Long.class) {
            buffer.addLong((Long)value);
        } else if (numberClass == BigInteger.class) {
            buffer.addBigInteger((BigInteger)value);
        } else if (numberClass == BigDecimal.class) {
            buffer.addBigDecimal((BigDecimal)value);
        } else if (numberClass == Double.class) {
            Double doubleValue = (Double)value;
            if (doubleValue.isInfinite()) {
                throw new JsonException("Number " + value + " can't be serialized as JSON: infinite are not allowed in JSON.");
            }
            if (doubleValue.isNaN()) {
                throw new JsonException("Number " + value + " can't be serialized as JSON: NaN are not allowed in JSON.");
            }
            buffer.addDouble(doubleValue);
        } else if (numberClass == Float.class) {
            Float floatValue = (Float)value;
            if (floatValue.isInfinite()) {
                throw new JsonException("Number " + value + " can't be serialized as JSON: infinite are not allowed in JSON.");
            }
            if (floatValue.isNaN()) {
                throw new JsonException("Number " + value + " can't be serialized as JSON: NaN are not allowed in JSON.");
            }
            buffer.addFloat(floatValue);
        } else if (numberClass == Byte.class) {
            buffer.addByte((Byte)value);
        } else if (numberClass == Short.class) {
            buffer.addShort((Short)value);
        } else {
            buffer.addString(value.toString());
        }
    }

    protected void writeObject(Object object, CharBuf buffer) {
        this.writeObject(null, object, buffer);
    }

    protected void writeObject(String key, Object object, CharBuf buffer) {
        if (this.isExcludingValues(object)) {
            return;
        }
        if (object == null) {
            buffer.addNull();
            return;
        }
        Class<?> objectClass = object.getClass();
        JsonGenerator.Converter converter = this.findConverter(objectClass);
        if (converter != null) {
            object = converter.convert(object, key);
            objectClass = object.getClass();
        }
        if (CharSequence.class.isAssignableFrom(objectClass)) {
            this.writeCharSequence((CharSequence)object, buffer);
        } else if (objectClass == Boolean.class) {
            buffer.addBoolean((Boolean)object);
        } else if (Number.class.isAssignableFrom(objectClass)) {
            this.writeNumber(objectClass, (Number)object, buffer);
        } else if (Date.class.isAssignableFrom(objectClass)) {
            this.writeDate((Date)object, buffer);
        } else if (Calendar.class.isAssignableFrom(objectClass)) {
            this.writeDate(((Calendar)object).getTime(), buffer);
        } else if (Map.class.isAssignableFrom(objectClass)) {
            this.writeMap((Map)object, buffer);
        } else if (Iterable.class.isAssignableFrom(objectClass)) {
            this.writeIterator(((Iterable)object).iterator(), buffer);
        } else if (Iterator.class.isAssignableFrom(objectClass)) {
            this.writeIterator((Iterator)object, buffer);
        } else if (objectClass == Character.class) {
            buffer.addJsonEscapedString(Chr.array(((Character)object).charValue()), this.disableUnicodeEscaping);
        } else if (objectClass == URL.class) {
            buffer.addJsonEscapedString(object.toString(), this.disableUnicodeEscaping);
        } else if (objectClass == UUID.class) {
            buffer.addQuoted(object.toString());
        } else if (objectClass == JsonOutput.JsonUnescaped.class) {
            buffer.add(object.toString());
        } else if (Closure.class.isAssignableFrom(objectClass)) {
            this.writeMap(JsonDelegate.cloneDelegateAndGetContent((Closure)object), buffer);
        } else if (Expando.class.isAssignableFrom(objectClass)) {
            this.writeMap(((Expando)object).getProperties(), buffer);
        } else if (Enumeration.class.isAssignableFrom(objectClass)) {
            ArrayList list = Collections.list((Enumeration)object);
            this.writeIterator(list.iterator(), buffer);
        } else if (objectClass.isArray()) {
            this.writeArray(objectClass, object, buffer);
        } else if (Enum.class.isAssignableFrom(objectClass)) {
            buffer.addQuoted(((Enum)object).name());
        } else if (File.class.isAssignableFrom(objectClass)) {
            Map<?, ?> properties = this.getObjectProperties(object);
            properties.entrySet().removeIf(entry -> entry.getValue() instanceof File);
            this.writeMap(properties, buffer);
        } else {
            Map<?, ?> properties = this.getObjectProperties(object);
            this.writeMap(properties, buffer);
        }
    }

    protected Map<?, ?> getObjectProperties(Object object) {
        List metaProperties = InvokerHelper.getMetaClass((Object)object).getProperties();
        LinkedHashMap<String, Object> namesAndValues = new LinkedHashMap<String, Object>(metaProperties.size());
        for (MetaProperty mp : metaProperties) {
            String name;
            MetaBeanProperty mbp;
            if (!Modifier.isPublic(mp.getModifiers()) || Modifier.isStatic(mp.getModifiers()) || mp instanceof MetaBeanProperty && (mbp = (MetaBeanProperty)mp).getField() == null && mbp.getGetter() == null || (name = mp.getName()).equals("class") || name.equals("metaClass") || name.equals("declaringClass")) continue;
            namesAndValues.put(name, mp.getProperty(object));
        }
        return namesAndValues;
    }

    protected void writeCharSequence(CharSequence seq, CharBuf buffer) {
        if (seq.length() > 0) {
            buffer.addJsonEscapedString(seq.toString(), this.disableUnicodeEscaping);
        } else {
            buffer.addChars(JsonOutput.EMPTY_STRING_CHARS);
        }
    }

    protected void writeRaw(CharSequence seq, CharBuf buffer) {
        if (seq != null) {
            buffer.add(seq.toString());
        }
    }

    protected void writeDate(Date date, CharBuf buffer) {
        SimpleDateFormat formatter = new SimpleDateFormat(this.dateFormat, this.dateLocale);
        formatter.setTimeZone(this.timezone);
        buffer.addQuoted(formatter.format(date));
    }

    protected void writeArray(Class<?> arrayClass, Object array, CharBuf buffer) {
        short[] shortArray;
        if (Object[].class.isAssignableFrom(arrayClass)) {
            Object[] objArray = (Object[])array;
            this.writeIterator(Arrays.asList(objArray).iterator(), buffer);
            return;
        }
        buffer.addChar('[');
        if (int[].class.isAssignableFrom(arrayClass)) {
            int[] intArray = (int[])array;
            if (intArray.length > 0) {
                buffer.addInt(intArray[0]);
                for (int i = 1; i < intArray.length; ++i) {
                    buffer.addChar(',').addInt(intArray[i]);
                }
            }
        } else if (long[].class.isAssignableFrom(arrayClass)) {
            long[] longArray = (long[])array;
            if (longArray.length > 0) {
                buffer.addLong(longArray[0]);
                for (int i = 1; i < longArray.length; ++i) {
                    buffer.addChar(',').addLong(longArray[i]);
                }
            }
        } else if (boolean[].class.isAssignableFrom(arrayClass)) {
            boolean[] booleanArray = (boolean[])array;
            if (booleanArray.length > 0) {
                buffer.addBoolean(booleanArray[0]);
                for (int i = 1; i < booleanArray.length; ++i) {
                    buffer.addChar(',').addBoolean(booleanArray[i]);
                }
            }
        } else if (char[].class.isAssignableFrom(arrayClass)) {
            char[] charArray = (char[])array;
            if (charArray.length > 0) {
                buffer.addJsonEscapedString(Chr.array(charArray[0]), this.disableUnicodeEscaping);
                for (int i = 1; i < charArray.length; ++i) {
                    buffer.addChar(',').addJsonEscapedString(Chr.array(charArray[i]), this.disableUnicodeEscaping);
                }
            }
        } else if (double[].class.isAssignableFrom(arrayClass)) {
            double[] doubleArray = (double[])array;
            if (doubleArray.length > 0) {
                buffer.addDouble(doubleArray[0]);
                for (int i = 1; i < doubleArray.length; ++i) {
                    buffer.addChar(',').addDouble(doubleArray[i]);
                }
            }
        } else if (float[].class.isAssignableFrom(arrayClass)) {
            float[] floatArray = (float[])array;
            if (floatArray.length > 0) {
                buffer.addFloat(floatArray[0]);
                for (int i = 1; i < floatArray.length; ++i) {
                    buffer.addChar(',').addFloat(floatArray[i]);
                }
            }
        } else if (byte[].class.isAssignableFrom(arrayClass)) {
            byte[] byteArray = (byte[])array;
            if (byteArray.length > 0) {
                buffer.addByte(byteArray[0]);
                for (int i = 1; i < byteArray.length; ++i) {
                    buffer.addChar(',').addByte(byteArray[i]);
                }
            }
        } else if (short[].class.isAssignableFrom(arrayClass) && (shortArray = (short[])array).length > 0) {
            buffer.addShort(shortArray[0]);
            for (int i = 1; i < shortArray.length; ++i) {
                buffer.addChar(',').addShort(shortArray[i]);
            }
        }
        buffer.addChar(']');
    }

    protected void writeMap(Map<?, ?> map, CharBuf buffer) {
        if (map.isEmpty()) {
            buffer.addChars(JsonOutput.EMPTY_MAP_CHARS);
            return;
        }
        buffer.addChar('{');
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getKey() == null) {
                throw new IllegalArgumentException("Maps with null keys can't be converted to JSON");
            }
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (this.isExcludingValues(value) || this.isExcludingFieldsNamed(key)) continue;
            this.writeMapEntry(key, value, buffer);
            buffer.addChar(',');
        }
        buffer.removeLastChar(',');
        buffer.addChar('}');
    }

    protected void writeMapEntry(String key, Object value, CharBuf buffer) {
        buffer.addJsonFieldName(key, this.disableUnicodeEscaping);
        this.writeObject(key, value, buffer);
    }

    protected void writeIterator(Iterator<?> iterator, CharBuf buffer) {
        if (!iterator.hasNext()) {
            buffer.addChars(JsonOutput.EMPTY_LIST_CHARS);
            return;
        }
        buffer.addChar('[');
        while (iterator.hasNext()) {
            Object it = iterator.next();
            if (this.isExcludingValues(it)) continue;
            this.writeObject(it, buffer);
            buffer.addChar(',');
        }
        buffer.removeLastChar(',');
        buffer.addChar(']');
    }

    protected JsonGenerator.Converter findConverter(Class<?> type) {
        for (JsonGenerator.Converter c : this.converters) {
            if (!c.handles(type)) continue;
            return c;
        }
        return null;
    }

    protected boolean shouldExcludeType(Class<?> type) {
        for (Class<?> t : this.excludedFieldTypes) {
            if (!t.isAssignableFrom(type)) continue;
            return true;
        }
        return false;
    }

    protected static class ClosureConverter
    implements JsonGenerator.Converter {
        protected final Class<?> type;
        protected final Closure<?> closure;
        protected final int paramCount;

        protected ClosureConverter(Class<?> type, Closure<?> closure) {
            Class param2;
            if (type == null) {
                throw new NullPointerException("Type parameter must not be null");
            }
            if (closure == null) {
                throw new NullPointerException("Closure parameter must not be null");
            }
            int paramCount = closure.getMaximumNumberOfParameters();
            if (paramCount < 1) {
                throw new IllegalArgumentException("Closure must accept at least one parameter");
            }
            Class param1 = closure.getParameterTypes()[0];
            if (!param1.isAssignableFrom(type)) {
                throw new IllegalArgumentException("Expected first parameter to be of type: " + type.toString());
            }
            if (paramCount > 1 && !(param2 = closure.getParameterTypes()[1]).isAssignableFrom(String.class)) {
                throw new IllegalArgumentException("Expected second parameter to be of type: " + String.class.toString());
            }
            this.type = type;
            this.closure = closure;
            this.paramCount = paramCount;
        }

        @Override
        public boolean handles(Class<?> type) {
            return this.type.isAssignableFrom(type);
        }

        @Override
        public Object convert(Object value, String key) {
            return this.paramCount == 1 ? this.closure.call(value) : this.closure.call(new Object[]{value, key});
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ClosureConverter)) {
                return false;
            }
            return this.type == ((ClosureConverter)o).type;
        }

        public int hashCode() {
            return this.type.hashCode();
        }

        public String toString() {
            return super.toString() + "<" + this.type.toString() + ">";
        }
    }
}

