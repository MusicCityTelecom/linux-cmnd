/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 */
package org.springframework.security.web;

import javax.servlet.ServletRequest;

public interface PortResolver {
    public int getServerPort(ServletRequest var1);
}

