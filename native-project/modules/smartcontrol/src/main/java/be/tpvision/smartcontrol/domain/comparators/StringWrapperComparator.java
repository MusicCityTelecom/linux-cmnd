package be.tpvision.smartcontrol.domain.comparators;

import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import java.util.Comparator;

public class StringWrapperComparator extends DelegatingComparator<StringWrapper> {
   public StringWrapperComparator(final Comparator<String> valueComparator) {
      super(Comparator.comparing(StringWrapper::getValue, valueComparator));
   }
}
