/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Registration
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Registration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.integration.codec.kryo.AbstractKryoRegistrar;
import org.springframework.util.CollectionUtils;

public class KryoClassMapRegistrar
extends AbstractKryoRegistrar {
    private final Map<Integer, Class<?>> registeredClasses;

    public KryoClassMapRegistrar(Map<Integer, Class<?>> kryoRegisteredClasses) {
        this.registeredClasses = new HashMap(kryoRegisteredClasses);
    }

    @Override
    public List<Registration> getRegistrations() {
        ArrayList<Registration> registrations = new ArrayList<Registration>();
        if (!CollectionUtils.isEmpty(this.registeredClasses)) {
            for (Map.Entry<Integer, Class<?>> entry : this.registeredClasses.entrySet()) {
                registrations.add(new Registration(entry.getValue(), KRYO.getSerializer(entry.getValue()), entry.getKey().intValue()));
            }
        }
        return registrations;
    }
}

