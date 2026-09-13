package be.tpvision.smartcontrol.rest.view_models.audio;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class VolumeUpDownViewModel {
   private String speakerOut;
   private String audioOut;

   protected VolumeUpDownViewModel() {
   }

   public VolumeUpDownViewModel(final String speakerOut, final String audioOut) {
      this.speakerOut = speakerOut;
      this.audioOut = audioOut;
   }

   public String getSpeakerOut() {
      return this.speakerOut;
   }

   public void setSpeakerOut(final String speakerOut) {
      this.speakerOut = speakerOut;
   }

   public String getAudioOut() {
      return this.audioOut;
   }

   public void setAudioOut(final String audioOut) {
      this.audioOut = audioOut;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof VolumeUpDownViewModel)) {
         return false;
      }

      VolumeUpDownViewModel that = (VolumeUpDownViewModel)object;
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
