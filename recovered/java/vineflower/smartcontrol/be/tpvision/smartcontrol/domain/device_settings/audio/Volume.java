package be.tpvision.smartcontrol.domain.device_settings.audio;

import be.tpvision.smartcontrol.domain.device_settings.DeviceSetting;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Volume implements DeviceSetting {
   private int speakerOut;
   private int audioOut;

   protected Volume() {
   }

   public Volume(final int speakerOut, final int audioOut) {
      this.speakerOut = speakerOut;
      this.audioOut = audioOut;
   }

   public int getSpeakerOut() {
      return this.speakerOut;
   }

   public void setSpeakerOut(final int speakerOut) {
      this.speakerOut = speakerOut;
   }

   public int getAudioOut() {
      return this.audioOut;
   }

   public void setAudioOut(final int audioOut) {
      this.audioOut = audioOut;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Volume)) {
         return false;
      }

      Volume that = (Volume)object;
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
}
