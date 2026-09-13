/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.logout.slo;

import java.io.Serializable;
import lombok.Generated;

public class SingleLogoutMessage<T>
implements Serializable {
    private static final long serialVersionUID = -7763669015027355811L;
    private final String payload;
    private final transient T message;

    @Generated
    protected SingleLogoutMessage(SingleLogoutMessageBuilder<T, ?, ?> b) {
        this.payload = b.payload;
        this.message = b.message;
    }

    @Generated
    public static <T> SingleLogoutMessageBuilder<T, ?, ?> builder() {
        return new SingleLogoutMessageBuilderImpl();
    }

    @Generated
    public String getPayload() {
        return this.payload;
    }

    @Generated
    public T getMessage() {
        return this.message;
    }

    @Generated
    public String toString() {
        return "SingleLogoutMessage(payload=" + this.payload + ")";
    }

    @Generated
    private static final class SingleLogoutMessageBuilderImpl<T>
    extends SingleLogoutMessageBuilder<T, SingleLogoutMessage<T>, SingleLogoutMessageBuilderImpl<T>> {
        @Generated
        private SingleLogoutMessageBuilderImpl() {
        }

        @Override
        @Generated
        protected SingleLogoutMessageBuilderImpl<T> self() {
            return this;
        }

        @Override
        @Generated
        public SingleLogoutMessage<T> build() {
            return new SingleLogoutMessage(this);
        }
    }

    @Generated
    public static abstract class SingleLogoutMessageBuilder<T, C extends SingleLogoutMessage<T>, B extends SingleLogoutMessageBuilder<T, C, B>> {
        @Generated
        private String payload;
        @Generated
        private T message;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B payload(String payload) {
            this.payload = payload;
            return this.self();
        }

        @Generated
        public B message(T message) {
            this.message = message;
            return this.self();
        }

        @Generated
        public String toString() {
            return "SingleLogoutMessage.SingleLogoutMessageBuilder(payload=" + this.payload + ", message=" + this.message + ")";
        }
    }
}

