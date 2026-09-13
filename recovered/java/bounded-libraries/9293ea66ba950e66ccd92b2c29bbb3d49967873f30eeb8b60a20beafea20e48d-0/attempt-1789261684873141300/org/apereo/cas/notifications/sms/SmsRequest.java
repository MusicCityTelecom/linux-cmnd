/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.function.FunctionUtils
 */
package org.apereo.cas.notifications.sms;

import java.util.List;
import java.util.Optional;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.function.FunctionUtils;

public class SmsRequest {
    private final Principal principal;
    private final String attribute;
    private final String text;
    private final String from;
    private final String to;

    public boolean hasAttributeValue() {
        return StringUtils.isNotBlank((CharSequence)this.attribute) && this.principal.getAttributes().containsKey(this.attribute);
    }

    public Optional<Object> getAttributeValue() {
        List value = (List)this.principal.getAttributes().get(this.attribute);
        return CollectionUtils.firstElement((Object)value);
    }

    public String getRecipient() {
        return (String)FunctionUtils.doIf((boolean)this.hasAttributeValue(), () -> this.getAttributeValue().map(Object::toString).orElseGet(this::getTo), this::getTo).get();
    }

    public boolean isSufficient() {
        return StringUtils.isNotBlank((CharSequence)this.getText()) && StringUtils.isNotBlank((CharSequence)this.getRecipient());
    }

    @Generated
    protected SmsRequest(SmsRequestBuilder<?, ?> b) {
        this.principal = b.principal;
        this.attribute = b.attribute;
        this.text = b.text;
        this.from = b.from;
        this.to = b.to;
    }

    @Generated
    public static SmsRequestBuilder<?, ?> builder() {
        return new SmsRequestBuilderImpl();
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
    public String getText() {
        return this.text;
    }

    @Generated
    public String getFrom() {
        return this.from;
    }

    @Generated
    public String getTo() {
        return this.to;
    }

    @Generated
    public SmsRequest withPrincipal(Principal principal) {
        return this.principal == principal ? this : new SmsRequest(principal, this.attribute, this.text, this.from, this.to);
    }

    @Generated
    public SmsRequest withAttribute(String attribute) {
        return this.attribute == attribute ? this : new SmsRequest(this.principal, attribute, this.text, this.from, this.to);
    }

    @Generated
    public SmsRequest withText(String text) {
        return this.text == text ? this : new SmsRequest(this.principal, this.attribute, text, this.from, this.to);
    }

    @Generated
    public SmsRequest withFrom(String from) {
        return this.from == from ? this : new SmsRequest(this.principal, this.attribute, this.text, from, this.to);
    }

    @Generated
    public SmsRequest withTo(String to) {
        return this.to == to ? this : new SmsRequest(this.principal, this.attribute, this.text, this.from, to);
    }

    @Generated
    public SmsRequest(Principal principal, String attribute, String text, String from, String to) {
        this.principal = principal;
        this.attribute = attribute;
        this.text = text;
        this.from = from;
        this.to = to;
    }

    @Generated
    private static final class SmsRequestBuilderImpl
    extends SmsRequestBuilder<SmsRequest, SmsRequestBuilderImpl> {
        @Generated
        private SmsRequestBuilderImpl() {
        }

        @Override
        @Generated
        protected SmsRequestBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public SmsRequest build() {
            return new SmsRequest(this);
        }
    }

    @Generated
    public static abstract class SmsRequestBuilder<C extends SmsRequest, B extends SmsRequestBuilder<C, B>> {
        @Generated
        private Principal principal;
        @Generated
        private String attribute;
        @Generated
        private String text;
        @Generated
        private String from;
        @Generated
        private String to;

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
        public B text(String text) {
            this.text = text;
            return this.self();
        }

        @Generated
        public B from(String from) {
            this.from = from;
            return this.self();
        }

        @Generated
        public B to(String to) {
            this.to = to;
            return this.self();
        }

        @Generated
        public String toString() {
            return "SmsRequest.SmsRequestBuilder(principal=" + this.principal + ", attribute=" + this.attribute + ", text=" + this.text + ", from=" + this.from + ", to=" + this.to + ")";
        }
    }
}

