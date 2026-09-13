/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import be.tpvision.smartcontrol.repository.converters.tiling.EnableConverter;
import be.tpvision.smartcontrol.repository.converters.tiling.FrameCompConverter;
import java.util.Objects;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class Tiling
implements DeviceSetting {
    @Convert(converter=EnableConverter.class)
    private Enable enable;
    @Convert(converter=FrameCompConverter.class)
    private FrameComp frameComp;
    private int position;
    private int numberOfHorizontalMonitors;
    private int numberOfVerticalMonitors;

    protected Tiling() {
    }

    public Tiling(Enable enable, FrameComp frameComp, int position, int numberOfHorizontalMonitors, int numberOfVerticalMonitors) {
        this.setEnable(enable);
        this.setFrameComp(frameComp);
        this.setPosition(position);
        this.setNumberOfHorizontalMonitors(numberOfHorizontalMonitors);
        this.setNumberOfVerticalMonitors(numberOfVerticalMonitors);
    }

    public Enable getEnable() {
        return this.enable;
    }

    public void setEnable(Enable enable) {
        this.enable = enable;
    }

    public FrameComp getFrameComp() {
        return this.frameComp;
    }

    public void setFrameComp(FrameComp frameComp) {
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
        if (!(object instanceof Tiling)) {
            return false;
        }
        Tiling tiling = (Tiling)object;
        return new EqualsBuilder().append((Object)this.getEnable(), (Object)tiling.getEnable()).append((Object)this.getFrameComp(), (Object)tiling.getFrameComp()).append(this.getPosition(), tiling.getPosition()).append(this.getNumberOfHorizontalMonitors(), tiling.getNumberOfHorizontalMonitors()).append(this.getNumberOfVerticalMonitors(), tiling.getNumberOfVerticalMonitors()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getEnable(), this.getFrameComp(), this.getPosition(), this.getNumberOfHorizontalMonitors(), this.getNumberOfVerticalMonitors()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("enable", (Object)this.getEnable()).append("frameComp", (Object)this.getFrameComp()).append("position", this.getPosition()).append("numberOfHorizontalMonitors", this.getNumberOfHorizontalMonitors()).append("numberOfVerticalMonitors", this.getNumberOfVerticalMonitors()).toString();
    }

    public static enum FrameComp {
        NO,
        YES,
        DO_NOT_OVERWRITE;

    }

    public static enum Enable {
        NO,
        YES;

    }
}

