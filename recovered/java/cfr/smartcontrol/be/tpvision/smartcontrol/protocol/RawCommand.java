/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol;

import be.tpvision.smartcontrol.protocol.SicpMessage;
import java.util.Arrays;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RawCommand
extends SicpMessage {
    private byte[] payload;

    public RawCommand(byte controlId, byte groupId, byte[] payload) {
        super(controlId, groupId);
        this.payload = payload;
    }

    @Override
    protected byte[] getData() {
        return this.payload;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RawCommand)) {
            return false;
        }
        RawCommand that = (RawCommand)object;
        return new EqualsBuilder().append(this.payload, that.payload).isEquals();
    }

    public int hashCode() {
        return Arrays.hashCode(this.payload);
    }

    public String toString() {
        return new ToStringBuilder(this).append("payload", this.payload).toString();
    }
}

