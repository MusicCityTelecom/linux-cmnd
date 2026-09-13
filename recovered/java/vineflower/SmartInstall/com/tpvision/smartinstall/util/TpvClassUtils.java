package com.tpvision.smartinstall.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TpvClassUtils {
   private TpvClassUtils() {
   }

   public static <T> List<Class<? extends T>> getAllAssignedClass(Class<T> cls) throws IOException, ClassNotFoundException {
      List<Class<? extends T>> classes = new ArrayList<>();

      for (Class<?> c : getClasses(cls)) {
         if (cls.isAssignableFrom(c) && !cls.equals(c)) {
            classes.add((Class<? extends T>)c);
         }
      }

      return classes;
   }

   private static List<Class<?>> getClasses(Class<?> cls) throws IOException, ClassNotFoundException {
      String pk = cls.getPackage().getName();
      String path = pk.replace('.', '/');
      ClassLoader classloader = Thread.currentThread().getContextClassLoader();
      URL url = classloader.getResource(path);
      return getClasses(new File(URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name())), pk);
   }

   private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException {
      List<Class<?>> classes = new ArrayList<>();
      if (!dir.exists()) {
         return classes;
      }

      File[] fileList = dir.listFiles();
      if (fileList != null) {
         for (File f : fileList) {
            if (f.isDirectory()) {
               classes.addAll(getClasses(f, pk + "." + f.getName()));
            }

            String name = f.getName();
            if (name.endsWith(".class")) {
               classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));
            }
         }
      }

      return classes;
   }
}
