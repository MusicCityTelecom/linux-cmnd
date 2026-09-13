/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Registration
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Registration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.integration.codec.kryo.AbstractKryoRegistrar;

public class KryoRegistrationRegistrar
extends AbstractKryoRegistrar {
    private final List<Registration> registrations;

    public KryoRegistrationRegistrar(List<Registration> registrations) {
        this.registrations = registrations != null ? new ArrayList<Registration>(registrations) : new ArrayList();
    }

    @Override
    public List<Registration> getRegistrations() {
        return this.registrations;
    }
}

