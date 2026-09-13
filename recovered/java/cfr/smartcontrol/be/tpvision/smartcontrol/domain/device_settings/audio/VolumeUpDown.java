/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.device_settings.audio;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VolumeUpDown
implements DeviceSetting {
    private Volume speakerOut;
    private Volume audioOut;

    protected VolumeUpDown() {
    }

    public VolumeUpDown(Volume speakerOut, Volume audioOut) {
        this.speakerOut = speakerOut;
        this.audioOut = audioOut;
    }

    public Volume getSpeakerOut() {
        return this.speakerOut;
    }

    public void setSpeakerOut(Volume speakerOut) {
        this.speakerOut = speakerOut;
    }

    public Volume getAudioOut() {
        return this.audioOut;
    }

    public void setAudioOut(Volume audioOut) {
        this.audioOut = audioOut;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VolumeUpDown)) {
            return false;
        }
        VolumeUpDown that = (VolumeUpDown)object;
        return new EqualsBuilder().append((Object)this.getSpeakerOut(), (Object)that.getSpeakerOut()).append((Object)this.getAudioOut(), (Object)that.getAudioOut()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getSpeakerOut(), this.getAudioOut()});
    }

    public String toString() {
        return new ToStringBuilder(this).append("speakerOut", (Object)this.getSpeakerOut()).append("audioOut", (Object)this.getAudioOut()).toString();
    }

    public static enum Volume {
        DOWN,
        UP,
        UNCHANGED;

    }
}

