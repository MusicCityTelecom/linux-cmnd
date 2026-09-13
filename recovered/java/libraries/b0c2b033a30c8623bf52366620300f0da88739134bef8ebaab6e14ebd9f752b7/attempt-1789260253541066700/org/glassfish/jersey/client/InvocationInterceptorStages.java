/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.internal.LocalizationMessages;
import org.glassfish.jersey.client.internal.routing.ClientResponseMediaTypeDeterminer;
import org.glassfish.jersey.client.spi.PostInvocationInterceptor;
import org.glassfish.jersey.client.spi.PreInvocationInterceptor;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.model.internal.RankedComparator;

class InvocationInterceptorStages {
    private static final Logger LOGGER = Logger.getLogger(InvocationInterceptorStages.class.getName());

    private InvocationInterceptorStages() {
    }

    static PreInvocationInterceptorStage createPreInvocationInterceptorStage(InjectionManager injectionManager) {
        return new PreInvocationInterceptorStage(injectionManager);
    }

    static PostInvocationInterceptorStage createPostInvocationInterceptorStage(InjectionManager injectionManager) {
        return new PostInvocationInterceptorStage(injectionManager);
    }

    private static ProcessingException createProcessingException(Throwable t) {
        ProcessingException processingException = InvocationInterceptorStages.createProcessingException(LocalizationMessages.EXCEPTION_SUPPRESSED());
        processingException.addSuppressed(t);
        return processingException;
    }

    private static ProcessingException createProcessingException(String message) {
        return new InvocationInterceptorException(message);
    }

    private static RuntimeException suppressExceptions(Deque<Throwable> throwables) {
        if (throwables.size() == 1 && RuntimeException.class.isInstance(throwables.getFirst())) {
            throw (RuntimeException)throwables.getFirst();
        }
        ProcessingException processingException = InvocationInterceptorStages.createProcessingException(LocalizationMessages.EXCEPTION_SUPPRESSED());
        for (Throwable throwable : throwables) {
            if (processingException.getCause() == null) {
                processingException.initCause(throwable);
            }
            processingException.addSuppressed(throwable);
        }
        return processingException;
    }

    private static class InvocationInterceptorException
    extends ProcessingException {
        private InvocationInterceptorException(String message) {
            super(message);
        }
    }

    private static class InvocationInterceptorResponseContext
    implements ClientResponseContext {
        private final ClientResponse clientResponse;

        private InvocationInterceptorResponseContext(ClientResponse clientResponse) {
            this.clientResponse = clientResponse;
        }

        @Override
        public int getStatus() {
            return this.clientResponse.getStatus();
        }

        @Override
        public void setStatus(int code) {
            this.clientResponse.setStatus(code);
        }

        @Override
        public Response.StatusType getStatusInfo() {
            return this.clientResponse.getStatusInfo();
        }

        @Override
        public void setStatusInfo(Response.StatusType statusInfo) {
            this.clientResponse.setStatusInfo(statusInfo);
        }

        @Override
        public MultivaluedMap<String, String> getHeaders() {
            return this.clientResponse.getHeaders();
        }

        @Override
        public String getHeaderString(String name) {
            return this.clientResponse.getHeaderString(name);
        }

        @Override
        public Set<String> getAllowedMethods() {
            return this.clientResponse.getAllowedMethods();
        }

        @Override
        public Date getDate() {
            return this.clientResponse.getDate();
        }

        @Override
        public Locale getLanguage() {
            return this.clientResponse.getLanguage();
        }

        @Override
        public int getLength() {
            return this.clientResponse.getLength();
        }

        @Override
        public MediaType getMediaType() {
            return this.clientResponse.getMediaType();
        }

        @Override
        public Map<String, NewCookie> getCookies() {
            return this.clientResponse.getCookies();
        }

        @Override
        public EntityTag getEntityTag() {
            return this.clientResponse.getEntityTag();
        }

        @Override
        public Date getLastModified() {
            return this.clientResponse.getLastModified();
        }

        @Override
        public URI getLocation() {
            return this.clientResponse.getLocation();
        }

        @Override
        public Set<Link> getLinks() {
            return this.clientResponse.getLinks();
        }

        @Override
        public boolean hasLink(String relation) {
            return this.clientResponse.hasLink(relation);
        }

        @Override
        public Link getLink(String relation) {
            return this.clientResponse.getLink(relation);
        }

        @Override
        public Link.Builder getLinkBuilder(String relation) {
            return this.clientResponse.getLinkBuilder(relation);
        }

        @Override
        public boolean hasEntity() {
            return this.clientResponse.hasEntity();
        }

        @Override
        public InputStream getEntityStream() {
            return this.clientResponse.getEntityStream();
        }

        @Override
        public void setEntityStream(InputStream input) {
            this.clientResponse.setEntityStream(input);
        }
    }

