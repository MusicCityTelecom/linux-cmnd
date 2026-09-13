/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.inject.Bindings;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.InstanceBinding;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.collection.KeyComparatorHashMap;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.message.internal.MessageBodyFactory;
import org.glassfish.jersey.spi.ContextResolvers;

public class ContextResolverFactory
implements ContextResolvers {
    private final Map<Type, Map<MediaType, ContextResolver>> resolver = new HashMap<Type, Map<MediaType, ContextResolver>>(3);
    private final Map<Type, ConcurrentHashMap<MediaType, ContextResolver>> cache = new HashMap<Type, ConcurrentHashMap<MediaType, ContextResolver>>(3);
    private static final NullContextResolverAdapter NULL_CONTEXT_RESOLVER = new NullContextResolverAdapter();

    private ContextResolverFactory() {
    }

    private void initialize(List<ContextResolver> contextResolvers) {
        HashMap rs = new HashMap();
        for (ContextResolver contextResolver : contextResolvers) {
            List<MediaType> ms = MediaTypes.createFrom(contextResolver.getClass().getAnnotation(Produces.class));
            Type type = this.getParameterizedType(contextResolver.getClass());
            HashMap<MediaType, ArrayList<ContextResolver>> mr = (HashMap<MediaType, ArrayList<ContextResolver>>)rs.get(type);
            if (mr == null) {
                mr = new HashMap<MediaType, ArrayList<ContextResolver>>();
                rs.put(type, mr);
            }
            for (MediaType m : ms) {
                ArrayList<ContextResolver> crl = (ArrayList<ContextResolver>)mr.get(m);
                if (crl == null) {
                    crl = new ArrayList<ContextResolver>();
                    mr.put(m, crl);
                }
                crl.add(contextResolver);
            }
        }
        for (Map.Entry entry : rs.entrySet()) {
            KeyComparatorHashMap<MediaType, ContextResolver> mr = new KeyComparatorHashMap<MediaType, ContextResolver>(4, MessageBodyFactory.MEDIA_TYPE_KEY_COMPARATOR);
            this.resolver.put((Type)entry.getKey(), (Map<MediaType, ContextResolver>)mr);
            this.cache.put((Type)entry.getKey(), new ConcurrentHashMap(4));
            for (Map.Entry f : ((Map)entry.getValue()).entrySet()) {
                mr.put((MediaType)f.getKey(), this.reduce((List)f.getValue()));
            }
        }
    }

    private Type getParameterizedType(Class<?> c) {
        ReflectionHelper.DeclaringClassInterfacePair p = ReflectionHelper.getClass(c, ContextResolver.class);
        Type[] as = ReflectionHelper.getParameterizedTypeArguments(p);
        return as != null ? as[0] : Object.class;
    }

    private ContextResolver reduce(List<ContextResolver> r) {
        if (r.size() == 1) {
            return r.iterator().next();
        }
        return new ContextResolverAdapter(r);
    }

    @Override
    public <T> ContextResolver<T> resolve(Type t, MediaType m) {
        ContextResolver cr;
        ConcurrentHashMap<MediaType, ContextResolver> crMapCache = this.cache.get(t);
        if (crMapCache == null) {
            return null;
        }
        if (m == null) {
            m = MediaType.WILDCARD_TYPE;
        }
        if ((cr = crMapCache.get(m)) == null) {
            ContextResolver _cr;
            Map<MediaType, ContextResolver> crMap = this.resolver.get(t);
            if (m.isWildcardType()) {
                cr = crMap.get(MediaType.WILDCARD_TYPE);
                if (cr == null) {
                    cr = NULL_CONTEXT_RESOLVER;
                }
            } else if (m.isWildcardSubtype()) {
                ContextResolver subTypeWildCard = crMap.get(m);
                ContextResolver wildCard = crMap.get(MediaType.WILDCARD_TYPE);
                cr = new ContextResolverAdapter(subTypeWildCard, wildCard).reduce();
            } else {
                ContextResolver type = crMap.get(m);
                ContextResolver subTypeWildCard = crMap.get(new MediaType(m.getType(), "*"));
                ContextResolver wildCard = crMap.get(MediaType.WILDCARD_TYPE);
                cr = new ContextResolverAdapter(type, subTypeWildCard, wildCard).reduce();
            }
            if ((_cr = crMapCache.putIfAbsent(m, cr)) != null) {
                cr = _cr;
            }
        }
        return cr != NULL_CONTEXT_RESOLVER ? cr : null;
    }

    private static final class ContextResolverAdapter
    implements ContextResolver {
        private final ContextResolver[] cra;

        ContextResolverAdapter(ContextResolver ... cra) {
            this(ContextResolverAdapter.removeNull(cra));
        }

        ContextResolverAdapter(List<ContextResolver> crl) {
            this.cra = crl.toArray(new ContextResolver[crl.size()]);
        }

        public Object getContext(Class objectType) {
            for (ContextResolver cr : this.cra) {
                Object c = cr.getContext(objectType);
                if (c == null) continue;
                return c;
            }
            return null;
        }

        ContextResolver reduce() {
            if (this.cra.length == 0) {
                return NULL_CONTEXT_RESOLVER;
            }
            if (this.cra.length == 1) {
                return this.cra[0];
            }
            return this;
        }

        private static List<ContextResolver> removeNull(ContextResolver ... cra) {
            ArrayList<ContextResolver> crl = new ArrayList<ContextResolver>(cra.length);
            for (ContextResolver cr : cra) {
                if (cr == null) continue;
                crl.add(cr);
            }
            return crl;
        }
    }

    private static final class NullContextResolverAdapter
    implements ContextResolver {
        private NullContextResolverAdapter() {
        }

        public Object getContext(Class type) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public static class ContextResolversConfigurator
    implements BootstrapConfigurator {
        private ContextResolverFactory contextResolverFactory;

        @Override
        public void init(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            this.contextResolverFactory = new ContextResolverFactory();
            InstanceBinding binding = (InstanceBinding)Bindings.service(this.contextResolverFactory).to(ContextResolvers.class);
            injectionManager.register(binding);
        }

        @Override
        public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            this.contextResolverFactory.initialize(injectionManager.getAllInstances((Type)((Object)ContextResolver.class)));
            bootstrapBag.setContextResolvers(this.contextResolverFactory);
        }
    }
}

