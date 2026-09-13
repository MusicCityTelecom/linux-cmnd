package be.tpvision.smartcontrol.domain.device_settings.audio;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VolumeUpDown implements DeviceSetting {
   private VolumeUpDown.Volume speakerOut;
   private VolumeUpDown.Volume audioOut;

   protected VolumeUpDown() {
   }

   public VolumeUpDown(final VolumeUpDown.Volume speakerOut, final VolumeUpDown.Volume audioOut) {
      this.speakerOut = speakerOut;
      this.audioOut = audioOut;
   }

   public VolumeUpDown.Volume getSpeakerOut() {
      return this.speakerOut;
   }

   public void setSpeakerOut(final VolumeUpDown.Volume speakerOut) {
      this.speakerOut = speakerOut;
   }

   public VolumeUpDown.Volume getAudioOut() {
      return this.audioOut;
   }

   public void setAudioOut(final VolumeUpDown.Volume audioOut) {
      this.audioOut = audioOut;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VolumeUpDown)) {
         return false;
      }

      VolumeUpDown that = (VolumeUpDown)object;
      return new EqualsBuilder().append(this.getSpeakerOut(), that.getSpeakerOut()).append(this.getAudioOut(), that.getAudioOut()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getSpeakerOut(), this.getAudioOut());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("speakerOut", this.getSpeakerOut()).append("audioOut", this.getAudioOut()).toString();
   }

   public enum Volume {
      DOWN,
      UP,
      UNCHANGED;
   }
}
