/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import java.util.Collections;
import java.util.Set;
import javax.ws.rs.core.EntityTag;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.HttpHeaderReader;

public class MatchingEntityTag
extends EntityTag {
    public static final Set<MatchingEntityTag> ANY_MATCH = Collections.emptySet();

    public MatchingEntityTag(String value) {
        super(value, false);
    }

    public MatchingEntityTag(String value, boolean weak) {
        super(value, weak);
    }

    public static MatchingEntityTag valueOf(HttpHeaderReader reader) throws ParseException {
        CharSequence ev;
        CharSequence tagString = reader.getRemainder();
        HttpHeaderReader.Event e = reader.next(false);
        if (e == HttpHeaderReader.Event.QuotedString) {
            return new MatchingEntityTag(reader.getEventValue().toString());
        }
        if (e == HttpHeaderReader.Event.Token && (ev = reader.getEventValue()) != null && ev.length() == 1 && 'W' == ev.charAt(0)) {
            reader.nextSeparator('/');
            return new MatchingEntityTag(reader.nextQuotedString().toString(), true);
        }
        throw new ParseException(LocalizationMessages.ERROR_PARSING_ENTITY_TAG(tagString), reader.getIndex());
    }
}

