/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.notifications.mail;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class EmailCommunicationResult
implements Serializable {
    private static final long serialVersionUID = -8625548429667623291L;
    private final List<String> to;
    private final boolean success;
    private final String body;

    @Generated
    protected EmailCommunicationResult(EmailCommunicationResultBuilder<?, ?> b) {
        this.to = b.to;
        this.success = b.success;
        this.body = b.body;
    }

    @Generated
    public static EmailCommunicationResultBuilder<?, ?> builder() {
        return new EmailCommunicationResultBuilderImpl();
    }

    @Generated
    public List<String> getTo() {
        return this.to;
    }

    @Generated
    public boolean isSuccess() {
        return this.success;
    }

    @Generated
    public String getBody() {
        return this.body;
    }

    @Generated
    private static final class EmailCommunicationResultBuilderImpl
    extends EmailCommunicationResultBuilder<EmailCommunicationResult, EmailCommunicationResultBuilderImpl> {
        @Generated
        private EmailCommunicationResultBuilderImpl() {
        }

        @Override
        @Generated
        protected EmailCommunicationResultBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public EmailCommunicationResult build() {
            return new EmailCommunicationResult(this);
        }
    }

    @Generated
    public static abstract class EmailCommunicationResultBuilder<C extends EmailCommunicationResult, B extends EmailCommunicationResultBuilder<C, B>> {
        @Generated
        private List<String> to;
        @Generated
        private boolean success;
        @Generated
        private String body;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B to(List<String> to) {
            this.to = to;
            return this.self();
        }

        @Generated
        public B success(boolean success) {
            this.success = success;
            return this.self();
        }

        @Generated
        public B body(String body) {
            this.body = body;
            return this.self();
        }

        @Generated
        public String toString() {
            return "EmailCommunicationResult.EmailCommunicationResultBuilder(to=" + this.to + ", success=" + this.success + ", body=" + this.body + ")";
        }
    }
}

