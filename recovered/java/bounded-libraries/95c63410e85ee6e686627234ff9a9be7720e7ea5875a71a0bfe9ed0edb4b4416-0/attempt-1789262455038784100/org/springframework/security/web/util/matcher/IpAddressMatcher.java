/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.security.web.util.matcher;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class IpAddressMatcher
implements RequestMatcher {
    private final int nMaskBits;
    private final InetAddress requiredAddress;

    public IpAddressMatcher(String ipAddress) {
        if (ipAddress.indexOf(47) > 0) {
            String[] addressAndMask = StringUtils.split((String)ipAddress, (String)"/");
            ipAddress = addressAndMask[0];
            this.nMaskBits = Integer.parseInt(addressAndMask[1]);
        } else {
            this.nMaskBits = -1;
        }
        this.requiredAddress = this.parseAddress(ipAddress);
        Assert.isTrue((this.requiredAddress.getAddress().length * 8 >= this.nMaskBits ? 1 : 0) != 0, (String)String.format("IP address %s is too short for bitmask of length %d", ipAddress, this.nMaskBits));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return this.matches(request.getRemoteAddr());
    }

    public boolean matches(String address) {
        InetAddress remoteAddress = this.parseAddress(address);
        if (!this.requiredAddress.getClass().equals(remoteAddress.getClass())) {
            return false;
        }
        if (this.nMaskBits < 0) {
            return remoteAddress.equals(this.requiredAddress);
        }
        byte[] remAddr = remoteAddress.getAddress();
        byte[] reqAddr = this.requiredAddress.getAddress();
        int nMaskFullBytes = this.nMaskBits / 8;
        byte finalByte = (byte)(65280 >> (this.nMaskBits & 7));
        for (int i = 0; i < nMaskFullBytes; ++i) {
            if (remAddr[i] == reqAddr[i]) continue;
            return false;
        }
        if (finalByte != 0) {
            return (remAddr[nMaskFullBytes] & finalByte) == (reqAddr[nMaskFullBytes] & finalByte);
        }
        return true;
    }

    private InetAddress parseAddress(String address) {
        try {
            return InetAddress.getByName(address);
        }
        catch (UnknownHostException ex) {
            throw new IllegalArgumentException("Failed to parse address" + address, ex);
        }
    }
}

