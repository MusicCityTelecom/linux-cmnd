/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationListener
 *  org.springframework.context.event.ContextRefreshedEvent
 *  org.springframework.integration.endpoint.MessageProducerSupport
 *  org.springframework.integration.support.AbstractIntegrationMessageBuilder
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class NotificationListeningMessageProducer
extends MessageProducerSupport
implements NotificationListener,
ApplicationListener<ContextRefreshedEvent> {
    private final AtomicBoolean listenerRegisteredOnStartup = new AtomicBoolean();
    private MBeanServerConnection server;
    private ObjectName[] mBeanObjectNames;
    private NotificationFilter filter;
    private Object handback;

    public void setServer(MBeanServerConnection server) {
        this.server = server;
    }

    public void setObjectName(ObjectName ... objectNames) {
        Assert.notEmpty((Object[])objectNames, (String)"'objectNames' must contain at least one ObjectName");
        this.mBeanObjectNames = Arrays.copyOf(objectNames, objectNames.length);
    }

    public void setFilter(NotificationFilter filter) {
        this.filter = filter;
    }

    public void setHandback(Object handback) {
        this.handback = handback;
    }

    @Override
    public void handleNotification(Notification notification, Object handback) {
        this.logger.info(() -> "received notification: " + notification + ", and handback: " + handback);
        AbstractIntegrationMessageBuilder builder = this.getMessageBuilderFactory().withPayload((Object)notification);
        if (handback != null) {
            builder.setHeader("jmx_notificationHandback", handback);
        }
        Message message = builder.build();
        this.sendMessage(message);
    }

    public String getComponentType() {
        return "jmx:notification-listening-channel-adapter";
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!this.listenerRegisteredOnStartup.getAndSet(true) && this.isAutoStartup()) {
            this.doStart();
        }
    }

    protected void doStart() {
        if (!this.listenerRegisteredOnStartup.get()) {
            return;
        }
        this.logger.debug((CharSequence)"Registering to receive notifications");
        Assert.notNull((Object)this.server, (String)"MBeanServer is required.");
        Assert.notNull((Object)this.mBeanObjectNames, (String)"An ObjectName is required.");
        try {
            Collection<ObjectName> objectNames = this.retrieveMBeanNames();
            if (objectNames.size() < 1) {
                this.logger.error(() -> "No MBeans found matching ObjectName pattern(s): " + Arrays.toString(this.mBeanObjectNames));
            }
            for (ObjectName objectName : objectNames) {
                this.server.addNotificationListener(objectName, this, this.filter, this.handback);
            }
        }
        catch (InstanceNotFoundException e) {
            throw new IllegalStateException("Failed to find MBean instance.", e);
        }
        catch (IOException e) {
            throw new IllegalStateException("IOException on MBeanServerConnection.", e);
        }
    }

    protected void doStop() {
        this.logger.debug((CharSequence)"Unregistering notifications");
        if (this.server != null && this.mBeanObjectNames != null) {
            Collection<ObjectName> objectNames = this.retrieveMBeanNames();
            for (ObjectName objectName : objectNames) {
                try {
                    this.server.removeNotificationListener(objectName, this, this.filter, this.handback);
                }
                catch (InstanceNotFoundException ex) {
                    this.logger.error((Throwable)ex, (CharSequence)"Failed to find MBean instance.");
                }
                catch (ListenerNotFoundException ex) {
                    this.logger.error((Throwable)ex, (CharSequence)"Failed to find NotificationListener.");
                }
                catch (IOException ex) {
                    this.logger.error((Throwable)ex, (CharSequence)"IOException on MBeanServerConnection.");
                }
            }
        }
    }

    protected Collection<ObjectName> retrieveMBeanNames() {
        ArrayList<ObjectName> objectNames = new ArrayList<ObjectName>();
        for (ObjectName pattern : this.mBeanObjectNames) {
            Set<ObjectInstance> mBeanInfos;
            try {
                mBeanInfos = this.server.queryMBeans(pattern, null);
            }
            catch (IOException e) {
                throw new IllegalStateException("IOException on MBeanServerConnection.", e);
            }
            if (mBeanInfos.size() == 0) {
                this.logger.debug(() -> "No MBeans found matching pattern: " + pattern);
            }
            for (ObjectInstance instance : mBeanInfos) {
                this.logger.debug(() -> "Found MBean: " + instance.getObjectName().toString());
                objectNames.add(instance.getObjectName());
            }
        }
        return objectNames;
    }
}

