/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.authentication;

import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.authentication.AuthenticationUtil;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFilter;
import org.glassfish.jersey.client.authentication.RequestAuthenticationException;
import org.glassfish.jersey.client.authentication.ResponseAuthenticationException;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.message.MessageUtils;
import org.glassfish.jersey.uri.UriComponent;

final class DigestAuthenticator {
    private static final char[] HEX_ARRAY = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final Pattern KEY_VALUE_PAIR_PATTERN = Pattern.compile("(\\w+)\\s*=\\s*(\"([^\"]+)\"|(\\w+))\\s*,?\\s*");
    private static final int CLIENT_NONCE_BYTE_COUNT = 4;
    private final SecureRandom randomGenerator;
    private final HttpAuthenticationFilter.Credentials credentials;
    private final Map<URI, DigestScheme> digestCache;

    DigestAuthenticator(HttpAuthenticationFilter.Credentials credentials, final int limit) {
        this.credentials = credentials;
        this.digestCache = Collections.synchronizedMap(new LinkedHashMap<URI, DigestScheme>(limit){
            private static final long serialVersionUID = 2546245625L;

            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return this.size() > limit;
            }
        });
        try {
            this.randomGenerator = SecureRandom.getInstance("SHA1PRNG");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RequestAuthenticationException(LocalizationMessages.ERROR_DIGEST_FILTER_GENERATOR(), e);
        }
    }

    boolean filterRequest(ClientRequestContext request) throws IOException {
        HttpAuthenticationFilter.Credentials cred;
        DigestScheme digestScheme = this.digestCache.get(AuthenticationUtil.getCacheKey(request));
        if (digestScheme != null && (cred = HttpAuthenticationFilter.getCredentials(request, this.credentials, HttpAuthenticationFilter.Type.DIGEST)) != null) {
            request.getHeaders().add("Authorization", this.createNextAuthToken(digestScheme, request, cred));
            return true;
        }
        return false;
    }

    public boolean filterResponse(ClientRequestContext request, ClientResponseContext response) throws IOException {
        if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.UNAUTHORIZED) {
            DigestScheme digestScheme = this.parseAuthHeaders((List)response.getHeaders().get("WWW-Authenticate"));
            if (digestScheme == null) {
                return false;
            }
            HttpAuthenticationFilter.Credentials cred = HttpAuthenticationFilter.getCredentials(request, this.credentials, HttpAuthenticationFilter.Type.DIGEST);
            if (cred == null) {
                if (response.hasEntity()) {
                    AuthenticationUtil.discardInputAndClose(response.getEntityStream());
                }
                throw new ResponseAuthenticationException(null, LocalizationMessages.AUTHENTICATION_CREDENTIALS_MISSING_DIGEST());
            }
            boolean success = HttpAuthenticationFilter.repeatRequest(request, response, this.createNextAuthToken(digestScheme, request, cred));
            URI cacheKey = AuthenticationUtil.getCacheKey(request);
            if (success) {
                this.digestCache.put(cacheKey, digestScheme);
            } else {
                this.digestCache.remove(cacheKey);
            }
            return success;
        }
        return true;
    }

    private DigestScheme parseAuthHeaders(List<?> headers) throws IOException {
        if (headers == null) {
            return null;
        }
        for (Object lineObject : headers) {
            String line;
            String[] parts;
            if (!(lineObject instanceof String) || (parts = (line = (String)lineObject).trim().split("\\s+", 2)).length != 2 || !"digest".equals(parts[0].toLowerCase(Locale.ROOT))) continue;
            String realm = null;
            String nonce = null;
            String opaque = null;
            QOP qop = QOP.UNSPECIFIED;
            Algorithm algorithm = Algorithm.UNSPECIFIED;
            boolean stale = false;
            Matcher match = KEY_VALUE_PAIR_PATTERN.matcher(parts[1]);
            while (match.find()) {
                String val;
                int nbGroups = match.groupCount();
                if (nbGroups != 4) continue;
                String key = match.group(1);
                String valNoQuotes = match.group(3);
                String valQuotes = match.group(4);
                String string = val = valNoQuotes == null ? valQuotes : valNoQuotes;
                if ("qop".equals(key)) {
                    qop = QOP.parse(val);
                    continue;
                }
                if ("realm".equals(key)) {
                    realm = val;
                    continue;
                }
                if ("nonce".equals(key)) {
                    nonce = val;
                    continue;
                }
                if ("opaque".equals(key)) {
                    opaque = val;
                    continue;
                }
                if ("stale".equals(key)) {
                    stale = Boolean.parseBoolean(val);
                    continue;
                }
                if (!"algorithm".equals(key)) continue;
                algorithm = Algorithm.parse(val);
            }
            return new DigestScheme(realm, nonce, opaque, qop, algorithm, stale);
        }
        return null;
    }

    private String createNextAuthToken(DigestScheme ds, ClientRequestContext requestContext, HttpAuthenticationFilter.Credentials credentials) throws IOException {
        String response;
        StringBuilder sb = new StringBuilder(100);
        sb.append("Digest ");
        DigestAuthenticator.append(sb, "username", credentials.getUsername());
        DigestAuthenticator.append(sb, "realm", ds.getRealm());
        DigestAuthenticator.append(sb, "nonce", ds.getNonce());
        DigestAuthenticator.append(sb, "opaque", ds.getOpaque());
        DigestAuthenticator.append(sb, "algorithm", ds.getAlgorithm().toString(), false);
        DigestAuthenticator.append(sb, "qop", ds.getQop().toString(), false);
        String uri = UriComponent.fullRelativeUri(requestContext.getUri());
        DigestAuthenticator.append(sb, "uri", uri);
        String ha1 = ds.getAlgorithm() == Algorithm.MD5_SESS ? DigestAuthenticator.md5(DigestAuthenticator.md5(credentials.getUsername(), ds.getRealm(), new String(credentials.getPassword(), MessageUtils.getCharset(requestContext.getMediaType())))) : DigestAuthenticator.md5(credentials.getUsername(), ds.getRealm(), new String(credentials.getPassword(), MessageUtils.getCharset(requestContext.getMediaType())));
        String ha2 = DigestAuthenticator.md5(requestContext.getMethod(), uri);
        if (ds.getQop() == QOP.UNSPECIFIED) {
            response = DigestAuthenticator.md5(ha1, ds.getNonce(), ha2);
        } else {
            String cnonce = this.randomBytes(4);
            DigestAuthenticator.append(sb, "cnonce", cnonce);
            String nc = String.format("%08x", ds.incrementCounter());
            DigestAuthenticator.append(sb, "nc", nc, false);
            response = DigestAuthenticator.md5(ha1, ds.getNonce(), nc, cnonce, ds.getQop().toString(), ha2);
        }
        DigestAuthenticator.append(sb, "response", response);
        return sb.toString();
    }

    private static void append(StringBuilder sb, String key, String value, boolean useQuote) {
        if (value == null) {
            return;
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') {
            sb.append(',');
        }
        sb.append(key);
        sb.append('=');
        if (useQuote) {
            sb.append('\"');
        }
        sb.append(value);
        if (useQuote) {
            sb.append('\"');
        }
    }

    private static void append(StringBuilder sb, String key, String value) {
        DigestAuthenticator.append(sb, key, value, true);
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0xF];
        }
        return new String(hexChars);
    }

    private static String md5(String ... tokens) throws IOException {
        MessageDigest md;
        StringBuilder sb = new StringBuilder(100);
        for (String token : tokens) {
            if (sb.length() > 0) {
                sb.append(':');
            }
            sb.append(token);
        }
        try {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IOException(ex.getMessage());
        }
        md.update(sb.toString().getBytes(HttpAuthenticationFilter.CHARACTER_SET), 0, sb.length());
        byte[] md5hash = md.digest();
        return DigestAuthenticator.bytesToHex(md5hash);
    }

    private String randomBytes(int nbBytes) {
        byte[] bytes = new byte[nbBytes];
        this.randomGenerator.nextBytes(bytes);
        return DigestAuthenticator.bytesToHex(bytes);
    }

    final class DigestScheme {
        private final String realm;
        private final String nonce;
        private final String opaque;
        private final Algorithm algorithm;
        private final QOP qop;
        private final boolean stale;
        private volatile int nc;

        DigestScheme(String realm, String nonce, String opaque, QOP qop, Algorithm algorithm, boolean stale) {
            this.realm = realm;
            this.nonce = nonce;
            this.opaque = opaque;
            this.qop = qop;
            this.algorithm = algorithm;
            this.stale = stale;
            this.nc = 0;
        }

        public int incrementCounter() {
            return ++this.nc;
        }

        public String getNonce() {
            return this.nonce;
        }

        public String getRealm() {
            return this.realm;
        }

        public String getOpaque() {
            return this.opaque;
        }

        public Algorithm getAlgorithm() {
            return this.algorithm;
        }

        public QOP getQop() {
            return this.qop;
        }

        public boolean isStale() {
            return this.stale;
        }

        public int getNc() {
            return this.nc;
        }
    }

    static enum Algorithm {
        UNSPECIFIED(null),
        MD5("MD5"),
        MD5_SESS("MD5-sess");

        private final String md;

        private Algorithm(String md) {
            this.md = md;
        }

        public String toString() {
            return this.md;
        }

        public static Algorithm parse(String val) {
            if (val == null || val.isEmpty()) {
                return UNSPECIFIED;
            }
            if ((val = val.trim()).contains(Algorithm.MD5_SESS.md) || val.contains(Algorithm.MD5_SESS.md.toLowerCase(Locale.ROOT))) {
                return MD5_SESS;
            }
            return MD5;
        }
    }

    private static enum QOP {
        UNSPECIFIED(null),
        AUTH("auth");

        private final String qop;

        private QOP(String qop) {
            this.qop = qop;
        }

        public String toString() {
            return this.qop;
        }

        public static QOP parse(String val) {
            if (val == null || val.isEmpty()) {
                return UNSPECIFIED;
            }
            if (val.contains("auth")) {
                return AUTH;
            }
            throw new UnsupportedOperationException(LocalizationMessages.DIGEST_FILTER_QOP_UNSUPPORTED(val));
        }
    }
}

