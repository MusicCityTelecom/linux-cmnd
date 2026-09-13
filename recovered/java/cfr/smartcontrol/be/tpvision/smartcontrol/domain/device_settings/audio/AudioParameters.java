/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.audio;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AudioParameters
implements DeviceSetting {
    private int treble;
    private int bass;

    protected AudioParameters() {
    }

    public AudioParameters(int treble, int bass) {
        this.setTreble(treble);
        this.setBass(bass);
    }

    public int getTreble() {
        return this.treble;
    }

    public void setTreble(int treble) {
        this.treble = treble;
    }

    public int getBass() {
        return this.bass;
    }

    public void setBass(int bass) {
        this.bass = bass;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AudioParameters)) {
            return false;
        }
        AudioParameters that = (AudioParameters)object;
        return new EqualsBuilder().append(this.getTreble(), that.getTreble()).append(this.getBass(), that.getBass()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getTreble(), this.getBass());
    }

    public String toString() {
        return new ToStringBuilder(this).append("treble", this.getTreble()).append("bass", this.getBass()).toString();
    }
}

