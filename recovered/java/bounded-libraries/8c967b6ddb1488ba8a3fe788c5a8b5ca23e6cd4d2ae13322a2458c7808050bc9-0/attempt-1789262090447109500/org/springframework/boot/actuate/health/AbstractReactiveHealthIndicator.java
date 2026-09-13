/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.health;

import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

public abstract class AbstractReactiveHealthIndicator
implements ReactiveHealthIndicator {
    private static final String NO_MESSAGE = null;
    private static final String DEFAULT_MESSAGE = "Health check failed";
    private final Log logger = LogFactory.getLog(this.getClass());
    private final Function<Throwable, String> healthCheckFailedMessage;

    protected AbstractReactiveHealthIndicator() {
        this(NO_MESSAGE);
    }

    protected AbstractReactiveHealthIndicator(String healthCheckFailedMessage) {
        this.healthCheckFailedMessage = ex -> healthCheckFailedMessage;
    }

    protected AbstractReactiveHealthIndicator(Function<Throwable, String> healthCheckFailedMessage) {
        Assert.notNull(healthCheckFailedMessage, (String)"HealthCheckFailedMessage must not be null");
        this.healthCheckFailedMessage = healthCheckFailedMessage;
    }

    @Override
    public final Mono<Health> health() {
        try {
            return this.doHealthCheck(new Health.Builder()).onErrorResume(this::handleFailure);
        }
        catch (Exception ex) {
            return this.handleFailure(ex);
        }
    }

    private Mono<Health> handleFailure(Throwable ex) {
        if (this.logger.isWarnEnabled()) {
            String message = this.healthCheckFailedMessage.apply(ex);
            this.logger.warn((Object)(StringUtils.hasText((String)message) ? message : DEFAULT_MESSAGE), ex);
        }
        return Mono.just((Object)new Health.Builder().down(ex).build());
    }

    protected abstract Mono<Health> doHealthCheck(Health.Builder var1);
}

