/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OSDInformation
implements Convertible {
    public static final int MINIMUM_VALUE = 0;
    public static final int MAXIMUM_VALUE = 60;
    private int osdInformation;

    public OSDInformation(int osdInformation) {
        this.setOsdInformation(osdInformation);
    }

    public int getOsdInformation() {
        return this.osdInformation;
    }

    public void setOsdInformation(int osdInformation) {
        this.osdInformation = ValueUtilities.getValue(osdInformation, 0, 60);
    }

    @Override
    public byte convert() {
        return (byte)this.osdInformation;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof OSDInformation)) {
            return false;
        }
        OSDInformation that = (OSDInformation)object;
        return new EqualsBuilder().append(this.osdInformation, that.osdInformation).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.osdInformation);
    }

    public String toString() {
        return new ToStringBuilder(this).append("osdInformation", this.osdInformation).toString();
    }
}

