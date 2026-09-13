/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.google.protobuf.Message;

public final class ProtoUtils {
    public static <T extends Message> T mergeFromProtos(T proto, T ... rest) {
        Message.Builder builder = proto.toBuilder();
        for (T message : rest) {
            builder.mergeFrom((Message)message);
        }
        Message result = builder.build();
        return (T)result;
    }

    private ProtoUtils() {
    }
}

