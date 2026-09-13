/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.Registration
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.codec.kryo.KryoRegistrar;

public abstract class AbstractKryoRegistrar
implements KryoRegistrar {
    protected static final Kryo KRYO = new Kryo();
    protected final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void registerTypes(Kryo kryo) {
        for (Registration registration : this.getRegistrations()) {
            this.register(kryo, registration);
        }
    }

    private void register(Kryo kryo, Registration registration) {
        int id = registration.getId();
        Registration existing = kryo.getRegistration(id);
        if (existing != null) {
            throw new IllegalStateException("registration already exists " + existing);
        }
        if (this.log.isInfoEnabled()) {
            this.log.info((Object)String.format("registering %s with serializer %s", registration, registration.getSerializer().getClass().getName()));
        }
        kryo.register(registration);
    }
}

