/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication.adaptive.intel;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import lombok.Generated;

public class IPAddressIntelligenceResponse
implements Serializable {
    private static final long serialVersionUID = 6438211402312848819L;
    private double score;
    private Map<String, Object> properties;
    private IPAddressIntelligenceStatus status;

    public boolean isBanned() {
        return this.status == IPAddressIntelligenceStatus.BANNED;
    }

    public boolean isRanked() {
        return this.status == IPAddressIntelligenceStatus.RANKED;
    }

    public boolean isAllowed() {
        return this.status == IPAddressIntelligenceStatus.ALLOWED;
    }

    public static IPAddressIntelligenceResponse allowed() {
        return ((IPAddressIntelligenceResponseBuilder)((IPAddressIntelligenceResponseBuilder)IPAddressIntelligenceResponse.builder().status(IPAddressIntelligenceStatus.ALLOWED)).score(IPAddressIntelligenceStatus.ALLOWED.getScore())).build();
    }

    public static IPAddressIntelligenceResponse banned() {
        return ((IPAddressIntelligenceResponseBuilder)((IPAddressIntelligenceResponseBuilder)IPAddressIntelligenceResponse.builder().status(IPAddressIntelligenceStatus.BANNED)).score(IPAddressIntelligenceStatus.BANNED.getScore())).build();
    }

    @Generated
    private static Map<String, Object> $default$properties() {
        return new TreeMap<String, Object>();
    }

    @Generated
    private static IPAddressIntelligenceStatus $default$status() {
        return IPAddressIntelligenceStatus.ALLOWED;
    }

    @Generated
    protected IPAddressIntelligenceResponse(IPAddressIntelligenceResponseBuilder<?, ?> b) {
        this.score = b.score;
        this.properties = b.properties$set ? b.properties$value : IPAddressIntelligenceResponse.$default$properties();
        this.status = b.status$set ? b.status$value : IPAddressIntelligenceResponse.$default$status();
    }

    @Generated
    public static IPAddressIntelligenceResponseBuilder<?, ?> builder() {
        return new IPAddressIntelligenceResponseBuilderImpl();
    }

    @Generated
    public double getScore() {
        return this.score;
    }

    @Generated
    public Map<String, Object> getProperties() {
        return this.properties;
    }

    @Generated
    public IPAddressIntelligenceStatus getStatus() {
        return this.status;
    }

    @Generated
    private static final class IPAddressIntelligenceResponseBuilderImpl
    extends IPAddressIntelligenceResponseBuilder<IPAddressIntelligenceResponse, IPAddressIntelligenceResponseBuilderImpl> {
        @Generated
        private IPAddressIntelligenceResponseBuilderImpl() {
        }

        @Override
        @Generated
        protected IPAddressIntelligenceResponseBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public IPAddressIntelligenceResponse build() {
            return new IPAddressIntelligenceResponse(this);
        }
    }

    @Generated
    public static abstract class IPAddressIntelligenceResponseBuilder<C extends IPAddressIntelligenceResponse, B extends IPAddressIntelligenceResponseBuilder<C, B>> {
        @Generated
        private double score;
        @Generated
        private boolean properties$set;
        @Generated
        private Map<String, Object> properties$value;
        @Generated
        private boolean status$set;
        @Generated
        private IPAddressIntelligenceStatus status$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B score(double score) {
            this.score = score;
            return this.self();
        }

        @Generated
        public B properties(Map<String, Object> properties) {
            this.properties$value = properties;
            this.properties$set = true;
            return this.self();
        }

        @Generated
        public B status(IPAddressIntelligenceStatus status) {
            this.status$value = status;
            this.status$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "IPAddressIntelligenceResponse.IPAddressIntelligenceResponseBuilder(score=" + this.score + ", properties$value=" + this.properties$value + ", status$value=" + this.status$value + ")";
        }
    }

    public static enum IPAddressIntelligenceStatus {
        BANNED(1),
        RANKED(-1),
        ALLOWED(0);

        private final int score;

        @Generated
        private IPAddressIntelligenceStatus(int score) {
            this.score = score;
        }

        @Generated
        public int getScore() {
            return this.score;
        }
    }
}

