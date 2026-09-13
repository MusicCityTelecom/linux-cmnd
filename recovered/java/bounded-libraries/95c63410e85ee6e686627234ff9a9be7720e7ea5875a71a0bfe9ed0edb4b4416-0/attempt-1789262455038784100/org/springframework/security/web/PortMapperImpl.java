/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.web.PortMapper;
import org.springframework.util.Assert;

public class PortMapperImpl
implements PortMapper {
    private final Map<Integer, Integer> httpsPortMappings = new HashMap<Integer, Integer>();

    public PortMapperImpl() {
        this.httpsPortMappings.put(80, 443);
        this.httpsPortMappings.put(8080, 8443);
    }

    public Map<Integer, Integer> getTranslatedPortMappings() {
        return this.httpsPortMappings;
    }

    @Override
    public Integer lookupHttpPort(Integer httpsPort) {
        for (Integer httpPort : this.httpsPortMappings.keySet()) {
            if (!this.httpsPortMappings.get(httpPort).equals(httpsPort)) continue;
            return httpPort;
        }
        return null;
    }

    @Override
    public Integer lookupHttpsPort(Integer httpPort) {
        return this.httpsPortMappings.get(httpPort);
    }

    public void setPortMappings(Map<String, String> newMappings) {
        Assert.notNull(newMappings, (String)"A valid list of HTTPS port mappings must be provided");
        this.httpsPortMappings.clear();
        for (Map.Entry<String, String> entry : newMappings.entrySet()) {
            Integer httpPort = Integer.valueOf(entry.getKey());
            Integer httpsPort = Integer.valueOf(entry.getValue());
            Assert.isTrue((this.isInPortRange(httpPort) && this.isInPortRange(httpsPort) ? 1 : 0) != 0, () -> "one or both ports out of legal range: " + httpPort + ", " + httpsPort);
            this.httpsPortMappings.put(httpPort, httpsPort);
        }
        Assert.isTrue((!this.httpsPortMappings.isEmpty() ? 1 : 0) != 0, (String)"must map at least one port");
    }

    private boolean isInPortRange(int port) {
        return port >= 1 && port <= 65535;
    }
}

