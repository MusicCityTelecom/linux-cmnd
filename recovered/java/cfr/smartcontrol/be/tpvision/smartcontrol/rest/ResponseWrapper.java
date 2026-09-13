/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResponseWrapper<T> {
    private final T result;

    public ResponseWrapper(T result) {
        this.result = result;
    }

    public T getResult() {
        return this.result;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ResponseWrapper)) {
            return false;
        }
        ResponseWrapper that = (ResponseWrapper)object;
        return new EqualsBuilder().append(this.getResult(), that.getResult()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getResult());
    }

    public String toString() {
        return new ToStringBuilder(this).append("result", this.getResult()).toString();
    }
}