    private static class PostInvocationExceptionContext
    implements PostInvocationInterceptor.ExceptionContext {
        private ClientResponse responseContext;
        private LinkedList<Throwable> throwables;
        private Response response = null;

        private PostInvocationExceptionContext(ClientResponse responseContext, Throwable throwable) {
            this.responseContext = responseContext;
            this.throwables = new LinkedList();
            if (throwable != null) {
                if (InvocationInterceptorException.class.isInstance(throwable)) {
                    for (Throwable t : throwable.getSuppressed()) {
                        this.throwables.add(t);
                    }
                } else {
                    this.throwables.add(throwable);
                }
            }
        }

        @Override
        public Optional<ClientResponseContext> getResponseContext() {
            return this.responseContext == null ? Optional.empty() : Optional.of(new InvocationInterceptorResponseContext(this.responseContext));
        }

        @Override
        public Deque<Throwable> getThrowables() {
            return this.throwables;
        }

        @Override
        public void resolve(Response response) {
            if (this.response != null) {
                LOGGER.warning(LocalizationMessages.POSTINVOCATION_INTERCEPTOR_MULTIPLE_RESOLVES());
                throw new IllegalStateException(LocalizationMessages.POSTINVOCATION_INTERCEPTOR_MULTIPLE_RESOLVES());
            }
            LOGGER.finer(LocalizationMessages.POSTINVOCATION_INTERCEPTOR_RESOLVE());
            this.response = response;
        }
    }

    private static class InvocationInterceptorRequestContext
    implements ClientRequestContext {
        private final ClientRequest clientRequest;

        private InvocationInterceptorRequestContext(ClientRequest clientRequestContext) {
            this.clientRequest = clientRequestContext;
        }

        @Override
        public Object getProperty(String name) {
            return this.clientRequest.getProperty(name);
        }

        @Override
        public Collection<String> getPropertyNames() {
            return this.clientRequest.getPropertyNames();
        }

        @Override
        public void setProperty(String name, Object object) {
            this.clientRequest.setProperty(name, object);
        }

        @Override
        public void removeProperty(String name) {
            this.clientRequest.removeProperty(name);
        }

        @Override
        public URI getUri() {
            return this.clientRequest.getUri();
        }

        @Override
        public void setUri(URI uri) {
            this.clientRequest.setUri(uri);
        }

        @Override
        public String getMethod() {
            return this.clientRequest.getMethod();
        }

        @Override
        public void setMethod(String method) {
            this.clientRequest.setMethod(method);
        }

        @Override
        public MultivaluedMap<String, Object> getHeaders() {
            return this.clientRequest.getHeaders();
        }

        @Override
        public MultivaluedMap<String, String> getStringHeaders() {
            return this.clientRequest.getStringHeaders();
        }

        @Override
        public String getHeaderString(String name) {
            return this.clientRequest.getHeaderString(name);
        }

        @Override
        public Date getDate() {
            return this.clientRequest.getDate();
        }

        @Override
        public Locale getLanguage() {
            return this.clientRequest.getLanguage();
        }

        @Override
        public MediaType getMediaType() {
            return this.clientRequest.getMediaType();
        }

        @Override
        public List<MediaType> getAcceptableMediaTypes() {
            return this.clientRequest.getAcceptableMediaTypes();
        }

        @Override
        public List<Locale> getAcceptableLanguages() {
            return this.clientRequest.getAcceptableLanguages();
        }

        @Override
        public Map<String, Cookie> getCookies() {
            return this.clientRequest.getCookies();
        }

        @Override
        public boolean hasEntity() {
            return this.clientRequest.hasEntity();
        }

        @Override
        public Object getEntity() {
            return this.clientRequest.getEntity();
        }

        @Override
        public Class<?> getEntityClass() {
            return this.clientRequest.getEntityClass();
        }

        @Override
        public Type getEntityType() {
            return this.clientRequest.getEntityType();
        }

        @Override
        public void setEntity(Object entity) {
            this.clientRequest.setEntity(entity);
        }

        @Override
        public void setEntity(Object entity, Annotation[] annotations, MediaType mediaType) {
            this.clientRequest.setEntity(entity, annotations, mediaType);
        }

        @Override
        public Annotation[] getEntityAnnotations() {
            return this.clientRequest.getEntityAnnotations();
        }

        @Override
        public OutputStream getEntityStream() {
            return this.clientRequest.getEntityStream();
        }

        @Override
        public void setEntityStream(OutputStream outputStream) {
            this.clientRequest.setEntityStream(outputStream);
        }

        @Override
        public Client getClient() {
            return this.clientRequest.getClient();
        }

        @Override
        public Configuration getConfiguration() {
            return this.clientRequest.getConfiguration();
        }

        @Override
        public void abortWith(Response response) {
            if (this.clientRequest.getAbortResponse() != null) {
                LOGGER.warning(LocalizationMessages.PREINVOCATION_INTERCEPTOR_MULTIPLE_ABORTIONS());
                throw new IllegalStateException(LocalizationMessages.PREINVOCATION_INTERCEPTOR_MULTIPLE_ABORTIONS());
            }
            LOGGER.finer(LocalizationMessages.PREINVOCATION_INTERCEPTOR_ABORT_WITH());
            this.clientRequest.abortWith(response);
        }
    }

