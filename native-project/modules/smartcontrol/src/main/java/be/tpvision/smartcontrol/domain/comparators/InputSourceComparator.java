package be.tpvision.smartcontrol.domain.comparators;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import java.util.Comparator;

public class InputSourceComparator extends DelegatingComparator<InputSource> {
   public InputSourceComparator(final Comparator<? super InputSource.SourceType> sourceTypeComparator) {
      super(Comparator.comparing(InputSource::getSourceType, sourceTypeComparator));
   }
}
