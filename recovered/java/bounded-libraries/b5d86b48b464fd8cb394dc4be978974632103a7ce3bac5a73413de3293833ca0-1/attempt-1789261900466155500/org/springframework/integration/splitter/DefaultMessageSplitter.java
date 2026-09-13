/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package org.springframework.integration.splitter;

import java.util.ArrayList;
import java.util.StringTokenizer;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;

public class DefaultMessageSplitter
extends AbstractMessageSplitter {
    private volatile String delimiters;

    public void setDelimiters(String delimiters) {
        this.delimiters = delimiters;
    }

    @Override
    protected final Object splitMessage(Message<?> message) {
        Object payload = message.getPayload();
        if (payload instanceof String && this.delimiters != null) {
            ArrayList<String> tokens = new ArrayList<String>();
            StringTokenizer tokenizer = new StringTokenizer((String)payload, this.delimiters);
            while (tokenizer.hasMoreElements()) {
                tokens.add(tokenizer.nextToken());
            }
            return tokens;
        }
        return payload;
    }
}

