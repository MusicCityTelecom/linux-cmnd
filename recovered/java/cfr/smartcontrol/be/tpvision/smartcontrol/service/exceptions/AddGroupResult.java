/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service.exceptions;

import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.service.exceptions.AddDeviceResult;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddGroupResult {
    private Group group;
    private List<AddDeviceResult> addDeviceResults;
    private Result result;
    private String message;

    public AddGroupResult(Group group, List<AddDeviceResult> addDeviceResults, Result result) {
        this(group, addDeviceResults, result, null);
    }

    public AddGroupResult(Group group, List<AddDeviceResult> addDeviceResults, Result result, String message) {
        this.group = group;
        this.addDeviceResults = addDeviceResults;
        this.result = result;
        this.message = message;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<AddDeviceResult> getAddDeviceResults() {
        return this.addDeviceResults;
    }

    public void setAddDeviceResults(List<AddDeviceResult> addDeviceResults) {
        this.addDeviceResults = addDeviceResults;
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
        if (!(object instanceof AddGroupResult)) {
            return false;
        }
        AddGroupResult that = (AddGroupResult)object;
        return new EqualsBuilder().append(this.getGroup(), that.getGroup()).append(this.getAddDeviceResults(), that.getAddDeviceResults()).append((Object)this.getResult(), (Object)that.getResult()).append(this.getMessage(), that.getMessage()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getGroup(), this.getAddDeviceResults(), this.getResult(), this.getMessage()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("group", this.getGroup()).append("addDeviceResults", this.getAddDeviceResults()).append("result", (Object)this.getResult()).append("message", this.getMessage()).toString();
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

