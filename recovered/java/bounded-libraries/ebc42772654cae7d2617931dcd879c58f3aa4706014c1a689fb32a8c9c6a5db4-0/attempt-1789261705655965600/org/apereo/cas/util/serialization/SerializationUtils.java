/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.jooq.lambda.Unchecked
 *  org.jooq.lambda.fi.util.function.CheckedConsumer
 */
package org.apereo.cas.util.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.function.FunctionUtils;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedConsumer;

public final class SerializationUtils {
    public static byte[] serialize(Serializable object) {
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        SerializationUtils.serialize(object, outBytes);
        return outBytes.toByteArray();
    }

    public static void serialize(Serializable object, OutputStream outputStream) {
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)o -> {
            try (ObjectOutputStream out = new ObjectOutputStream(outputStream);){
                out.writeObject(object);
            }
        }), new Object[0]);
    }

    public static <T> T deserialize(byte[] inBytes, Class<T> clazz) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inBytes);
        return SerializationUtils.deserialize(inputStream, clazz);
    }

    public static <T> T deserialize(InputStream inputStream, Class<T> clazz) {
        return Unchecked.supplier(() -> {
            try (ObjectInputStream in = new ObjectInputStream(inputStream);){
                Object obj = in.readObject();
                if (!clazz.isAssignableFrom(obj.getClass())) {
                    throw new ClassCastException("Result [" + obj + " is of type " + obj.getClass() + " when we were expecting " + clazz);
                }
                Object object = obj;
                return object;
            }
        }).get();
    }

    public static byte[] serializeAndEncodeObject(CipherExecutor cipher, Serializable object, Object[] parameters) {
        byte[] outBytes = SerializationUtils.serialize(object);
        return (byte[])cipher.encode((Object)outBytes, parameters);
    }

    public static byte[] serializeAndEncodeObject(CipherExecutor cipher, Serializable object) {
        return SerializationUtils.serializeAndEncodeObject(cipher, object, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    public static <T extends Serializable> T decodeAndDeserializeObject(byte[] object, CipherExecutor cipher, Class<T> type, Object[] parameters) {
        byte[] decoded = (byte[])cipher.decode((Object)object, parameters);
        return SerializationUtils.deserializeAndCheckObject(decoded, type);
    }

    public static <T extends Serializable> T decodeAndDeserializeObject(byte[] object, CipherExecutor cipher, Class<T> type) {
        return SerializationUtils.decodeAndDeserializeObject(object, cipher, type, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    public static <T extends Serializable> T deserializeAndCheckObject(byte[] object, Class<T> type) {
        Serializable result = (Serializable)SerializationUtils.deserialize(object, type);
        return (T)result;
    }

    @Generated
    private SerializationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

