/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.Registration
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import java.util.List;

public interface KryoRegistrar {
    public static final int MIN_REGISTRATION_VALUE = 10;

    public void registerTypes(Kryo var1);

    public List<Registration> getRegistrations();
}

