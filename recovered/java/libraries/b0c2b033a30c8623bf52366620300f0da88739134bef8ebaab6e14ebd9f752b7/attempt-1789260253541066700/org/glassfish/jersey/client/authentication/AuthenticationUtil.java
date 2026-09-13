/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.client.ClientRequestContext;

class AuthenticationUtil {
    AuthenticationUtil() {
    }

    static void discardInputAndClose(InputStream is) {
        byte[] buf = new byte[4096];
        try {
            while (is.read(buf) > 0) {
            }
        }
        catch (IOException iOException) {
        }
        finally {
            try {
                is.close();
            }
            catch (IOException iOException) {}
        }
    }

    static URI getCacheKey(ClientRequestContext request) {
        URI requestUri = request.getUri();
        if (requestUri.getRawQuery() != null) {
            try {
                return new URI(requestUri.getScheme(), requestUri.getAuthority(), requestUri.getPath(), null, requestUri.getFragment());
            }
            catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
        }
        return requestUri;
    }
}

