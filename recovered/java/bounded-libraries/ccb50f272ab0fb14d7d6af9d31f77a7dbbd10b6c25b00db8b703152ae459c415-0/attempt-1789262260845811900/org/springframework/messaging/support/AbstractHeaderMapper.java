/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.lang.Nullable
 *  org.springframework.util.StringUtils
 */
package org.springframework.messaging.support;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.Nullable;
import org.springframework.messaging.support.HeaderMapper;
import org.springframework.util.StringUtils;

public abstract class AbstractHeaderMapper<T>
implements HeaderMapper<T> {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private String inboundPrefix = "";
    private String outboundPrefix = "";

    public void setInboundPrefix(@Nullable String inboundPrefix) {
        this.inboundPrefix = inboundPrefix != null ? inboundPrefix : "";
    }

    public void setOutboundPrefix(@Nullable String outboundPrefix) {
        this.outboundPrefix = outboundPrefix != null ? outboundPrefix : "";
    }

    protected String fromHeaderName(String headerName) {
        String propertyName = headerName;
        if (StringUtils.hasText((String)this.outboundPrefix) && !propertyName.startsWith(this.outboundPrefix)) {
            propertyName = this.outboundPrefix + headerName;
        }
        return propertyName;
    }

    protected String toHeaderName(String propertyName) {
        String headerName = propertyName;
        if (StringUtils.hasText((String)this.inboundPrefix) && !headerName.startsWith(this.inboundPrefix)) {
            headerName = this.inboundPrefix + propertyName;
        }
        return headerName;
    }

    @Nullable
    protected <V> V getHeaderIfAvailable(Map<String, Object> headers, String name, Class<V> type) {
        Object value = headers.get(name);
        if (value == null) {
            return null;
        }
        if (!type.isAssignableFrom(value.getClass())) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("Skipping header '" + name + "': expected type [" + type + "], but got [" + value.getClass() + "]"));
            }
            return null;
        }
        return type.cast(value);
    }
}

