/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import java.util.Map;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.StringBuilderUtils;
import org.glassfish.jersey.message.internal.Utils;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Singleton
public class MediaTypeProvider
implements HeaderDelegateProvider<MediaType> {
    private static final String MEDIA_TYPE_IS_NULL = LocalizationMessages.MEDIA_TYPE_IS_NULL();

    @Override
    public boolean supports(Class<?> type) {
        return MediaType.class.isAssignableFrom(type);
    }

    @Override
    public String toString(MediaType header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, MEDIA_TYPE_IS_NULL);
        StringBuilder b = new StringBuilder();
        b.append(header.getType()).append('/').append(header.getSubtype());
        for (Map.Entry<String, String> e : header.getParameters().entrySet()) {
            b.append(";").append(e.getKey()).append('=');
            StringBuilderUtils.appendQuotedIfNonToken(b, e.getValue());
        }
        return b.toString();
    }

    @Override
    public MediaType fromString(String header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, MEDIA_TYPE_IS_NULL);
        try {
            return MediaTypeProvider.valueOf(HttpHeaderReader.newInstance(header));
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException("Error parsing media type '" + header + "'", ex);
        }
    }

    public static MediaType valueOf(HttpHeaderReader reader) throws ParseException {
        reader.hasNext();
        String type = reader.nextToken().toString();
        reader.nextSeparator('/');
        String subType = reader.nextToken().toString();
        Map<String, String> params = null;
        if (reader.hasNext()) {
            params = HttpHeaderReader.readParameters(reader);
        }
        return new MediaType(type, subType, params);
    }
}

