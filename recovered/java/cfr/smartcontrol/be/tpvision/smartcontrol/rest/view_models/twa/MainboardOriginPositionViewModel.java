/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.twa;

import be.tpvision.smartcontrol.rest.view_models.twa.TwaDeviceSettingViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MainboardOriginPositionViewModel
implements TwaDeviceSettingViewModel {
    private int x;
    private int y;

    protected MainboardOriginPositionViewModel() {
    }

    public MainboardOriginPositionViewModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MainboardOriginPositionViewModel)) {
            return false;
        }
        MainboardOriginPositionViewModel that = (MainboardOriginPositionViewModel)object;
        return new EqualsBuilder().append(this.getX(), that.getX()).append(this.getY(), that.getY()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getX(), this.getY());
    }

    public String toString() {
        return new ToStringBuilder(this).append(super.toString()).append("x", this.getX()).append("y", this.getY()).toString();
    }
}

