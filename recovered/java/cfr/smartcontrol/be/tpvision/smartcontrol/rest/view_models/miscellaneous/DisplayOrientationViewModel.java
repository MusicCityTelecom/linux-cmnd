/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DisplayOrientationViewModel {
    private String autoRotate;
    private String osdRotation;
    private String imageAll;
    private String displayWindow1;
    private String displayWindow2;
    private String displayWindow3;
    private String displayWindow4;

    protected DisplayOrientationViewModel() {
    }

    public DisplayOrientationViewModel(String autoRotate, String osdRotation, String imageAll, String displayWindow1, String displayWindow2, String displayWindow3, String displayWindow4) {
        this.autoRotate = autoRotate;
        this.osdRotation = osdRotation;
        this.imageAll = imageAll;
        this.displayWindow1 = displayWindow1;
        this.displayWindow2 = displayWindow2;
        this.displayWindow3 = displayWindow3;
        this.displayWindow4 = displayWindow4;
    }

    public String getAutoRotate() {
        return this.autoRotate;
    }

    public void setAutoRotate(String autoRotate) {
        this.autoRotate = autoRotate;
    }

    public String getOsdRotation() {
        return this.osdRotation;
    }

    public void setOsdRotation(String osdRotation) {
        this.osdRotation = osdRotation;
    }

    public String getImageAll() {
        return this.imageAll;
    }

    public void setImageAll(String imageAll) {
        this.imageAll = imageAll;
    }

    public String getDisplayWindow1() {
        return this.displayWindow1;
    }

    public void setDisplayWindow1(String displayWindow1) {
        this.displayWindow1 = displayWindow1;
    }

    public String getDisplayWindow2() {
        return this.displayWindow2;
    }

    public void setDisplayWindow2(String displayWindow2) {
        this.displayWindow2 = displayWindow2;
    }

    public String getDisplayWindow3() {
        return this.displayWindow3;
    }

    public void setDisplayWindow3(String displayWindow3) {
        this.displayWindow3 = displayWindow3;
    }

    public String getDisplayWindow4() {
        return this.displayWindow4;
    }

    public void setDisplayWindow4(String displayWindow4) {
        this.displayWindow4 = displayWindow4;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DisplayOrientationViewModel)) {
            return false;
        }
        DisplayOrientationViewModel that = (DisplayOrientationViewModel)object;
        return new EqualsBuilder().append(this.getAutoRotate(), that.getAutoRotate()).append(this.getOsdRotation(), that.getOsdRotation()).append(this.getImageAll(), that.getImageAll()).append(this.getDisplayWindow1(), that.getDisplayWindow1()).append(this.getDisplayWindow2(), that.getDisplayWindow2()).append(this.getDisplayWindow3(), that.getDisplayWindow3()).append(this.getDisplayWindow4(), that.getDisplayWindow4()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getAutoRotate(), this.getOsdRotation(), this.getImageAll(), this.getDisplayWindow1(), this.getDisplayWindow2(), this.getDisplayWindow3(), this.getDisplayWindow4());
    }

    public String toString() {
        return new ToStringBuilder(this).append("autoRotate", this.getAutoRotate()).append("osdRotation", this.getOsdRotation()).append("imageAll", this.getImageAll()).append("displayWindow1", this.getDisplayWindow1()).append("displayWindow2", this.getDisplayWindow2()).append("displayWindow3", this.getDisplayWindow3()).append("displayWindow4", this.getDisplayWindow4()).toString();
    }
}

