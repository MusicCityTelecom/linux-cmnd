/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.simpl;

import java.net.InetAddress;
import org.quartz.SchedulerException;
import org.quartz.spi.InstanceIdGenerator;

public class HostnameInstanceIdGenerator
implements InstanceIdGenerator {
    @Override
    public String generateInstanceId() throws SchedulerException {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (Exception e) {
            throw new SchedulerException("Couldn't get host name!", e);
        }
    }
}

