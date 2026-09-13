/*
 * Decompiled with CFR 0.152.
 */
package javax.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.PropertyNotWritableException;

public class ResourceBundleELResolver
extends ELResolver {
    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof ResourceBundle) {
            context.setPropertyResolved(true);
            if (property != null) {
                try {
                    return ((ResourceBundle)base).getObject(property.toString());
                }
                catch (MissingResourceException e) {
                    return "???" + property + "???";
                }
            }
        }
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof ResourceBundle) {
            context.setPropertyResolved(true);
        }
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof ResourceBundle) {
            context.setPropertyResolved(true);
            throw new PropertyNotWritableException("ResourceBundles are immutable");
        }
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof ResourceBundle) {
            context.setPropertyResolved(true);
            return true;
        }
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        if (base instanceof ResourceBundle) {
            ResourceBundle bundle = (ResourceBundle)base;
            ArrayList<FeatureDescriptor> features = new ArrayList<FeatureDescriptor>();
            String key = null;
            FeatureDescriptor desc = null;
            Enumeration<String> e = bundle.getKeys();
            while (e.hasMoreElements()) {
                key = e.nextElement();
                desc = new FeatureDescriptor();
                desc.setDisplayName(key);
                desc.setExpert(false);
                desc.setHidden(false);
                desc.setName(key);
                desc.setPreferred(true);
                desc.setValue("type", String.class);
                desc.setValue("resolvableAtDesignTime", Boolean.TRUE);
                features.add(desc);
            }
            return features.iterator();
        }
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        if (base instanceof ResourceBundle) {
            return String.class;
        }
        return null;
    }
}

