/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol;

import be.tpvision.smartcontrol.protocol.Response;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResponseWrapper
implements Response {
    private Object response;

    public ResponseWrapper(Object response) {
        this.response = response;
    }

    @Override
    public Object getResponse() {
        return this.response;
    }

    public String toString() {
        return new ToStringBuilder(this).append("response", this.response).toString();
    }
}

