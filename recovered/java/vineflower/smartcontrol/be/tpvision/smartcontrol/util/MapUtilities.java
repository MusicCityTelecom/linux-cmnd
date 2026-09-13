package be.tpvision.smartcontrol.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MapUtilities {
   private MapUtilities() {
   }

   public static <K, V> Map<V, K> inverse(Map<K, V> map) {
      return map == null ? null : map.entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
   }
}
