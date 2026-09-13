/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.Metadata;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ProxyForSameScope;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.UseProxy;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.internal.ActiveDescriptorBuilderImpl;
import org.glassfish.hk2.internal.ConstantActiveDescriptor;
import org.glassfish.hk2.internal.DescriptorBuilderImpl;
import org.glassfish.hk2.internal.IndexedFilterImpl;
import org.glassfish.hk2.internal.SpecificFilterImpl;
import org.glassfish.hk2.internal.StarFilter;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.ActiveDescriptorBuilder;
import org.glassfish.hk2.utilities.DescriptorBuilder;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.jvnet.hk2.annotations.Contract;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;

public class BuilderHelper {
    public static final String NAME_KEY = "name";
    public static final String QUALIFIER_KEY = "qualifier";
    public static final String TOKEN_SEPARATOR = ";";

    public static IndexedFilter createContractFilter(String contract) {
        return new IndexedFilterImpl(contract, null);
    }

    public static IndexedFilter createNameFilter(String name) {
        return new IndexedFilterImpl(null, name);
    }

    public static IndexedFilter createNameAndContractFilter(String contract, String name) {
        return new IndexedFilterImpl(contract, name);
    }

    public static IndexedFilter createTokenizedFilter(String tokenString) throws IllegalArgumentException {
        if (tokenString == null) {
            throw new IllegalArgumentException("null passed to createTokenizedFilter");
        }
        StringTokenizer st = new StringTokenizer(tokenString, TOKEN_SEPARATOR);
        String contract = null;
        String name = null;
        final LinkedHashSet<String> qualifiers = new LinkedHashSet<String>();
        boolean firstToken = true;
        if (tokenString.startsWith(TOKEN_SEPARATOR)) {
            firstToken = false;
        }
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (firstToken) {
                firstToken = false;
                if (token.length() <= 0) continue;
                contract = token;
                continue;
            }
            int index = token.indexOf(61);
            if (index < 0) {
                throw new IllegalArgumentException("No = character found in token " + token);
            }
            String leftHandSide = token.substring(0, index);
            String rightHandSide = token.substring(index + 1);
            if (rightHandSide.length() <= 0) {
                throw new IllegalArgumentException("No value found in token " + token);
            }
            if (NAME_KEY.equals(leftHandSide)) {
                name = rightHandSide;
                continue;
            }
            if (QUALIFIER_KEY.equals(leftHandSide)) {
                qualifiers.add(rightHandSide);
                continue;
            }
            throw new IllegalArgumentException("Unknown key: " + leftHandSide);
        }
        final String fContract = contract;
        final String fName = name;
        return new IndexedFilter(){

            @Override
            public boolean matches(Descriptor d) {
                if (qualifiers.isEmpty()) {
                    return true;
                }
                return d.getQualifiers().containsAll(qualifiers);
            }

            @Override
            public String getAdvertisedContract() {
                return fContract;
            }

            @Override
            public String getName() {
                return fName;
            }

            public String toString() {
                String cField = fContract == null ? "" : fContract;
                String nField = fName == null ? "" : ";name=" + fName;
                StringBuffer sb = new StringBuffer();
                for (String q : qualifiers) {
                    sb.append(";qualifier=" + q);
                }
                return "TokenizedFilter(" + cField + nField + sb.toString() + ")";
            }
        };
    }

    public static IndexedFilter createSpecificDescriptorFilter(Descriptor descriptor) {
        String contract = ServiceLocatorUtilities.getBestContract(descriptor);
        String name = descriptor.getName();
        if (descriptor.getServiceId() == null) {
            throw new IllegalArgumentException("The descriptor must have a specific service ID");
        }
        if (descriptor.getLocatorId() == null) {
            throw new IllegalArgumentException("The descriptor must have a specific locator ID");
        }
        return new SpecificFilterImpl(contract, name, descriptor.getServiceId(), descriptor.getLocatorId());
    }

    public static IndexedFilter createDescriptorFilter(Descriptor descriptorImpl, boolean deepCopy) {
        final Descriptor filterDescriptor = deepCopy ? new DescriptorImpl(descriptorImpl) : descriptorImpl;
        return new IndexedFilter(){

            @Override
            public boolean matches(Descriptor d) {
                return DescriptorImpl.descriptorEquals(filterDescriptor, d);
            }

            @Override
            public String getAdvertisedContract() {
                Set<String> contracts = filterDescriptor.getAdvertisedContracts();
                if (contracts == null || contracts.isEmpty()) {
                    return null;
                }
                return contracts.iterator().next();
            }

            @Override
            public String getName() {
                return filterDescriptor.getName();
            }
        };
    }

    public static IndexedFilter createDescriptorFilter(Descriptor descriptorImpl) {
        return BuilderHelper.createDescriptorFilter(descriptorImpl, true);
    }

    public static Filter allFilter() {
        return StarFilter.getDescriptorFilter();
    }

    public static DescriptorBuilder link(String implementationClass, boolean addToContracts) throws IllegalArgumentException {
        if (implementationClass == null) {
            throw new IllegalArgumentException();
        }
        return new DescriptorBuilderImpl(implementationClass, addToContracts);
    }

    public static DescriptorBuilder link(String implementationClass) throws IllegalArgumentException {
        return BuilderHelper.link(implementationClass, true);
    }

    public static DescriptorBuilder link(Class<?> implementationClass, boolean addToContracts) throws IllegalArgumentException {
        if (implementationClass == null) {
            throw new IllegalArgumentException();
        }
        DescriptorBuilder builder = BuilderHelper.link(implementationClass.getName(), addToContracts);
        return builder;
    }

    public static DescriptorBuilder link(Class<?> implementationClass) throws IllegalArgumentException {
        if (implementationClass == null) {
            throw new IllegalArgumentException();
        }
        boolean isFactory = Factory.class.isAssignableFrom(implementationClass);
        DescriptorBuilder db = BuilderHelper.link(implementationClass, !isFactory);
        return db;
    }

    public static ActiveDescriptorBuilder activeLink(Class<?> implementationClass) throws IllegalArgumentException {
        if (implementationClass == null) {
            throw new IllegalArgumentException();
        }
        return new ActiveDescriptorBuilderImpl(implementationClass);
    }

    public static <T> AbstractActiveDescriptor<T> createConstantDescriptor(T constant) {
        Set<Object> contracts;
        if (constant == null) {
            throw new IllegalArgumentException();
        }
        Class<?> cClass = constant.getClass();
        ContractsProvided provided = cClass.getAnnotation(ContractsProvided.class);
        if (provided != null) {
            contracts = new HashSet();
            for (Class<?> specified : provided.value()) {
                contracts.add(specified);
            }
        } else {
            contracts = ReflectionHelper.getAdvertisedTypesFromObject(constant, Contract.class);
        }
        return BuilderHelper.createConstantDescriptor(constant, ReflectionHelper.getName(constant.getClass()), contracts.toArray(new Type[contracts.size()]));
    }

    public static int getRank(Class<?> fromClass) {
        while (fromClass != null && !Object.class.equals(fromClass)) {
            Rank rank = fromClass.getAnnotation(Rank.class);
            if (rank != null) {
                return rank.value();
            }
            fromClass = fromClass.getSuperclass();
        }
        return 0;
    }

    /*
     * WARNING - void declaration
     */
    public static <T> AbstractActiveDescriptor<T> createConstantDescriptor(T constant, String name, Type ... contracts) {
        void var8_13;
        Set<Type> contractsAsSet;
        if (constant == null) {
            throw new IllegalArgumentException();
        }
        Annotation scope = ReflectionHelper.getScopeAnnotationFromObject(constant);
        Class scopeClass = scope == null ? PerLookup.class : scope.annotationType();
        Set<Annotation> qualifiers = ReflectionHelper.getQualifiersFromObject(constant);
        HashMap<String, List<String>> metadata = new HashMap<String, List<String>>();
        if (scope != null) {
            BuilderHelper.getMetadataValues(scope, metadata);
        }
        for (Annotation annotation : qualifiers) {
            BuilderHelper.getMetadataValues(annotation, metadata);
        }
        if (contracts.length <= 0) {
            contractsAsSet = ReflectionHelper.getAdvertisedTypesFromObject(constant, Contract.class);
        } else {
            contractsAsSet = new LinkedHashSet<Type>();
            for (Type cType : contracts) {
                contractsAsSet.add(cType);
            }
        }
        Object var8_11 = null;
        UseProxy up = constant.getClass().getAnnotation(UseProxy.class);
        if (up != null) {
            Boolean bl = up.value();
        }
        Boolean proxyForSameScope = null;
        ProxyForSameScope pfss = constant.getClass().getAnnotation(ProxyForSameScope.class);
        if (pfss != null) {
            proxyForSameScope = pfss.value();
        }
        DescriptorVisibility visibility = DescriptorVisibility.NORMAL;
        Visibility vi = constant.getClass().getAnnotation(Visibility.class);
        if (vi != null) {
            visibility = vi.value();
        }
        String classAnalysisName = null;
        Service service = constant.getClass().getAnnotation(Service.class);
        if (service != null) {
            classAnalysisName = service.analyzer();
        }
        int rank = BuilderHelper.getRank(constant.getClass());
        return new ConstantActiveDescriptor<T>(constant, contractsAsSet, scopeClass, name, qualifiers, visibility, (Boolean)var8_13, proxyForSameScope, classAnalysisName, metadata, rank);
    }

    public static DescriptorImpl createDescriptorFromClass(Class<?> clazz) {
        if (clazz == null) {
            return new DescriptorImpl();
        }
        Set<String> contracts = ReflectionHelper.getContractsFromClass(clazz, Contract.class);
        String name = ReflectionHelper.getName(clazz);
        String scope = ReflectionHelper.getScopeFromClass(clazz, ServiceLocatorUtilities.getPerLookupAnnotation()).annotationType().getName();
        Set<String> qualifiers = ReflectionHelper.getQualifiersFromClass(clazz);
        DescriptorType type = DescriptorType.CLASS;
        if (Factory.class.isAssignableFrom(clazz)) {
            type = DescriptorType.PROVIDE_METHOD;
        }
        Boolean proxy = null;
        UseProxy up = clazz.getAnnotation(UseProxy.class);
        if (up != null) {
            proxy = up.value();
        }
        Boolean proxyForSameScope = null;
        ProxyForSameScope pfss = clazz.getAnnotation(ProxyForSameScope.class);
        if (pfss != null) {
            proxyForSameScope = pfss.value();
        }
        DescriptorVisibility visibility = DescriptorVisibility.NORMAL;
        Visibility vi = clazz.getAnnotation(Visibility.class);
        if (vi != null) {
            visibility = vi.value();
        }
        int rank = BuilderHelper.getRank(clazz);
        return new DescriptorImpl(contracts, name, scope, clazz.getName(), new HashMap<String, List<String>>(), qualifiers, type, visibility, null, rank, proxy, proxyForSameScope, null, null, null);
    }

    public static DescriptorImpl deepCopyDescriptor(Descriptor copyMe) {
        return new DescriptorImpl(copyMe);
    }

    public static void getMetadataValues(Annotation annotation, Map<String, List<String>> metadata) {
        Method[] annotationMethods;
        if (annotation == null || metadata == null) {
            throw new IllegalArgumentException();
        }
        final Class<? extends Annotation> annotationClass = annotation.annotationType();
        for (Method annotationMethod : annotationMethods = AccessController.doPrivileged(new PrivilegedAction<Method[]>(){

            @Override
            public Method[] run() {
                return annotationClass.getDeclaredMethods();
            }
        })) {
            String addMeString;
            Object addMe;
            Metadata metadataAnno = annotationMethod.getAnnotation(Metadata.class);
            if (metadataAnno == null) continue;
            String key = metadataAnno.value();
            try {
                addMe = ReflectionHelper.invoke(annotation, annotationMethod, new Object[0], false);
            }
            catch (Throwable th) {
                throw new MultiException(th);
            }
            if (addMe == null) continue;
            if (addMe instanceof Class) {
                addMeString = ((Class)addMe).getName();
            } else if (addMe.getClass().isArray()) {
                int length = Array.getLength(addMe);
                for (int lcv = 0; lcv < length; ++lcv) {
                    Object iValue = Array.get(addMe, lcv);
                    if (iValue == null) continue;
                    if (iValue instanceof Class) {
                        String cName = ((Class)iValue).getName();
                        ReflectionHelper.addMetadata(metadata, key, cName);
                        continue;
                    }
                    ReflectionHelper.addMetadata(metadata, key, iValue.toString());
                }
                addMeString = null;
            } else {
                addMeString = addMe.toString();
            }
            if (addMeString == null) continue;
            ReflectionHelper.addMetadata(metadata, key, addMeString);
        }
    }

    public static <T> ServiceHandle<T> createConstantServiceHandle(final T obj) {
        return new ServiceHandle<T>(){
            private Object serviceData;

            @Override
            public T getService() {
                return obj;
            }

            @Override
            public ActiveDescriptor<T> getActiveDescriptor() {
                return null;
            }

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public void close() {
            }

            @Override
            public synchronized void setServiceData(Object serviceData) {
                this.serviceData = serviceData;
            }

            @Override
            public synchronized Object getServiceData() {
                return this.serviceData;
            }

            @Override
            public List<ServiceHandle<?>> getSubHandles() {
                return Collections.emptyList();
            }
        };
    }

    public static boolean filterMatches(Descriptor baseDescriptor, Filter filter) {
        if (baseDescriptor == null) {
            throw new IllegalArgumentException();
        }
        if (filter == null) {
            return true;
        }
        if (filter instanceof IndexedFilter) {
            IndexedFilter indexedFilter = (IndexedFilter)filter;
            String indexContract = indexedFilter.getAdvertisedContract();
            if (indexContract != null && !baseDescriptor.getAdvertisedContracts().contains(indexContract)) {
                return false;
            }
            String indexName = indexedFilter.getName();
            if (indexName != null) {
                if (baseDescriptor.getName() == null) {
                    return false;
                }
                if (!indexName.equals(baseDescriptor.getName())) {
                    return false;
                }
            }
        }
        return filter.matches(baseDescriptor);
    }
}

