package be.tpvision.smartcontrol.domain.comparators;

import java.util.Comparator;

public abstract class DelegatingComparator<T> implements Comparator<T> {
   private final Comparator<? super T> comparator;

   public DelegatingComparator(final Comparator<? super T> comparator) {
      this.comparator = comparator;
   }

   @Override
   public int compare(final T type1, final T type2) {
      return this.comparator.compare(type1, type2);
   }
}
