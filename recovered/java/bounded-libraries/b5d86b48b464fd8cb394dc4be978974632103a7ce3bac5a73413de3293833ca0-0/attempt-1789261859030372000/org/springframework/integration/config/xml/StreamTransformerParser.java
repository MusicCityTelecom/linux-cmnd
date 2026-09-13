/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.config.xml;

import org.springframework.integration.config.xml.ObjectToStringTransformerParser;
import org.springframework.integration.transformer.StreamTransformer;

public class StreamTransformerParser
extends ObjectToStringTransformerParser {
    @Override
    protected String getTransformerClassName() {
        return StreamTransformer.class.getName();
    }
}

