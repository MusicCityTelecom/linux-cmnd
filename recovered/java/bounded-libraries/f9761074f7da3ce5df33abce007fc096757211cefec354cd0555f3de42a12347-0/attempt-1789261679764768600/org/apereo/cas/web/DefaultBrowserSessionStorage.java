/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 *  org.apereo.cas.web.BrowserSessionStorage
 */
package org.apereo.cas.web;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Generated;
import org.apereo.cas.web.BrowserSessionStorage;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class DefaultBrowserSessionStorage
implements BrowserSessionStorage {
    private static final long serialVersionUID = 775566570310426414L;
    private final String payload;
    private String destinationUrl;

    @Generated
    protected DefaultBrowserSessionStorage(DefaultBrowserSessionStorageBuilder<?, ?> b) {
        this.payload = b.payload;
        this.destinationUrl = b.destinationUrl;
    }

    @Generated
    public static DefaultBrowserSessionStorageBuilder<?, ?> builder() {
        return new DefaultBrowserSessionStorageBuilderImpl();
    }

    @Generated
    public String getPayload() {
        return this.payload;
    }

    @Generated
    public String getDestinationUrl() {
        return this.destinationUrl;
    }

    @Generated
    public String toString() {
        return "DefaultBrowserSessionStorage(payload=" + this.payload + ", destinationUrl=" + this.destinationUrl + ")";
    }

    @Generated
    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    @Generated
    public DefaultBrowserSessionStorage() {
        this.payload = null;
    }

    @Generated
    private static final class DefaultBrowserSessionStorageBuilderImpl
    extends DefaultBrowserSessionStorageBuilder<DefaultBrowserSessionStorage, DefaultBrowserSessionStorageBuilderImpl> {
        @Generated
        private DefaultBrowserSessionStorageBuilderImpl() {
        }

        @Override
        @Generated
        protected DefaultBrowserSessionStorageBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public DefaultBrowserSessionStorage build() {
            return new DefaultBrowserSessionStorage(this);
        }
    }

    @Generated
    public static abstract class DefaultBrowserSessionStorageBuilder<C extends DefaultBrowserSessionStorage, B extends DefaultBrowserSessionStorageBuilder<C, B>> {
        @Generated
        private String payload;
        @Generated
        private String destinationUrl;

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
        public B destinationUrl(String destinationUrl) {
            this.destinationUrl = destinationUrl;
            return this.self();
        }

        @Generated
        public String toString() {
            return "DefaultBrowserSessionStorage.DefaultBrowserSessionStorageBuilder(payload=" + this.payload + ", destinationUrl=" + this.destinationUrl + ")";
        }
    }
}

