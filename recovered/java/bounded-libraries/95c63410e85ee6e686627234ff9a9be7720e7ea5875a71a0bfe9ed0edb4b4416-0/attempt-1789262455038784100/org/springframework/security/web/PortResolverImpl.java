/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  org.springframework.util.Assert
 */
package org.springframework.security.web;

import javax.servlet.ServletRequest;
import org.springframework.security.web.PortMapper;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolver;
import org.springframework.util.Assert;

public class PortResolverImpl
implements PortResolver {
    private PortMapper portMapper = new PortMapperImpl();

    public PortMapper getPortMapper() {
        return this.portMapper;
    }

    @Override
    public int getServerPort(ServletRequest request) {
        String scheme;
        int serverPort = request.getServerPort();
        Integer mappedPort = this.getMappedPort(serverPort, scheme = request.getScheme().toLowerCase());
        return mappedPort != null ? mappedPort : serverPort;
    }

    private Integer getMappedPort(int serverPort, String scheme) {
        if ("http".equals(scheme)) {
            return this.portMapper.lookupHttpPort(serverPort);
        }
        if ("https".equals(scheme)) {
            return this.portMapper.lookupHttpsPort(serverPort);
        }
        return null;
    }

    public void setPortMapper(PortMapper portMapper) {
        Assert.notNull((Object)portMapper, (String)"portMapper cannot be null");
        this.portMapper = portMapper;
    }
}

