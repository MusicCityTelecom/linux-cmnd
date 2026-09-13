/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.EndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoint;
import org.springframework.util.Assert;

public class PathMappedEndpoints
implements Iterable<PathMappedEndpoint> {
    private final String basePath;
    private final Map<EndpointId, PathMappedEndpoint> endpoints;

    public PathMappedEndpoints(String basePath, EndpointsSupplier<?> supplier) {
        Assert.notNull(supplier, (String)"Supplier must not be null");
        this.basePath = basePath != null ? basePath : "";
        this.endpoints = this.getEndpoints(Collections.singleton(supplier));
    }

    public PathMappedEndpoints(String basePath, Collection<EndpointsSupplier<?>> suppliers) {
        Assert.notNull(suppliers, (String)"Suppliers must not be null");
        this.basePath = basePath != null ? basePath : "";
        this.endpoints = this.getEndpoints(suppliers);
    }

    private Map<EndpointId, PathMappedEndpoint> getEndpoints(Collection<EndpointsSupplier<?>> suppliers) {
        LinkedHashMap endpoints = new LinkedHashMap();
        suppliers.forEach(supplier -> supplier.getEndpoints().forEach(endpoint -> {
            if (endpoint instanceof PathMappedEndpoint) {
                endpoints.put(endpoint.getEndpointId(), (PathMappedEndpoint)((Object)endpoint));
            }
        }));
        return Collections.unmodifiableMap(endpoints);
    }

    public String getBasePath() {
        return this.basePath;
    }

    public String getRootPath(EndpointId endpointId) {
        PathMappedEndpoint endpoint = this.getEndpoint(endpointId);
        return endpoint != null ? endpoint.getRootPath() : null;
    }

    public String getPath(EndpointId endpointId) {
        return this.getPath(this.getEndpoint(endpointId));
    }

    public Collection<String> getAllRootPaths() {
        return this.asList(this.stream().map(PathMappedEndpoint::getRootPath));
    }

    public Collection<String> getAllPaths() {
        return this.asList(this.stream().map(this::getPath));
    }

    public PathMappedEndpoint getEndpoint(EndpointId endpointId) {
        return this.endpoints.get(endpointId);
    }

    public Stream<PathMappedEndpoint> stream() {
        return this.endpoints.values().stream();
    }

    @Override
    public Iterator<PathMappedEndpoint> iterator() {
        return this.endpoints.values().iterator();
    }

    private String getPath(PathMappedEndpoint endpoint) {
        return endpoint != null ? this.basePath + "/" + endpoint.getRootPath() : null;
    }

    private <T> List<T> asList(Stream<T> stream) {
        return stream.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
}

