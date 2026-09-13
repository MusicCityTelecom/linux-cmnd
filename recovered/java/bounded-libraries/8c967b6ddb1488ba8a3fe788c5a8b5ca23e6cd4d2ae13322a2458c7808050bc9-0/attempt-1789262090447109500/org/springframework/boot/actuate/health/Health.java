/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.health;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.Assert;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public final class Health
extends HealthComponent {
    private final Status status;
    private final Map<String, Object> details;

    private Health(Builder builder) {
        Assert.notNull((Object)builder, (String)"Builder must not be null");
        this.status = builder.status;
        this.details = Collections.unmodifiableMap(builder.details);
    }

    Health(Status status, Map<String, Object> details) {
        this.status = status;
        this.details = details;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @JsonInclude(value=JsonInclude.Include.NON_EMPTY)
    public Map<String, Object> getDetails() {
        return this.details;
    }

    Health withoutDetails() {
        if (this.details.isEmpty()) {
            return this;
        }
        return Health.status(this.getStatus()).build();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Health) {
            Health other = (Health)obj;
            return this.status.equals(other.status) && this.details.equals(other.details);
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.status.hashCode();
        return 13 * hashCode + this.details.hashCode();
    }

    public String toString() {
        return this.getStatus() + " " + this.getDetails();
    }

    public static Builder unknown() {
        return Health.status(Status.UNKNOWN);
    }

    public static Builder up() {
        return Health.status(Status.UP);
    }

    public static Builder down(Exception ex) {
        return Health.down().withException(ex);
    }

    public static Builder down() {
        return Health.status(Status.DOWN);
    }

    public static Builder outOfService() {
        return Health.status(Status.OUT_OF_SERVICE);
    }

    public static Builder status(String statusCode) {
        return Health.status(new Status(statusCode));
    }

    public static Builder status(Status status) {
        return new Builder(status);
    }

    public static class Builder {
        private Status status;
        private Map<String, Object> details;
        private Throwable exception;

        public Builder() {
            this.status = Status.UNKNOWN;
            this.details = new LinkedHashMap<String, Object>();
        }

        public Builder(Status status) {
            Assert.notNull((Object)status, (String)"Status must not be null");
            this.status = status;
            this.details = new LinkedHashMap<String, Object>();
        }

        public Builder(Status status, Map<String, ?> details) {
            Assert.notNull((Object)status, (String)"Status must not be null");
            Assert.notNull(details, (String)"Details must not be null");
            this.status = status;
            this.details = new LinkedHashMap(details);
        }

        public Builder withException(Throwable ex) {
            Assert.notNull((Object)ex, (String)"Exception must not be null");
            this.exception = ex;
            return this.withDetail("error", ex.getClass().getName() + ": " + ex.getMessage());
        }

        public Builder withDetail(String key, Object value) {
            Assert.notNull((Object)key, (String)"Key must not be null");
            Assert.notNull((Object)value, (String)"Value must not be null");
            this.details.put(key, value);
            return this;
        }

        public Builder withDetails(Map<String, ?> details) {
            Assert.notNull(details, (String)"Details must not be null");
            this.details.putAll(details);
            return this;
        }

        public Builder unknown() {
            return this.status(Status.UNKNOWN);
        }

        public Builder up() {
            return this.status(Status.UP);
        }

        public Builder down(Throwable ex) {
            return this.down().withException(ex);
        }

        public Builder down() {
            return this.status(Status.DOWN);
        }

        public Builder outOfService() {
            return this.status(Status.OUT_OF_SERVICE);
        }

        public Builder status(String statusCode) {
            return this.status(new Status(statusCode));
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Health build() {
            return new Health(this);
        }

        Throwable getException() {
            return this.exception;
        }
    }
}

