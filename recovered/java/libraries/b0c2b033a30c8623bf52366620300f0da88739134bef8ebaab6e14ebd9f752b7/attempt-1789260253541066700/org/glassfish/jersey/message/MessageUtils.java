/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message;

import java.nio.charset.Charset;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.message.internal.ReaderWriter;

public final class MessageUtils {
    public static Charset getCharset(MediaType media) {
        return ReaderWriter.getCharset(media);
    }

    private MessageUtils() {
        throw new AssertionError((Object)"No instances allowed.");
    }
}

