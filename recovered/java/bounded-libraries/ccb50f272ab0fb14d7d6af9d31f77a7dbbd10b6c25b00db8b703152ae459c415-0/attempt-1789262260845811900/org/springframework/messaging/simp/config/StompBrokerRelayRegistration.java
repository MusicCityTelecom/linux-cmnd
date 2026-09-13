/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.TaskScheduler
 *  org.springframework.util.Assert
 */
package org.springframework.messaging.simp.config;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.simp.config.AbstractBrokerRegistration;
import org.springframework.messaging.simp.stomp.StompBrokerRelayMessageHandler;
import org.springframework.messaging.tcp.TcpOperations;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

public class StompBrokerRelayRegistration
extends AbstractBrokerRegistration {
    private String relayHost = "127.0.0.1";
    private int relayPort = 61613;
    private String clientLogin = "guest";
    private String clientPasscode = "guest";
    private String systemLogin = "guest";
    private String systemPasscode = "guest";
    @Nullable
    private Long systemHeartbeatSendInterval;
    @Nullable
    private Long systemHeartbeatReceiveInterval;
    @Nullable
    private String virtualHost;
    @Nullable
    private TcpOperations<byte[]> tcpClient;
    @Nullable
    private TaskScheduler taskScheduler;
    private boolean autoStartup = true;
    @Nullable
    private String userDestinationBroadcast;
    @Nullable
    private String userRegistryBroadcast;

    public StompBrokerRelayRegistration(SubscribableChannel clientInboundChannel, MessageChannel clientOutboundChannel, String[] destinationPrefixes) {
        super(clientInboundChannel, clientOutboundChannel, destinationPrefixes);
    }

    public StompBrokerRelayRegistration setRelayHost(String relayHost) {
        Assert.hasText((String)relayHost, (String)"relayHost must not be empty");
        this.relayHost = relayHost;
        return this;
    }

    public StompBrokerRelayRegistration setRelayPort(int relayPort) {
        this.relayPort = relayPort;
        return this;
    }

    public StompBrokerRelayRegistration setClientLogin(String login) {
        Assert.hasText((String)login, (String)"clientLogin must not be empty");
        this.clientLogin = login;
        return this;
    }

    public StompBrokerRelayRegistration setClientPasscode(String passcode) {
        Assert.hasText((String)passcode, (String)"clientPasscode must not be empty");
        this.clientPasscode = passcode;
        return this;
    }

    public StompBrokerRelayRegistration setSystemLogin(String login) {
        Assert.hasText((String)login, (String)"systemLogin must not be empty");
        this.systemLogin = login;
        return this;
    }

    public StompBrokerRelayRegistration setSystemPasscode(String passcode) {
        Assert.hasText((String)passcode, (String)"systemPasscode must not be empty");
        this.systemPasscode = passcode;
        return this;
    }

    public StompBrokerRelayRegistration setSystemHeartbeatSendInterval(long systemHeartbeatSendInterval) {
        this.systemHeartbeatSendInterval = systemHeartbeatSendInterval;
        return this;
    }

    public StompBrokerRelayRegistration setSystemHeartbeatReceiveInterval(long heartbeatReceiveInterval) {
        this.systemHeartbeatReceiveInterval = heartbeatReceiveInterval;
        return this;
    }

    public StompBrokerRelayRegistration setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
        return this;
    }

    public StompBrokerRelayRegistration setTcpClient(TcpOperations<byte[]> tcpClient) {
        this.tcpClient = tcpClient;
        return this;
    }

    public StompBrokerRelayRegistration setTaskScheduler(@Nullable TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        return this;
    }

    public StompBrokerRelayRegistration setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
        return this;
    }

    public StompBrokerRelayRegistration setUserDestinationBroadcast(String destination) {
        this.userDestinationBroadcast = destination;
        return this;
    }

    @Nullable
    protected String getUserDestinationBroadcast() {
        return this.userDestinationBroadcast;
    }

    public StompBrokerRelayRegistration setUserRegistryBroadcast(String destination) {
        this.userRegistryBroadcast = destination;
        return this;
    }

    @Nullable
    protected String getUserRegistryBroadcast() {
        return this.userRegistryBroadcast;
    }

    @Override
    protected StompBrokerRelayMessageHandler getMessageHandler(SubscribableChannel brokerChannel) {
        StompBrokerRelayMessageHandler handler = new StompBrokerRelayMessageHandler(this.getClientInboundChannel(), this.getClientOutboundChannel(), brokerChannel, this.getDestinationPrefixes());
        handler.setRelayHost(this.relayHost);
        handler.setRelayPort(this.relayPort);
        handler.setClientLogin(this.clientLogin);
        handler.setClientPasscode(this.clientPasscode);
        handler.setSystemLogin(this.systemLogin);
        handler.setSystemPasscode(this.systemPasscode);
        if (this.systemHeartbeatSendInterval != null) {
            handler.setSystemHeartbeatSendInterval(this.systemHeartbeatSendInterval);
        }
        if (this.systemHeartbeatReceiveInterval != null) {
            handler.setSystemHeartbeatReceiveInterval(this.systemHeartbeatReceiveInterval);
        }
        if (this.virtualHost != null) {
            handler.setVirtualHost(this.virtualHost);
        }
        if (this.tcpClient != null) {
            handler.setTcpClient(this.tcpClient);
        }
        if (this.taskScheduler != null) {
            handler.setTaskScheduler(this.taskScheduler);
        }
        handler.setAutoStartup(this.autoStartup);
        return handler;
    }
}

