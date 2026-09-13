/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.Registration
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.integration.codec.kryo.AbstractKryoRegistrar;
import org.springframework.integration.codec.kryo.KryoRegistrar;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class CompositeKryoRegistrar
extends AbstractKryoRegistrar {
    private final List<KryoRegistrar> delegates;

    public CompositeKryoRegistrar(List<KryoRegistrar> delegates) {
        this.delegates = new ArrayList<KryoRegistrar>(delegates);
        if (!CollectionUtils.isEmpty(this.delegates)) {
            this.validateRegistrations();
        }
    }

    @Override
    public void registerTypes(Kryo kryo) {
        for (KryoRegistrar registrar : this.delegates) {
            registrar.registerTypes(kryo);
        }
    }

    @Override
    public final List<Registration> getRegistrations() {
        ArrayList<Registration> registrations = new ArrayList<Registration>();
        for (KryoRegistrar registrar : this.delegates) {
            registrations.addAll(registrar.getRegistrations());
        }
        return registrations;
    }

    private void validateRegistrations() {
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<Class> types = new ArrayList<Class>();
        for (Registration registration : this.getRegistrations()) {
            Assert.isTrue((registration.getId() >= 10 ? 1 : 0) != 0, (String)"registration ID must be >= 10");
            if (ids.contains(registration.getId())) {
                throw new IllegalArgumentException(String.format("Duplicate registration ID found: %d", registration.getId()));
            }
            ids.add(registration.getId());
            if (types.contains(registration.getType())) {
                throw new IllegalArgumentException(String.format("Duplicate registration found for type: %s", registration.getType()));
            }
            types.add(registration.getType());
            if (!this.log.isInfoEnabled()) continue;
            this.log.info((Object)String.format("configured Kryo registration %s with serializer %s", registration, registration.getSerializer().getClass().getName()));
        }
    }
}

