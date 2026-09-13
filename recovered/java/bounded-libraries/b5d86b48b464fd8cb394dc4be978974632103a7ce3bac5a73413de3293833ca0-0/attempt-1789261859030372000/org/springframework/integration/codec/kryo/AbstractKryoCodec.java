/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.io.Input
 *  com.esotericsoftware.kryo.io.Output
 *  com.esotericsoftware.kryo.pool.KryoFactory
 *  com.esotericsoftware.kryo.pool.KryoPool
 *  com.esotericsoftware.kryo.pool.KryoPool$Builder
 *  org.springframework.util.Assert
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.integration.codec.Codec;
import org.springframework.util.Assert;

public abstract class AbstractKryoCodec
implements Codec {
    protected final KryoPool pool;

    protected AbstractKryoCodec() {
        KryoFactory factory = () -> {
            Kryo kryo = new Kryo();
            kryo.setRegistrationRequired(true);
            this.configureKryoInstance(kryo);
            return kryo;
        };
        this.pool = new KryoPool.Builder(factory).softReferences().build();
    }

    @Override
    public void encode(Object object, OutputStream outputStream) {
        Assert.notNull((Object)object, (String)"cannot encode a null object");
        Assert.notNull((Object)outputStream, (String)"'outputSteam' cannot be null");
        Output output = outputStream instanceof Output ? (Output)outputStream : new Output(outputStream);
        this.pool.run(kryo -> {
            this.doEncode(kryo, object, output);
            return Void.class;
        });
        output.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T decode(byte[] bytes, Class<T> type) throws IOException {
        Assert.notNull((Object)bytes, (String)"'bytes' cannot be null");
        try (Input input = new Input(bytes);){
            T t = this.decode((InputStream)input, type);
            return t;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T decode(InputStream inputStream, Class<T> type) {
        Assert.notNull((Object)inputStream, (String)"'inputStream' cannot be null");
        Assert.notNull(type, (String)"'type' cannot be null");
        Object result = null;
        try (Input input = inputStream instanceof Input ? (Input)inputStream : new Input(inputStream);){
            result = this.pool.run(kryo -> this.doDecode(kryo, input, type));
        }
        return (T)result;
    }

    @Override
    public byte[] encode(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        this.encode(object, bos);
        byte[] bytes = bos.toByteArray();
        bos.close();
        return bytes;
    }

    protected abstract void doEncode(Kryo var1, Object var2, Output var3);

    protected abstract <T> T doDecode(Kryo var1, Input var2, Class<T> var3);

    protected abstract void configureKryoInstance(Kryo var1);
}

