/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.authentication.principal;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.services.RegisteredService;

public class ChainingPrincipalAttributesRepository
implements RegisteredServicePrincipalAttributesRepository {
    private static final long serialVersionUID = 3132218595095989750L;
    private final List<RegisteredServicePrincipalAttributesRepository> repositories;

    public Set<String> getAttributeRepositoryIds() {
        return this.repositories.stream().map(RegisteredServicePrincipalAttributesRepository::getAttributeRepositoryIds).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public Map<String, List<Object>> getAttributes(Principal principal, RegisteredService registeredService) {
        LinkedHashMap<String, List<Object>> results = new LinkedHashMap<String, List<Object>>();
        this.repositories.forEach(repo -> results.putAll(repo.getAttributes(principal, registeredService)));
        return results;
    }

    public void update(String id, Map<String, List<Object>> attributes, RegisteredService registeredService) {
        this.repositories.forEach(repo -> repo.update(id, attributes, registeredService));
    }

    @Generated
    public ChainingPrincipalAttributesRepository(List<RegisteredServicePrincipalAttributesRepository> repositories) {
        this.repositories = repositories;
    }
}

