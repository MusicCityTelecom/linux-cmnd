/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.todelete;

import be.tpvision.smartcontrol.util.Convertible;
import be.tpvision.smartcontrol.util.Convertibles;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class Miscellaneous
implements Convertibles {
    @Column(nullable=true)
    private int operatingHours;

    protected Miscellaneous() {
    }

    public Miscellaneous(int operatingHours) {
        this.operatingHours = operatingHours;
    }

    public int getOperatingHours() {
        return this.operatingHours;
    }

    public void setOperatingHours(int operatingHours) {
        this.operatingHours = operatingHours;
    }

    @Override
    public byte[] convert() {
        byte operatingHoursByte = (byte)this.operatingHours;
        return new byte[]{operatingHoursByte};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Miscellaneous)) {
            return false;
        }
        Miscellaneous that = (Miscellaneous)object;
        return new EqualsBuilder().append(this.operatingHours, that.operatingHours).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.operatingHours);
    }

    public String toString() {
        return new ToStringBuilder(this).append("operatingHours", this.operatingHours).toString();
    }

    public static enum Info implements Convertible
    {
        OPERATING_HOURS(2);

        private byte data;

        private Info(byte data) {
            this.data = data;
        }

        public byte getData() {
            return this.data;
        }

        @Override
        public byte convert() {
            return this.data;
        }
    }
}

