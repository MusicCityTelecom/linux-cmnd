/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 */
package org.apereo.cas.authentication.principal.resolvers;

import java.util.HashSet;
import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.support.merger.IAttributeMerger;

public class PrincipalResolutionContext {
    private final IPersonAttributeDao attributeRepository;
    private final PrincipalFactory principalFactory;
    private final boolean returnNullIfNoAttributes;
    private final PrincipalNameTransformer principalNameTransformer;
    private final String principalAttributeNames;
    private final boolean useCurrentPrincipalId;
    private final boolean resolveAttributes;
    private final Set<String> activeAttributeRepositoryIdentifiers;
    private final IAttributeMerger attributeMerger;

    @Generated
    private static Set<String> $default$activeAttributeRepositoryIdentifiers() {
        return new HashSet<String>();
    }

    @Generated
    protected PrincipalResolutionContext(PrincipalResolutionContextBuilder<?, ?> b) {
        this.attributeRepository = b.attributeRepository;
        this.principalFactory = b.principalFactory;
        this.returnNullIfNoAttributes = b.returnNullIfNoAttributes;
        this.principalNameTransformer = b.principalNameTransformer;
        this.principalAttributeNames = b.principalAttributeNames;
        this.useCurrentPrincipalId = b.useCurrentPrincipalId;
        this.resolveAttributes = b.resolveAttributes;
        this.activeAttributeRepositoryIdentifiers = b.activeAttributeRepositoryIdentifiers$set ? b.activeAttributeRepositoryIdentifiers$value : PrincipalResolutionContext.$default$activeAttributeRepositoryIdentifiers();
        this.attributeMerger = b.attributeMerger;
    }

    @Generated
    public static PrincipalResolutionContextBuilder<?, ?> builder() {
        return new PrincipalResolutionContextBuilderImpl();
    }

    @Generated
    public IPersonAttributeDao getAttributeRepository() {
        return this.attributeRepository;
    }

    @Generated
    public PrincipalFactory getPrincipalFactory() {
        return this.principalFactory;
    }

    @Generated
    public boolean isReturnNullIfNoAttributes() {
        return this.returnNullIfNoAttributes;
    }

    @Generated
    public PrincipalNameTransformer getPrincipalNameTransformer() {
        return this.principalNameTransformer;
    }

    @Generated
    public String getPrincipalAttributeNames() {
        return this.principalAttributeNames;
    }

    @Generated
    public boolean isUseCurrentPrincipalId() {
        return this.useCurrentPrincipalId;
    }

    @Generated
    public boolean isResolveAttributes() {
        return this.resolveAttributes;
    }

    @Generated
    public Set<String> getActiveAttributeRepositoryIdentifiers() {
        return this.activeAttributeRepositoryIdentifiers;
    }

    @Generated
    public IAttributeMerger getAttributeMerger() {
        return this.attributeMerger;
    }

    @Generated
    private static final class PrincipalResolutionContextBuilderImpl
    extends PrincipalResolutionContextBuilder<PrincipalResolutionContext, PrincipalResolutionContextBuilderImpl> {
        @Generated
        private PrincipalResolutionContextBuilderImpl() {
        }

        @Override
        @Generated
        protected PrincipalResolutionContextBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public PrincipalResolutionContext build() {
            return new PrincipalResolutionContext(this);
        }
    }

    @Generated
    public static abstract class PrincipalResolutionContextBuilder<C extends PrincipalResolutionContext, B extends PrincipalResolutionContextBuilder<C, B>> {
        @Generated
        private IPersonAttributeDao attributeRepository;
        @Generated
        private PrincipalFactory principalFactory;
        @Generated
        private boolean returnNullIfNoAttributes;
        @Generated
        private PrincipalNameTransformer principalNameTransformer;
        @Generated
        private String principalAttributeNames;
        @Generated
        private boolean useCurrentPrincipalId;
        @Generated
        private boolean resolveAttributes;
        @Generated
        private boolean activeAttributeRepositoryIdentifiers$set;
        @Generated
        private Set<String> activeAttributeRepositoryIdentifiers$value;
        @Generated
        private IAttributeMerger attributeMerger;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B attributeRepository(IPersonAttributeDao attributeRepository) {
            this.attributeRepository = attributeRepository;
            return this.self();
        }

        @Generated
        public B principalFactory(PrincipalFactory principalFactory) {
            this.principalFactory = principalFactory;
            return this.self();
        }

        @Generated
        public B returnNullIfNoAttributes(boolean returnNullIfNoAttributes) {
            this.returnNullIfNoAttributes = returnNullIfNoAttributes;
            return this.self();
        }

        @Generated
        public B principalNameTransformer(PrincipalNameTransformer principalNameTransformer) {
            this.principalNameTransformer = principalNameTransformer;
            return this.self();
        }

        @Generated
        public B principalAttributeNames(String principalAttributeNames) {
            this.principalAttributeNames = principalAttributeNames;
            return this.self();
        }

        @Generated
        public B useCurrentPrincipalId(boolean useCurrentPrincipalId) {
            this.useCurrentPrincipalId = useCurrentPrincipalId;
            return this.self();
        }

        @Generated
        public B resolveAttributes(boolean resolveAttributes) {
            this.resolveAttributes = resolveAttributes;
            return this.self();
        }

        @Generated
        public B activeAttributeRepositoryIdentifiers(Set<String> activeAttributeRepositoryIdentifiers) {
            this.activeAttributeRepositoryIdentifiers$value = activeAttributeRepositoryIdentifiers;
            this.activeAttributeRepositoryIdentifiers$set = true;
            return this.self();
        }

        @Generated
        public B attributeMerger(IAttributeMerger attributeMerger) {
            this.attributeMerger = attributeMerger;
            return this.self();
        }

        @Generated
        public String toString() {
            return "PrincipalResolutionContext.PrincipalResolutionContextBuilder(attributeRepository=" + this.attributeRepository + ", principalFactory=" + this.principalFactory + ", returnNullIfNoAttributes=" + this.returnNullIfNoAttributes + ", principalNameTransformer=" + this.principalNameTransformer + ", principalAttributeNames=" + this.principalAttributeNames + ", useCurrentPrincipalId=" + this.useCurrentPrincipalId + ", resolveAttributes=" + this.resolveAttributes + ", activeAttributeRepositoryIdentifiers$value=" + this.activeAttributeRepositoryIdentifiers$value + ", attributeMerger=" + this.attributeMerger + ")";
        }
    }
}

