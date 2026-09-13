/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.DisplayOrientation;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DisplayOrientationLimitsViewModel {
    private EnumLimitsViewModel<DisplayOrientation.AutoRotate> autoRotate;
    private EnumLimitsViewModel<DisplayOrientation.OsdRotation> osdRotation;
    private EnumLimitsViewModel<DisplayOrientation.ImageAll> imageAll;
    private EnumLimitsViewModel<DisplayOrientation.DisplayWindow1> displayWindow1;
    private EnumLimitsViewModel<DisplayOrientation.DisplayWindow2> displayWindow2;
    private EnumLimitsViewModel<DisplayOrientation.DisplayWindow3> displayWindow3;
    private EnumLimitsViewModel<DisplayOrientation.DisplayWindow4> displayWindow4;

    public DisplayOrientationLimitsViewModel() {
        this(new EnumLimitsViewModel<DisplayOrientation.AutoRotate>(DisplayOrientation.AutoRotate.class), new EnumLimitsViewModel<DisplayOrientation.OsdRotation>(DisplayOrientation.OsdRotation.class), new EnumLimitsViewModel<DisplayOrientation.ImageAll>(DisplayOrientation.ImageAll.class), new EnumLimitsViewModel<DisplayOrientation.DisplayWindow1>(DisplayOrientation.DisplayWindow1.class), new EnumLimitsViewModel<DisplayOrientation.DisplayWindow2>(DisplayOrientation.DisplayWindow2.class), new EnumLimitsViewModel<DisplayOrientation.DisplayWindow3>(DisplayOrientation.DisplayWindow3.class), new EnumLimitsViewModel<DisplayOrientation.DisplayWindow4>(DisplayOrientation.DisplayWindow4.class));
    }

    public DisplayOrientationLimitsViewModel(EnumLimitsViewModel<DisplayOrientation.AutoRotate> autoRotate, EnumLimitsViewModel<DisplayOrientation.OsdRotation> osdRotation, EnumLimitsViewModel<DisplayOrientation.ImageAll> imageAll, EnumLimitsViewModel<DisplayOrientation.DisplayWindow1> displayWindow1, EnumLimitsViewModel<DisplayOrientation.DisplayWindow2> displayWindow2, EnumLimitsViewModel<DisplayOrientation.DisplayWindow3> displayWindow3, EnumLimitsViewModel<DisplayOrientation.DisplayWindow4> displayWindow4) {
        this.autoRotate = autoRotate;
        this.osdRotation = osdRotation;
        this.imageAll = imageAll;
        this.displayWindow1 = displayWindow1;
        this.displayWindow2 = displayWindow2;
        this.displayWindow3 = displayWindow3;
        this.displayWindow4 = displayWindow4;
    }

    public EnumLimitsViewModel<DisplayOrientation.AutoRotate> getAutoRotate() {
        return this.autoRotate;
    }

    public void setAutoRotate(EnumLimitsViewModel<DisplayOrientation.AutoRotate> autoRotate) {
        this.autoRotate = autoRotate;
    }

    public EnumLimitsViewModel<DisplayOrientation.OsdRotation> getOsdRotation() {
        return this.osdRotation;
    }

    public void setOsdRotation(EnumLimitsViewModel<DisplayOrientation.OsdRotation> osdRotation) {
        this.osdRotation = osdRotation;
    }

    public EnumLimitsViewModel<DisplayOrientation.ImageAll> getImageAll() {
        return this.imageAll;
    }

    public void setImageAll(EnumLimitsViewModel<DisplayOrientation.ImageAll> imageAll) {
        this.imageAll = imageAll;
    }

    public EnumLimitsViewModel<DisplayOrientation.DisplayWindow1> getDisplayWindow1() {
        return this.displayWindow1;
    }

    public void setDisplayWindow1(EnumLimitsViewModel<DisplayOrientation.DisplayWindow1> displayWindow1) {
        this.displayWindow1 = displayWindow1;
    }

    public EnumLimitsViewModel<DisplayOrientation.DisplayWindow2> getDisplayWindow2() {
        return this.displayWindow2;
    }

    public void setDisplayWindow2(EnumLimitsViewModel<DisplayOrientation.DisplayWindow2> displayWindow2) {
        this.displayWindow2 = displayWindow2;
    }

    public EnumLimitsViewModel<DisplayOrientation.DisplayWindow3> getDisplayWindow3() {
        return this.displayWindow3;
    }

    public void setDisplayWindow3(EnumLimitsViewModel<DisplayOrientation.DisplayWindow3> displayWindow3) {
        this.displayWindow3 = displayWindow3;
    }

    public EnumLimitsViewModel<DisplayOrientation.DisplayWindow4> getDisplayWindow4() {
        return this.displayWindow4;
    }

    public void setDisplayWindow4(EnumLimitsViewModel<DisplayOrientation.DisplayWindow4> displayWindow4) {
        this.displayWindow4 = displayWindow4;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DisplayOrientationLimitsViewModel)) {
            return false;
        }
        DisplayOrientationLimitsViewModel that = (DisplayOrientationLimitsViewModel)object;
        return new EqualsBuilder().append(this.getAutoRotate(), that.getAutoRotate()).append(this.getOsdRotation(), that.getOsdRotation()).append(this.getImageAll(), that.getImageAll()).append(this.getDisplayWindow1(), that.getDisplayWindow1()).append(this.getDisplayWindow2(), that.getDisplayWindow2()).append(this.getDisplayWindow3(), that.getDisplayWindow3()).append(this.getDisplayWindow4(), that.getDisplayWindow4()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getAutoRotate(), this.getOsdRotation(), this.getImageAll(), this.getDisplayWindow1(), this.getDisplayWindow2(), this.getDisplayWindow3(), this.getDisplayWindow4());
    }

    public String toString() {
        return new ToStringBuilder(this).append("autoRotate", this.getAutoRotate()).append("osdRotation", this.getOsdRotation()).append("imageAll", this.getImageAll()).append("displayWindow1", this.getDisplayWindow1()).append("displayWindow2", this.getDisplayWindow2()).append("displayWindow3", this.getDisplayWindow3()).append("displayWindow4", this.getDisplayWindow4()).toString();
    }
}

