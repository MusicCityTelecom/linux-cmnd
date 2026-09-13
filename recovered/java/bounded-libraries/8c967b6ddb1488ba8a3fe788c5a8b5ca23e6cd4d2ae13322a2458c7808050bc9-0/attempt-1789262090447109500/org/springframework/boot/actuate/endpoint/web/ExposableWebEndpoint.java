/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.endpoint.web;

import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebOperation;

public interface ExposableWebEndpoint
extends ExposableEndpoint<WebOperation>,
PathMappedEndpoint {
}

