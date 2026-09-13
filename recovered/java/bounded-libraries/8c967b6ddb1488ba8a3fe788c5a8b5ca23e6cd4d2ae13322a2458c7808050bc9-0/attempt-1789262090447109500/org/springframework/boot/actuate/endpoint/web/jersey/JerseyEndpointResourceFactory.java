/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.ws.rs.container.ContainerRequestContext
 *  javax.ws.rs.core.MultivaluedMap
 *  javax.ws.rs.core.Response
 *  javax.ws.rs.core.Response$Status
 *  javax.ws.rs.core.SecurityContext
 *  org.glassfish.jersey.process.Inflector
 *  org.glassfish.jersey.server.ContainerRequest
 *  org.glassfish.jersey.server.model.Resource
 *  org.glassfish.jersey.server.model.Resource$Builder
 *  org.springframework.core.io.Resource
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.endpoint.web.jersey;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.model.Resource;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.OperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.ProducibleOperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.WebOperation;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.boot.actuate.endpoint.web.jersey.JerseyRemainingPathSegmentProvider;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class JerseyEndpointResourceFactory {
    public Collection<Resource> createEndpointResources(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints, EndpointMediaTypes endpointMediaTypes, EndpointLinksResolver linksResolver, boolean shouldRegisterLinks) {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        endpoints.stream().flatMap(endpoint -> endpoint.getOperations().stream()).map(operation -> this.createResource(endpointMapping, (WebOperation)operation)).forEach(resources::add);
        if (shouldRegisterLinks) {
            Resource resource = this.createEndpointLinksResource(endpointMapping.getPath(), endpointMediaTypes, linksResolver);
            resources.add(resource);
        }
        return resources;
    }

    protected Resource createResource(EndpointMapping endpointMapping, WebOperation operation) {
        WebOperationRequestPredicate requestPredicate = operation.getRequestPredicate();
        String path = requestPredicate.getPath();
        String matchAllRemainingPathSegmentsVariable = requestPredicate.getMatchAllRemainingPathSegmentsVariable();
        if (matchAllRemainingPathSegmentsVariable != null) {
            path = path.replace("{*" + matchAllRemainingPathSegmentsVariable + "}", "{" + matchAllRemainingPathSegmentsVariable + ": .*}");
        }
        return this.getResource(endpointMapping, operation, requestPredicate, path, null, null);
    }

    protected Resource getResource(EndpointMapping endpointMapping, WebOperation operation, WebOperationRequestPredicate requestPredicate, String path, WebServerNamespace serverNamespace, JerseyRemainingPathSegmentProvider remainingPathSegmentProvider) {
        Resource.Builder resourceBuilder = Resource.builder().path(endpointMapping.getPath()).path(endpointMapping.createSubPath(path));
        resourceBuilder.addMethod(requestPredicate.getHttpMethod().name()).consumes(StringUtils.toStringArray(requestPredicate.getConsumes())).produces(StringUtils.toStringArray(requestPredicate.getProduces())).handledBy((Inflector)new OperationInflector(operation, !requestPredicate.getConsumes().isEmpty(), serverNamespace, remainingPathSegmentProvider));
        return resourceBuilder.build();
    }

    private Resource createEndpointLinksResource(String endpointPath, EndpointMediaTypes endpointMediaTypes, EndpointLinksResolver linksResolver) {
        Resource.Builder resourceBuilder = Resource.builder().path(endpointPath);
        resourceBuilder.addMethod("GET").produces(StringUtils.toStringArray(endpointMediaTypes.getProduced())).handledBy((Inflector)new EndpointLinksInflector(linksResolver));
        return resourceBuilder.build();
    }

    private static final class JerseySecurityContext
    implements SecurityContext {
        private final javax.ws.rs.core.SecurityContext securityContext;

        private JerseySecurityContext(javax.ws.rs.core.SecurityContext securityContext) {
            this.securityContext = securityContext;
        }

        @Override
        public Principal getPrincipal() {
            return this.securityContext.getUserPrincipal();
        }

        @Override
        public boolean isUserInRole(String role) {
            return this.securityContext.isUserInRole(role);
        }
    }

    private static final class EndpointLinksInflector
    implements Inflector<ContainerRequestContext, Response> {
        private final EndpointLinksResolver linksResolver;

        private EndpointLinksInflector(EndpointLinksResolver linksResolver) {
            this.linksResolver = linksResolver;
        }

        public Response apply(ContainerRequestContext request) {
            Map<String, Link> links = this.linksResolver.resolveLinks(request.getUriInfo().getAbsolutePath().toString());
            return Response.ok(Collections.singletonMap("_links", links)).build();
        }
    }

    private static final class FluxBodyConverter
    implements Function<Object, Object> {
        private FluxBodyConverter() {
        }

        @Override
        public Object apply(Object body) {
            if (body instanceof Flux) {
                return ((Flux)body).collectList();
            }
            return body;
        }
    }

    private static final class MonoBodyConverter
    implements Function<Object, Object> {
        private MonoBodyConverter() {
        }

        @Override
        public Object apply(Object body) {
            if (body instanceof Mono) {
                return ((Mono)body).block();
            }
            return body;
        }
    }

    private static final class ResourceBodyConverter
    implements Function<Object, Object> {
        private ResourceBodyConverter() {
        }

        @Override
        public Object apply(Object body) {
            if (body instanceof org.springframework.core.io.Resource) {
                try {
                    return ((org.springframework.core.io.Resource)body).getInputStream();
                }
                catch (IOException ex) {
                    throw new IllegalStateException();
                }
            }
            return body;
        }
    }

    private static final class OperationInflector
    implements Inflector<ContainerRequestContext, Object> {
        private static final String PATH_SEPARATOR = "/";
        private static final List<Function<Object, Object>> BODY_CONVERTERS;
        private final WebOperation operation;
        private final boolean readBody;
        private final WebServerNamespace serverNamespace;
        private final JerseyRemainingPathSegmentProvider remainingPathSegmentProvider;

        private OperationInflector(WebOperation operation, boolean readBody, WebServerNamespace serverNamespace, JerseyRemainingPathSegmentProvider remainingPathSegments) {
            this.operation = operation;
            this.readBody = readBody;
            this.serverNamespace = serverNamespace;
            this.remainingPathSegmentProvider = remainingPathSegments;
        }

        public Response apply(ContainerRequestContext data) {
            HashMap<String, Object> arguments = new HashMap<String, Object>();
            if (this.readBody) {
                arguments.putAll(this.extractBodyArguments(data));
            }
            arguments.putAll(this.extractPathParameters(data));
            arguments.putAll(this.extractQueryParameters(data));
            try {
                JerseySecurityContext securityContext = new JerseySecurityContext(data.getSecurityContext());
                OperationArgumentResolver serverNamespaceArgumentResolver = OperationArgumentResolver.of(WebServerNamespace.class, () -> this.serverNamespace);
                InvocationContext invocationContext = new InvocationContext(securityContext, arguments, serverNamespaceArgumentResolver, new ProducibleOperationArgumentResolver(() -> (List)data.getHeaders().get((Object)"Accept")));
                Object response = this.operation.invoke(invocationContext);
                return this.convertToJaxRsResponse(response, data.getRequest().getMethod());
            }
            catch (InvalidEndpointRequestException ex) {
                return Response.status((Response.Status)Response.Status.BAD_REQUEST).build();
            }
        }

        private Map<String, Object> extractBodyArguments(ContainerRequestContext data) {
            Map<String, Object> entity = (Map<String, Object>)((ContainerRequest)data).readEntity(Map.class);
            return entity != null ? entity : Collections.emptyMap();
        }

        private Map<String, Object> extractPathParameters(ContainerRequestContext requestContext) {
            Map<String, Object> pathParameters = this.extract((MultivaluedMap<String, String>)requestContext.getUriInfo().getPathParameters());
            String matchAllRemainingPathSegmentsVariable = this.operation.getRequestPredicate().getMatchAllRemainingPathSegmentsVariable();
            if (matchAllRemainingPathSegmentsVariable != null) {
                String remainingPathSegments = this.getRemainingPathSegments(requestContext, pathParameters, matchAllRemainingPathSegmentsVariable);
                pathParameters.put(matchAllRemainingPathSegmentsVariable, this.tokenizePathSegments(remainingPathSegments));
            }
            return pathParameters;
        }

        private String getRemainingPathSegments(ContainerRequestContext requestContext, Map<String, Object> pathParameters, String matchAllRemainingPathSegmentsVariable) {
            if (this.remainingPathSegmentProvider != null) {
                return this.remainingPathSegmentProvider.get(requestContext, matchAllRemainingPathSegmentsVariable);
            }
            return (String)pathParameters.get(matchAllRemainingPathSegmentsVariable);
        }

        private String[] tokenizePathSegments(String path) {
            String[] segments = StringUtils.tokenizeToStringArray((String)path, (String)PATH_SEPARATOR, (boolean)false, (boolean)true);
            for (int i = 0; i < segments.length; ++i) {
                if (!segments[i].contains("%")) continue;
                segments[i] = StringUtils.uriDecode((String)segments[i], (Charset)StandardCharsets.UTF_8);
            }
            return segments;
        }

        private Map<String, Object> extractQueryParameters(ContainerRequestContext requestContext) {
            return this.extract((MultivaluedMap<String, String>)requestContext.getUriInfo().getQueryParameters());
        }

        private Map<String, Object> extract(MultivaluedMap<String, String> multivaluedMap) {
            HashMap<String, Object> result = new HashMap<String, Object>();
            multivaluedMap.forEach((name, values) -> {
                if (!CollectionUtils.isEmpty((Collection)values)) {
                    result.put((String)name, values.size() != 1 ? values : values.get(0));
                }
            });
            return result;
        }

        private Response convertToJaxRsResponse(Object response, String httpMethod) {
            if (response == null) {
                boolean isGet = "GET".equals(httpMethod);
                Response.Status status = isGet ? Response.Status.NOT_FOUND : Response.Status.NO_CONTENT;
                return Response.status((Response.Status)status).build();
            }
            if (!(response instanceof WebEndpointResponse)) {
                return Response.status((Response.Status)Response.Status.OK).entity(this.convertIfNecessary(response)).build();
            }
            WebEndpointResponse webEndpointResponse = (WebEndpointResponse)response;
            return Response.status((int)webEndpointResponse.getStatus()).header("Content-Type", (Object)webEndpointResponse.getContentType()).entity(this.convertIfNecessary(webEndpointResponse.getBody())).build();
        }

        private Object convertIfNecessary(Object body) {
            for (Function<Object, Object> converter : BODY_CONVERTERS) {
                body = converter.apply(body);
            }
            return body;
        }

        static {
            ArrayList<Function<Object, Object>> converters = new ArrayList<Function<Object, Object>>();
            converters.add(new ResourceBodyConverter());
            if (ClassUtils.isPresent((String)"reactor.core.publisher.Mono", (ClassLoader)OperationInflector.class.getClassLoader())) {
                converters.add(new FluxBodyConverter());
                converters.add(new MonoBodyConverter());
            }
            BODY_CONVERTERS = Collections.unmodifiableList(converters);
        }
    }
}

