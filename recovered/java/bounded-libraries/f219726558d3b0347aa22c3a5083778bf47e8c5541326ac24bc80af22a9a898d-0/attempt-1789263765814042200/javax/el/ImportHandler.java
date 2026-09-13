/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.el.ELException;

public class ImportHandler {
    private Map<String, String> classNameMap = new HashMap<String, String>();
    private Map<String, Class<?>> classMap = new HashMap();
    private Map<String, String> staticNameMap = new HashMap<String, String>();
    private HashSet<String> notAClass = new HashSet();
    private List<String> packages = new ArrayList<String>();

    public ImportHandler() {
        this.importPackage("java.lang");
    }

    public void importStatic(String name) throws ELException {
        int i = name.lastIndexOf(46);
        if (i <= 0) {
            throw new ELException("The name " + name + " is not a full static member name");
        }
        String memberName = name.substring(i + 1);
        String className = name.substring(0, i);
        this.staticNameMap.put(memberName, className);
    }

    public void importClass(String name) throws ELException {
        int i = name.lastIndexOf(46);
        if (i <= 0) {
            throw new ELException("The name " + name + " is not a full class name");
        }
        String className = name.substring(i + 1);
        this.classNameMap.put(className, name);
    }

    public void importPackage(String packageName) {
        this.packages.add(packageName);
    }

    public Class<?> resolveClass(String name) {
        String className = this.classNameMap.get(name);
        if (className != null) {
            return this.resolveClassFor(className);
        }
        for (String packageName : this.packages) {
            String fullClassName = packageName + "." + name;
            Class<?> c = this.resolveClassFor(fullClassName);
            if (c == null) continue;
            this.classNameMap.put(name, fullClassName);
            return c;
        }
        return null;
    }

    public Class<?> resolveStatic(String name) {
        Class<?> c;
        String className = this.staticNameMap.get(name);
        if (className != null && (c = this.resolveClassFor(className)) != null) {
            return c;
        }
        return null;
    }

    private Class<?> resolveClassFor(String className) {
        Class<?> c = this.classMap.get(className);
        if (c != null) {
            return c;
        }
        c = this.getClassFor(className);
        if (c != null) {
            this.checkModifiers(c.getModifiers());
            this.classMap.put(className, c);
        }
        return c;
    }

    private Class<?> getClassFor(String className) {
        if (!this.notAClass.contains(className)) {
            try {
                return Class.forName(className, false, this.getClass().getClassLoader());
            }
            catch (ClassNotFoundException ex) {
                this.notAClass.add(className);
            }
        }
        return null;
    }

    private void checkModifiers(int modifiers) {
        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers) || !Modifier.isPublic(modifiers)) {
            throw new ELException("Imported class must be public, and cannot be abstract or an interface");
        }
    }
}

