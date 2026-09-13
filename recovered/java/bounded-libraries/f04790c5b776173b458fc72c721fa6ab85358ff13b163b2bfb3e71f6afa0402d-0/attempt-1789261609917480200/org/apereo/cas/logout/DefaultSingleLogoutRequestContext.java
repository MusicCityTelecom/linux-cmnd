/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.logout.LogoutRequestStatus
 *  org.apereo.cas.logout.SingleLogoutExecutionRequest
 *  org.apereo.cas.logout.slo.SingleLogoutRequestContext
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceLogoutType
 *  org.springframework.data.annotation.Transient
 */
package org.apereo.cas.logout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Transient;
import lombok.Generated;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.LogoutRequestStatus;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceLogoutType;

public class DefaultSingleLogoutRequestContext
implements SingleLogoutRequestContext {
    private static final long serialVersionUID = -6411421298859045022L;
    private final String ticketId;
    private final WebApplicationService service;
    private final URL logoutUrl;
    @JsonIgnore
    @Transient
    @org.springframework.data.annotation.Transient
    private final transient RegisteredService registeredService;
    @JsonIgnore
    @Transient
    @org.springframework.data.annotation.Transient
    private final transient SingleLogoutExecutionRequest executionRequest;
    private final RegisteredServiceLogoutType logoutType;
    private LogoutRequestStatus status;
    private Map<String, String> properties;

    @Generated
    private static RegisteredServiceLogoutType $default$logoutType() {
        return RegisteredServiceLogoutType.BACK_CHANNEL;
    }

    @Generated
    private static LogoutRequestStatus $default$status() {
        return LogoutRequestStatus.NOT_ATTEMPTED;
    }

    @Generated
    private static Map<String, String> $default$properties() {
        return new LinkedHashMap<String, String>(0);
    }

    @Generated
    protected DefaultSingleLogoutRequestContext(DefaultSingleLogoutRequestContextBuilder<?, ?> b) {
        this.ticketId = b.ticketId;
        this.service = b.service;
        this.logoutUrl = b.logoutUrl;
        this.registeredService = b.registeredService;
        this.executionRequest = b.executionRequest;
        this.logoutType = b.logoutType$set ? b.logoutType$value : DefaultSingleLogoutRequestContext.$default$logoutType();
        this.status = b.status$set ? b.status$value : DefaultSingleLogoutRequestContext.$default$status();
        this.properties = b.properties$set ? b.properties$value : DefaultSingleLogoutRequestContext.$default$properties();
    }

    @Generated
    public static DefaultSingleLogoutRequestContextBuilder<?, ?> builder() {
        return new DefaultSingleLogoutRequestContextBuilderImpl();
    }

    @Generated
    public String toString() {
        return "DefaultSingleLogoutRequestContext(ticketId=" + this.ticketId + ", service=" + this.service + ", logoutUrl=" + this.logoutUrl + ", registeredService=" + this.registeredService + ", executionRequest=" + this.executionRequest + ", logoutType=" + this.logoutType + ", status=" + this.status + ", properties=" + this.properties + ")";
    }

    @Generated
    public String getTicketId() {
        return this.ticketId;
    }

    @Generated
    public WebApplicationService getService() {
        return this.service;
    }

    @Generated
    public URL getLogoutUrl() {
        return this.logoutUrl;
    }

    @Generated
    public RegisteredService getRegisteredService() {
        return this.registeredService;
    }

    @Generated
    public SingleLogoutExecutionRequest getExecutionRequest() {
        return this.executionRequest;
    }

    @Generated
    public RegisteredServiceLogoutType getLogoutType() {
        return this.logoutType;
    }

    @Generated
    public LogoutRequestStatus getStatus() {
        return this.status;
    }

    @Generated
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Generated
    public void setStatus(LogoutRequestStatus status) {
        this.status = status;
    }

    @Generated
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Generated
    private static final class DefaultSingleLogoutRequestContextBuilderImpl
    extends DefaultSingleLogoutRequestContextBuilder<DefaultSingleLogoutRequestContext, DefaultSingleLogoutRequestContextBuilderImpl> {
        @Generated
        private DefaultSingleLogoutRequestContextBuilderImpl() {
        }

        @Override
        @Generated
        protected DefaultSingleLogoutRequestContextBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public DefaultSingleLogoutRequestContext build() {
            return new DefaultSingleLogoutRequestContext(this);
        }
    }

    @Generated
    public static abstract class DefaultSingleLogoutRequestContextBuilder<C extends DefaultSingleLogoutRequestContext, B extends DefaultSingleLogoutRequestContextBuilder<C, B>> {
        @Generated
        private String ticketId;
        @Generated
        private WebApplicationService service;
        @Generated
        private URL logoutUrl;
        @Generated
        private RegisteredService registeredService;
        @Generated
        private SingleLogoutExecutionRequest executionRequest;
        @Generated
        private boolean logoutType$set;
        @Generated
        private RegisteredServiceLogoutType logoutType$value;
        @Generated
        private boolean status$set;
        @Generated
        private LogoutRequestStatus status$value;
        @Generated
        private boolean properties$set;
        @Generated
        private Map<String, String> properties$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B ticketId(String ticketId) {
            this.ticketId = ticketId;
            return this.self();
        }

        @Generated
        public B service(WebApplicationService service) {
            this.service = service;
            return this.self();
        }

        @Generated
        public B logoutUrl(URL logoutUrl) {
            this.logoutUrl = logoutUrl;
            return this.self();
        }

        @JsonIgnore
        @Generated
        public B registeredService(RegisteredService registeredService) {
            this.registeredService = registeredService;
            return this.self();
        }

        @JsonIgnore
        @Generated
        public B executionRequest(SingleLogoutExecutionRequest executionRequest) {
            this.executionRequest = executionRequest;
            return this.self();
        }

        @Generated
        public B logoutType(RegisteredServiceLogoutType logoutType) {
            this.logoutType$value = logoutType;
            this.logoutType$set = true;
            return this.self();
        }

        @Generated
        public B status(LogoutRequestStatus status) {
            this.status$value = status;
            this.status$set = true;
            return this.self();
        }

        @Generated
        public B properties(Map<String, String> properties) {
            this.properties$value = properties;
            this.properties$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "DefaultSingleLogoutRequestContext.DefaultSingleLogoutRequestContextBuilder(ticketId=" + this.ticketId + ", service=" + this.service + ", logoutUrl=" + this.logoutUrl + ", registeredService=" + this.registeredService + ", executionRequest=" + this.executionRequest + ", logoutType$value=" + this.logoutType$value + ", status$value=" + this.status$value + ", properties$value=" + this.properties$value + ")";
        }
    }
}

