/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.health;

import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class AbstractHealthIndicator
implements HealthIndicator {
    private static final String NO_MESSAGE = null;
    private static final String DEFAULT_MESSAGE = "Health check failed";
    private final Log logger = LogFactory.getLog(this.getClass());
    private final Function<Exception, String> healthCheckFailedMessage;

    protected AbstractHealthIndicator() {
        this(NO_MESSAGE);
    }

    protected AbstractHealthIndicator(String healthCheckFailedMessage) {
        this.healthCheckFailedMessage = ex -> healthCheckFailedMessage;
    }

    protected AbstractHealthIndicator(Function<Exception, String> healthCheckFailedMessage) {
        Assert.notNull(healthCheckFailedMessage, (String)"HealthCheckFailedMessage must not be null");
        this.healthCheckFailedMessage = healthCheckFailedMessage;
    }

    @Override
    public final Health health() {
        Health.Builder builder = new Health.Builder();
        try {
            this.doHealthCheck(builder);
        }
        catch (Exception ex) {
            builder.down(ex);
        }
        this.logExceptionIfPresent(builder.getException());
        return builder.build();
    }

    private void logExceptionIfPresent(Throwable ex) {
        if (ex != null && this.logger.isWarnEnabled()) {
            String message = ex instanceof Exception ? this.healthCheckFailedMessage.apply((Exception)ex) : null;
            this.logger.warn((Object)(StringUtils.hasText((String)message) ? message : DEFAULT_MESSAGE), ex);
        }
    }

    protected abstract void doHealthCheck(Health.Builder var1) throws Exception;
}

