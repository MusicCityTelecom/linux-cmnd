/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.LanguageTag;
import org.glassfish.jersey.message.internal.Qualified;

public class AcceptableLanguageTag
extends LanguageTag
implements Qualified {
    private final int quality;

    public AcceptableLanguageTag(String primaryTag, String subTags) {
        super(primaryTag, subTags);
        this.quality = 1000;
    }

    public AcceptableLanguageTag(String header) throws ParseException {
        this(HttpHeaderReader.newInstance(header));
    }

    public AcceptableLanguageTag(HttpHeaderReader reader) throws ParseException {
        reader.hasNext();
        this.tag = reader.nextToken().toString();
        if (!this.tag.equals("*")) {
            this.parse(this.tag);
        } else {
            this.primaryTag = this.tag;
        }
        this.quality = reader.hasNext() ? HttpHeaderReader.readQualityFactorParameter(reader) : 1000;
    }

    @Override
    public int getQuality() {
        return this.quality;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        AcceptableLanguageTag other = (AcceptableLanguageTag)obj;
        return this.quality == other.quality;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 47 * hash + this.quality;
        return hash;
    }
}

