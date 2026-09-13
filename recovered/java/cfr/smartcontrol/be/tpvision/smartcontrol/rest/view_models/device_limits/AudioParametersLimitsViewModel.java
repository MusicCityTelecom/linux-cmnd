/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits;

import be.tpvision.smartcontrol.rest.view_models.device_limits.LimitsViewModel;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AudioParametersLimitsViewModel {
    private LimitsViewModel treble;
    private LimitsViewModel bass;

    public AudioParametersLimitsViewModel() {
        this(new LimitsViewModel(0, 100), new LimitsViewModel(0, 100));
    }

    public AudioParametersLimitsViewModel(LimitsViewModel treble, LimitsViewModel bass) {
        this.treble = treble;
        this.bass = bass;
    }

    public LimitsViewModel getTreble() {
        return this.treble;
    }

    public void setTreble(LimitsViewModel treble) {
        this.treble = treble;
    }

    public LimitsViewModel getBass() {
        return this.bass;
    }

    public void setBass(LimitsViewModel bass) {
        this.bass = bass;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AudioParametersLimitsViewModel)) {
            return false;
        }
        AudioParametersLimitsViewModel that = (AudioParametersLimitsViewModel)object;
        return new EqualsBuilder().append(this.treble, that.treble).append(this.bass, that.bass).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.treble, this.bass);
    }

    public String toString() {
        return new ToStringBuilder(this).append("treble", this.treble).append("bass", this.bass).toString();
    }
}

