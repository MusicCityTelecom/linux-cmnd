/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.configuration.model.support.email.EmailProperties
 *  org.apereo.cas.util.CollectionUtils
 */
package org.apereo.cas.notifications.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.configuration.model.support.email.EmailProperties;
import org.apereo.cas.util.CollectionUtils;

public class EmailMessageRequest {
    private final Principal principal;
    private final String attribute;
    private final EmailProperties emailProperties;
    private final String body;
    private final List<String> to;
    private final Locale locale;

    public boolean hasAttributeValue() {
        return StringUtils.isNotBlank((CharSequence)this.attribute) && this.principal.getAttributes().containsKey(this.attribute);
    }

    public Optional<Object> getAttributeValue() {
        List value = (List)this.principal.getAttributes().get(this.attribute);
        return CollectionUtils.firstElement((Object)value);
    }

    public List<String> getRecipients() {
        Optional<Object> value;
        if (this.hasAttributeValue() && (value = this.getAttributeValue()).isPresent()) {
            return (List)CollectionUtils.toCollection((Object)value.get(), ArrayList.class);
        }
        return this.getTo();
    }

    @Generated
    protected EmailMessageRequest(EmailMessageRequestBuilder<?, ?> b) {
        this.principal = b.principal;
        this.attribute = b.attribute;
        this.emailProperties = b.emailProperties;
        this.body = b.body;
        this.to = b.to;
        this.locale = b.locale;
    }

    @Generated
    public static EmailMessageRequestBuilder<?, ?> builder() {
        return new EmailMessageRequestBuilderImpl();
    }

    @Generated
    public Principal getPrincipal() {
        return this.principal;
    }

    @Generated
    public String getAttribute() {
        return this.attribute;
    }

    @Generated
    public EmailProperties getEmailProperties() {
        return this.emailProperties;
    }

    @Generated
    public String getBody() {
        return this.body;
    }

    @Generated
    public List<String> getTo() {
        return this.to;
    }

    @Generated
    public Locale getLocale() {
        return this.locale;
    }

    @Generated
    public EmailMessageRequest withPrincipal(Principal principal) {
        return this.principal == principal ? this : new EmailMessageRequest(principal, this.attribute, this.emailProperties, this.body, this.to, this.locale);
    }

    @Generated
    public EmailMessageRequest withAttribute(String attribute) {
        return this.attribute == attribute ? this : new EmailMessageRequest(this.principal, attribute, this.emailProperties, this.body, this.to, this.locale);
    }

    @Generated
    public EmailMessageRequest withEmailProperties(EmailProperties emailProperties) {
        return this.emailProperties == emailProperties ? this : new EmailMessageRequest(this.principal, this.attribute, emailProperties, this.body, this.to, this.locale);
    }

    @Generated
    public EmailMessageRequest withBody(String body) {
        return this.body == body ? this : new EmailMessageRequest(this.principal, this.attribute, this.emailProperties, body, this.to, this.locale);
    }

    @Generated
    public EmailMessageRequest withTo(List<String> to) {
        return this.to == to ? this : new EmailMessageRequest(this.principal, this.attribute, this.emailProperties, this.body, to, this.locale);
    }

    @Generated
    public EmailMessageRequest withLocale(Locale locale) {
        return this.locale == locale ? this : new EmailMessageRequest(this.principal, this.attribute, this.emailProperties, this.body, this.to, locale);
    }

    @Generated
    public EmailMessageRequest(Principal principal, String attribute, EmailProperties emailProperties, String body, List<String> to, Locale locale) {
        this.principal = principal;
        this.attribute = attribute;
        this.emailProperties = emailProperties;
        this.body = body;
        this.to = to;
        this.locale = locale;
    }

    @Generated
    private static final class EmailMessageRequestBuilderImpl
    extends EmailMessageRequestBuilder<EmailMessageRequest, EmailMessageRequestBuilderImpl> {
        @Generated
        private EmailMessageRequestBuilderImpl() {
        }

        @Override
        @Generated
        protected EmailMessageRequestBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public EmailMessageRequest build() {
            return new EmailMessageRequest(this);
        }
    }

    @Generated
    public static abstract class EmailMessageRequestBuilder<C extends EmailMessageRequest, B extends EmailMessageRequestBuilder<C, B>> {
        @Generated
        private Principal principal;
        @Generated
        private String attribute;
        @Generated
        private EmailProperties emailProperties;
        @Generated
        private String body;
        @Generated
        private List<String> to;
        @Generated
        private Locale locale;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B principal(Principal principal) {
            this.principal = principal;
            return this.self();
        }

        @Generated
        public B attribute(String attribute) {
            this.attribute = attribute;
            return this.self();
        }

        @Generated
        public B emailProperties(EmailProperties emailProperties) {
            this.emailProperties = emailProperties;
            return this.self();
        }

        @Generated
        public B body(String body) {
            this.body = body;
            return this.self();
        }

        @Generated
        public B to(List<String> to) {
            this.to = to;
            return this.self();
        }

        @Generated
        public B locale(Locale locale) {
            this.locale = locale;
            return this.self();
        }

        @Generated
        public String toString() {
            return "EmailMessageRequest.EmailMessageRequestBuilder(principal=" + this.principal + ", attribute=" + this.attribute + ", emailProperties=" + this.emailProperties + ", body=" + this.body + ", to=" + this.to + ", locale=" + this.locale + ")";
        }
    }
}

