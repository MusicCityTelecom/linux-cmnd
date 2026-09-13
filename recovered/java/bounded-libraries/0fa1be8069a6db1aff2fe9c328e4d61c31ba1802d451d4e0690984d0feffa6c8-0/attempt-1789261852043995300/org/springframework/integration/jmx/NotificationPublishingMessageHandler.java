/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.integration.handler.AbstractMessageHandler
 *  org.springframework.integration.mapping.OutboundMessageMapper
 *  org.springframework.integration.support.management.IntegrationManagedResource
 *  org.springframework.jmx.export.MBeanExporter
 *  org.springframework.jmx.export.annotation.ManagedResource
 *  org.springframework.jmx.export.notification.NotificationPublisher
 *  org.springframework.jmx.export.notification.NotificationPublisherAware
 *  org.springframework.jmx.support.ObjectNameManager
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 */
package org.springframework.integration.jmx;

import java.util.Iterator;
import java.util.Map;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.ObjectName;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.jmx.DefaultNotificationMapper;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

public class NotificationPublishingMessageHandler
extends AbstractMessageHandler {
    private final PublisherDelegate delegate = new PublisherDelegate();
    private final ObjectName objectName;
    private String defaultNotificationType;
    private OutboundMessageMapper<Notification> notificationMapper;

    public NotificationPublishingMessageHandler(ObjectName objectName) {
        Assert.notNull((Object)objectName, (String)"JMX ObjectName is required");
        this.objectName = objectName;
    }

    public NotificationPublishingMessageHandler(String objectName) {
        Assert.notNull((Object)objectName, (String)"JMX ObjectName is required");
        try {
            this.objectName = ObjectNameManager.getInstance((String)objectName);
        }
        catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setNotificationMapper(@Nullable OutboundMessageMapper<Notification> notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    public void setDefaultNotificationType(String defaultNotificationType) {
        this.defaultNotificationType = defaultNotificationType;
    }

    public String getComponentType() {
        return "jmx:notification-publishing-channel-adapter";
    }

    public final void onInit() {
        MBeanExporter exp;
        BeanFactory beanFactory = this.getBeanFactory();
        Assert.isTrue((boolean)(beanFactory instanceof ListableBeanFactory), (String)"A ListableBeanFactory is required.");
        Map exporters = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)((ListableBeanFactory)beanFactory), MBeanExporter.class);
        Assert.state((exporters.size() > 0 ? 1 : 0) != 0, (String)"No MBeanExporter is available in the current context.");
        MBeanExporter exporter = null;
        Iterator iterator = exporters.values().iterator();
        while (iterator.hasNext() && !((exporter = (exp = (MBeanExporter)iterator.next())) instanceof IntegrationMBeanExporter)) {
        }
        if (this.notificationMapper == null) {
            this.notificationMapper = new DefaultNotificationMapper(this.objectName, this.defaultNotificationType);
        }
        if (exporter != null) {
            exporter.registerManagedResource((Object)this.delegate, this.objectName);
            this.logger.info(() -> "Registered JMX notification publisher as MBean with ObjectName: " + this.objectName);
        }
    }

    protected void handleMessageInternal(Message<?> message) {
        this.delegate.publish((Notification)this.notificationMapper.fromMessage(message));
    }

    @ManagedResource
    @IntegrationManagedResource
    public static class PublisherDelegate
    implements NotificationPublisherAware {
        private NotificationPublisher notificationPublisher;

        public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
            this.notificationPublisher = notificationPublisher;
        }

        private void publish(Notification notification) {
            Assert.state((this.notificationPublisher != null ? 1 : 0) != 0, (String)"NotificationPublisher must not be null.");
            this.notificationPublisher.sendNotification(notification);
        }
    }
}

