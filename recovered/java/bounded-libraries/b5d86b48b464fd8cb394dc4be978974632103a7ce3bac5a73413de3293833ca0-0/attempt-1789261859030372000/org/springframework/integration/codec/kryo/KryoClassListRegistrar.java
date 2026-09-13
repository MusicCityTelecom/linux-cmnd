/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Registration
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Registration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.integration.codec.kryo.AbstractKryoRegistrar;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class KryoClassListRegistrar
extends AbstractKryoRegistrar {
    private static final int DEFAULT_INITIAL_ID = 50;
    private final List<Class<?>> registeredClasses;
    private int initialValue = 50;

    public KryoClassListRegistrar(Class<?> ... classes) {
        this(Arrays.asList(classes));
    }

    public KryoClassListRegistrar(List<Class<?>> classes) {
        this.registeredClasses = new ArrayList(classes);
    }

    public void setInitialValue(int initialValue) {
        Assert.isTrue((initialValue >= 10 ? 1 : 0) != 0, (String)"'initialValue' must be >= 10");
        this.initialValue = initialValue;
    }

    @Override
    public List<Registration> getRegistrations() {
        ArrayList<Registration> registrations = new ArrayList<Registration>();
        if (!CollectionUtils.isEmpty(this.registeredClasses)) {
            for (int i = 0; i < this.registeredClasses.size(); ++i) {
                registrations.add(new Registration(this.registeredClasses.get(i), KRYO.getSerializer(this.registeredClasses.get(i)), i + this.initialValue));
            }
        }
        return registrations;
    }
}

