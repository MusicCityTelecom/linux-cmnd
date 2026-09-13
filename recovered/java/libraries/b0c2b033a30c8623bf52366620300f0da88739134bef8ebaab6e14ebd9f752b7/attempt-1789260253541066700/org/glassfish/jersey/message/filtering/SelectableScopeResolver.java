/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.internal.util.Tokenizer;
import org.glassfish.jersey.message.filtering.spi.ScopeResolver;

@Singleton
public class SelectableScopeResolver
implements ScopeResolver {
    public static final String PREFIX = SelectableScopeResolver.class.getName() + "_";
    public static final String DEFAULT_SCOPE = PREFIX + "*";
    private static String SELECTABLE_PARAM_NAME = "select";
    @Context
    private Configuration configuration;
    @Context
    private UriInfo uriInfo;

    @PostConstruct
    private void init() {
        String paramName = (String)this.configuration.getProperty("jersey.config.entityFiltering.selectable.query");
        SELECTABLE_PARAM_NAME = paramName != null ? paramName : SELECTABLE_PARAM_NAME;
    }

    @Override
    public Set<String> resolve(Annotation[] annotations) {
        HashSet<String> scopes = new HashSet<String>();
        List fields = (List)this.uriInfo.getQueryParameters().get(SELECTABLE_PARAM_NAME);
        if (fields != null && !fields.isEmpty()) {
            for (String field : fields) {
                scopes.addAll(this.getScopesForField(field));
            }
        } else {
            scopes.add(DEFAULT_SCOPE);
        }
        return scopes;
    }

    private Set<String> getScopesForField(String fieldName) {
        String[] fields;
        HashSet<String> scopes = new HashSet<String>();
        for (String field : fields = Tokenizer.tokenize(fieldName, ",")) {
            String[] subfields = Tokenizer.tokenize(field, ".");
            if (subfields.length == 0) continue;
            scopes.add(PREFIX + subfields[0]);
            if (subfields.length <= 1) continue;
            scopes.add(PREFIX + field);
        }
        return scopes;
    }
}

