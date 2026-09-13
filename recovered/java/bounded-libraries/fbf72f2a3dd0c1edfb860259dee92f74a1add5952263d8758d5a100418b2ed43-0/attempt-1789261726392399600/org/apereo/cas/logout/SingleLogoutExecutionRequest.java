/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.ticket.TicketGrantingTicket
 */
package org.apereo.cas.logout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.ticket.TicketGrantingTicket;

public class SingleLogoutExecutionRequest {
    private final TicketGrantingTicket ticketGrantingTicket;
    @JsonIgnore
    private final Optional<HttpServletRequest> httpServletRequest;
    @JsonIgnore
    private final Optional<HttpServletResponse> httpServletResponse;

    @Generated
    private static Optional<HttpServletRequest> $default$httpServletRequest() {
        return Optional.empty();
    }

    @Generated
    private static Optional<HttpServletResponse> $default$httpServletResponse() {
        return Optional.empty();
    }

    @Generated
    protected SingleLogoutExecutionRequest(SingleLogoutExecutionRequestBuilder<?, ?> b) {
        this.ticketGrantingTicket = b.ticketGrantingTicket;
        this.httpServletRequest = b.httpServletRequest$set ? b.httpServletRequest$value : SingleLogoutExecutionRequest.$default$httpServletRequest();
        this.httpServletResponse = b.httpServletResponse$set ? b.httpServletResponse$value : SingleLogoutExecutionRequest.$default$httpServletResponse();
    }

    @Generated
    public static SingleLogoutExecutionRequestBuilder<?, ?> builder() {
        return new SingleLogoutExecutionRequestBuilderImpl();
    }

    @Generated
    public TicketGrantingTicket getTicketGrantingTicket() {
        return this.ticketGrantingTicket;
    }

    @Generated
    public Optional<HttpServletRequest> getHttpServletRequest() {
        return this.httpServletRequest;
    }

    @Generated
    public Optional<HttpServletResponse> getHttpServletResponse() {
        return this.httpServletResponse;
    }

    @Generated
    private static final class SingleLogoutExecutionRequestBuilderImpl
    extends SingleLogoutExecutionRequestBuilder<SingleLogoutExecutionRequest, SingleLogoutExecutionRequestBuilderImpl> {
        @Generated
        private SingleLogoutExecutionRequestBuilderImpl() {
        }

        @Override
        @Generated
        protected SingleLogoutExecutionRequestBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public SingleLogoutExecutionRequest build() {
            return new SingleLogoutExecutionRequest(this);
        }
    }

    @Generated
    public static abstract class SingleLogoutExecutionRequestBuilder<C extends SingleLogoutExecutionRequest, B extends SingleLogoutExecutionRequestBuilder<C, B>> {
        @Generated
        private TicketGrantingTicket ticketGrantingTicket;
        @Generated
        private boolean httpServletRequest$set;
        @Generated
        private Optional<HttpServletRequest> httpServletRequest$value;
        @Generated
        private boolean httpServletResponse$set;
        @Generated
        private Optional<HttpServletResponse> httpServletResponse$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B ticketGrantingTicket(TicketGrantingTicket ticketGrantingTicket) {
            this.ticketGrantingTicket = ticketGrantingTicket;
            return this.self();
        }

        @JsonIgnore
        @Generated
        public B httpServletRequest(Optional<HttpServletRequest> httpServletRequest) {
            this.httpServletRequest$value = httpServletRequest;
            this.httpServletRequest$set = true;
            return this.self();
        }

        @JsonIgnore
        @Generated
        public B httpServletResponse(Optional<HttpServletResponse> httpServletResponse) {
            this.httpServletResponse$value = httpServletResponse;
            this.httpServletResponse$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "SingleLogoutExecutionRequest.SingleLogoutExecutionRequestBuilder(ticketGrantingTicket=" + this.ticketGrantingTicket + ", httpServletRequest$value=" + this.httpServletRequest$value + ", httpServletResponse$value=" + this.httpServletResponse$value + ")";
        }
    }
}

