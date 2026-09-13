/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.logout.LogoutExecutionPlan
 *  org.apereo.cas.logout.LogoutManager
 *  org.apereo.cas.logout.SingleLogoutExecutionRequest
 *  org.apereo.cas.logout.slo.SingleLogoutRequestContext
 *  org.apereo.cas.logout.slo.SingleLogoutServiceMessageHandler
 *  org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket
 *  org.apereo.cas.ticket.TicketGrantingTicket
 *  org.apereo.inspektr.audit.annotation.Audit
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.logout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.tuple.Pair;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.LogoutExecutionPlan;
import org.apereo.cas.logout.LogoutManager;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;
import org.apereo.cas.logout.slo.SingleLogoutServiceMessageHandler;
import org.apereo.cas.ticket.AuthenticatedServicesAwareTicketGrantingTicket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.inspektr.audit.annotation.Audit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogoutManager
implements LogoutManager {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLogoutManager.class);
    private final boolean singleLogoutCallbacksDisabled;
    private final LogoutExecutionPlan logoutExecutionPlan;

    @Audit(action="LOGOUT", actionResolverName="LOGOUT_ACTION_RESOLVER", resourceResolverName="LOGOUT_RESOURCE_RESOLVER")
    public List<SingleLogoutRequestContext> performLogout(SingleLogoutExecutionRequest context) {
        TicketGrantingTicket ticket = context.getTicketGrantingTicket();
        LOGGER.info("Performing logout operations for [{}]", (Object)ticket.getId());
        if (this.singleLogoutCallbacksDisabled) {
            LOGGER.info("Single logout callbacks are disabled");
            return new ArrayList<SingleLogoutRequestContext>(0);
        }
        List<SingleLogoutRequestContext> logoutRequests = this.performLogoutForTicket(context);
        this.logoutExecutionPlan.getLogoutPostProcessors().forEach(h -> {
            LOGGER.debug("Invoking logout handler [{}] to process ticket [{}]", (Object)h.getClass().getSimpleName(), (Object)ticket.getId());
            h.handle(ticket);
        });
        LOGGER.info("[{}] logout requests were processed", (Object)logoutRequests.size());
        return logoutRequests;
    }

    private List<SingleLogoutRequestContext> performLogoutForTicket(SingleLogoutExecutionRequest context) {
        TicketGrantingTicket ticketToBeLoggedOut = context.getTicketGrantingTicket();
        LinkedHashMap streamServices = new LinkedHashMap();
        if (ticketToBeLoggedOut instanceof AuthenticatedServicesAwareTicketGrantingTicket) {
            Map services = ((AuthenticatedServicesAwareTicketGrantingTicket)ticketToBeLoggedOut).getServices();
            streamServices.putAll(services);
        }
        streamServices.putAll(ticketToBeLoggedOut.getProxyGrantingTickets());
        List logoutServices = streamServices.entrySet().stream().filter(entry -> entry.getValue() instanceof WebApplicationService).filter(Objects::nonNull).map(entry -> Pair.of((Object)((String)entry.getKey()), (Object)((WebApplicationService)entry.getValue()))).collect(Collectors.toList());
        Collection sloHandlers = this.logoutExecutionPlan.getSingleLogoutServiceMessageHandlers();
        return logoutServices.stream().map(entry -> sloHandlers.stream().sorted(Comparator.comparing(SingleLogoutServiceMessageHandler::getOrder)).filter(handler -> handler.supports(context, (WebApplicationService)entry.getValue())).map(handler -> {
            WebApplicationService service = (WebApplicationService)entry.getValue();
            LOGGER.trace("Handling single logout callback for [{}]", (Object)service.getId());
            return handler.handle(service, (String)entry.getKey(), context);
        }).flatMap(Collection::stream).filter(Objects::nonNull).collect(Collectors.toList())).flatMap(Collection::stream).filter(DefaultLogoutManager.distinctByKey(SingleLogoutRequestContext::getService)).collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        ConcurrentHashMap seen = new ConcurrentHashMap();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Generated
    public DefaultLogoutManager(boolean singleLogoutCallbacksDisabled, LogoutExecutionPlan logoutExecutionPlan) {
        this.singleLogoutCallbacksDisabled = singleLogoutCallbacksDisabled;
        this.logoutExecutionPlan = logoutExecutionPlan;
    }

    @Generated
    public boolean isSingleLogoutCallbacksDisabled() {
        return this.singleLogoutCallbacksDisabled;
    }

    @Generated
    public LogoutExecutionPlan getLogoutExecutionPlan() {
        return this.logoutExecutionPlan;
    }
}

