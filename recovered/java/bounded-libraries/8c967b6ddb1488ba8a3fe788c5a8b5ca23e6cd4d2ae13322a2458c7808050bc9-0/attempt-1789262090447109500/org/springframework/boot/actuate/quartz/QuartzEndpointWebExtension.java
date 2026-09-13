/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.SchedulerException
 */
package org.springframework.boot.actuate.quartz;

import org.quartz.SchedulerException;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.quartz.QuartzEndpoint;

@EndpointWebExtension(endpoint=QuartzEndpoint.class)
public class QuartzEndpointWebExtension {
    private final QuartzEndpoint delegate;

    public QuartzEndpointWebExtension(QuartzEndpoint delegate) {
        this.delegate = delegate;
    }

    @ReadOperation
    public WebEndpointResponse<QuartzEndpoint.QuartzGroups> quartzJobOrTriggerGroups(@Selector String jobsOrTriggers) throws SchedulerException {
        return this.handle(jobsOrTriggers, this.delegate::quartzJobGroups, this.delegate::quartzTriggerGroups);
    }

    @ReadOperation
    public WebEndpointResponse<Object> quartzJobOrTriggerGroup(@Selector String jobsOrTriggers, @Selector String group) throws SchedulerException {
        return this.handle(jobsOrTriggers, () -> this.delegate.quartzJobGroupSummary(group), () -> this.delegate.quartzTriggerGroupSummary(group));
    }

    @ReadOperation
    public WebEndpointResponse<Object> quartzJobOrTrigger(@Selector String jobsOrTriggers, @Selector String group, @Selector String name) throws SchedulerException {
        return this.handle(jobsOrTriggers, () -> this.delegate.quartzJob(group, name), () -> this.delegate.quartzTrigger(group, name));
    }

    private <T> WebEndpointResponse<T> handle(String jobsOrTriggers, ResponseSupplier<T> jobAction, ResponseSupplier<T> triggerAction) throws SchedulerException {
        if ("jobs".equals(jobsOrTriggers)) {
            return this.handleNull(jobAction.get());
        }
        if ("triggers".equals(jobsOrTriggers)) {
            return this.handleNull(triggerAction.get());
        }
        return new WebEndpointResponse<int>(400);
    }

    private <T> WebEndpointResponse<T> handleNull(T value) {
        if (value != null) {
            return new WebEndpointResponse<T>(value);
        }
        return new WebEndpointResponse<int>(404);
    }

    @FunctionalInterface
    private static interface ResponseSupplier<T> {
        public T get() throws SchedulerException;
    }
}

