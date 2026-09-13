/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Registration
 *  com.esotericsoftware.kryo.Serializer
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.springframework.integration.codec.kryo.AbstractKryoRegistrar;
import org.springframework.integration.codec.kryo.FileSerializer;

public class FileKryoRegistrar
extends AbstractKryoRegistrar {
    private final int registrationId;
    private final FileSerializer fileSerializer = new FileSerializer();

    public FileKryoRegistrar() {
        this.registrationId = 40;
    }

    public FileKryoRegistrar(int registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public List<Registration> getRegistrations() {
        return Collections.singletonList(new Registration(File.class, (Serializer)this.fileSerializer, this.registrationId));
    }
}