    static class PostInvocationInterceptorStage {
        private final Iterable<PostInvocationInterceptor> postInvocationInterceptors;

        private PostInvocationInterceptorStage(InjectionManager injectionManager) {
            RankedComparator comparator = new RankedComparator(RankedComparator.Order.ASCENDING);
            this.postInvocationInterceptors = Providers.getAllProviders(injectionManager, PostInvocationInterceptor.class, comparator);
        }

        boolean hasPostInvocationInterceptor() {
            return this.postInvocationInterceptors.iterator().hasNext();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private ClientResponse afterRequestWithoutException(Iterator<PostInvocationInterceptor> postInvocationInterceptors, InvocationInterceptorRequestContext requestContext, PostInvocationExceptionContext exceptionContext) {
            boolean withoutException = true;
            if (postInvocationInterceptors.hasNext()) {
                PostInvocationInterceptor postInvocationInterceptor = postInvocationInterceptors.next();
                try {
                    postInvocationInterceptor.afterRequest(requestContext, exceptionContext.getResponseContext().get());
                    return withoutException ? this.afterRequestWithoutException(postInvocationInterceptors, requestContext, exceptionContext) : this.afterRequestWithException(postInvocationInterceptors, requestContext, exceptionContext);
                }
                catch (Throwable throwable) {
                    try {
                        LOGGER.log(Level.FINE, LocalizationMessages.POSTINVOCATION_INTERCEPTOR_EXCEPTION(), throwable);
                        withoutException = false;
                        exceptionContext.throwables.add(throwable);
                        return withoutException ? this.afterRequestWithoutException(postInvocationInterceptors, requestContext, exceptionContext) : this.afterRequestWithException(postInvocationInterceptors, requestContext, exceptionContext);
                    }
                    catch (Throwable throwable2) {
                        return withoutException ? this.afterRequestWithoutException(postInvocationInterceptors, requestContext, exceptionContext) : this.afterRequestWithException(postInvocationInterceptors, requestContext, exceptionContext);
                    }
                }
            }
            return exceptionContext.responseContext;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private ClientResponse afterRequestWithException(Iterator<PostInvocationInterceptor> postInvocationInterceptors, InvocationInterceptorRequestContext requestContext, PostInvocationExceptionContext exceptionContext) {
            Throwable caught = null;
            if (postInvocationInterceptors.hasNext()) {
                PostInvocationInterceptor postInvocationInterceptor = postInvocationInterceptors.next();
                try {
                    postInvocationInterceptor.onException(requestContext, exceptionContext);
                }
                catch (Throwable throwable) {
                    LOGGER.log(Level.FINE, LocalizationMessages.POSTINVOCATION_INTERCEPTOR_EXCEPTION(), throwable);
                    caught = throwable;
                }
                try {
                    PostInvocationInterceptorStage.resolveResponse(requestContext, exceptionContext);
                }
                catch (Throwable throwable) {
                    LOGGER.log(Level.FINE, LocalizationMessages.POSTINVOCATION_INTERCEPTOR_EXCEPTION(), throwable);
                    exceptionContext.throwables.add(throwable);
                }
                finally {
                    if (caught != null) {
                        exceptionContext.throwables.add(caught);
                    }
                }
                return exceptionContext.throwables.isEmpty() && exceptionContext.responseContext != null ? this.afterRequestWithoutException(postInvocationInterceptors, requestContext, exceptionContext) : this.afterRequestWithException(postInvocationInterceptors, requestContext, exceptionContext);
            }
            throw InvocationInterceptorStages.suppressExceptions(exceptionContext.throwables);
        }

        ClientResponse afterRequest(ClientRequest request, ClientResponse response, Throwable previousException) {
            PostInvocationExceptionContext exceptionContext = new PostInvocationExceptionContext(response, previousException);
            InvocationInterceptorRequestContext requestContext = new InvocationInterceptorRequestContext(request);
            return previousException != null ? this.afterRequestWithException(this.postInvocationInterceptors.iterator(), requestContext, exceptionContext) : this.afterRequestWithoutException(this.postInvocationInterceptors.iterator(), requestContext, exceptionContext);
        }

        private static boolean resolveResponse(InvocationInterceptorRequestContext requestContext, PostInvocationExceptionContext exceptionContext) {
            if (exceptionContext.response != null) {
                exceptionContext.throwables.clear();
                ClientResponseMediaTypeDeterminer determiner = new ClientResponseMediaTypeDeterminer(requestContext.clientRequest.getWorkers());
                determiner.setResponseMediaTypeIfNotSet(exceptionContext.response, requestContext.getConfiguration());
                ClientResponse response = new ClientResponse(requestContext.clientRequest, exceptionContext.response);
                exceptionContext.responseContext = response;
                exceptionContext.response = null;
                return true;
            }
            return false;
        }
    }

    static class PreInvocationInterceptorStage {
        private Iterable<PreInvocationInterceptor> preInvocationInterceptors;

        private PreInvocationInterceptorStage(InjectionManager injectionManager) {
            RankedComparator comparator = new RankedComparator(RankedComparator.Order.DESCENDING);
            this.preInvocationInterceptors = Providers.getAllProviders(injectionManager, PreInvocationInterceptor.class, comparator);
        }

        boolean hasPreInvocationInterceptors() {
            return this.preInvocationInterceptors.iterator().hasNext();
        }

        void beforeRequest(ClientRequest request) {
            LinkedList<Throwable> throwables = new LinkedList<Throwable>();
            InvocationInterceptorRequestContext requestContext = new InvocationInterceptorRequestContext(request);
            Iterator<PreInvocationInterceptor> preInvocationInterceptorIterator = this.preInvocationInterceptors.iterator();
            while (preInvocationInterceptorIterator.hasNext()) {
                try {
                    preInvocationInterceptorIterator.next().beforeRequest(requestContext);
                }
                catch (Throwable throwable) {
                    LOGGER.log(Level.FINE, LocalizationMessages.PREINVOCATION_INTERCEPTOR_EXCEPTION(), throwable);
                    throwables.add(throwable);
                }
            }
            if (!throwables.isEmpty()) {
                throw InvocationInterceptorStages.suppressExceptions(throwables);
            }
        }

        ClientRequestFilter createPreInvocationInterceptorFilter() {
            return new ClientRequestFilter(){

                @Override
                public void filter(ClientRequestContext requestContext) throws IOException {
                }
            };
        }
    }
}

