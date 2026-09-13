/*
 * Decompiled with CFR 0.152.
 */
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
        ArrayList<Class<T>> classes = new ArrayList<Class<T>>();
        for (Class<?> c : TpvClassUtils.getClasses(cls)) {
            if (!cls.isAssignableFrom(c) || cls.equals(c)) continue;
            classes.add(c);
        }
        return classes;
    }

    private static List<Class<?>> getClasses(Class<?> cls) throws IOException, ClassNotFoundException {
        String pk = cls.getPackage().getName();
        String path = pk.replace('.', '/');
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(path);
        return TpvClassUtils.getClasses(new File(URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.name())), pk);
    }

    private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException {
        ArrayList classes = new ArrayList();
        if (!dir.exists()) {
            return classes;
        }
        File[] fileList = dir.listFiles();
        if (fileList != null) {
            for (File f : fileList) {
                String name;
                if (f.isDirectory()) {
                    classes.addAll(TpvClassUtils.getClasses(f, pk + "." + f.getName()));
                }
                if (!(name = f.getName()).endsWith(".class")) continue;
                classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));
            }
        }
        return classes;
    }
}

