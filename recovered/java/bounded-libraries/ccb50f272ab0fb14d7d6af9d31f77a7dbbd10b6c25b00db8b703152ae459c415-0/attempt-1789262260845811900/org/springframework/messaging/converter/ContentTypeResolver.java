/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.InvalidMimeTypeException
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.converter;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.MimeType;

@FunctionalInterface
public interface ContentTypeResolver {
    @Nullable
    public MimeType resolve(@Nullable MessageHeaders var1) throws InvalidMimeTypeException;
}

