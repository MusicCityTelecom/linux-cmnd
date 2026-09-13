package be.tpvision.smartcontrol.rest.view_models.device_limits;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AudioParametersLimitsViewModel {
   private LimitsViewModel treble;
   private LimitsViewModel bass;

   public AudioParametersLimitsViewModel() {
      this(new LimitsViewModel(0, 100), new LimitsViewModel(0, 100));
   }

   public AudioParametersLimitsViewModel(final LimitsViewModel treble, final LimitsViewModel bass) {
      this.treble = treble;
      this.bass = bass;
   }

   public LimitsViewModel getTreble() {
      return this.treble;
   }

   public void setTreble(final LimitsViewModel treble) {
      this.treble = treble;
   }

   public LimitsViewModel getBass() {
      return this.bass;
   }

   public void setBass(final LimitsViewModel bass) {
      this.bass = bass;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof AudioParametersLimitsViewModel)) {
         return false;
      }

      AudioParametersLimitsViewModel that = (AudioParametersLimitsViewModel)object;
      return new EqualsBuilder().append(this.treble, that.treble).append(this.bass, that.bass).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.treble, this.bass);
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("treble", this.treble).append("bass", this.bass).toString();
   }
}
