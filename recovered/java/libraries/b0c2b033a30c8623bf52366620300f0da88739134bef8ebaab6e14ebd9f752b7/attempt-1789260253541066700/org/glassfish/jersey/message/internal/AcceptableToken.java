/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.Qualified;
import org.glassfish.jersey.message.internal.Token;

public class AcceptableToken
extends Token
implements Qualified {
    protected int quality = 1000;

    public AcceptableToken(String header) throws ParseException {
        this(HttpHeaderReader.newInstance(header));
    }

    public AcceptableToken(HttpHeaderReader reader) throws ParseException {
        reader.hasNext();
        this.token = reader.nextToken().toString();
        if (reader.hasNext()) {
            this.quality = HttpHeaderReader.readQualityFactorParameter(reader);
        }
    }

    @Override
    public int getQuality() {
        return this.quality;
    }
}

