/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.protobuf.ExtensionRegistry
 *  com.google.protobuf.util.JsonFormat$Parser
 *  com.google.protobuf.util.JsonFormat$Printer
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.converter;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.util.JsonFormat;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.ProtobufMessageConverter;

public class ProtobufJsonFormatMessageConverter
extends ProtobufMessageConverter {
    public ProtobufJsonFormatMessageConverter(@Nullable ExtensionRegistry extensionRegistry) {
        this((JsonFormat.Parser)null, (JsonFormat.Printer)null);
    }

    public ProtobufJsonFormatMessageConverter(@Nullable JsonFormat.Parser parser, @Nullable JsonFormat.Printer printer) {
        this(parser, printer, null);
    }

    public ProtobufJsonFormatMessageConverter(@Nullable JsonFormat.Parser parser, @Nullable JsonFormat.Printer printer, @Nullable ExtensionRegistry extensionRegistry) {
        super(new ProtobufMessageConverter.ProtobufJavaUtilSupport(parser, printer), extensionRegistry);
    }
}

