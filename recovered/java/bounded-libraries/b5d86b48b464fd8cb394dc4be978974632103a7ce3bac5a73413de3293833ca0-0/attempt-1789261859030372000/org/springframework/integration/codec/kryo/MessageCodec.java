/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.codec.kryo;

import org.springframework.integration.codec.kryo.MessageKryoRegistrar;
import org.springframework.integration.codec.kryo.PojoCodec;

public class MessageCodec
extends PojoCodec {
    public MessageCodec() {
        super(new MessageKryoRegistrar());
    }

    public MessageCodec(MessageKryoRegistrar registrar) {
        super(registrar);
    }
}

