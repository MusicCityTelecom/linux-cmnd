/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.filter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.spi.ContentEncoder;

public final class EncodingFilter
implements ClientRequestFilter {
    @Inject
    private InjectionManager injectionManager;
    private volatile List<Object> supportedEncodings = null;

    @Override
    public void filter(ClientRequestContext request) throws IOException {
        if (this.getSupportedEncodings().isEmpty()) {
            return;
        }
        request.getHeaders().addAll("Accept-Encoding", this.getSupportedEncodings());
        String useEncoding = (String)request.getConfiguration().getProperty("jersey.config.client.useEncoding");
        if (useEncoding != null) {
            if (!this.getSupportedEncodings().contains(useEncoding)) {
                Logger.getLogger(this.getClass().getName()).warning(LocalizationMessages.USE_ENCODING_IGNORED("jersey.config.client.useEncoding", useEncoding, this.getSupportedEncodings()));
            } else if (request.hasEntity() && request.getHeaders().getFirst("Content-Encoding") == null) {
                request.getHeaders().putSingle("Content-Encoding", useEncoding);
            }
        }
    }

    List<Object> getSupportedEncodings() {
        if (this.supportedEncodings == null) {
            TreeSet<String> se = new TreeSet<String>();
            List encoders = this.injectionManager.getAllInstances((Type)((Object)ContentEncoder.class));
            for (ContentEncoder encoder : encoders) {
                se.addAll(encoder.getSupportedEncodings());
            }
            this.supportedEncodings = new ArrayList<Object>(se);
        }
        return this.supportedEncodings;
    }
}

