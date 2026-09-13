/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

import be.tpvision.smartcontrol.messages.view_models.miscellaneous.tiling.SetEnableMessages;
import be.tpvision.smartcontrol.messages.view_models.miscellaneous.tiling.SetFrameCompMessages;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class TilingViewModel {
    private String enable;
    private String frameComp;
    private int position;
    private int numberOfHorizontalMonitors;
    private int numberOfVerticalMonitors;

    protected TilingViewModel() {
    }

    public TilingViewModel(String enable, String frameComp, int position, int numberOfHorizontalMonitors, int numberOfVerticalMonitors) {
        this.setEnable(enable);
        this.setFrameComp(frameComp);
        this.setPosition(position);
        this.setNumberOfHorizontalMonitors(numberOfHorizontalMonitors);
        this.setNumberOfVerticalMonitors(numberOfVerticalMonitors);
    }

    public String getEnable() {
        return this.enable;
    }

    public void setEnable(String enable) {
        Assert.notNull((Object)enable, SetEnableMessages.ENABLE_CAN_NOT_BE_NULL);
        this.enable = enable;
    }

    public String getFrameComp() {
        return this.frameComp;
    }

    public void setFrameComp(String frameComp) {
        Assert.notNull((Object)frameComp, SetFrameCompMessages.FRAME_COMP_CAN_NOT_BE_NULL);
        this.frameComp = frameComp;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNumberOfHorizontalMonitors() {
        return this.numberOfHorizontalMonitors;
    }

    public void setNumberOfHorizontalMonitors(int numberOfHorizontalMonitors) {
        this.numberOfHorizontalMonitors = numberOfHorizontalMonitors;
    }

    public int getNumberOfVerticalMonitors() {
        return this.numberOfVerticalMonitors;
    }

    public void setNumberOfVerticalMonitors(int numberOfVerticalMonitors) {
        this.numberOfVerticalMonitors = numberOfVerticalMonitors;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TilingViewModel)) {
            return false;
        }
        TilingViewModel that = (TilingViewModel)object;
        return new EqualsBuilder().append(this.getEnable(), that.getEnable()).append(this.getFrameComp(), that.getFrameComp()).append(this.getPosition(), that.getPosition()).append(this.getNumberOfHorizontalMonitors(), that.getNumberOfHorizontalMonitors()).append(this.getNumberOfVerticalMonitors(), that.getNumberOfVerticalMonitors()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getEnable(), this.getFrameComp(), this.getPosition(), this.getNumberOfHorizontalMonitors(), this.getNumberOfVerticalMonitors());
    }

    public String toString() {
        return new ToStringBuilder(this).append("enable", this.getEnable()).append("frameComp", this.getFrameComp()).append("position", this.getPosition()).append("numberOfHorizontalMonitors", this.getNumberOfHorizontalMonitors()).append("numberOfVerticalMonitors", this.getNumberOfVerticalMonitors()).toString();
    }
}

