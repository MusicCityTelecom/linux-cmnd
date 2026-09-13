/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  org.springframework.security.access.ConfigAttribute
 */
package org.springframework.security.web.access.channel;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;

public interface ChannelProcessor {
    public void decide(FilterInvocation var1, Collection<ConfigAttribute> var2) throws IOException, ServletException;

    public boolean supports(ConfigAttribute var1);
}

