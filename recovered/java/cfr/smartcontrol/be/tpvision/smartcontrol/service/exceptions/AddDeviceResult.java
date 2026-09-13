/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service.exceptions;

import be.tpvision.smartcontrol.domain.Device;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddDeviceResult {
    private Device device;
    private Result result;
    private String message;

    public AddDeviceResult(Device device, Result result) {
        this(device, result, null);
    }

    public AddDeviceResult(Device device, Result result, String message) {
        this.device = device;
        this.result = result;
        this.message = message;
    }

    public Device getDevice() {
        return this.device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AddDeviceResult)) {
            return false;
        }
        AddDeviceResult that = (AddDeviceResult)object;
        return new EqualsBuilder().append(this.getDevice(), that.getDevice()).append((Object)this.getResult(), (Object)that.getResult()).append(this.getMessage(), that.getMessage()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getDevice(), this.getResult(), this.getMessage()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("device", this.getDevice()).append("result", (Object)this.getResult()).append("message", this.getMessage()).toString();
    }

    public static enum Result {
        SUCCESS("Success"),
        FAILED("Failed");

        private String name;

        private Result(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}

