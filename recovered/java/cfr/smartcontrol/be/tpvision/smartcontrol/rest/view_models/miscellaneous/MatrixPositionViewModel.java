/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.miscellaneous;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MatrixPositionViewModel {
    private int x;
    private int y;
    private int sizeX;
    private int sizeY;

    protected MatrixPositionViewModel() {
    }

    public MatrixPositionViewModel(int x, int y, int sizeX, int sizeY) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
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

    public int getSizeX() {
        return this.sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof MatrixPositionViewModel)) {
            return false;
        }
        MatrixPositionViewModel that = (MatrixPositionViewModel)object;
        return new EqualsBuilder().append(this.getX(), that.getX()).append(this.getY(), that.getY()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getX(), this.getY());
    }

    public String toString() {
        return new ToStringBuilder(this).append("x", this.getX()).append("y", this.getY()).append("sizeX", this.getSizeX()).append("sizeY", this.getSizeY()).toString();
    }
}

