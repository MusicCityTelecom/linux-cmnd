/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.util.NettyRuntime
 *  io.netty.util.ResourceLeakDetector
 *  io.netty.util.ResourceLeakDetector$Level
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 */
package org.springframework.boot.autoconfigure.netty;

import io.netty.util.NettyRuntime;
import io.netty.util.ResourceLeakDetector;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.netty.NettyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@ConditionalOnClass(value={NettyRuntime.class})
@EnableConfigurationProperties(value={NettyProperties.class})
public class NettyAutoConfiguration {
    public NettyAutoConfiguration(NettyProperties properties) {
        if (properties.getLeakDetection() != null) {
            NettyProperties.LeakDetection leakDetection = properties.getLeakDetection();
            ResourceLeakDetector.setLevel((ResourceLeakDetector.Level)ResourceLeakDetector.Level.valueOf((String)leakDetection.name()));
        }
    }
}

