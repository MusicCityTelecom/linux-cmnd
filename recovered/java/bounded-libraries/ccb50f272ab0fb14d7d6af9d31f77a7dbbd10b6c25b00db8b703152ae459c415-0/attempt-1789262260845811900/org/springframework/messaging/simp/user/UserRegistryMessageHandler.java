/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationListener
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp.user;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.messaging.simp.user.MultiServerUserRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

public class UserRegistryMessageHandler
implements MessageHandler,
ApplicationListener<BrokerAvailabilityEvent> {
    private final MultiServerUserRegistry userRegistry;
    private final SimpMessagingTemplate brokerTemplate;
    private final String broadcastDestination;
    private final TaskScheduler scheduler;
    private final UserRegistryTask schedulerTask = new UserRegistryTask();
    @Nullable
    private volatile ScheduledFuture<?> scheduledFuture;
    private long registryExpirationPeriod = TimeUnit.SECONDS.toMillis(20L);

    public UserRegistryMessageHandler(MultiServerUserRegistry userRegistry, SimpMessagingTemplate brokerTemplate, String broadcastDestination, TaskScheduler scheduler) {
        Assert.notNull((Object)userRegistry, (String)"'userRegistry' is required");
        Assert.notNull((Object)brokerTemplate, (String)"'brokerTemplate' is required");
        Assert.hasText((String)broadcastDestination, (String)"'broadcastDestination' is required");
        Assert.notNull((Object)scheduler, (String)"'scheduler' is required");
        this.userRegistry = userRegistry;
        this.brokerTemplate = brokerTemplate;
        this.broadcastDestination = broadcastDestination;
        this.scheduler = scheduler;
    }

    public String getBroadcastDestination() {
        return this.broadcastDestination;
    }

    public void setRegistryExpirationPeriod(long milliseconds) {
        this.registryExpirationPeriod = milliseconds;
    }

    public long getRegistryExpirationPeriod() {
        return this.registryExpirationPeriod;
    }

    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        if (event.isBrokerAvailable()) {
            long delay = this.getRegistryExpirationPeriod() / 2L;
            this.scheduledFuture = this.scheduler.scheduleWithFixedDelay((Runnable)this.schedulerTask, delay);
        } else {
            ScheduledFuture<?> future = this.scheduledFuture;
            if (future != null) {
                future.cancel(true);
                this.scheduledFuture = null;
            }
        }
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageConverter converter = this.brokerTemplate.getMessageConverter();
        this.userRegistry.addRemoteRegistryDto(message, converter, this.getRegistryExpirationPeriod());
    }

    private class UserRegistryTask
    implements Runnable {
        private UserRegistryTask() {
        }

        @Override
        public void run() {
            try {
                SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                accessor.setHeader("simpIgnoreError", true);
                accessor.setLeaveMutable(true);
                Object payload = UserRegistryMessageHandler.this.userRegistry.getLocalRegistryDto();
                UserRegistryMessageHandler.this.brokerTemplate.convertAndSend(UserRegistryMessageHandler.this.getBroadcastDestination(), payload, accessor.getMessageHeaders());
            }
            finally {
                UserRegistryMessageHandler.this.userRegistry.purgeExpiredRegistries();
            }
        }
    }
}

