/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.core.Link;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.Tokenizer;
import org.glassfish.jersey.message.internal.JerseyLink;
import org.glassfish.jersey.message.internal.Utils;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Singleton
public class LinkProvider
implements HeaderDelegateProvider<Link> {
    private static final Logger LOGGER = Logger.getLogger(LinkProvider.class.getName());

    @Override
    public boolean supports(Class<?> type) {
        return Link.class.isAssignableFrom(type);
    }

    @Override
    public Link fromString(String value) throws IllegalArgumentException {
        return LinkProvider.initBuilder(new JerseyLink.Builder(), value).build(new Object[0]);
    }

    static JerseyLink.Builder initBuilder(JerseyLink.Builder lb, String value) {
        Utils.throwIllegalArgumentExceptionIfNull(value, LocalizationMessages.LINK_IS_NULL());
        try {
            int gtIndex;
            value = value.trim();
            if (value.startsWith("<")) {
                gtIndex = value.indexOf(62);
                if (gtIndex == -1) {
                    throw new IllegalArgumentException("Missing token > in " + value);
                }
            } else {
                throw new IllegalArgumentException("Missing starting token < in " + value);
            }
            lb.uri(value.substring(1, gtIndex).trim());
            String params = value.substring(gtIndex + 1).trim();
            StringTokenizer st = new StringTokenizer(params, ";=\"", true);
            while (st.hasMoreTokens()) {
                LinkProvider.checkToken(st, ";");
                String n = st.nextToken().trim();
                LinkProvider.checkToken(st, "=");
                String v = LinkProvider.nextNonEmptyToken(st);
                if (v.equals("\"")) {
                    v = st.nextToken();
                    LinkProvider.checkToken(st, "\"");
                }
                lb.param(n, v);
            }
        }
        catch (Throwable e) {
            if (LOGGER.isLoggable(Level.FINER)) {
                LOGGER.log(Level.FINER, "Error parsing link value '" + value + "'", e);
            }
            lb = null;
        }
        if (lb == null) {
            throw new IllegalArgumentException("Unable to parse link " + value);
        }
        return lb;
    }

    private static String nextNonEmptyToken(StringTokenizer st) throws IllegalArgumentException {
        String token;
        while ((token = st.nextToken().trim()).length() == 0) {
        }
        return token;
    }

    private static void checkToken(StringTokenizer st, String expected) throws IllegalArgumentException {
        String token;
        while ((token = st.nextToken().trim()).length() == 0) {
        }
        if (!token.equals(expected)) {
            throw new IllegalArgumentException("Expected token " + expected + " but found " + token);
        }
    }

    @Override
    public String toString(Link value) {
        return LinkProvider.stringfy(value);
    }

    static String stringfy(Link value) {
        Utils.throwIllegalArgumentExceptionIfNull(value, LocalizationMessages.LINK_IS_NULL());
        Map<String, String> map = value.getParams();
        StringBuilder sb = new StringBuilder();
        sb.append('<').append(value.getUri()).append('>');
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("; ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
        }
        return sb.toString();
    }

    static List<String> getLinkRelations(String rel) {
        return rel == null ? null : Arrays.asList(Tokenizer.tokenize(rel, "\" "));
    }
}

