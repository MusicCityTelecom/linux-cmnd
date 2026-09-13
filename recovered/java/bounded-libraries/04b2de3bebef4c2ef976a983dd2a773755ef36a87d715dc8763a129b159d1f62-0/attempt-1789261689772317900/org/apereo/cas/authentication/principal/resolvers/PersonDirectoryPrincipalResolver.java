/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalResolver
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.authentication.principal.resolvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.attribute.PrincipalAttributeRepositoryFetcher;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.cas.authentication.principal.resolvers.PrincipalResolutionContext;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class PersonDirectoryPrincipalResolver
implements PrincipalResolver {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDirectoryPrincipalResolver.class);
    private final PrincipalResolutionContext context;

    public Principal resolve(Credential credential, Optional<Principal> currentPrincipal, Optional<AuthenticationHandler> handler) {
        LOGGER.trace("Attempting to resolve a principal via [{}]", (Object)this.getName());
        String principalId = this.extractPrincipalId(credential, currentPrincipal);
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)principalId)) {
            LOGGER.debug("Principal id [{}] could not be found", (Object)principalId);
            return null;
        }
        if (this.context.getPrincipalNameTransformer() != null) {
            principalId = this.context.getPrincipalNameTransformer().transform(principalId);
        }
        LOGGER.trace("Creating principal for [{}]", (Object)principalId);
        if (this.context.isResolveAttributes()) {
            Map<String, List<Object>> attributes = this.retrievePersonAttributes(principalId, credential, currentPrincipal, new HashMap<String, List<Object>>(0));
            if (attributes == null || attributes.isEmpty()) {
                LOGGER.debug("Principal id [{}] did not specify any attributes", (Object)principalId);
                if (!this.context.isReturnNullIfNoAttributes()) {
                    Principal principal = this.buildResolvedPrincipal(principalId, new HashMap<String, List<Object>>(0), credential, currentPrincipal, handler);
                    LOGGER.debug("Returning the principal with id [{}] without any attributes", (Object)principal);
                    return principal;
                }
                LOGGER.debug("[{}] is configured to return null if no attributes are found for [{}]", (Object)this.getClass().getName(), (Object)principalId);
                return null;
            }
            LOGGER.debug("Retrieved [{}] attribute(s) from the repository", (Object)attributes.size());
            PrincipalResolutionResult result = this.convertPersonAttributesToPrincipal(principalId, currentPrincipal, attributes);
            if (!result.isSuccess() && this.context.isReturnNullIfNoAttributes()) {
                LOGGER.warn("Principal resolution is unable to produce a result and will return null");
                return null;
            }
            Principal principal = this.buildResolvedPrincipal(result.getPrincipalId(), result.getAttributes(), credential, currentPrincipal, handler);
            LOGGER.debug("Final resolved principal by [{}] is [{}]", (Object)this.getName(), (Object)principal);
            return principal;
        }
        Principal principal = this.buildResolvedPrincipal(principalId, new HashMap<String, List<Object>>(0), credential, currentPrincipal, handler);
        LOGGER.debug("Final resolved principal by [{}] without resolving attributes is [{}]", (Object)this.getName(), (Object)principal);
        return principal;
    }

    public boolean supports(Credential credential) {
        return credential != null && credential.getId() != null;
    }

    public IPersonAttributeDao getAttributeRepository() {
        return this.context.getAttributeRepository();
    }

    protected Principal buildResolvedPrincipal(String id, Map<String, List<Object>> attributes, Credential credential, Optional<Principal> currentPrincipal, Optional<AuthenticationHandler> handler) {
        return this.context.getPrincipalFactory().createPrincipal(id, attributes);
    }

    protected PrincipalResolutionResult convertPersonAttributesToPrincipal(String extractedPrincipalId, Optional<Principal> currentPrincipal, Map<String, List<Object>> attributes) {
        LinkedHashMap<String, List<Object>> convertedAttributes = new LinkedHashMap<String, List<Object>>();
        attributes.forEach((key, attrValue) -> {
            List values = ((List)CollectionUtils.toCollection((Object)attrValue, ArrayList.class)).stream().filter(Objects::nonNull).collect(Collectors.toList());
            LOGGER.debug("Found attribute [{}] with value(s) [{}]", key, values);
            convertedAttributes.put((String)key, values);
        });
        PrincipalResolutionResult.PrincipalResolutionResultBuilder<?, ?> builder = PrincipalResolutionResult.builder();
        String principalId = extractedPrincipalId;
        if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)this.context.getPrincipalAttributeNames())) {
            Set attrNames = StringUtils.commaDelimitedListToSet((String)this.context.getPrincipalAttributeNames());
            LinkedHashMap<String, List<Object>> principalIdAttributes = new LinkedHashMap<String, List<Object>>(attributes);
            if (this.context.isUseCurrentPrincipalId() && currentPrincipal.isPresent()) {
                Map currentPrincipalAttributes = currentPrincipal.get().getAttributes();
                LOGGER.trace("Merging current principal attributes [{}] with resolved attributes [{}]", (Object)currentPrincipalAttributes, principalIdAttributes);
                this.context.getAttributeMerger().mergeAttributes(principalIdAttributes, currentPrincipalAttributes);
            }
            LOGGER.debug("Using principal attributes [{}] to determine principal id", principalIdAttributes);
            Optional<List> result = attrNames.stream().map(String::trim).filter(principalIdAttributes::containsKey).map(principalIdAttributes::get).findFirst();
            if (result.isEmpty()) {
                LOGGER.warn("Principal resolution is set to resolve users via attribute(s) [{}], and yet the collection of attributes retrieved [{}] do not contain any of those attributes. This is likely due to misconfiguration and CAS will use [{}] as the final principal id", new Object[]{this.context.getPrincipalAttributeNames(), principalIdAttributes.keySet(), principalId});
                builder.success(false);
            } else {
                List values = result.get();
                if (!values.isEmpty()) {
                    principalId = CollectionUtils.firstElement((Object)values).map(Object::toString).orElseThrow();
                    LOGGER.debug("Found principal id attribute value [{}]", (Object)principalId);
                }
            }
        }
        return ((PrincipalResolutionResult.PrincipalResolutionResultBuilder)((PrincipalResolutionResult.PrincipalResolutionResultBuilder)builder.principalId(principalId)).attributes(convertedAttributes)).build();
    }

    protected Map<String, List<Object>> retrievePersonAttributes(String principalId, Credential credential, Optional<Principal> currentPrincipal, Map<String, List<Object>> queryAttributes) {
        queryAttributes.computeIfAbsent("credentialId", k1 -> CollectionUtils.wrapList((Object[])new Object[]{credential.getId()}));
        queryAttributes.computeIfAbsent("credentialClass", k -> CollectionUtils.wrapList((Object[])new Object[]{credential.getClass().getSimpleName()}));
        return ((PrincipalAttributeRepositoryFetcher)((PrincipalAttributeRepositoryFetcher.PrincipalAttributeRepositoryFetcherBuilder)((PrincipalAttributeRepositoryFetcher.PrincipalAttributeRepositoryFetcherBuilder)((PrincipalAttributeRepositoryFetcher.PrincipalAttributeRepositoryFetcherBuilder)((PrincipalAttributeRepositoryFetcher.PrincipalAttributeRepositoryFetcherBuilder)((PrincipalAttributeRepositoryFetcher.PrincipalAttributeRepositoryFetcherBuilder)PrincipalAttributeRepositoryFetcher.builder().attributeRepository(this.context.getAttributeRepository())).principalId(principalId)).activeAttributeRepositoryIdentifiers(this.context.getActiveAttributeRepositoryIdentifiers())).currentPrincipal(currentPrincipal.orElse(null))).queryAttributes(queryAttributes)).build()).retrieve();
    }

    protected String extractPrincipalId(Credential credential, Optional<Principal> currentPrincipal) {
        LOGGER.debug("Extracting credential id based on existing credential [{}]", (Object)credential);
        String id = credential.getId();
        if (currentPrincipal.isPresent()) {
            Principal principal = currentPrincipal.get();
            LOGGER.debug("Principal is currently resolved as [{}]", (Object)principal);
            if (this.context.isUseCurrentPrincipalId()) {
                LOGGER.debug("Using the existing resolved principal id [{}]", (Object)principal.getId());
                return principal.getId();
            }
            LOGGER.debug("CAS will NOT be using the identifier from the resolved principal [{}] as it's not configured to use the currently-resolved principal id and will fall back onto using the identifier for the credential, that is [{}], for principal resolution", (Object)principal, (Object)id);
        } else {
            LOGGER.debug("No principal is currently resolved and available. Falling back onto using the identifier  for the credential, that is [{}], for principal resolution", (Object)id);
        }
        LOGGER.debug("Extracted principal id [{}]", (Object)id);
        return org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)id) ? id.trim() : null;
    }

    @Generated
    public String toString() {
        return "PersonDirectoryPrincipalResolver(context=" + this.context + ")";
    }

    @Generated
    public PersonDirectoryPrincipalResolver(PrincipalResolutionContext context) {
        this.context = context;
    }

    @Generated
    public PrincipalResolutionContext getContext() {
        return this.context;
    }

    static class PrincipalResolutionResult {
        private final String principalId;
        private final Map<String, List<Object>> attributes;
        private final boolean success;

        @Generated
        private static Map<String, List<Object>> $default$attributes() {
            return new HashMap<String, List<Object>>();
        }

        @Generated
        private static boolean $default$success() {
            return true;
        }

        @Generated
        protected PrincipalResolutionResult(PrincipalResolutionResultBuilder<?, ?> b) {
            this.principalId = b.principalId;
            this.attributes = b.attributes$set ? b.attributes$value : PrincipalResolutionResult.$default$attributes();
            this.success = b.success$set ? b.success$value : PrincipalResolutionResult.$default$success();
        }

        @Generated
        public static PrincipalResolutionResultBuilder<?, ?> builder() {
            return new PrincipalResolutionResultBuilderImpl();
        }

        @Generated
        public String getPrincipalId() {
            return this.principalId;
        }

        @Generated
        public Map<String, List<Object>> getAttributes() {
            return this.attributes;
        }

        @Generated
        public boolean isSuccess() {
            return this.success;
        }

        @Generated
        private static final class PrincipalResolutionResultBuilderImpl
        extends PrincipalResolutionResultBuilder<PrincipalResolutionResult, PrincipalResolutionResultBuilderImpl> {
            @Generated
            private PrincipalResolutionResultBuilderImpl() {
            }

            @Override
            @Generated
            protected PrincipalResolutionResultBuilderImpl self() {
                return this;
            }

            @Override
            @Generated
            public PrincipalResolutionResult build() {
                return new PrincipalResolutionResult(this);
            }
        }

        @Generated
        public static abstract class PrincipalResolutionResultBuilder<C extends PrincipalResolutionResult, B extends PrincipalResolutionResultBuilder<C, B>> {
            @Generated
            private String principalId;
            @Generated
            private boolean attributes$set;
            @Generated
            private Map<String, List<Object>> attributes$value;
            @Generated
            private boolean success$set;
            @Generated
            private boolean success$value;

            @Generated
            protected abstract B self();

            @Generated
            public abstract C build();

            @Generated
            public B principalId(String principalId) {
                this.principalId = principalId;
                return this.self();
            }

            @Generated
            public B attributes(Map<String, List<Object>> attributes) {
                this.attributes$value = attributes;
                this.attributes$set = true;
                return this.self();
            }

            @Generated
            public B success(boolean success) {
                this.success$value = success;
                this.success$set = true;
                return this.self();
            }

            @Generated
            public String toString() {
                return "PersonDirectoryPrincipalResolver.PrincipalResolutionResult.PrincipalResolutionResultBuilder(principalId=" + this.principalId + ", attributes$value=" + this.attributes$value + ", success$value=" + this.success$value + ")";
            }
        }
    }
}

