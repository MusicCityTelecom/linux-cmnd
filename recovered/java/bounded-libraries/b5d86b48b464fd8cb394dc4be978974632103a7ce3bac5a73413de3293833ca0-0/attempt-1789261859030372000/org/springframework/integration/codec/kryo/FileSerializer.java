/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.kryo.Kryo
 *  com.esotericsoftware.kryo.Serializer
 *  com.esotericsoftware.kryo.io.Input
 *  com.esotericsoftware.kryo.io.Output
 */
package org.springframework.integration.codec.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.File;

public class FileSerializer
extends Serializer<File> {
    public void write(Kryo kryo, Output output, File file) {
        output.writeString(file.getPath());
    }

    public File read(Kryo kryo, Input input, Class<File> type) {
        String path = input.readString();
        return new File(path);
    }
}

