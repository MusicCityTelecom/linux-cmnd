/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DisplayOrientation
implements DeviceSetting {
    private AutoRotate autoRotate;
    private OsdRotation osdRotation;
    private ImageAll imageAll;
    private DisplayWindow1 displayWindow1;
    private DisplayWindow2 displayWindow2;
    private DisplayWindow3 displayWindow3;
    private DisplayWindow4 displayWindow4;

    protected DisplayOrientation() {
    }

    public DisplayOrientation(AutoRotate autoRotate, OsdRotation osdRotation, ImageAll imageAll, DisplayWindow1 displayWindow1, DisplayWindow2 displayWindow2, DisplayWindow3 displayWindow3, DisplayWindow4 displayWindow4) {
        this.autoRotate = autoRotate;
        this.osdRotation = osdRotation;
        this.imageAll = imageAll;
        this.displayWindow1 = displayWindow1;
        this.displayWindow2 = displayWindow2;
        this.displayWindow3 = displayWindow3;
        this.displayWindow4 = displayWindow4;
    }

    public AutoRotate getAutoRotate() {
        return this.autoRotate;
    }

    public void setAutoRotate(AutoRotate autoRotate) {
        this.autoRotate = autoRotate;
    }

    public OsdRotation getOsdRotation() {
        return this.osdRotation;
    }

    public void setOsdRotation(OsdRotation osdRotation) {
        this.osdRotation = osdRotation;
    }

    public ImageAll getImageAll() {
        return this.imageAll;
    }

    public void setImageAll(ImageAll imageAll) {
        this.imageAll = imageAll;
    }

    public DisplayWindow1 getDisplayWindow1() {
        return this.displayWindow1;
    }

    public void setDisplayWindow1(DisplayWindow1 displayWindow1) {
        this.displayWindow1 = displayWindow1;
    }

    public DisplayWindow2 getDisplayWindow2() {
        return this.displayWindow2;
    }

    public void setDisplayWindow2(DisplayWindow2 displayWindow2) {
        this.displayWindow2 = displayWindow2;
    }

    public DisplayWindow3 getDisplayWindow3() {
        return this.displayWindow3;
    }

    public void setDisplayWindow3(DisplayWindow3 displayWindow3) {
        this.displayWindow3 = displayWindow3;
    }

    public DisplayWindow4 getDisplayWindow4() {
        return this.displayWindow4;
    }

    public void setDisplayWindow4(DisplayWindow4 displayWindow4) {
        this.displayWindow4 = displayWindow4;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DisplayOrientation)) {
            return false;
        }
        DisplayOrientation that = (DisplayOrientation)object;
        return new EqualsBuilder().append((Object)this.getAutoRotate(), (Object)that.getAutoRotate()).append((Object)this.getOsdRotation(), (Object)that.getOsdRotation()).append((Object)this.getImageAll(), (Object)that.getImageAll()).append((Object)this.getDisplayWindow1(), (Object)that.getDisplayWindow1()).append((Object)this.getDisplayWindow2(), (Object)that.getDisplayWindow2()).append((Object)this.getDisplayWindow3(), (Object)that.getDisplayWindow3()).append((Object)this.getDisplayWindow4(), (Object)that.getDisplayWindow4()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getAutoRotate(), this.getOsdRotation(), this.getImageAll(), this.getDisplayWindow1(), this.getDisplayWindow2(), this.getDisplayWindow3(), this.getDisplayWindow4()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("autoRotate", (Object)this.getAutoRotate()).append("osdRotation", (Object)this.getOsdRotation()).append("imageAll", (Object)this.getImageAll()).append("displayWindow1", (Object)this.getDisplayWindow1()).append("displayWindow2", (Object)this.getDisplayWindow2()).append("displayWindow3", (Object)this.getDisplayWindow3()).append("displayWindow4", (Object)this.getDisplayWindow4()).toString();
    }

    public static enum DisplayWindow4 {
        OFF,
        ON;

    }

    public static enum DisplayWindow3 {
        OFF,
        ON;

    }

    public static enum DisplayWindow2 {
        OFF,
        ON;

    }

    public static enum DisplayWindow1 {
        OFF,
        ON;

    }

    public static enum ImageAll {
        OFF,
        ON,
        ON_CLOCKWISE,
        ON_COUNTERCLOCKWISE;

    }

    public static enum OsdRotation {
        LANDSCAPE,
        PORTRAIT;

    }

    public static enum AutoRotate {
        OFF,
        ON;

    }
}

