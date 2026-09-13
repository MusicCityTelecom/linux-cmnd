package be.tpvision.smartcontrol.domain.comparators;

import java.util.Comparator;

public class EnumComparator<E extends Enum> extends DelegatingComparator<E> {
   public EnumComparator(final Comparator<String> nameComparator) {
      super(Comparator.comparing(Enum::name, nameComparator));
   }
}
