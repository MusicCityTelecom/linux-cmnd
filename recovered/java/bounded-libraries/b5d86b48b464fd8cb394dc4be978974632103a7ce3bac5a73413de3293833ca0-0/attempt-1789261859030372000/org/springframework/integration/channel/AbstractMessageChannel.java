/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.OrderComparator
 *  org.springframework.core.log.LogAccessor
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.messaging.MessageDeliveryException
 *  org.springframework.messaging.converter.MessageConverter
 *  org.springframework.messaging.support.ChannelInterceptor
 *  org.springframework.messaging.support.InterceptableChannel
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.channel;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.OrderComparator;
import org.springframework.core.log.LogAccessor;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.history.MessageHistory;
import org.springframework.integration.support.management.IntegrationManagedResource;
import org.springframework.integration.support.management.IntegrationManagement;
import org.springframework.integration.support.management.TrackableComponent;
import org.springframework.integration.support.management.metrics.MeterFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.integration.support.management.metrics.SampleFacade;
import org.springframework.integration.support.management.metrics.TimerFacade;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.InterceptableChannel;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@IntegrationManagedResource
public abstract class AbstractMessageChannel
extends IntegrationObjectSupport
implements MessageChannel,
TrackableComponent,
InterceptableChannel,
IntegrationManagement,
IntegrationPattern {
    protected final ChannelInterceptorList interceptors;
    private final Comparator<Object> orderComparator;
    private final IntegrationManagement.ManagementOverrides managementOverrides;
    protected final Set<MeterFacade> meters;
    private volatile boolean shouldTrack;
    private volatile Class<?>[] datatypes;
    private volatile String fullChannelName;
    private volatile MessageConverter messageConverter;
    private volatile boolean loggingEnabled;
    private MetricsCaptor metricsCaptor;
    private TimerFacade successTimer;
    private TimerFacade failureTimer;

    public AbstractMessageChannel() {
        this.interceptors = new ChannelInterceptorList(this.logger);
        this.orderComparator = new OrderComparator();
        this.managementOverrides = new IntegrationManagement.ManagementOverrides();
        this.meters = ConcurrentHashMap.newKeySet();
        this.shouldTrack = false;
        this.datatypes = new Class[0];
        this.loggingEnabled = true;
    }

    @Override
    public String getComponentType() {
        return "channel";
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.message_channel;
    }

    @Override
    public void setShouldTrack(boolean shouldTrack) {
        this.shouldTrack = shouldTrack;
    }

    @Override
    public void registerMetricsCaptor(MetricsCaptor metricsCaptorToRegister) {
        this.metricsCaptor = metricsCaptorToRegister;
    }

    @Nullable
    protected MetricsCaptor getMetricsCaptor() {
        return this.metricsCaptor;
    }

    @Override
    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    @Override
    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
        this.managementOverrides.loggingConfigured = true;
    }

    public void setDatatypes(Class<?> ... datatypes) {
        this.datatypes = datatypes != null && datatypes.length > 0 ? datatypes : new Class[]{};
    }

    public void setInterceptors(List<ChannelInterceptor> interceptors) {
        interceptors.sort(this.orderComparator);
        this.interceptors.set(interceptors);
    }

    public void addInterceptor(ChannelInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }

    public void addInterceptor(int index, ChannelInterceptor interceptor) {
        this.interceptors.add(index, interceptor);
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public List<ChannelInterceptor> getInterceptors() {
        return this.interceptors.getInterceptors();
    }

    public boolean removeInterceptor(ChannelInterceptor interceptor) {
        return this.interceptors.remove(interceptor);
    }

    @Nullable
    public ChannelInterceptor removeInterceptor(int index) {
        return this.interceptors.remove(index);
    }

    protected ChannelInterceptorList getIChannelInterceptorList() {
        return this.interceptors;
    }

    @Override
    public IntegrationManagement.ManagementOverrides getOverrides() {
        return this.managementOverrides;
    }

    @Override
    protected void onInit() {
        BeanFactory beanFactory;
        super.onInit();
        if (this.messageConverter == null && (beanFactory = this.getBeanFactory()) != null && beanFactory.containsBean("datatypeChannelMessageConverter")) {
            this.messageConverter = (MessageConverter)beanFactory.getBean("datatypeChannelMessageConverter", MessageConverter.class);
        }
        this.fullChannelName = null;
    }

    public String getFullChannelName() {
        if (this.fullChannelName == null) {
            String contextId = this.getApplicationContextId();
            String componentName = this.getComponentName();
            this.fullChannelName = componentName = (StringUtils.hasText((String)contextId) ? contextId + "." : "") + (StringUtils.hasText((String)componentName) ? componentName : "unknown.channel.name");
        }
        return this.fullChannelName;
    }

    public boolean send(Message<?> message) {
        return this.send(message, -1L);
    }

    public boolean send(Message<?> messageArg, long timeout) {
        Assert.notNull(messageArg, (String)"message must not be null");
        Assert.notNull((Object)messageArg.getPayload(), (String)"message payload must not be null");
        Message<?> message = messageArg;
        if (this.shouldTrack) {
            message = MessageHistory.write(message, this, this.getMessageBuilderFactory());
        }
        ArrayDeque<ChannelInterceptor> interceptorStack = null;
        boolean sent = false;
        boolean metricsProcessed = false;
        ChannelInterceptorList interceptorList = this.interceptors;
        SampleFacade sample = null;
        try {
            boolean debugEnabled;
            message = this.convertPayloadIfNecessary(message);
            boolean bl = debugEnabled = this.loggingEnabled && this.logger.isDebugEnabled();
            if (debugEnabled) {
                this.logger.debug((CharSequence)("preSend on channel '" + this + "', message: " + message));
            }
            if (interceptorList.getSize() > 0 && (message = interceptorList.preSend(message, this, interceptorStack = new ArrayDeque<ChannelInterceptor>())) == null) {
                return false;
            }
            if (this.metricsCaptor != null) {
                sample = this.metricsCaptor.start();
            }
            sent = this.doSend(message, timeout);
            if (sample != null) {
                sample.stop(this.sendTimer(sent));
            }
            metricsProcessed = true;
            if (debugEnabled) {
                this.logger.debug((CharSequence)("postSend (sent=" + sent + ") on channel '" + this + "', message: " + message));
            }
            if (interceptorStack != null) {
                interceptorList.postSend(message, this, sent);
                interceptorList.afterSendCompletion(message, this, sent, null, interceptorStack);
            }
            return sent;
        }
        catch (Exception ex) {
            if (!metricsProcessed && sample != null) {
                sample.stop(this.buildSendTimer(false, ex.getClass().getSimpleName()));
            }
            if (interceptorStack != null) {
                interceptorList.afterSendCompletion(message, this, sent, ex, interceptorStack);
            }
            throw IntegrationUtils.wrapInDeliveryExceptionIfNecessary(message, () -> "failed to send Message to channel '" + this.getComponentName() + "'", ex);
        }
    }

    private TimerFacade sendTimer(boolean sent) {
        if (sent) {
            if (this.successTimer == null) {
                this.successTimer = this.buildSendTimer(true, "none");
            }
            return this.successTimer;
        }
        if (this.failureTimer == null) {
            this.failureTimer = this.buildSendTimer(false, "none");
        }
        return this.failureTimer;
    }

    private TimerFacade buildSendTimer(boolean success, String exception) {
        TimerFacade timer = this.metricsCaptor.timerBuilder("spring.integration.send").tag("type", "channel").tag("name", this.getComponentName() == null ? "unknown" : this.getComponentName()).tag("result", success ? "success" : "failure").tag("exception", exception).description("Send processing time").build();
        this.meters.add(timer);
        return timer;
    }

    private Message<?> convertPayloadIfNecessary(Message<?> message) {
        if (this.datatypes.length > 0) {
            for (Class<?> datatype : this.datatypes) {
                if (!datatype.isAssignableFrom(message.getPayload().getClass())) continue;
                return message;
            }
            if (this.messageConverter != null) {
                for (Class<?> datatype : this.datatypes) {
                    Object converted = this.messageConverter.fromMessage(message, datatype);
                    if (converted == null) continue;
                    if (converted instanceof Message) {
                        return (Message)converted;
                    }
                    return this.getMessageBuilderFactory().withPayload(converted).copyHeaders((Map<String, ?>)message.getHeaders()).build();
                }
            }
            throw new MessageDeliveryException(message, "Channel '" + this.getComponentName() + "' expected one of the following data types [" + StringUtils.arrayToCommaDelimitedString((Object[])this.datatypes) + "], but received [" + message.getPayload().getClass() + "]");
        }
        return message;
    }

    protected abstract boolean doSend(Message<?> var1, long var2);

    @Override
    public void destroy() {
        this.meters.forEach(MeterFacade::remove);
        this.meters.clear();
    }

    protected static class ChannelInterceptorList {
        protected final List<ChannelInterceptor> interceptors = new CopyOnWriteArrayList<ChannelInterceptor>();
        private final LogAccessor logger;
        private int size;

        public ChannelInterceptorList(LogAccessor logger) {
            this.logger = logger;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean set(List<ChannelInterceptor> interceptors) {
            List<ChannelInterceptor> list = this.interceptors;
            synchronized (list) {
                this.interceptors.clear();
                this.size = interceptors.size();
                return this.interceptors.addAll(interceptors);
            }
        }

        public int getSize() {
            return this.size;
        }

        public boolean add(ChannelInterceptor interceptor) {
            ++this.size;
            return this.interceptors.add(interceptor);
        }

        public void add(int index, ChannelInterceptor interceptor) {
            ++this.size;
            this.interceptors.add(index, interceptor);
        }

        @Nullable
        public Message<?> preSend(Message<?> messageArg, MessageChannel channel, Deque<ChannelInterceptor> interceptorStack) {
            Message message = messageArg;
            if (this.size > 0) {
                for (ChannelInterceptor interceptor : this.interceptors) {
                    Message previous = message;
                    if ((message = interceptor.preSend(message, channel)) == null) {
                        this.logger.debug(() -> interceptor.getClass().getSimpleName() + " returned null from preSend, i.e. precluding the send.");
                        this.afterSendCompletion(previous, channel, false, null, interceptorStack);
                        return null;
                    }
                    interceptorStack.add(interceptor);
                }
            }
            return message;
        }

        public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
            if (this.size > 0) {
                for (ChannelInterceptor interceptor : this.interceptors) {
                    interceptor.postSend(message, channel, sent);
                }
            }
        }

        public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex, Deque<ChannelInterceptor> interceptorStack) {
            Iterator<ChannelInterceptor> iterator = interceptorStack.descendingIterator();
            while (iterator.hasNext()) {
                ChannelInterceptor interceptor = iterator.next();
                try {
                    interceptor.afterSendCompletion(message, channel, sent, ex);
                }
                catch (Exception ex2) {
                    this.logger.error((Throwable)ex2, () -> "Exception from afterSendCompletion in " + interceptor);
                }
            }
        }

        public boolean preReceive(MessageChannel channel, Deque<ChannelInterceptor> interceptorStack) {
            if (this.size > 0) {
                for (ChannelInterceptor interceptor : this.interceptors) {
                    if (!interceptor.preReceive(channel)) {
                        this.afterReceiveCompletion(null, channel, null, interceptorStack);
                        return false;
                    }
                    interceptorStack.add(interceptor);
                }
            }
            return true;
        }

        @Nullable
        public Message<?> postReceive(Message<?> messageArg, MessageChannel channel) {
            Message message = messageArg;
            if (this.size > 0) {
                for (ChannelInterceptor interceptor : this.interceptors) {
                    message = interceptor.postReceive(message, channel);
                    if (message != null) continue;
                    return null;
                }
            }
            return message;
        }

        public void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex, @Nullable Deque<ChannelInterceptor> interceptorStack) {
            if (interceptorStack != null) {
                Iterator<ChannelInterceptor> iter = interceptorStack.descendingIterator();
                while (iter.hasNext()) {
                    ChannelInterceptor interceptor = iter.next();
                    try {
                        interceptor.afterReceiveCompletion(message, channel, ex);
                    }
                    catch (Exception ex2) {
                        this.logger.error((Throwable)ex2, () -> "Exception from afterReceiveCompletion in " + interceptor);
                    }
                }
            }
        }

        public List<ChannelInterceptor> getInterceptors() {
            return Collections.unmodifiableList(this.interceptors);
        }

        public boolean remove(ChannelInterceptor interceptor) {
            if (this.interceptors.remove(interceptor)) {
                --this.size;
                return true;
            }
            return false;
        }

        @Nullable
        public ChannelInterceptor remove(int index) {
            ChannelInterceptor removed = this.interceptors.remove(index);
            if (removed != null) {
                --this.size;
            }
            return removed;
        }
    }
}

