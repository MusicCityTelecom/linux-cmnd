/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Singleton;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.Utils;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Singleton
public class UriProvider
implements HeaderDelegateProvider<URI> {
    @Override
    public boolean supports(Class<?> type) {
        return type == URI.class;
    }

    @Override
    public String toString(URI header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.URI_IS_NULL());
        return header.toASCIIString();
    }

    @Override
    public URI fromString(String header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.URI_IS_NULL());
        try {
            return new URI(header);
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Error parsing uri '" + header + "'", e);
        }
    }
}

