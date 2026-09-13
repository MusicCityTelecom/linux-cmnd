/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import javax.inject.Singleton;
import javax.ws.rs.core.EntityTag;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.StringBuilderUtils;
import org.glassfish.jersey.message.internal.Utils;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Singleton
public class EntityTagProvider
implements HeaderDelegateProvider<EntityTag> {
    @Override
    public boolean supports(Class<?> type) {
        return type == EntityTag.class;
    }

    @Override
    public String toString(EntityTag header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.ENTITY_TAG_IS_NULL());
        StringBuilder b = new StringBuilder();
        if (header.isWeak()) {
            b.append("W/");
        }
        StringBuilderUtils.appendQuoted(b, header.getValue());
        return b.toString();
    }

    @Override
    public EntityTag fromString(String header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.ENTITY_TAG_IS_NULL());
        try {
            CharSequence ev;
            HttpHeaderReader reader = HttpHeaderReader.newInstance(header);
            HttpHeaderReader.Event e = reader.next(false);
            if (e == HttpHeaderReader.Event.QuotedString) {
                return new EntityTag(reader.getEventValue().toString());
            }
            if (e == HttpHeaderReader.Event.Token && (ev = reader.getEventValue()) != null && ev.length() > 0 && ev.charAt(0) == 'W') {
                reader.nextSeparator('/');
                return new EntityTag(reader.nextQuotedString().toString(), true);
            }
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException("Error parsing entity tag '" + header + "'", ex);
        }
        throw new IllegalArgumentException("Error parsing entity tag '" + header + "'");
    }
}

