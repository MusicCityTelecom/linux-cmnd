package be.tpvision.smartcontrol.rest.view_models.audio;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VolumeViewModel {
   private int speakerOut;
   private int audioOut;

   protected VolumeViewModel() {
   }

   public VolumeViewModel(final int speakerOut, final int audioOut) {
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

      if (!(object instanceof VolumeViewModel)) {
         return false;
      }

      VolumeViewModel that = (VolumeViewModel)object;
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
