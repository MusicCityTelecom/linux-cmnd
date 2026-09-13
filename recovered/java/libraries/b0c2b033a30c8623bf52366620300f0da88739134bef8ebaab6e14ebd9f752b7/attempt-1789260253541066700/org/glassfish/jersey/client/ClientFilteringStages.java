/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.AbortException;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.InboundJaxrsResponse;
import org.glassfish.jersey.client.internal.routing.ClientResponseMediaTypeDeterminer;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Providers;
import org.glassfish.jersey.model.internal.RankedComparator;
import org.glassfish.jersey.process.internal.AbstractChainableStage;
import org.glassfish.jersey.process.internal.ChainableStage;
import org.glassfish.jersey.process.internal.Stage;

class ClientFilteringStages {
    private ClientFilteringStages() {
    }

    static ChainableStage<ClientRequest> createRequestFilteringStage(InjectionManager injectionManager) {
        RankedComparator comparator = new RankedComparator(RankedComparator.Order.ASCENDING);
        Iterable<ClientRequestFilter> requestFilters = Providers.getAllProviders(injectionManager, ClientRequestFilter.class, comparator);
        return requestFilters.iterator().hasNext() ? new RequestFilteringStage(requestFilters) : null;
    }

    static ChainableStage<ClientRequest> createRequestFilteringStage(ClientRequestFilter firstFilter, InjectionManager injectionManager) {
        RankedComparator comparator = new RankedComparator(RankedComparator.Order.ASCENDING);
        Iterable<ClientRequestFilter> requestFilters = Providers.getAllProviders(injectionManager, ClientRequestFilter.class, comparator);
        if (firstFilter != null && !requestFilters.iterator().hasNext()) {
            return new RequestFilteringStage(Collections.singletonList(firstFilter));
        }
        if (firstFilter != null && requestFilters.iterator().hasNext()) {
            return new RequestFilteringStage(ClientFilteringStages.prependFilter(firstFilter, requestFilters));
        }
        return null;
    }

    static ChainableStage<ClientResponse> createResponseFilteringStage(InjectionManager injectionManager) {
        RankedComparator comparator = new RankedComparator(RankedComparator.Order.DESCENDING);
        Iterable<ClientResponseFilter> responseFilters = Providers.getAllProviders(injectionManager, ClientResponseFilter.class, comparator);
        return responseFilters.iterator().hasNext() ? new ResponseFilterStage(responseFilters) : null;
    }

    private static <T> Iterable<T> prependFilter(final T filter, final Iterable<T> filters) {
        return new Iterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>(){
                    final Iterator<T> filterIterator;
                    boolean wasInterceptorFilterNext;
                    {
                        this.filterIterator = filters.iterator();
                        this.wasInterceptorFilterNext = false;
                    }

                    @Override
                    public boolean hasNext() {
                        return !this.wasInterceptorFilterNext || this.filterIterator.hasNext();
                    }

                    @Override
                    public T next() {
                        if (this.wasInterceptorFilterNext) {
                            return this.filterIterator.next();
                        }
                        this.wasInterceptorFilterNext = true;
                        return filter;
                    }
                };
            }
        };
    }

    private static class ResponseFilterStage
    extends AbstractChainableStage<ClientResponse> {
        private final Iterable<ClientResponseFilter> filters;

        private ResponseFilterStage(Iterable<ClientResponseFilter> filters) {
            this.filters = filters;
        }

        @Override
        public Stage.Continuation<ClientResponse> apply(ClientResponse responseContext) {
            try {
                for (ClientResponseFilter filter : this.filters) {
                    filter.filter(responseContext.getRequestContext(), responseContext);
                }
            }
            catch (IOException ex) {
                InboundJaxrsResponse response = new InboundJaxrsResponse(responseContext, null);
                throw new ResponseProcessingException((Response)response, (Throwable)ex);
            }
            return Stage.Continuation.of(responseContext, this.getDefaultNext());
        }
    }

    private static final class RequestFilteringStage
    extends AbstractChainableStage<ClientRequest> {
        private final Iterable<ClientRequestFilter> requestFilters;

        private RequestFilteringStage(Iterable<ClientRequestFilter> requestFilters) {
            this.requestFilters = requestFilters;
        }

        @Override
        public Stage.Continuation<ClientRequest> apply(ClientRequest requestContext) {
            for (ClientRequestFilter filter : this.requestFilters) {
                try {
                    filter.filter(requestContext);
                    Response abortResponse = requestContext.getAbortResponse();
                    if (abortResponse == null) continue;
                    ClientResponseMediaTypeDeterminer determiner = new ClientResponseMediaTypeDeterminer(requestContext.getWorkers());
                    determiner.setResponseMediaTypeIfNotSet(abortResponse, requestContext.getConfiguration());
                    throw new AbortException(new ClientResponse(requestContext, abortResponse));
                }
                catch (IOException ex) {
                    throw new ProcessingException(ex);
                }
            }
            return Stage.Continuation.of(requestContext, this.getDefaultNext());
        }
    }
}

