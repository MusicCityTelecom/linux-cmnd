/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.Kryo$DefaultInstantiatorStrategy
 *  com.esotericsoftware.kryo.io.Input
 *  com.esotericsoftware.kryo.io.Output
 *  org.objenesis.strategy.InstantiatorStrategy
 *  org.objenesis.strategy.StdInstantiatorStrategy
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.util.Collections;
import java.util.List;
import org.objenesis.strategy.InstantiatorStrategy;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.integration.codec.kryo.AbstractKryoCodec;
import org.springframework.integration.codec.kryo.CompositeKryoRegistrar;
import org.springframework.integration.codec.kryo.KryoRegistrar;
import org.springframework.util.CollectionUtils;

public class PojoCodec
extends AbstractKryoCodec {
    private final CompositeKryoRegistrar kryoRegistrar;
    private final boolean useReferences;

    public PojoCodec() {
        this.kryoRegistrar = null;
        this.useReferences = true;
    }

    public PojoCodec(KryoRegistrar kryoRegistrar) {
        this(kryoRegistrar != null ? Collections.singletonList(kryoRegistrar) : null, true);
    }

    public PojoCodec(List<KryoRegistrar> kryoRegistrars) {
        this.kryoRegistrar = CollectionUtils.isEmpty(kryoRegistrars) ? null : new CompositeKryoRegistrar(kryoRegistrars);
        this.useReferences = true;
    }

    public PojoCodec(KryoRegistrar kryoRegistrar, boolean useReferences) {
        this(kryoRegistrar != null ? Collections.singletonList(kryoRegistrar) : null, useReferences);
    }

    public PojoCodec(List<KryoRegistrar> kryoRegistrars, boolean useReferences) {
        this.kryoRegistrar = CollectionUtils.isEmpty(kryoRegistrars) ? null : new CompositeKryoRegistrar(kryoRegistrars);
        this.useReferences = useReferences;
    }

    @Override
    protected void doEncode(Kryo kryo, Object object, Output output) {
        kryo.writeObject(output, object);
    }

    @Override
    protected <T> T doDecode(Kryo kryo, Input input, Class<T> type) {
        return (T)kryo.readObject(input, type);
    }

    @Override
    protected void configureKryoInstance(Kryo kryo) {
        kryo.setInstantiatorStrategy((InstantiatorStrategy)new Kryo.DefaultInstantiatorStrategy((InstantiatorStrategy)new StdInstantiatorStrategy()));
        if (this.kryoRegistrar != null) {
            this.kryoRegistrar.registerTypes(kryo);
        }
        kryo.setReferences(this.useReferences);
    }
}

